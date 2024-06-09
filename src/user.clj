(ns user
  (:require
   [clojure.spec.test.alpha :as stest]
   [kiur.polygon :as p]
   [kiur.polygon.spec :as poly-spec]
   [clojure.spec.alpha :as s]))

(time (stest/instrument `p/inside?))

(s/assert ::poly-spec/point [2 1])
(s/assert not (p/inside? [[0 1] [2 1] [4 2]] [2 1]))
