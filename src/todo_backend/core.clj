(ns todo-backend.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.cors :refer [wrap-cors]]))

(defn app [req]
  {:status 200
   :body "ok"})

(def handler
  (wrap-cors app :access-control-allow-origin [#".*"]
                 :access-control-allow-methods [:get :put :post :delete]))

(defn -main [port]
  (jetty/run-jetty #'handler {:port (Integer. port)
                              :join? false}))
