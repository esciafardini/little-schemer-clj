(ns little-schemer-clj.chapter2
  (:require
   [little-schemer-clj.chapter1 :refer [atom? eq? car cdr null?]]))

;; Chapter 2, during which the unskilled developer chews on recursion and feels sick to his stomach

(defn lat?
  "Well, dad, it stands for List Of Atoms"
  [l]
  (cond
    (null? l) true
    (atom? (first l)) (lat? (rest l))
    :else false))

(defn member?
  "This function recursively checks a LAT asking if `a` is a member"
  [a lat]
  (cond
    (null? lat) false
    (eq? (car lat) a) true
    :else (member? a (cdr lat))))

(comment
  (lat? (list 1 2 3 'whoa))
  (lat? (list 1 2 4 (list 1 2 3)))
  (member? "a" (list 1 2 3 4 5))
  (member? "mashed" (list "gravy" "and" "mashed" "potato")))
