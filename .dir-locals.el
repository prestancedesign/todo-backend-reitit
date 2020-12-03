((clojure-mode
  (cider-ns-refresh-before-fn . "juxt.clip.repl/stop")
  (cider-ns-refresh-after-fn . "juxt.clip.repl/start")
  (cider-clojure-cli-global-options . "-M:dev")))
