(ns kiur.keymap)

(defn str->key-list [string]
  (mapv #(-> % str keyword) string))

(def qwerty (str->key-list "1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm,./"))
(def dvorak (str->key-list "1234567890[]',.pyfgcrl/=aoeuidhtns-\\;qjkxbmwvz"))

(defn key-list->qwerty [keys]
  (->> keys
       (map vector qwerty)
       (reduce (fn [m [v k]] (assoc m k v)) {})))

((key-list->qwerty dvorak) (keyword ","))
