(ns todo-backend.store
  (:require [clojure.set :refer [rename-keys]]
            [next.jdbc :as jdbc]
            [next.jdbc.result-set :as rs]
            [next.jdbc.sql :as sql]))

(defn as-row [row]
  (rename-keys row {:order :position}))

(defn as-todo [row]
  (rename-keys row {:position :order}))

(defn create-todos [todo db]
  (as-todo (sql/insert! db :todos (as-row todo)
                        {:builder-fn rs/as-unqualified-lower-maps})))

(defn get-todo [id db]
  (as-todo (sql/get-by-id db :todos id
                          {:builder-fn rs/as-unqualified-lower-maps})))

(defn update-todo [body id db]
  (sql/update! db :todos (as-row body) {:id id})
  (get-todo id db))

(defn delete-todos [id db]
  (sql/delete! db :todos {:id id}))

(defn get-all-todos [db]
  (jdbc/execute! db ["SELECT * FROM todos"]
                 {:builder-fn rs/as-unqualified-lower-maps}))

(defn delete-all-todos [db]
  (sql/delete! db :todos [true]))
