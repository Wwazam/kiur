(ns kiur.geometry.vector.spec
  (:require
   [clojure.spec.alpha :as s]))

(s/def ::coord number?)
(s/def ::point (s/coll-of (s/spec ::coord)))
(s/def ::vector (s/or :list-of-points (s/coll-of (s/spec ::point))
                      :x-y (s/coll-of (s/spec ::coord))))
