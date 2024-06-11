(ns kiur.geometry.collision
  (:require
   [kiur.geometry.vector :as vector]))

(defn project [axis points]
  (reduce (fn [[mn mx] point]
            (let [dot (vector/dot-product axis point)]
              [(min mn dot) (max mx dot)]))
          [##Inf ##-Inf]
          points))

(defn overlap [[min1 max1] [min2 max2]]
  [(- min1 max2) (- min2 max1)])

(defn add-offset-to-1st [offset [a b]]
  [(update a 1 + offset) b])

(let [triangle-1 [[50 0] [20 100] [150 150]]
      shape-1 [[50 80]  [20 150] [180 250] [180 210]]
      triangle-2 [[250 0] [220 100] [350 150]]
      shape-2 [[250 115] [220 150] [380 250] [380 210]]
      a triangle-2
      b shape-2]
  (->> (concat a b)
       (mapv (fn overlap-on-projection? [edge]
               (->> [a b]
                    (mapv #(project edge %))
                    #_(add-offset-to-1st)
                    (apply overlap))))))
