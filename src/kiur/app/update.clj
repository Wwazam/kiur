(ns kiur.app.update
  (:require
   [kiur.geometry.vector :as v]))

(defn move-player [{{:keys [target]} :player
                    :keys [player]
                    :as state}]
  (if target
    (let [deltas (->> [target player]
                      (mapv (juxt :x :y))
                      (apply mapv -))
          magnitude (v/magnitude deltas)
          [displ-x displ-y] (if (> 1 magnitude)
                              [0 0]
                              (mapv #(/ (* (min (:speed player) magnitude) %) magnitude) deltas))]
      (-> state
          (update-in [:player :x] + displ-x)
          (update-in [:player :y] + displ-y)))
    state))

(defn update-state [state]
  (-> state
      #_move-player))
