(ns kiur.core
  (:require
   [kiur.event :as event]
   [kiur.state :as state]
   [quil.core :as q]
   [quil.middleware :as qm]))

(defn setup [frame-rate]
  (q/frame-rate frame-rate)
  (state/default-state))

(defn draw-player [{{:keys [x y]} :player
                    {{px :x py :y} :pointer} :controller}]
  (when (and px py) (q/line x y px py))
  (q/with-translation [x y]
    (q/ellipse 0 0 20 20)))
(defn draw-state [state]
  (q/background 255)
  (draw-player state))

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
     :draw draw-state
     :update update-state
     :handler make-handler
     :features [:resizable]
     :middleware [qm/fun-mode]}))

(comment
  (let [config {:frame-rate 60}]
    (start-app config)))
