(ns kiur.pathfinding-test
  (:require
   [clojure.test :refer [deftest is]]
   [kiur.pathfinding :as subject]))

(deftest neighbors-test
  (is (= [[-1 -1] [-1 0] [-1 1] [0 -1] [0 1] [1 -1] [1 0] [1 1]]
         (subject/neighbors [0 0] 1)))
  (is (= [[-10 -10] [-10 0] [-10 10] [0 -10] [0 10] [10 -10] [10 0] [10 10]]
         (subject/neighbors [0 0] 10)))
  (is (= [[-16 -12] [-16 17] [-16 46] [13 -12] [13 46] [42 -12] [42 17] [42 46]]
         (subject/neighbors [13 17] 29))))
