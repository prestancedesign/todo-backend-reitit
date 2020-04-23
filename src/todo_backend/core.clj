(ns todo-backend.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.cors :refer [wrap-cors]]
            [reitit.ring :as ring]
            [ring.middleware.json :refer [wrap-json-response]]))

(defn ok [body]
  {:status 200
   :body {:name "Hello"}})

(def app-routes
  (ring/ring-handler
   (ring/router
    [["/todos" {:get {:handler (fn [req] (ok "OK GET"))}
                :post {:handler (fn [req] (ok "OK POST"))}}]])
   (ring/create-default-handler
    {:not-found (constantly {:status 404 :body "Not found"})})))

(def handler
  (-> app-routes
      wrap-json-response
      (wrap-cors :access-control-allow-origin [#".*"]
                 :access-control-allow-methods [:get :put :post :delete])))

(defn -main [port]
  (jetty/run-jetty #'handler {:port (Integer. port)
                              :join? false}))

(comment
  (def server (jetty/run-jetty #'handler {:port 3000
                                          :join? false})))
