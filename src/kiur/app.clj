(ns kiur.app
  (:require
   [kiur.draw :as draw]
   [kiur.event :as event]
   [kiur.state :as state]
   [kiur.update :as update]
   [quil.applet :as applet]
   [quil.core :as q]
   [quil.middleware :as qm]))

(defn setup []
  (state/default-state))

(defn start-app []
  ^{:clj-kondo/ignore [:inline-def]}
  (q/defsketch kiur
    :size [650 400]
    :setup setup
    :draw draw/draw-state
    :update update/update-state
    :mouse-moved (fn [st ev] (event/event st (assoc ev :type :mouse-moved)))
    :mouse-wheel (fn [st ev]  (event/event st (assoc ev :type :mouse-wheel)))
    :mouse-pressed (fn [st ev] (event/event st (assoc ev :type :mouse-pressed)))
    :mouse-released (fn [st ev] (event/event st (assoc ev :type :mouse-released)))
    :key-pressed (fn [st ev]
                   (event/event st (assoc ev :type :key-pressed)))
    :key-released (fn [st ev] (event/event st (assoc ev :type :key-released)))
    :features [:resizable]
    :middleware [qm/fun-mode]))
