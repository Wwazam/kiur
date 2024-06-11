(ns kiur.geometry.vector
  (:require
   [clojure.math :as math]
   [clojure.spec.alpha :as s]
   [kiur.geometry.vector.spec :as vspec]))

(defn make-vector [point-1 point-2]
  (s/assert ::vspec/point point-1)
  (s/assert ::vspec/point point-2)
  (s/assert (fn points-have-same-dimension [points] (reduce = points)) (map count [point-1 point-2]))
  (mapv - point-2 point-1))

(defn magnitude [vec]
  (->> vec
       (map #(math/pow % 2))
       (reduce +)
       math/sqrt))

(defn normalize [vec]
  (let [m (magnitude vec)]
    (cond->> vec
      (not (zero? m)) (mapv #(/ % m)))))

(defn normal [[x y :as vect]]
  (s/assert ::vspec/vector vect)
  (normalize [y (- x)]))

(defn dot-product [a b]
  (->> [a b]
       (apply map *)
       (reduce +)))

