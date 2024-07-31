(ns kiur.zip
  (:require
   [clojure.zip :as z]))

(defn iter-zip  [zipper]
  (->> zipper
       (iterate z/next)
       (take-while (complement z/end?))))
(defn loc-end [loc]
  (if-let [loc (z/down loc)]
    (recur (z/rightmost loc))
    loc))

(defn iter-rev-zip [zipper]
  (->> zipper
       loc-end
       (iterate z/prev)
       (take-while #(not= (z/root zipper) (z/node %)))))

(defn branch? [loc]
  (vector? loc))

(->> [[[1 2 3] [4 5 6 [7 8]]
       [9 10 11 [12 13]] 100]]
     (z/zipper branch? seq (fn [_ c] c))
     (iter-rev-zip)
     (mapv z/node)
     (filter (complement branch?)))
