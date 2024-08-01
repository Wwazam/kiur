(ns kiur.pathfinding-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [kiur.pathfinding :as subject]
   [clojure.math :as math]))

(deftest neighbors-test
  (is (= [[-1 -1] [-1 0] [-1 1] [0 -1] [0 1] [1 -1] [1 0] [1 1]]
         (subject/neighbors [0 0] 1)))
  (is (= [[-10 -10] [-10 0] [-10 10] [0 -10] [0 10] [10 -10] [10 0] [10 10]]
         (subject/neighbors [0 0] 10)))
  (is (= [[-16 -12] [-16 17] [-16 46] [13 -12] [13 46] [42 -12] [42 17] [42 46]]
         (subject/neighbors [13 17] 29))))

(deftest astar-test
  (is (= [[7.5 17.5]
          [5.0 15.0]
          [2.5 12.5]
          [2.5 10.0]
          [2.5 7.5]
          [2.5 5.0]
          [1.0 1.0]]
         (subject/astar {:map [] :player {:x 10 :y 20 :r 5}}
                        [1 1]))
      "A basic path finding without obstacles"))

(deftest cost-map-test
  (let [target [50.0 50.0]
        player {:x 1 :y 1 :r 10}
        cm (subject/cost-map {:player player} target)]

    (is (= (subject/map->Node {:coord target
                               :heuristic 0.0
                               :cost 482.0
                               :coming-from [46.0 46.0]})
           (get cm target))
        "The last node of the cost map matches our expectation")))

(deftest calc-cost-test
  (testing "With an origin point"
    (is (= 1.0 (subject/calc-cost [0 0] [1 0])))
    (is (= 2.0 (subject/calc-cost [0 0] [1 1])))
    (is (= 25.0 (subject/calc-cost [0 0] [3 4]))))
  (testing "With more complicated coords"
    (is (= 181.0 (subject/calc-cost [10 10] [1 0])))
    (is (= 522.0 (subject/calc-cost [10 -20] [1 1])))
    (is (= (->> [11.1 8.3] (mapv #(math/pow % 2)) (reduce +) int)
           (int (subject/calc-cost [-50.9 10.4] [-39.8 2.1]))))))
