(ns kiur.draw
  (:require
   [quil.core :as q]))

(defn draw-player [{{:keys [x y]} :player
                    {{px :x py :y} :pointer} :controller}]
  (when (and px py) (q/line x y px py))
  (q/with-translation [x y]
    (q/ellipse 0 0 20 20)))
(defn draw-state [state]
  (q/background 255)
  (draw-player state))
