(ns me.mishok13.bktree)

(declare make-tree)

(defn levenstein-distance
  "Calculate Levenstein distance using Wagner-Fischer algorithm"
  [s t]
  (let [m (count s)
        n (count t)]
    ;; FIXME: rewrite this in a more functional way
    (get (reduce (fn [acc [j i]]
                   (assoc acc [(inc i) (inc j)]
                          (if (= (get s i) (get t j))
                            (get acc [i j])
                            (->> [[i (inc j)] [(inc i) j] [i j]]
                                 (map (partial get acc))
                                 (filter (complement nil?))
                                 (apply min)
                                 inc))))
                 (merge (into {} (for [x (range (inc n))] [[0 x] x]))
                        (into {} (for [x (range (inc m))] [[x 0] x])))
                 (for [j (range n) i (range m)] [j i])) [m n])))

(defprotocol ITree
  (children [this]
    "List all children nodes of a given node.")
  (insert [this node]
    "Insert node into tree"))

(defprotocol IBKTree
  (search [this string n]
    "Perform a fuzzy string search with a distance 'tolerance' of n"))

(defrecord BKTree [^String value leafs]
  ITree
  (insert [this s]
    (if (nil? value)
      ;; Empty node, can insert immediately
      (assoc this
             :value s
             :leafs {})
      (let [distance (levenstein-distance value s)]
        (if-let [leaf (get leafs distance)]
          (assoc-in this [:leafs distance] (insert leaf s))
          (assoc-in this [:leafs distance] (make-tree s))))))
  (children [this]
    (vals leafs))

  IBKTree
  (search [this s n]
    (let [distance (levenstein-distance (:value this) s)]
      (when (<= distance n)
        (prn "printing" value leafs))
      (seq (doall (map #(search % s n) (map second (filter (fn [[d l]] (<= (- distance n) d (+ distance n))) leafs))))))))

(defn make-tree
  ([] (->BKTree nil {}))
  ([s] (->BKTree s {})))
