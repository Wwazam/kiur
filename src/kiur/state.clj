(ns kiur.state
  (:require
   [kiur.keymap :as keymap]))

(defn default-state  []
  {:player {:x 100 :y 100 :speed 3}
   :controller {:direction #{}
                :keymap keymap/dvorak-kb}
   :map [{:type :rect :x 0 :y  0 :w  650 :h 50 :color [23 94 140]}
         {:type :rect :x 50 :y  150 :w  12 :h 500 :color [94 140 23]}
         {:type :rect :x 150 :y  30 :w  12 :h 500 :color [140 94 23]}]
   :debug false})
