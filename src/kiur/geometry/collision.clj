(ns kiur.geometry.collision
  (:require
   [kiur.geometry.polygon :as p]
   [kiur.geometry.vector :as vector]))

(defn project [axis points]
  (reduce (fn [[mn mx] point]
            (let [dot (vector/dot-product axis point)]
              [(min mn dot) (max mx dot)]))
          [##Inf ##-Inf]
          points))

(defn overlap? [[min1 max1 :as a] [min2 max2 :as b]]
  (or (<= min1 min2 max1)
      (<= min2 min1 max2)))

(defn collision? [a b]
  (->> [a b]
       (mapcat p/poly->edges)
       (map #(->> (apply vector/make-vector %)
                  vector/normal))
       (reduce (fn [_ edge]
                 (if (->> [a b]
                          (map #(project edge %))
                          (apply overlap?))
                   true
                   (reduced false)))
               [##Inf ##Inf])))
