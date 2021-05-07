(ns todo-backend.handlers
  (:require [ring.util.response :as rr]
            [todo-backend.store :as store]))

(defn append-todo-url [todo request]
  (let [host (-> request :headers (get "host" "localhost"))
        scheme (name (:scheme request))
        id (:id todo)]
    (merge todo {:url (str scheme "://" host "/todos/" id)})))

(defn list-all-todos [request]
  (-> #(append-todo-url % request)
      (map (store/get-all-todos))
      rr/response))

(defn create-todo [{:keys [body-params] :as request}]
  (-> (store/create-todos body-params)
      (append-todo-url request)
      rr/response))

(defn delete-all-todos [_]
  (store/delete-all-todos)
  (rr/status 204))

(defn retrieve-todo [{:keys [parameters] :as request}]
  (let [id (-> parameters :path :id)]
    (-> (store/get-todo id)
        (append-todo-url request)
        rr/response)))
