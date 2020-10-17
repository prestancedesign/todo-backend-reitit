(ns todo-backend.core
  (:require [reitit.coercion.schema :as rcs]
            [reitit.ring :as ring]
            [reitit.ring.coercion :as rrc]
            [reitit.ring.middleware.muuntaja :as rrmm]
            [muuntaja.core :as m]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.cors :refer [wrap-cors]]
            [schema.core :as s]
            [todo-backend.store :as store]
            [todo-backend.migration :refer [migrate]]))

(defn ok [body]
  {:status 200
   :body body})

(defn append-todo-url [todo request]
  (let [host (-> request :headers (get "host" "localhost"))
        scheme (name (:scheme request))
        id (:id todo)]
    (merge todo {:url (str scheme "://" host "/todos/" id)})))

(def app-routes
  (ring/ring-handler
   (ring/router
    [["/todos" {:get (fn [req] (ok (map #(append-todo-url % req) (store/get-all-todos))))
                :post (fn [{:keys [body-params] :as req}] (-> body-params
                                                             store/create-todos
                                                             (append-todo-url req)
                                                             ok))
                :delete (fn [_] (store/delete-all-todos)
                          {:status 204})
                :options (fn [_] {:status 200})}]
     ["/todos/:id" {:parameters {:path {:id s/Int}}
                    :get (fn [{:keys [parameters] :as req}] (-> (store/get-todo (get-in parameters [:path :id]))
                                                               (append-todo-url req)
                                                               ok))
                    :patch (fn [{:keys [parameters body-params] :as req}] (-> body-params
                                                                             (store/update-todo (get-in parameters [:path :id]))
                                                                             (append-todo-url req)
                                                                             ok))
                    :delete (fn [{:keys [parameters]}] (store/delete-todos (get-in parameters [:path :id]))
                              {:status 204})}]]
    {:data {:muuntaja m/instance
            :coercion rcs/coercion
            :middleware [rrmm/format-middleware
                         rrc/coerce-exceptions-middleware
                         rrc/coerce-response-middleware
                         rrc/coerce-request-middleware
                         [wrap-cors :access-control-allow-origin [#".*"]
                          :access-control-allow-methods [:get :put :post :patch :delete]]]}})

   (ring/create-default-handler
    {:not-found (constantly {:status 404 :body "Not found"})})))

(defn -main [port]
  (migrate)
  (jetty/run-jetty #'app-routes {:port (Integer. port)
                                 :join? false}))

(comment
  (def server (jetty/run-jetty #'app-routes {:port 3000
                                             :join? false})))
