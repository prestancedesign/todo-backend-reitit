(ns todo-backend.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.cors :refer [wrap-cors]]
            [reitit.ring :as ring]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.params :refer [wrap-params]]))

(defn ok [body]
  {:status 200
   :body body})

(def app-routes
  (ring/ring-handler
   (ring/router
    [["/todos" {:get {:handler (fn [req] (ok "OK GET"))}
                :post {:handler (fn [req] (ok (:params req)))}}]]
    {:data {:middleware [wrap-json-response
                         wrap-params
                         wrap-keyword-params
                         [wrap-cors :access-control-allow-origin [#".*"]
                                    :access-control-allow-methods [:get :put :post :delete]]]}})
   (ring/create-default-handler
    {:not-found (constantly {:status 404 :body "Not found"})})))

(defn -main [port]
  (jetty/run-jetty #'app-routes {:port (Integer. port)
                                 :join? false}))

(comment
  (def server (jetty/run-jetty #'app-routes {:port 3000
                                             :join? false})))
