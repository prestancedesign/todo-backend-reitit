(ns todo-backend.core
  (:require [ring.adapter.jetty :as jetty]))

(defn app [req]
  {:status 200
   :body "ok"})

(defn -main [port]
  (jetty/run-jetty #'app {:port (Integer. port)
                          :join? false}))
