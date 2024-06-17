(ns kiur.geometry.polygon-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [kiur.geometry.polygon :as subject]))

(deftest rect-test
  (is (= [[0 0] [1 0] [1 1] [0 1]]
         (subject/rect 0 0 1 1)))
  (is (= [[0 5] [7 5] [7 18] [0 18]]
         (subject/rect 0 5 7 13))))

(deftest center-test
  (is (= [2.5 5.0]
         (subject/center (subject/rect 0 0 5 10))))
  (is (= [2.5 9.0]
         (subject/center (subject/rect 0 4 5 10)))))

(deftest bounding-box-test
  (is (= {:min-x 0 :min-y 0 :max-x 5 :max-y 5}
         (subject/bounding-box (subject/rect 0 0 5 5))))
  (is (= {:min-x 0 :min-y 0 :max-x 9 :max-y 9}
         (subject/bounding-box [[0 0] [1 5] [6 8] [3 2] [6 9] [9 0] [8 5]]))))

(deftest inter-y-test
  [[17 492] [417 493] [490 468] [425 150] [482 38] [120 15] [16 49]]
  (is (= true (subject/inter-y [1 1] [[2 0] [2 4]])))
  (is (= true (subject/inter-y [300 20] [[120 38]  [482 15]])))
  (is (= true (subject/inter-y [0 492] [[17 491] [417 493]]))))
(deftest inside?-test
  (let [poly [[0 0] [0 1] [1 1] [1 0]]]
    (testing "The polygon points are inside"
      (is (= true
             (subject/inside? poly [0 0])))
      (is (= true
             (subject/inside? poly [0 1])))
      (is (= true
             (subject/inside? poly [1 1]))))
    (is (= true
           (subject/inside? poly [0.5 0.5])))
    (is (= false
           (subject/inside? poly [0.3 1.1])))
    (is (= false
           (subject/inside? poly [-0.1 0.1]))))
  (testing "A more complicated polygon"
    (let [poly [[17 492] [417 493] [490 468] [425 150] [482 38] [120 15] [16 49]]]
      (is (= true
             (subject/inside? poly [452 66])))
      (is (= false
             (subject/inside? poly [434 151])))
      (is (= true
             (subject/inside? poly [298 281])))
      (is (= false
             (subject/inside? poly [10 174])))
      (is (= false
             (subject/inside? poly [74 21]))))))

(deftest convex-hull-test
  (let [rect [[0 5] [5 5] [5 0] [0 0]]]
    (is (= (set rect)
           (set (subject/convex-hull rect))))
    (is (= (set rect)
           (set (subject/convex-hull (conj rect [2.5 2.5])))))))
