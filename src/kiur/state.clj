(ns kiur.state
  (:require
   [kiur.keymap :as keymap]))

(defn default-state  []
  {:player {:x 100 :y 100 :speed 3}
   :controller {:direction #{}
                :keymap keymap/dvorak-kb}
   :debug false})
