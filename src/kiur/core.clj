(ns kiur.core
  (:require
   [kiur.event :as event]
   [kiur.state :as state]
   [quil.core :as q]
   [quil.middleware :as qm]))

(defn setup []
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

(comment (q/defsketch example
           :size [650 400]
           :setup setup
           :draw draw-state
           :update update-state
           :mouse-moved (make-handler :mouse-moved)
           :mouse-wheel (make-handler :mouse-wheel)
           :mouse-pressed (make-handler :mouse-pressed)
           :mouse-released (make-handler :mouse-released)
           :key-pressed (make-handler :key-pressed)
           :key-released (make-handler :key-released)
           :features [:resizable]
           :middleware [qm/fun-mode]))
