(ns kiur.geometry.vector-test
  (:require
   [clojure.test :refer [deftest is testing]]
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
              subject/magnitude
              dec)
         (/ 1 10000)))
  (testing "With a magnitude of zero"
    (is (= [0 0]
           (subject/normalize  [0 0])))))

(deftest make-vector-test
  (is (= [1 2] (subject/make-vector [0 0] [1 2])))
  (is (= [-1 -2] (subject/make-vector [1 2] [0 0])))
  (is (= [15 47] (subject/make-vector [-5 3] [10 50]))))

(defn- round [val prec]
  (let [mult (math/pow 10 prec)]
    (double (/ (math/round (* val mult))
               mult))))

(deftest dot-product-test
  (is (= 0 (subject/dot-product [0 0] [1 1])))
  (is (= 0 (subject/dot-product [0 1] [1 0])))
  (let [a [1 3 -5]
        b [4 -2 -1]]
    (is (= 3 (subject/dot-product a b)))
    (is (= 35 (subject/dot-product a a)))
    (is (= 21 (subject/dot-product b b))))
  (testing "Codirectional vectors"
    (let [vec1 [1 1]
          vec2 [5 5]]
      (is (= (round (reduce * (map subject/magnitude [vec1 vec2])) 2)
             (double (subject/dot-product vec1 vec2)))))))

(deftest magnitude-test
  (is (= 1.0 (subject/magnitude [0 1])))
  (is (= (math/sqrt 2) (subject/magnitude [1 1])))
  (is (= 3.0 (subject/magnitude [1 1 1 1 1 1 1 1 1]))))

(deftest normal-test
  (is (= [0.0 -1.0]
         (subject/normal [1 0])))
  (is (= [0.7808688094430304 -0.6246950475544243]
         (subject/normal [4 5])))
  (is (= [-0.6246950475544243 -0.7808688094430304]
         (subject/normal [5 -4]))))
