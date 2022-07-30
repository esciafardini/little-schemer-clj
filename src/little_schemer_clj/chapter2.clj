(ns little-schemer-clj.chapter2
  (:require
   [little-schemer-clj.chapter1 :refer [atom? eq? car cdr null?]]))

(defn lat? [l]
  (cond
    (null? l) true
    (atom? (first l)) (lat? (rest l))
    :else false))

(defn member? [a lat]
  (cond
    (null? lat) false
    (eq? (car lat) a) true
    :else (member? a (cdr lat))))

(comment
  (lat? (list 1 2 3 'whoa))
  (lat? (list 1 2 4 (list 1 2 3)))
  (member? "a" (list 1 2 3 4 5))
  (member? "mashed" (list "gravy" "and" "mashed" "potato")))
