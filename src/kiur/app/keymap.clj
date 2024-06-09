(ns kiur.app.keymap
  (:require
   [clojure.spec.alpha :as s]))

(defmacro ^:private kw  [s]
  `(keyword (str ~s)))

(defn str->key-list [string]
  (mapv #(kw %) string))

(def qwerty-kb (str->key-list "`1234567890-=qwertyuiop[]asdfghjkl;'zxcvbnm,./"))
(def dvorak-kb (str->key-list "`1234567890[]',.pyfgcrl/=aoeuidhtns-\\;qjkxbmwvz"))

(defn key-list->qwerty [keys]
  (->> keys
       (map vector qwerty-kb)
       (reduce (fn [m [v k]] (assoc m k v)) {})))

(def qwerty-keymap {:w :accelerate-up
                    :a :accelerate-left
                    :s :accelerate-down
                    :d :accelerate-right
                    :o :reset-state})

(defn- -keymap [keyboard]
  (->> (map vector keyboard qwerty-kb)
       (reduce (fn [m [k qw-val]] (assoc m k (qwerty-keymap qw-val))) {})))
(def ^:function keymap (memoize -keymap))

(s/assert #{:accelerate-up} ((kw \,) (keymap dvorak-kb)))
(s/assert #{:accelerate-down} ((kw \o) (keymap dvorak-kb)))
(s/assert #{:accelerate-right} ((kw \e) (keymap dvorak-kb)))
(s/assert #{:accelerate-left} ((kw \a) (keymap dvorak-kb)))
