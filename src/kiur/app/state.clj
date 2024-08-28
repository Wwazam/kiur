(ns kiur.app.state
  (:require
   [kiur.app.keymap :as keymap]
   [kiur.geometry.polygon :as poly]))

(defn make-random-map [{:keys [width height t-w t-h]
                        :or {width 100
                             height 100
                             t-w 10
                             t-h 10}}]
  (->> (for [x (range 0 width)
             y (range 0 height)]
         (when (or (= 0 x)
                   (= 0 y)
                   (= (dec width) x)
                   (= (dec height) y)
                   (< 0.7 (rand)))
           [(* t-w x) (* t-h y)]))
       (filter identity)
       (mapv (fn [[x y]] {:color [0 155 155] :points (poly/rect x y t-w t-h) :type :rect}))))

(defn default-state  []
  {:player {:x 100 :y 100 :speed 3 :r 20}
   :controller {:direction #{}
                :keymap keymap/dvorak-kb}
   :map (make-random-map {:width 10 :height 10
                          :t-h 50 :t-w 50})
   :debug false})
