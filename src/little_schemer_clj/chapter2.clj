(ns little-schemer-clj.chapter2
  (:require
   [little-schemer-clj.chapter1 :refer [atom? eq? car cdr null?]]))

;; Chapter 2, during which the unskilled developer chews on recursion and feels sick to his stomach

(defn lat?
  "Well, dad, it stands for List Of Atoms"
  [l]
  (cond
    (not (seq l)) true
    (atom? (first l)) (lat? (rest l))
    :else false))

(defn member?
  "This function recursively checks a LAT asking if `a` is a member"
  [a lat]
  (cond
    (not (seq lat)) false
    (eq? (car lat) a) true
    :else (member? a (cdr lat))))
