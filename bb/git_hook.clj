(ns git-hook
  (:require [babashka.fs :as fs]
            [clojure.string :as str]
            [clojure.java.shell :refer [sh]]))

(defn changed-files []
  (->> (sh "git" "diff" "--name-only" "--cached" "--diff-filter=ACM")
       :out
       str/split-lines
       (filter seq)
       seq))

(def extensions #{"clj" "cljx" "cljc" "cljs" "edn"})

(defn clj?
  [s]
  (when s
    (let [extension (last (str/split s #"\."))]
      (extensions extension))))

(defn hook-text
  [hook]
  (format "#!/bin/sh
# Installed by babashka task on %s

bb hooks %s" (java.util.Date.) hook))

(defn spit-hook
  [hook]
  (println "Installing hook: " hook)
  (let [file (str ".git/hooks/" hook)]
    (spit file (hook-text hook))
    (fs/set-posix-file-permissions file "rwx------")
    (assert (fs/executable? file))))

(defn is-main-branch? []
  (->> (sh "git" "branch" "--show-current")
       :out str/trim
       (= "main")))

(defmulti hooks (fn [& args] (first args)))

(defmethod hooks "install" [& _]
  (spit-hook "pre-commit"))

(defmethod hooks "pre-commit" [& _]
  (println "Running pre-commit hook")
  (when-let [files (changed-files)]
    (apply sh "cljstyle" "fix" (filter clj? files)))
  (when (is-main-branch?)
    (->> (sh "clj" "-M:test")
         :out println)
    (println "billy bob")))

(defmethod hooks :default [& args]
  (println "Unknown command:" (first args)))
