(ns user
  (:require [juxt.clip.repl :refer [start stop set-init! reset system]]
            [todo-backend.system :refer [system-config]]))

(set-init! (fn [] system-config))

(comment
  (start)
  (reset)
  (stop)
  system)
