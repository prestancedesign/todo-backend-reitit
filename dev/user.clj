(ns user
  (:require [juxt.clip.repl :refer [start stop set-init! reset system]]
            [juxt.clip.core :as clip]))

(defn stop-jetty [jetty]
  (.stop jetty))

(def system-config
  {:components
   {:db
    {:start
     `(next.jdbc/get-datasource "jdbc:postgresql://localhost/todos?user=postgres&password=mypass")}

    :router
    {:start
     `(todo-backend.core/app-routes (clip/ref :db))}

    :http
    {:start
     `(ring.adapter.jetty/run-jetty
       (clip/ref :router)
       {:port 3005
        :join? false})
     :stop `user/stop-jetty}}})

(set-init! (fn [] system-config))

(comment
  (start)
  (reset)
  (stop)
  system)
