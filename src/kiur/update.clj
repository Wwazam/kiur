(ns kiur.update
  (:require
   [clojure.math :as math]))

(defn move-player [{{:keys [target]} :player
                    :keys [player]
                    :as state}]
  (if target
    (let [deltas (->> [target player]
                      (mapv (juxt :x :y))
                      (apply mapv -))
          hypot (->> deltas
                     (map #(math/pow % 2))
                     (reduce +)
                     math/sqrt)
          [displ-x displ-y] (if (> 1 hypot)
                              [0 0]
                              (mapv #(/ (* (min (:speed player) hypot) %) hypot) deltas))]
      (-> state
          (update-in [:player :x] + displ-x)
          (update-in [:player :y] + displ-y)))
    state))

(defn update-state [state]
  (-> state
      move-player))


