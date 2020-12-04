(ns user
  (:require [juxt.clip.repl :refer [start stop set-init! reset system]]
            [aero.core :refer [read-config]]
            [clojure.java.io :as io]))

(set-init! (fn [] (read-config (io/resource "config.edn"))))

(comment
  (start)
  (reset)
  (stop)
  system)
