(ns kiur.keymap)
(defmacro kw [s]
  `(keyword (str ~s)))

(defn str->key-list [string]
  (mapv #(kw %) string))

(def qwerty-kb (str->key-list "`1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm,./"))
(def dvorak-kb (str->key-list "`1234567890[]',.pyfgcrl/=aoeuidhtns-\\;qjkxbmwvz"))

(defn key-list->qwerty [keys]
  (->> keys
       (map vector qwerty-kb)
       (reduce (fn [m [v k]] (assoc m k v)) {})))

((key-list->qwerty dvorak) (keyword ","))
