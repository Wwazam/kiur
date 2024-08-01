(ns user
  (:require
   [kiur.geometry.collision :as coll]
   [kiur.app.state :as state]
   [kiur.geometry.polygon :as p]))

(defn valid? [m poly]
  (some #(coll/collision? poly %) m))

(let [state (state/default-state)
      m (:map state)]
  (valid? (p/octogon)))
