(ns user
  (:require [ragtime.jdbc :as jdbc]
            [ragtime.repl :as repl]))

(def config
  {:datastore (jdbc/sql-database "jdbc:postgresql:todos?user=postgres&password=mypass")
   :migrations (jdbc/load-resources "migrations")})

(defn migrate []
  (repl/migrate config))

(comment
 (repl/migrate config)
 (repl/rollback config))
