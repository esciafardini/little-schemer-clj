(ns little-schemer-clj.chapter2
  "Do It, Do It Again, and Again, and Again..."
  (:require
   [little-schemer-clj.chapter1 :refer [atom?]]))

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
    (= (first lat) a) true
    :else (member? a (rest lat))))

(member? 3 [1 2 3])
