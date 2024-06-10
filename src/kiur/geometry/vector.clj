(ns kiur.geometry.vector)

(defn make-vector [a b]
  (mapv - b a))

(defn perpendicular [[x y]]
  [y (- x)])

(defn dot-product [a b]
  (->> [a b]
       (apply map *)
       (reduce +)))
