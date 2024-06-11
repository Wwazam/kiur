(ns kiur.geometry.pythagore-test
  (:require
   [clojure.test :refer [deftest is]]
   [kiur.geometry.pythagore :as subject]))

(deftest hypotenuse-test
  (is (= 5.0
         (subject/hypotenuse 3 4)))
  (is (= 5.477225575051661
         (subject/hypotenuse 1 2 3 4))))
