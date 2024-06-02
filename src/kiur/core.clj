(ns kiur.core
  (:require
   [quil.core :as q]
   [quil.middleware :as qm]))

(def default-state {:player {:x 100 :y 100}})

(defn setup []
  default-state)

(defn draw-player [{:keys [x y]}]
  (q/with-translation [x y]
    (q/ellipse 0 0 10 10)))
(defn draw-state [state]
  (q/background 255)
  (draw-player (:player state)))

(defn update-state [state]
  (-> state))
(defn mouse-moved [state _]
  (-> state))
(defn mouse-clicked [state _]
  (-> state))

(defn key-pressed [state event]
  (case (:key event)
    :r default-state
    state))

(comment (q/defsketch example
           :size [500 1000]
           :setup setup
           :draw draw-state
           :update update-state
           :mouse-moved mouse-moved
           :mouse-clicked mouse-clicked
           :key-pressed key-pressed
           :features [:resizable]
           :middleware [qm/fun-mode]))
