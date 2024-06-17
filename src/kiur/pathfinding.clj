(ns kiur.pathfinding
  (:require
   [clojure.math :as math]
   [kiur.app.state :as state]
   [kiur.geometry.polygon :as poly]
   [kiur.geometry.vector :as v]))

(defn octogone [x y r]
  (mapv #(->> %
              (mapv (fn [coord delta] (+ (* delta r) coord)) [x y]))
        [[-1 0] [-2/3 -2/3] [0 -1] [2/3 -2/3] [1 0] [2/3 2/3] [0 1] [-2/3 2/3] [-1 0]]))

(defn get-cost [target point]
  (->> (v/make-vector target point)
       (mapv #(math/pow % 2))
       (reduce +)))
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
(defn make-next-points [{:keys [coord heur cost]} step]
  (->> (neighbors coord step)
       (map (fn [new-coord] {:coord new-coord
                             :cost (+ cost (get-cost  coord new-coord))
                             :coming-from coord}))))

(defn cost-map [{:keys [player]} target]
  (let [step (/ (:r player) 2.0)
        target (mapv double target)
        init-pos ((juxt :x :y) player)
        queue [{:heur (get-cost target init-pos)
                :cost 0
                :coord init-pos}]]
    (loop [[{:keys [heur coord] :as nxt} & queue] queue
           cost-map {}]
      (cond
        (nil? coord) cost-map

        (< 1000 (count cost-map)) cost-map

        (cost-map target) cost-map

        (better-path? cost-map nxt) (recur (into queue (make-next-points nxt step)) (assoc cost-map coord nxt))

        :else (recur queue cost-map)))))

(comment (let [state (state/default-state)
               target [200 200]
               target (mapv double target)
               cm (cost-map state target)

               start-pos ((juxt :x :y) (:player state))]
           (loop [t target
                  path []]
             (let [{:keys [coord coming-from]} (get cm t)]
               (cond
                 (= coord start-pos) path
                 :else (recur coming-from (conj path t)))))))
