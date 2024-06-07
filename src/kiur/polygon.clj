(ns kiur.polygon
  (:require
   [clojure.spec.alpha :as s]))

(defn square [x y w h]
  (vec (for [dx [0 w]
             dy [0 h]]
         [(+ x dx) (+ y dy)])))
(defn center [poly]
  (let [{:keys [x y count]} (reduce (fn [m [x y]] (-> m
                                                      (update :x + x)
                                                      (update :y + y)
                                                      (update :count inc)))
                                    {:x 0 :y 0 :count 0}
                                    poly)]
    (mapv #(/ (double %) count) [x y])))
(defn bounding-box [poly]
  (reduce (fn [m [x y]] (-> m
                            (update-in  [0 0] min x)
                            (update-in  [0 1] min y)
                            (update-in  [1 0] max x)
                            (update-in  [1 1] max y)))
          [(first poly) (first poly)] poly))

(defn- axis-aligned-inside? [[[x1 y1] [x2 y2]] [x y]]
  (and (<= x1 x x2)
       (<= y1 y y2)))

(defn poly->vertices [poly]
  (let [cycled-points (conj poly (first poly))]
    (->> cycled-points
         (partition 2 1)
         (mapv vec))))

(s/def ::point (s/cat :x number? :y number?))
(s/def ::vertice (s/cat :p1 (s/spec ::point) :p2 (s/spec ::point)))
(s/def ::vertices (s/coll-of ::vertice))
(s/conform ::point [0 0])
(s/assert ::vertice [[0 0] [0 1]])
(s/conform ::vertices (poly->vertices [[0 0] [1 0] [1 1]]))

(defn inside? [poly point]
  (and (axis-aligned-inside? (bounding-box poly) point)
       ()))

