(ns kiur.app.draw
  (:require
   [quil.core :as q]))

(defn- draw-player [{{:keys [x y r]} :player}]
  (q/with-fill [20 220 20]
    (q/with-translation [x y]
      (q/begin-shape)
      (mapv #(->> %
                  (mapv (partial * r))
                  (apply q/vertex))
            [[-1 0] [-2/3 -2/3]
             [0 -1] [2/3 -2/3]
             [1 0] [2/3 2/3]
             [0 1] [-2/3 2/3]
             [-1 0]])
      (q/end-shape))))
(defn draw-shape [building]
  (let [{:keys [points color]} building]
    (q/with-fill (apply q/color color)
      (q/begin-shape)
      (mapv (fn [p]
              (apply q/vertex p))
            points)
      (q/end-shape))))

(defn draw-path [{{:keys [path]} :player}]
  (when path
    (q/with-fill (q/color 150 150 0)
      (doseq [[a b] (partition 2 1 path)]
        (q/line a b)))))

(defn- draw-map [{:keys [map]}]
  (mapv draw-shape map))
(defn draw-state [state]
  (q/background 255)
  (draw-player state)
  (draw-map state)
  (draw-path state))
