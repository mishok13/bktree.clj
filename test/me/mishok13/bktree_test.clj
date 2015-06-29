(ns me.mishok13.bktree-test
  (:require
   [midje.sweet :refer :all]
   [me.mishok13.bktree :refer :all]))

(fact
 "Exact search on a tree that's missing the word should fail"
 (search (make-tree "foo") "bar" 0) => nil
 (search (make-tree "foo") "bar" 4) => ["foo"]
 (search (insert (make-tree "foo") "bar") "bar" 3) => ["foo" "bar"])
