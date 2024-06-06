(ns kiur.core
  (:require
   [kiur.app :as app]))

(defn -main [& _]
  (app/start-app))

(comment (-main))
