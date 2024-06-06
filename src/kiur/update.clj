(ns kiur.update
  (:require
   [clojure.math :as math]))

(defonce *st (atom nil))
(defn move-player [{{:keys [pointer]} :controller
                    :keys [player]
                    :as state}]
  (if pointer
    (let [max-speed 1
          deltas (->> [pointer player]
                      (mapv (juxt :x :y))
                      (apply mapv -))
          hypot (->> deltas
                     (map #(math/pow % 2))
                     (reduce +)
                     math/sqrt)
          [displ-x displ-y] (mapv #(/ (* max-speed %) hypot) deltas)]
      (-> state
          (update-in [:player :x] + displ-x)
          (update-in [:player :y] + displ-y)))
    state))

(defn update-state [state]
  (reset! *st state)
  (-> state
      move-player))


