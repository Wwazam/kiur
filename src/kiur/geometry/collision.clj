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

(defn overlap [[min1 max1 :as a] [min2 max2 :as b]]
  (or (<= min1 min2 max1)
      (<= min2 min1 max2)))

(defn add-offset-to-1st [offset [a b]]
  [(update a 1 + offset) b])

(defn collision? [a b]
  (->> [a b]
       (mapcat p/poly->edges)
       (mapv #(apply vector/make-vector %))
       (mapv (fn overlap-on-projection? [edge]
               (->> [a b]
                    (mapv #(project (vector/normal edge) %))
                    (apply overlap))))
       (not-any? not)))
