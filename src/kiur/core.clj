(ns kiur.core
  (:require
   [kiur.app.core :as app]))

(defonce *app (atom []))
(defn running-app [] (first @*app))
(defn -main [& _]
  (swap! *app conj (app/start-app)))

(comment (-main))
