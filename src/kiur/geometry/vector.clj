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

(defn normal [[x y :as vect]]
  (s/assert ::vspec/vector vect)
  [y (- x)])

(defn magnitude [vec]
  (apply ptgr/hypotenuse vec))

(defn normalize [vec]
  (let [m (magnitude vec)]
    (cond->> vec
      (not (zero? m)) (mapv #(/ % m)))))

(defn dot-product [a b]
  (->> [a b]
       (apply map *)
       (reduce +)))

