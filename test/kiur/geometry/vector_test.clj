(ns kiur.geometry.vector-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [kiur.geometry.pythagore :as ptgr]
   [kiur.geometry.vector :as subject]
   [clojure.math :as math]))

(deftest normalize-test
  (is (= [0.0 1.0]
         (subject/normalize [0 5])))
  (is (= [1.0 0.0]
         (subject/normalize [532 0])))
  (is (= [0.25766265056033233 0.9662349396012463]
         (subject/normalize [12 45])))
  (is (= [-0.9662349396012463 0.25766265056033233]
         (subject/normalize [-45 12])))
  (is (< (/ -1 10000)
         (->> [(rand-int 10000) (rand-int 10000)]
              subject/normalize
              (apply ptgr/hypotenuse)
              dec)
         (/ 1 10000)))
  (testing "With a magnitude of zero"
    (is (= [0 0]
           (subject/normalize  [0 0])))))

(deftest make-vector-test
  (is (= [1 2] (subject/make-vector [0 0] [1 2])))
  (is (= [-1 -2] (subject/make-vector [1 2] [0 0])))
  (is (= [15 47] (subject/make-vector [-5 3] [10 50]))))

(deftest dot-product-test
  (is (= 0 (subject/dot-product [0 0] [1 1])))
  (is (= 0 (subject/dot-product [0 1] [1 0])))
  #_(let [vec1 [1 1]
          vec2 [5 5]]
      (is (= (subject/dot-product vec1 vec2)))
      (ptgr/hypotenuse vec1)))

(deftest magnitude-test
  (is (= 1.0 (subject/magnitude [0 1])))
  (is (= (math/sqrt 2) (subject/magnitude [1 1])))
  (is (= 3.0 (subject/magnitude [1 1 1 1 1 1 1 1 1]))))

(deftest normal-test
  (is (= 1 1)))
