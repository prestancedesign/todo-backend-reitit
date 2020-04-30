(ns todo-backend.store
  (:require [next.jdbc :as jdbc]
            [next.jdbc.result-set :as rs]
            [next.jdbc.sql :as sql]))

(def db {:dbtype "postgres" :dbname "todos" :user "postgres" :password "mypass"})

(def ds (jdbc/get-datasource db))

(defn create-todos [todo]
  (sql/insert! ds :todos todo {:builder-fn rs/as-unqualified-lower-maps}))

(defn delete-todos [id]
  (sql/delete! ds :todos {:id id}))

(defn get-all-todos []
  (jdbc/execute! ds ["
SELECT title, \"order\", completed FROM todos;
"] {:builder-fn rs/as-unqualified-lower-maps}))

(defn delete-all-todos []
  (sql/delete! ds :todos [true]))
