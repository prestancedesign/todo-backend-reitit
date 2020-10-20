(ns todo-backend.migration
  (:require [ragtime.jdbc :as jdbc]
            [ragtime.repl :as repl]))

(def config
  {:datastore (jdbc/sql-database (System/getenv "JDBC_DATABASE_URL"))
   :migrations (jdbc/load-resources "migrations")})

(defn migrate []
  (repl/migrate config))
