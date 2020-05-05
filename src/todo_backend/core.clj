(ns todo-backend.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.cors :refer [wrap-cors]]
            [reitit.ring :as ring]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [todo-backend.store :as store]))

(defn ok [body]
  {:status 200
   :body body})

(defn append-todo-url [todo]
  (assoc todo :url (str "/todos/" (:id todo))))

(def app-routes
  (ring/ring-handler
   (ring/router
    [["/todos" {:get (fn [_] (-> (store/get-all-todos)
                                (#(map append-todo-url %))
                                ok))
                :post (fn [{:keys [body]}] (-> body
                                              store/create-todos
                                              append-todo-url
                                              ok))
                :delete (fn [_] (store/delete-all-todos)
                               {:status 204})
                :options (fn [_] {:status 200})}]]
    {:data {:middleware [wrap-keyword-params
                         wrap-json-response
                         [wrap-json-body {:keywords? true}]
                         [wrap-cors :access-control-allow-origin [#".*"]
                                    :access-control-allow-methods [:get :put :post :delete]]]}})

   (ring/create-default-handler
    {:not-found (constantly {:status 404 :body "Not found"})})))

(defn -main [port]
  (jetty/run-jetty #'app-routes {:port (Integer. port)
                                 :join? false}))

(comment
  (def server (jetty/run-jetty #'app-routes {:port 3000
                                             :join? false})))
