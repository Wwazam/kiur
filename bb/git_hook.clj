(ns git-hook
  (:require
   [babashka.fs :as fs]
   [clojure.java.shell :refer [sh]]
   [clojure.string :as str]))

(defn changed-files []
  (->> (sh "git" "diff" "--name-only" "--cached" "--diff-filter=ACM")
       :out
       str/split-lines
       (filter seq)
       seq))

(def extensions #{"clj" "cljx" "cljc" "cljs" "edn"})

(defn clj? [s]
  (when s
    (let [extension (last (str/split s #"\."))]
      (extensions extension))))

(defn hook-text [hook]
  (format "#!/bin/sh
# Installed by babashka task on %s

bb hooks %s" (java.util.Date.) hook))

(defn spit-hook [hook]
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
  (spit-hook "pre-commit")
  (spit-hook "pre-push"))

(defn run-tests []
  (sh "clj" "-M:test"))

(defmethod hooks "pre-commit" [& _]
  (println "Running pre-commit hook")
  (when (is-main-branch?)
    (let [{:keys [exit out]} (run-tests)]
      (when (not (zero? exit))
        (throw (Exception. (format  "Tests are not passing\n%s" out))))))
  #_(when-let [files (changed-files)]
      (apply sh "cljstyle" "fix" (filter clj? files))))

(defmethod hooks "pre-push" [& _]
  (println "Running pre-push hook"))

(defmethod hooks :default [& args]
  (println "Unknown command:" (first args)))
