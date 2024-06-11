(ns kiur.geometry.collision-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [kiur.geometry.collision :as subject]
   [clojure.math :as math]))

(deftest overlap-test
  (testing "Some false cases"
    (is (= false
           (subject/overlap [0 5] [6 8])))
    (is (= false
           (subject/overlap [-23 -20] [-19 -18]))
        "With negative numbers")
    (is (= false
           (subject/overlap [5 0] [6 8]))
        "With shuffled coords")
    (testing "When the points are real close"
      (is (= false
             (subject/overlap [0 3.14159265358979] [math/PI 6])))))
  (testing "Some true cases"
    (is (= true
           (subject/overlap [5 7] [6 8])))

    (testing "When the points are real close"
      (is (= true
             (subject/overlap [0 math/PI] [3.14159265358979 6]))))
    (testing  "Overlap when points equals"
      (is (= true
             (subject/overlap [5 6] [6 8])))
      (is (= true
             (subject/overlap [5 6] [5 6])))
      (is (= true
             (subject/overlap [5 6] [2 5]))))))
