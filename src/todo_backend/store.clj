(ns todo-backend.store
  (:require [next.jdbc :as jdbc]
            [next.jdbc.result-set :as rs]
            [next.jdbc.sql :as sql]))

(def db {:dbtype "postgres" :dbname "todos" :user "postgres" :password "mypass"})

(def ds (jdbc/get-datasource db))

(defn as-row [row]
  (dissoc (assoc row :position (:order row)) :order))

(defn create-todos [todo]
  (sql/insert! ds :todos (as-row todo)
               {:builder-fn rs/as-unqualified-lower-maps}))

(defn get-todo [id]
  (sql/get-by-id ds :todos (Integer. id)
                 {:builder-fn rs/as-unqualified-lower-maps}))

(defn update-todo [id title]
  (sql/update! ds :todos title {:id (Integer. id)})
  (get-todo id))

(defn delete-todos [id]
  (sql/delete! ds :todos {:id (Integer. id)}))

(defn get-all-todos []
  (jdbc/execute! ds ["SELECT * FROM todos;"]
                 {:builder-fn rs/as-unqualified-lower-maps}))

(defn delete-all-todos []
  (sql/delete! ds :todos [true]))
