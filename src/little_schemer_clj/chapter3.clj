(ns little-schemer-clj.chapter3
  (:require
   [little-schemer-clj.chapter1 :refer [atom? eq? not-eq? car cdr null?]]
   [little-schemer-clj.chapter2 :refer [lat? member?]]))

;; Cons The Magnificent

;;During which the adventurous developer comes across the SECOND COMMANDMENT:
;;  Use cons to build lists

;;Self Version:
(defn rember-v1
  "Remove a member
  STIPULATION: only removes first instance"
  [a lat]
  (cond
    (not (seq lat)) '()
    (eq? a (car lat)) (rember-v1 '() (cdr lat)) ;a will never be '() bc a can never be a list
    (not-eq? a (car lat)) (cons (car lat) (cdr a (rest lat)))))

;;Enlightened Master Self Version
(defn rember
  "Remove a member
  STIPULATION: only removes first instance"
  [a lat]
  (cond
    (not (seq lat)) '()
    :else (cond
            (eq? a (car lat)) (cdr lat)
            (not-eq? a (car lat)) (cons (car lat) (rember a (cdr lat))))))

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

; (rember "mint" ["lamb" "mint" "jelly" "mint"])
;
;  mint != lamb => 
;    (cons "lamb" (rember "mint" ["mint" "jelly" "mint"])
;                         mint = mint =>
;                            (rember '() ["jelly" "mint"])
;                               NOW....every element will be added to list bc no element will = '()

(rember "mint" ["lamb" "chops" "and" "mint" "jelly"])
(rember "mint" ["lamb" "chops" "and" "mint" "flavored" "mint" "jelly"])
(rember "toast" ["bacon" "lettuce" "tomato"])
(rember "cup" ["coffee" "cup" "covfefe" "cup" "hick" "cup"])
(rember-ERROR "mint" ["lamb" "chops" "and" "mint" "flavored" "mint" "jelly"])
(rember-ERROR "mint" ["lamb" "chops" "and" "flavored" "jelly"])
(rember-ERROR "and" ["bacon" "lettuce" "and" "tomato"])
