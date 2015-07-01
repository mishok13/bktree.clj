(ns me.mishok13.bktree-test
  (:require
   [midje.sweet :refer :all]
   [me.mishok13.bktree :refer :all]))

(fact
 "Exact search on a tree that's missing the word should fail"
 (search (make-tree "foo") "bar" 0) => nil
 (search (make-tree "foo") "bar" 2) => nil
 )

(fact
 ""
 (search (make-tree "foo") "bar" 3) => ["foo"]
 (search (insert (make-tree "foo") "bar") "bar" 3) => (just ["foo" "bar"] :in-any-order)
 (search (reduce insert (make-tree) ["foo" "bar" "baz"]) "bar" 1) => (just ["baz" "bar"] :in-any-order)
 (search (reduce insert (make-tree) ["foo" "bar" "baz"]) "what" 3) => (just ["bar" "baz"] :in-any-order)
 (search (reduce insert (make-tree) ["foo" "bar" "baz"]) "what" 4) => (just ["foo" "bar" "baz"] :in-any-order))
