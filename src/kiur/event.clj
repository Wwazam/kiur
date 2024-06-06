(ns kiur.event
  (:require
   [kiur.keymap :as keymap]
   [kiur.state :as state]))

(defn- -event-type [_state event]  (:type event))
(defmulti handle #'-event-type)

(defmethod handle :mouse-pressed
  [state _] state)
(defmethod handle :mouse-released
  [state _]  state)
(defmethod handle :mouse-wheel
  [state _]
  state)
(defmethod handle :key-pressed
  [state ev]
  (let [km (keymap/keymap (-> state :controller :keymap))
        action (km (:key ev))]
    (when action (println action))
    (case action
      :reset-state (state/default-state)
      state)))
(defmethod handle :mouse-moved
  [state ev]
  (update-in state [:controller :pointer]
             assoc :x (:x ev) :y (:y ev)))
(defmethod handle :default
  [state ev]
  state)
