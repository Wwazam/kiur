(ns kiur.geometry.pythagore
  (:require
   [clojure.math :as math]))

(defn hypotenuse [& points]
  (->> points
       (map #(math/pow % 2))
       (reduce +)
       math/sqrt))
