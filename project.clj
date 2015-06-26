(defproject me.mishok13/bktree "0.1.0-SNAPSHOT"
  :description "BK-tree implementation"
  :url "http://github.com/mishok13/bktree"
  :license {:name "MIT"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :profiles {:dev {:dependencies [[midje "1.6.3"]]
                   :plugins [[lein-midje "3.1.3"]]}})
