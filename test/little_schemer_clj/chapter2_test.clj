(ns little-schemer-clj.chapter2-test
  (:require
   [clojure.test :refer :all]
   [little-schemer-clj.chapter2 :refer [lat? member?]]))

(deftest truther
  (is (= (lat? (list 1 2 3 'whoa)) true))
  (is (= (lat? '("Jack" "Sprat" "could" "eat" "no" "chicken" "fat")) true))
  (is (= (lat? '()) true))
  (is (= (lat? '("bacon" "and" "eggs")) true)))

(deftest falser
  (is (= (member? "a" (list 1 2 3 4 5)) false))
  (is (= (lat? '(("Jack") "Sprat" "could" "eat" "no" "chicken" "fat")) false))
  (is (= (lat? '("Jack" ("Sprat" "could") "eat" "no" "chicken" "fat")) false))
  (is (= (lat? (list 1 2 4 (list 1 2 3))) false))
  (is (= (lat? '("bacon" ("and" "eggs"))) false)))

(run-tests)
