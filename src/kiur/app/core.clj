(ns kiur.app.core
  (:require
   [kiur.app.draw :as draw]
   [kiur.app.event :as event]
   [kiur.app.state :as state]
   [kiur.app.update :as update]
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
    :mouse-moved (fn [st ev] (event/handle st (assoc ev :type :mouse-moved)))
    :mouse-dragged (fn [st ev] (event/handle st (assoc ev :type :mouse-moved)))
    :mouse-wheel (fn [st ev]  (event/handle st {:type :mouse-wheel :value ev}))
    :mouse-pressed (fn [st ev] (event/handle st (assoc ev :type :mouse-pressed)))
    :mouse-released (fn [st ev] (event/handle st (assoc ev :type :mouse-released)))
    :key-pressed (fn [st ev]
                   (when (= 27 (q/key-code))
                     (set! (.key (applet/current-applet)) (char 0)))
                   (event/handle st (assoc ev :type :key-pressed)))
    :key-released (fn [st ev] (event/handle st (assoc ev :type :key-released)))
    :features [:resizable]
    :middleware [qm/fun-mode])
  kiur)
