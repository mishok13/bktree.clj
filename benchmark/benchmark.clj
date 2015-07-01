(ns benchmark
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]
   [criterium.core :as bench]
   [me.mishok13.bktree :as bktree])
  (:gen-class))

(def seed 88699)

(defn choose-edit-distance
  "Choose edit distance for a given word"
  [rand-gen word]
  (->> (count word) (.nextInt rand-gen) (max 1) (min (-> word count (/ 2) int))))

(defn choose-words
  "Choose words from dictionary on a random basis."
  [dictionary]
  (let [rand-gen (java.util.Random. seed)]
    (->> (repeatedly #(.nextInt rand-gen (count dictionary)))
         (map (partial nth dictionary))
         (map (juxt identity (partial choose-edit-distance rand-gen))))))

(defn bk-tree
  [dictionary]
  (println "Running BK-tree benchmark")
  ;; Perhaps optimize decision making by randomizing list first? That
  ;; would introduce some unpredictability factor though, so hmmm...
  (println "Populating BK-tree")
  (let [tree (reduce bktree/insert (bktree/make-tree) dictionary)]
    (println "All ready to go")
    (bench/bench
     (doseq [[word edit-distance] (take 25 (choose-words dictionary))]
       (bktree/search tree word edit-distance)))))

(defn spellchecker
  [dictionary]
  (bench/bench
   (Thread/sleep 1000)))

(defn simple-list
  [dictionary]
  (println "Running simple list benchmark")
  (bench/bench
   (doseq [[word edit-distance] (take 25 (choose-words dictionary))]
     (->> dictionary
          (filter (fn [entry] (<= (bktree/levenstein-distance entry word) edit-distance)))
          doall))))

(defn -main
  [benchmark]
  (let [dictionary (doall (map str/lower-case (line-seq (io/reader "/usr/share/dict/words"))))]
    (case benchmark
      "bktree" (bk-tree dictionary)
      "spellchecker" (spellchecker)
      "simple" (simple-list dictionary))))
