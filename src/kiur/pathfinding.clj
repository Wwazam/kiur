(ns kiur.pathfinding
  (:require
   [clojure.math :as math]
   [kiur.geometry.polygon :as poly]
   [kiur.geometry.vector :as v]))

(defn octogone [[x y] r]
  (mapv #(->> %
              (mapv (fn [coord delta] (+ (* delta r) coord)) [x y]))
        [[-1 0] [-2/3 -2/3] [0 -1] [2/3 -2/3] [1 0] [2/3 2/3] [0 1] [-2/3 2/3] [-1 0]]))

(defn calc-cost [target point]
  (->> (v/make-vector target point)
       (mapv #(math/pow % 2))
       (reduce +)))
(defn total-cost [{:keys [heuristic cost]}]
  (+ heuristic cost))
(defn neighbors [point step]
  (for [i (range -1 2)
        j (range -1 2)
        :when (not= 0 i j)]
    (->> [i j]
         (mapv #(* step %))
         (mapv + point))))
(defn better-path? [cost-map {:keys [coord cost]}]
  (let [val (cost-map coord)]
    (or (nil? val) (< cost val))))

(defn valid? [bounding-box point]
  (poly/inside? bounding-box point))
(defrecord Node [coord heuristic cost coming-from])

(defn make-next-points [{:keys [coord cost]} target step]
  (->> (neighbors coord step)
       (map (fn [new-coord] (->Node new-coord
                                    (calc-cost new-coord target)
                                    (+ cost (calc-cost coord new-coord))
                                    coord)))))
(defn make-queue [& vals]
  (apply sorted-set-by (fn [& args] (->> args (map total-cost) (reduce compare)))  vals))
(defn pop-queue [q]
  (let [a (first q)]
    [a (disj q a)]))

(defn cost-map [{:keys [player]} target]
  (let [step (/ (:r player) 2.0)
        target (mapv double target)
        init-pos ((juxt :x :y) player)
        root-node (->Node init-pos (calc-cost target init-pos) 0 nil)
        queue (make-queue root-node)]
    (loop [queue queue
           result {}]
      (let [[{:keys [coord] :as nxt} queue] (pop-queue queue)]
        (cond
          (nil? coord) result

          ;; TODO compare nxt to target and return (assoc result target nxt)
          ;; Faire gaffe aux cost et tout (add-node function ?)
          (poly/inside? (octogone coord (:r player)) target) (assoc result target nxt)

          (better-path? result nxt) (recur (into queue (make-next-points nxt target step)) (assoc result coord nxt))

          :else (recur queue result))))))

(defn astar [state target]
  (let [target (mapv double target)
        cm (cost-map state target)

        start-pos ((juxt :x :y) (:player state))]
    (loop [t target
           path []]
      (let [{:keys [coord coming-from]} (get cm t)]
        (cond
          (= coord start-pos) path
          :else (recur coming-from (conj path t)))))))

