(ns kiur.geometry.polygon)

(defn rect [x y w h]
  [[x y]
   [(+ x w) y]
   [(+ x w) (+ y h)]
   [x (+ y h)]])

(defn- center [poly]
  (let [{:keys [x y count]} (reduce (fn [m [x y]] (-> m
                                                      (update :x + x)
                                                      (update :y + y)
                                                      (update :count inc)))
                                    {:x 0 :y 0 :count 0}
                                    poly)]
    (mapv #(/ (double %) count) [x y])))
(defn bounding-box [poly]
  (reduce (fn [m [x y]] (-> m
                            (update-in [0 0] min x)
                            (update-in [0 1] min y)
                            (update-in [1 0] max x)
                            (update-in [1 1] max y)))
          [(first poly) (first poly)] poly))

(defn- axis-aligned-inside? [[[x1 y1] [x2 y2]] [x y]]
  (and (<= x1 x x2)
       (<= y1 y y2)))

(defn poly->edges [poly]
  (let [cycled-points (conj poly (first poly))]
    (->> cycled-points
         (partition 2 1)
         (mapv vec))))

(defn- inter-y [[x y] [[x1 y1] [x2 y2]]]
  (and
   (< (min y1 y2) y)
   (<= y (max y1 y2))
   (<= (max x1 x2) x)
   (let [x-inter (+ (/ (* (- y y1) (- x2 x1))
                       (- y2 y1))
                    x1)] (or (= x1 x2)
                             (<= x x-inter)))))

(defn inside? [poly point]
  (and (axis-aligned-inside? (bounding-box poly) point)
       (->> poly
            poly->edges
            (reduce (fn [crossing edge]
                      (cond-> crossing
                        (inter-y point edge) not))
                    false))))

(defn- orientation
  "Find the orientation of an ordered triplet p q r "
  [[px py] [qx qy] [rx ry]]
  (let [val (- (* (- qy py) (- rx qx))
               (* (- qx px) (- ry qy)))]
    (cond
      (zero? val) :colinear
      (> 0 val) :counter-clockwise
      :else :clockwise)))

(defn convex-hull [poly]
  (let [leftmost (reduce (fn [[x1 :as left] [x2 :as point]] (if (> x1 x2) point left)) poly)]
    (loop [points (into (hash-set) poly)
           [h :as hull] (list leftmost)]
      (cond
        (and (< 1 (count hull)) (= h (last hull))) (-> hull rest vec)
        :else (let [next-point (reduce (fn [q r] (if (= :clockwise (orientation h q r)) r q))
                                       (first (remove #{h} points))
                                       points)]
                (recur (disj points next-point) (conj hull next-point)))))))

