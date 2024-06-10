(ns kiur.geometry.vector
  (:require
   [clojure.spec.alpha :as s]
   [kiur.geometry.vector.spec :as vspec]
   [kiur.geometry.pythagore :as ptgr]))

(defn make-vector [point-1 point-2]
  (s/assert ::vspec/point point-1)
  (s/assert ::vspec/point point-2)
  (s/assert (fn points-have-same-dimension [points] (reduce = points)) (map count [point-1 point-2]))
  (mapv - point-2 point-1))

(defn perpendicular [[x y :as vect]]
  (s/assert ::vspec/vector vect)
  [y (- x)])

(defn normalize [vec]
  (let [h (apply ptgr/hypotenuse vec)]
    (mapv #(/ % h) vec)))

(defn dot-product [a b]
  (->> [a b]
       (s/assert (s/coll-of (s/spec ::vspec/vector)))
       (apply map *)
       (reduce +)))

