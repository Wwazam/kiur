(ns kiur.app.draw
  (:require
   [quil.core :as q]))

(defn- draw-player [{{:keys [x y]} :player}]
  (q/with-translation [x y]
    (q/ellipse 0 0 20 20)))
(defn draw-shape [building]
  (let [{:keys [points color]} building]
    (q/with-fill (apply q/color color)
      (q/begin-shape)
      (mapv (fn [p]
              (apply q/vertex p))
            points)
      (q/end-shape))))

(defn- draw-map [{:keys [map]}]
  (mapv draw-shape map))
(defn draw-state [state]
  (q/background 255)
  (draw-player state)
  (draw-map state))
