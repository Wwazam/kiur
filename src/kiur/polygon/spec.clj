(ns kiur.polygon.spec
  (:require
   [clojure.spec.alpha :as s]))

(s/def ::point (s/cat :x number? :y number?))
(s/def ::vertice (s/cat :p1 (s/spec ::point) :p2 (s/spec ::point)))
(s/def ::vertices (s/coll-of ::vertice))
(s/def ::polygon (s/and (s/coll-of ::point)
                        #(< 2 (count %))))
(s/assert ::point [0 0])
(s/assert ::vertice [[0 0] [0 1]])

(s/def ::inside?-args
  (s/cat :polygon (s/spec ::polygon) :point (s/spec ::point)))
(s/fdef kiur.polygon/inside?
  :args ::inside?-args)
