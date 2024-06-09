(ns kiur.app.state
  (:require
   [kiur.app.keymap :as keymap]))

(defn default-state  []
  {:player {:x 100 :y 100 :speed 3}
   :controller {:direction #{}
                :keymap keymap/dvorak-kb}
   :map [{:type :poly :points [[50 0] [20 100] [150 150]] :color [23 94 140]}
         {:type :poly :points [[50 80]  [20 150] [180 250] [180 210]] :color [180 54 23]}
         {:type :poly :points [[250 0] [220 100] [350 150]] :color [23 94 140]}
         {:type :poly :points [[250 115] [220 150] [380 250] [380 210]] :color [180 54 23]}]
   :debug false})
