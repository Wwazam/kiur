(ns kiur.event
  (:require
   [kiur.state :as state]))

(defn- -event-type [_state event]  (:type event))
(defmulti event #'-event-type)

(defmethod event :mouse-pressed
  [state _] (println state _) state)
(defmethod event :mouse-released
  [state _] (println state _) state)
(defmethod event :mouse-wheel
  [state event]
  state)
(defmethod event :key-pressed
  [state event]
  (case (:key event)
    :r state/default-state
    state))
(defmethod event :mouse-moved
  [state event]
  (update-in state [:controller :pointer]
             assoc :x (:x event) :y (:y event)))
(defmethod event :default
  [state event]
  state)
