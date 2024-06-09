(ns kiur.draw
  (:require
   [quil.core :as q]
   [kiur.polygon :as poly]))

(defn- draw-player [{{:keys [x y]} :player
                     {{px :x py :y} :pointer} :controller}]
  (q/with-translation [x y]
    (q/ellipse 0 0 20 20)))
(defn- draw-map [{:keys [map]}]
  (mapv (fn [building]
          (let [{:keys [points color]} building]
            (q/with-fill (apply q/color color)
              (q/begin-shape)
              (mapv (fn [p]
                      (apply q/vertex p))
                    points)
              (q/end-shape))))
        map))
(defn draw-state [state]
  (q/background 255)
  (draw-player state)
  (draw-map state))
