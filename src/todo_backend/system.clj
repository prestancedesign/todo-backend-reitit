(ns todo-backend.system
  (:require [aero.core :refer [read-config]]
            [clojure.java.io :as io]))

(def system-config (read-config (io/resource "config.edn")))
