(ns kiur.event
  (:require
   [kiur.keymap :as keymap]
   [kiur.state :as state]))

(defn- -event-type [_state event]  (:type event))
(defmulti event #'-event-type)

(defmethod event :mouse-pressed
  [state _] state)
(defmethod event :mouse-released
  [state _]  state)
(defmethod event :mouse-wheel
  [state _]
  state)
(defmethod event :key-pressed
  [state ev]
  (let [km (keymap/keymap (-> state :controller :keymap))
        action (km (:key ev))]
    (when action (println action))
    (case action
      :reset-state (state/default-state)
      state)))
(defmethod event :mouse-moved
  [state ev]
  (update-in state [:controller :pointer]
             assoc :x (:x ev) :y (:y ev)))
(defmethod event :default
  [state ev]
  state)
