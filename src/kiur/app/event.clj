(ns kiur.app.event
  (:require
   [kiur.app.keymap :as keymap]
   [kiur.app.state :as state]))

(defn- -event-type [_state event]  (:type event))
(defmulti handle #'-event-type)

(defmethod handle :mouse-pressed
  [state ev]
  (case (:button ev)
    :right (update-in state [:player :target]
                      assoc
                      :x (:x ev)
                      :y (:y ev))
    :left state
    :center state))
(defmethod handle :mouse-released
  [state _]  state)
(defmethod handle :mouse-wheel
  [state {:keys [value]}]
  (update-in state [:player :speed] (fn [speed] (min 10 (max 0 (- speed value))))))
(defmethod handle :key-pressed
  [state ev]
  (let [km (keymap/keymap (-> state :controller :keymap))
        action (km (:key ev))]
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
