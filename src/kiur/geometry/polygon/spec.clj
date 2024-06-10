(ns kiur.geometry.polygon.spec
  (:require
   [clojure.spec.alpha :as s]))

(s/def ::point (s/cat :x number? :y number?))
(s/def ::edge (s/cat :p1 (s/spec ::point) :p2 (s/spec ::point)))
(s/def ::edges (s/coll-of ::edge))
(s/def ::polygon (s/and (s/coll-of ::point)
                        #(< 2 (count %))))
(s/assert ::point [0 0])
(s/assert ::edge [[0 0] [0 1]])

(s/def ::inside?-args
  (s/cat :polygon (s/spec ::polygon) :point (s/spec ::point)))
(s/fdef kiur.polygon/inside?
  :args ::inside?-args)
