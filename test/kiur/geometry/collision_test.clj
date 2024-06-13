(ns kiur.geometry.collision-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [kiur.geometry.collision :as subject]
   [clojure.math :as math]))

(deftest overlap?-test
  (testing "Some false cases"
    (is (= false
           (subject/overlap? [0 5] [6 8])))
    (is (= false
           (subject/overlap? [-23 -20] [-19 -18]))
        "With negative numbers")
    (is (= false
           (subject/overlap? [5 0] [6 8]))
        "With shuffled coords")
    (testing "When the points are real close"
      (is (= false
             (subject/overlap? [0 3.14159265358979] [math/PI 6])))))
  (testing "Some true cases"
    (is (= true
           (subject/overlap? [5 7] [6 8])))

    (testing "When the points are real close"
      (is (= true
             (subject/overlap? [0 math/PI] [3.14159265358979 6]))))
    (testing  "Overlap when points equals"
      (is (= true
             (subject/overlap? [5 6] [6 8])))
      (is (= true
             (subject/overlap? [5 6] [5 6])))
      (is (= true
             (subject/overlap? [5 6] [2 5]))))))

(deftest collision?-test
  (let [shape-1 [[13 10] [13 3] [6 3] [6 10]]
        shape-2 [[14 18] [15 11] [10 13]]
        shape-3 [[11 10] [11 3] [4 3] [4 10]]
        shape-4 [[13 13] [8 9] [7 15]]]
    (is (= false
           (subject/collision? shape-1 shape-2)))
    (is (= true
           (subject/collision? shape-3 shape-4)))))
