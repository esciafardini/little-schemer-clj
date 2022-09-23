(ns little-schemer-clj.chapter3
  "Cons the Magnificent"
  (:require
   [little-schemer-clj.chapter1 :refer [eq? not-eq?]]))

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
    (eq? a (first lat)) (rember-v1 '() (rest lat)) ;a will never be '() bc a can never be a list
    (not-eq? a (first lat)) (cons (first lat) (rest a (rest lat)))))

;;Book's Bad Version:
(defn rember-ERROR
  "An example from the book--
   This doesn't work...
   Ask thineself: whyYyyYYYyyyYy?"
  [a lat]
  (cond
    (not (seq lat)) '()
    :else (cond
            (eq? (first lat) a) (rest lat)
            :else (rember-ERROR a (rest lat)))))

;;Enlightened Master Self Version
(defn rember
  "Remove a member
   STIPULATION: only removes first instance"
  [a lat]
  (cond
    (not (seq lat)) '()
    (eq? a (first lat)) (rest lat)
    :else (cons (first lat)
                (rember a (rest lat)))))

; (rember "mint" ["lamb" "mint" "jelly" "mint"])
;
;  mint != lamb =>
;    (cons "lamb" (rember "mint" ["mint" "jelly" "mint"])
;                   = '("jelly" "mint")
;                  THEREFORE:
;                  equates to...  (cons "lamb" '("jelly" "mint"))

(defn firsts
  "returns the first value of each seq
   Typical Element: first item of each list"
  [l]
  (let [idx0 (first l)
        remaining (rest l)]
    (if (not (seq l))
      '()
      (cons (first idx0)
            (firsts remaining)))))

;;Mental Model that works for me:
(firsts '(['a 'b] ['c 'd] ['e 'f]))
;; (cons 'a (cons 'c (cons 'e '())
;=> ('a 'c 'e)

(defn seconds
  "This fn was mentioned in passing, but I wanted to practice
   Typical Element: second item of the first list
   (car (cdr (car l)))"
  [l]
  (cond
    (not (seq l)) '()
    (< (count (first l)) 2) (seconds (rest l))
    :else (cons (first (rest (first l))) (seconds (rest l)))))

(defn insertR
  "Adds nu to the right of old in a lat
   Typical element: first element of list
   (car lat)"
  [nu old lat]
  (cond
    (not (seq lat)) '()
    (eq? old (first lat)) (cons old (cons nu (rest lat)))
    :else (cons (first lat) (insertR nu old (rest lat)))))

(defn insertL
  "Adds nu to the left of old in a lat
   Typical element: first element of list
   (car lat)"
  [nu old lat]
  (cond
    (not (seq lat)) '()
    (eq? (first lat) old) (cons nu lat)
    :else (cons (first lat) (insertL nu old (rest lat)))))

(defn subst
  "Substitutes first instance of old value with nu
   Typical element: first element of list
   (car lat)"
  [nu old lat]
  (cond
    (not (seq lat)) '()
    (eq? old (first lat)) (cons nu (rest lat))
    :else (cons (first lat) (subst nu old (rest lat)))))

(defn subst2
  "Replaces the first element = o1 or o2 with nu
   Typical element: first element of list
   (car lat)"
  [nu o1 o2 lat]
  (cond
    (not (seq lat)) '()
    (or
     (eq? (first lat) o2)
     (eq? (first lat) o1)) (cons nu (rest lat))
    :else (cons (first lat) (subst2 nu o1 o2 (rest lat)))))

(defn multirember
  "removes all instances of a from lat"
  [a lat]
  (cond
    (not (seq lat)) '()
    (= (first lat) a) (multirember a (rest lat))
    :else (cons (first lat) (multirember a (rest lat)))))

(defn multiinsertR
  "Inserts item to the right of old with new"
  [nu old lat]
  (cond
    (not (seq lat)) '()
    (eq? (first lat) old) (cons old (cons nu (multiinsertR nu old (rest lat))))
    :else (cons (first lat) (multiinsertR nu old (rest lat)))))

(defn multiinsertL
  "Inserts item to the left of old with new"
  [nu old lat]
  (cond
    (not (seq lat)) '()
    (eq? (first lat) old) (cons nu (cons old (multiinsertL nu old (rest lat))))
    :else (cons (first lat) (multiinsertL nu old (rest lat)))))

(defn multisubst
  "Replaces all instances of old with nu"
  [nu old lat]
  (cond
    (not (seq lat)) '()
    (eq? (first lat) old) (cons nu (multisubst nu old (rest lat)))
    :else (cons (first lat) (multisubst nu old (rest lat)))))
