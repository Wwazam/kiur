(ns kiur.model.player)

(defn update-target [player target]
  (update player :target assoc
          :x (:x target)
          :y (:y target)))
(defn update-pos [player target]
  (assoc player
         :x (:x target)
         :y (:y target)))
