(ns little-schemer-clj.chapter2
  (:require [clojure.test :refer :all]
            [little-schemer-clj.chapter1 :refer [null? atom? car cdr]]))

#_(with-test 
  ;; Function-definition
  (defn lat? [l]
    (cond (null? l) true
            (atom? (car l)) (lat? (cdr l))
            :else false))
  ;; Tests
  (testing "returns true"
    (is (= (lat? '(Jack Sprat could eat no chicken fat)) true))
    (is (= (lat? '()) true))
    (is (= (lat? '(bacon and eggs)) true)))
  (testing "returns false"
    (is (= (lat? '((Jack) Sprat could eat no chicken fat)) false))
    (is (= (lat? '(Jack (Sprat could) eat no chicken fat)) false))
    (is (= (lat? '(bacon (and eggs))) false))))

(run-tests)
