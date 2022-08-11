(ns little-schemer-clj.chapter3
  (:require
   [clojure.string :as str]
   [little-schemer-clj.chapter1 :refer [car cdr eq? not-eq?]]))

;; Cons The Magnificent

;;During which the adventurous developer comes across more commandments

;;SECOND COMMANDMENT:
;;  Use cons to build lists

;;THIRD COMMANDMENT:
;;  When building a list, describe the first typical element & cons it onto the natural recursion

;;FOURTH COMMANDMENT (preliminary):
;;  Always change at least one argument while recurring
;;  The change must bring the fn towards terminal condition
;;  The changing arg must be tested in termination condition

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

(defn firsts
  "returns the first value of each seq
   Typical Element: first item of each list
   (car (car l))"
  [l]
  (cond
    (not (seq l)) '() ;terminal condition
    :else (cons (car (car l)) ;typical element
                (firsts (cdr l)))))

;;Mental Model that works for me:
(firsts '(['a 'b] ['c 'd] ['e 'f]))
;; 1. cons a onto
;; 2. the cons of c onto
;; 3. the cons of e onto '()
;=> ('a 'c 'e)

(defn seconds
  "This fn was mentioned in passing, but I wanted to practice
   Typical Element: second item of the first list
   (car (cdr (car l)))"
  [l]
  (cond
    (not (seq l)) '()
    (< (count (first l)) 2) (seconds (cdr l))
    :else (cons (car (cdr (car l))) (seconds (cdr l)))))

(defn insertR
  "Adds nu to the right of old in a lat
   Typical element: first element of list
   (car lat)"
  [nu old lat]
  (cond
    (not (seq lat)) '()
    (eq? old (car lat)) (cons old (cons nu (cdr lat)))
    :else (cons (car lat) (insertR nu old (cdr lat)))))

(defn insertL
  "Adds nu to the left of old in a lat
   Typical element: first element of list
   (car lat)"
  [nu old lat]
  (cond
    (not (seq lat)) '()
    (eq? (car lat) old) (cons nu lat)
    :else (cons (car lat) (insertL nu old (cdr lat)))))

(defn subst
  "Substitutes first instance of old value with nu
   Typical element: first element of list
   (car lat)"
  [nu old lat]
  (cond
    (not (seq lat)) '()
    (eq? old (car lat)) (cons nu (cdr lat))
    :else (cons (car lat) (subst nu old (cdr lat)))))

(defn subst2
  "Replaces the first element = o1 or o2 with nu
   Typical element: first element of list
   (car lat)"
  [nu o1 o2 lat]
  (cond
    (not (seq lat)) '()
    (or
     (eq? (car lat) o2)
     (eq? (car lat) o1)) (cons nu (cdr lat))
    :else (cons (car lat) (subst2 nu o1 o2 (cdr lat)))))

(defn multirember [a lat]
  (cond
    (not (seq lat)) '()
    (eq? (car lat) a) (multirember a (cdr lat))
    :else (cons (car lat) (multirember a (cdr lat)))))

(defn multiinsertR [nu old lat]
  (cond
    (not (seq lat)) '()
    (eq? (car lat) old) (cons old (cons nu (multiinsertR nu old (cdr lat))))
    :else (cons (car lat) (multiinsertR nu old (cdr lat)))))

(defn multiinsertL [nu old lat]
  (cond
    (not (seq lat)) '()
    (eq? (car lat) old) (cons nu (cons old (multiinsertL nu old (cdr lat))))
    :else (cons (car lat) (multiinsertL nu old (cdr lat)))))

(defn multisubst [nu old lat]
  (cond
    (not (seq lat)) '()
    (eq? (car lat) old) (cons nu (multisubst nu old (cdr lat)))
    :else (cons (car lat) (multisubst nu old (cdr lat)))))
