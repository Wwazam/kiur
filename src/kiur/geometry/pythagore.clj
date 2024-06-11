(ns kiur.geometry.pythagore
  (:require
   [clojure.math :as math]))

(defn hypotenuse [& lengths]
  (->> lengths
       (map #(math/pow % 2))
       (reduce +)
       math/sqrt))
