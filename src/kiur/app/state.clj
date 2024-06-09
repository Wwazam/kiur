(ns kiur.app.state
  (:require
   [kiur.app.keymap :as keymap]
   [kiur.geometry.polygon :as poly]))

(defn default-state  []
  {:player {:x 100 :y 100 :speed 3}
   :controller {:direction #{}
                :keymap keymap/dvorak-kb}
   :map [{:type :rect :points (poly/rect 0 0 650 50) :color [23 94 140]}
         {:type :rect :points (poly/rect 50 150 12 500) :color [94 140 23]}
         {:type :rect :points (poly/rect 150 30 12 500) :color [140 94 23]}
         {:type :rect :points (poly/rect 90 5 65 50) :color [239 45 12]}
         {:type :poly :points [[10 30] [90 70] [70 90] [70 20] [90 60] [10 80]] :color [38 59 123]}]
   :debug false})
