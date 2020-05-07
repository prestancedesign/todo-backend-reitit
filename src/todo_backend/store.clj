(ns todo-backend.store
  (:require [clojure.set :refer [rename-keys]]
            [next.jdbc :as jdbc]
            [next.jdbc.result-set :as rs]
            [next.jdbc.sql :as sql]))

(def db (System/getenv "JDBC_DATABASE_URL"))

(def ds (jdbc/get-datasource db))

(defn as-row [row]
  (rename-keys row {:order :position}))

(defn as-todo [row]
  (rename-keys row {:position :order}))

(defn create-todos [todo]
  (as-todo (sql/insert! ds :todos (as-row todo)
                        {:builder-fn rs/as-unqualified-lower-maps})))

(defn get-todo [id]
  (as-todo (sql/get-by-id ds :todos id
                          {:builder-fn rs/as-unqualified-lower-maps})))

(defn update-todo [id body]
  (sql/update! ds :todos (as-row body) {:id id})
  (get-todo id))

(defn delete-todos [id]
  (sql/delete! ds :todos {:id id}))

(defn get-all-todos []
  (jdbc/execute! ds ["SELECT * FROM todos;"]
                 {:builder-fn rs/as-unqualified-lower-maps}))

(defn delete-all-todos []
  (sql/delete! ds :todos [true]))
