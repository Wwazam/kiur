(ns kiur.core
  (:require
   [kiur.draw :as draw]
   [kiur.event :as event]
   [kiur.state :as state]
   [quil.core :as q]
   [quil.middleware :as qm]))

(defn setup [frame-rate]
  (q/frame-rate frame-rate)
  (state/default-state))


(defn update-state [state]
  (-> state))

(defn make-handler [type]
  (fn [st ev]
    (let [ev (assoc (if (map? ev) ev {:value ev}) :type type)]
      (event/event st ev))))

(defmacro defapp [name config]
  (let [init-config (->> config
                         (mapcat (fn [[k :as v]] (if (#{:size :setup :draw :update :middleware :features} k) v [])))
                         vec)
        quil-config (->> [:mouse-moved :mouse-wheel :mouse-pressed :mouse-released :key-pressed :key-released]
                         (mapcat #(vector % `(make-handler ~%)))
                         (concat init-config))]
    ^{:clj-kondo/ignore [:inline-def]}
    `(q/defsketch ~name ~@quil-config)))

(defn start-app [{:keys [frame-rate]}]
  (defapp kiur
    {:size [650 400]
     :setup #(setup frame-rate)
     :update update-state
     :draw draw/draw-state
     :handler make-handler
     :features [:resizable]
     :middleware [qm/fun-mode]}))

(comment
  (let [config {:frame-rate 60}]
    (start-app config)))
