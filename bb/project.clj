(ns project
  (:require
   [git-hook]))

^{:clj-kondo/ignore [:redefined-var]}
(defn sync [& _]
  (git-hook/hooks "sync"))
