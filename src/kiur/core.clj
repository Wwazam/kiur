(ns kiur.core
  (:require
   [kiur.app.core :as app]))

(defn -main [& _]
  (app/start-app))

(comment (-main))
