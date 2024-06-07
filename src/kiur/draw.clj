(ns kiur.draw
  (:require
   [quil.core :as q]))

(defn- draw-player [{{:keys [x y]} :player
                     {{px :x py :y} :pointer} :controller}]
  (q/with-translation [x y]
    (q/ellipse 0 0 20 20)))
(defn- draw-map [{:keys [map]}]
  (mapv (fn [building]
          (let [{:keys [x y w h color]} building]
            (q/with-fill (apply q/color color)
              (q/rect x y w h))))
        map))
(defn draw-state [state]
  (q/background 255)
  (draw-player state)
  (draw-map state))
