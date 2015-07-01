(defproject me.mishok13/bktree "0.1.0-SNAPSHOT"
  :description "BK-tree implementation"
  :url "http://github.com/mishok13/bktree"
  :license {:name "MIT"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :profiles {:dev {:dependencies [[midje "1.6.3"]]
                   :plugins [[lein-midje "3.1.3"]]}
             :benchmark {:dependencies [[criterium "0.4.3"]]
                         :source-paths ["benchmark"]
                         :main benchmark}}
  :aliases {"benchmark" ^{:doc "Run benchmarks"}
            ["do"
             "clean"
             ["with-profile" "benchmark" "run" "bktree"]
             ["with-profile" "benchmark" "run" "simple"]]})
