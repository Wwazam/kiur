(ns kiur.geometry.polygon-test
  (:require
   [clojure.test :refer [deftest is]]
   [kiur.geometry.polygon :as subject]))

(deftest rect-test
  (is (= [[0 0] [1 0] [1 1] [0 1]]
         (subject/rect 0 0 1 1)))
  (is (= [[0 5] [7 5] [7 18] [0 18]]
         (subject/rect 0 5 7 13))))
