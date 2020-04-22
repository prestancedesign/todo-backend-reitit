(ns todo-backend.core
  (:require [ring.adapter.jetty :as jetty]))

(defn app [req]
  {:status 200
   :body "ok"})

(defn -main []
  (jetty/run-jetty #'app {:port 3000
                          :join? false}))
