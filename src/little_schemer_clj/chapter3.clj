(ns little-schemer-clj.chapter3
  (:require
   [little-schemer-clj.chapter1 :refer [atom? eq? not-eq? car cdr null?]]
   [little-schemer-clj.chapter2 :refer [lat? member?]]))

;; Cons The Magnificent

;;During which the adventurous developer comes across more commandments

;;SECOND COMMANDMENT:
;;  Use cons to build lists

;;THIRD COMMANDMENT:
;;  When building a list, describe the first typical element & cons it onto the natural recursion

;;Self Version:
(defn rember-v1
  "Remove a member
  STIPULATION: only removes first instance"
  [a lat]
  (cond
    (not (seq lat)) '()
    (eq? a (car lat)) (rember-v1 '() (cdr lat)) ;a will never be '() bc a can never be a list
    (not-eq? a (car lat)) (cons (car lat) (cdr a (rest lat)))))

;;Book's Bad Version:
(defn rember-ERROR
  "An example from the book--
   This doesn't work...
   Ask thineself: whyYyyYYYyyyYy?"
  [a lat]
  (cond
    (not (seq lat)) '()
    :else (cond
            (eq? (car lat) a) (cdr lat)
            :else (rember-ERROR a (cdr lat)))))

;;Enlightened Master Self Version
(defn rember
  "Remove a member
   STIPULATION: only removes first instance"
  [a lat]
  (cond
    (not (seq lat)) '()
    (eq? a (car lat)) (cdr lat)
    :else (cons (car lat)
                (rember a (cdr lat)))))

; (rember "mint" ["lamb" "mint" "jelly" "mint"])
;
;  mint != lamb => 
;    (cons "lamb" (rember "mint" ["mint" "jelly" "mint"])
;                   = '("jelly" "mint")
;                  THEREFORE:
;                  equates to...  (cons "lamb" '("jelly" "mint"))

;TODO: move these into test files
(=
 (rember "mint" ["lamb" "mint" "jelly" "mint"])
 (cons "lamb" '("jelly" "mint"))) ;=> true

(=
 (rember "mint" ["lamb" "and" "mint" "jelly" "mint"])
 (cons "lamb" (cons "and" '("jelly" "mint")))) ;=> true


(rember "mint" ["lamb" "chops" "and" "mint" "jelly"])
(rember "mint" ["lamb" "chops" "and" "mint" "flavored" "mint" "jelly"])
(rember "toast" ["bacon" "lettuce" "tomato"])
(rember "cup" ["coffee" "cup" "covfefe" "cup" "hick" "cup"])
(rember-ERROR "mint" ["lamb" "chops" "and" "mint" "flavored" "mint" "jelly"])
(rember-ERROR "mint" ["lamb" "chops" "and" "flavored" "jelly"])
(rember-ERROR "and" ["bacon" "lettuce" "and" "tomato"])

(defn firsts
  "returns the first value of each seq
   Typical Element: first item of each list
   (car (car l))"
  [l]
  (cond
    (not (seq l)) '()
    :else (cons (car (car l))
                (firsts (cdr l)))))

(firsts '([0 1 2 3] [8 9 5] ["first" "second" "woof"]))
(firsts '())
(firsts '(["five" "plums"] ["four"] ["eleven" "green" "oranges"]))
(firsts '([["five" "plums"] "four"] ["eleven" "green" "oranges"] [["no"] "more"]))

(defn seconds
  "This fn was mentioned in passing, but I wanted to practice
   Typical Element: second item of the first list
   (car (cdr (car l)))"
  [l]
  (cond
    (not (seq l)) '()
    (< (count (first l)) 2) (seconds (cdr l))
    :else (cons (car (cdr (car l))) (seconds (cdr l)))))

(seconds '([1 2 3 4] ["oh" "my" "wow"] ["first element" "second element" "third element" "fourth?"]))
(seconds '([14] ["oh" "my" "wow"] ["first element" "second element" "third element" "fourth?"]))

;;Mental Model that works for me:
(firsts '(['a 'b] ['c 'd] ['e 'f]))
;; 1. cons e onto ()
;; 2. cons c onto value of 1
;; 3. cons a onto value of 2
;=> ('a 'c 'e)
