(ns little-schemer-clj.chapter1-test
  (:require
   [clojure.test :refer [deftest is run-tests testing]]
   [little-schemer-clj.chapter1 :refer [s-expression?]]))

(deftest s-expression-test
  (is (= true (s-expression? 5)))
  (is (= false (s-expression? nil)))
  (is (= true (s-expression? (list 5 4 3 2 1)))))

(run-tests)
