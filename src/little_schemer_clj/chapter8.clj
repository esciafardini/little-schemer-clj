(ns little-schemer-clj.chapter8
  "Lambda The Ultimate"
  (:require
   [clojure.string :as string]
   [little-schemer-clj.chapter1 :refer [atom?]]
   [little-schemer-clj.chapter3 :refer [insertL subst]]
   [little-schemer-clj.chapter6 :refer [exp third]]))

;;this chapter be kinda hard brother

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;The Ninth Commandment
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; Abstract common patterns with a new function
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;The Tennth Commandment
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; Build functions to collect more than one value at a time
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn rember-f
  "Takes a predicate function...
   Returns a function that will recursively call predicate function
   on first item & if f resolves truthy, item is removed"
  [test-f]
  (fn [a l]
    (cond
      (not (seq l)) '()

      (test-f (first l) a)
      (rest l)

      :else
    ;kinda weird but ultimately just recursion
      (cons (first l) ((rember-f test-f) a (rest l))))))

;Currying
(defn curried=
  "Currying is a function that returns a function with a passed-in argument
   Useful when not all arguments are provided right away at function call"
  [a]
  (fn [x] (= x a)))

(defn insertL-f
  "Curry with the Sauce
   Returns a FUNCTION that will add an item to the left of FIRST occurrence in list
   based on predicate function passed in."
  [f]
  (fn [nu old lat]

    (cond
      (not (seq lat))
      '()

      (f old (first lat))
      (cons nu lat)

      :else
      (cons (first lat) ((insertL-f f) nu old (rest lat))))))

(defn insertR-f
  "more curry for the sauce
   to the right hand side"
  [f]
  (fn [nu old l]
    (cond
      (not (seq l)) '()

      (f old (first l))
      (cons old (cons nu (rest l)))

      :else
      (cons (first l) ((insertR-f f) nu old (rest l))))))

(defn insert-g-my-first-pass
  "inserts either at left or right depending on second argument"
  [f direction]
  (if (= direction :right)
    (insertR-f f)
    (insertL-f f)))

(defn seqR
  "adds nu item to the right of old item"
  [nu old l]
  (cons old (cons nu l)))

(defn seqL
  "adds nu item to the left of old item"
  [nu old l]
  (cons nu (cons old l)))

(defn insert-g
  "List altering function
   does the function you pass into it when
   (= old (first l)

   This abstraction can be used for insertL, insertR, subst"
  [seq-fn]
  (fn [nu old l]
    (cond
      (not (seq l)) '()

      (= old (first l)) (seq-fn nu old (rest l))

      :else
      (cons (first l) ((insert-g seq-fn) nu old (rest l))))))

(defn seqS [nu _old l]
  (cons nu (rest l)))

(def subst2 (insert-g seqS))
(subst2 "carrot" "chicken" ["i" "eat" "chicken"])

(defn seqrem [_nu _old l]
  l)

(defn yyy [a l]
  ((insert-g seqrem) false a l))

(defn yyyy [a l]
  ((insert-g (fn [_nu _old l] l)) 777 a l))

(defn atom-to-fn [x]
  (cond
    (= x *)
    (fn [arg1 arg2] (* arg1 arg2))

    (= x +)
    (fn [arg1 arg2] (+ arg1 arg2))

    :else
    (fn [arg1 arg2] (exp arg1 arg2))))

(defn value
  "This is value for regular s-expressions"
  [aexp]
  (if
   (atom? aexp)
    aexp
    ((atom-to-fn (first aexp)) (second aexp) (third aexp))))

(defn multi-rember-f
  "After all people come and people go away
   God with me He stays
   Pass in a function to remove items on"
  [test-f]
  (fn [a lat]
    (cond
      (not (seq lat)) '()

      (test-f a (first lat))
      ((multi-rember-f test-f) a (rest lat))

      :else
      (cons (first lat) ((multi-rember-f test-f) a (rest lat))))))

(defn multiremberT
  "takes a test-f and does the work on the lat"
  [test-f lat]
  (cond
    (not (seq lat))
    '()

    (test-f (first lat))
    (multiremberT test-f (rest lat))

    :else
    (cons (first lat) (multiremberT test-f (rest lat)))))

(defn equals-chicken? [x]
  (= x "chicken"))

(defn multirember&co
  "This will rember and then some
   takes an atom, a list, and a function

   ultimately function is called on two args -
   1st arg: the new list with items removed
   2nd arg: a list made of all removed items "
  [a lat f]
  (cond
    (not (seq lat))
    (f '() '())

    (= (first lat) a)
    (multirember&co a
                    (rest lat)
                    (fn [newlat removed-items]
                      (f
                       newlat
                       (cons (first lat) removed-items))))

    :else
    (multirember&co a
                    (rest lat)
                    (fn [newlat removed-items]
                      (f
                       (cons (first lat) newlat)
                       removed-items)))))

(defn multiinsertLR
  "will insert nu to the left of oldL and to the right of oldR"
  [nu oldL oldR lat]
  (cond
    (not (seq lat)) '()

    (= oldL (first lat))
    (cons nu (cons oldL (multiinsertLR nu oldL oldR (rest lat))))

    (= oldR (first lat))
    (cons oldR (cons nu (multiinsertLR nu oldL oldR (rest lat))))

    :else
    (cons (first lat) (multiinsertLR nu oldL oldR (rest lat)))))

(defn multiinsertLR&co
  "builds a new collection and counts the number of L inserts + R inserts separately"
  [nu oldL oldR lat f]
  (cond
    (not (seq lat))
    (f '() 0 0)

    (= oldL (first lat))
    (multiinsertLR&co nu oldL oldR (rest lat)
                      (fn [new-lat left-insertions right-insertions]
                        (f
                         (cons nu (cons oldL new-lat))
                         (inc left-insertions)
                         right-insertions)))

    (= oldR (first lat))
    (multiinsertLR&co nu oldL oldR (rest lat)
                      (fn [new-lat left-insertions right-insertions]
                        (f
                         (cons (first lat) (cons nu new-lat))
                         left-insertions
                         (inc right-insertions))))

    :else
    (multiinsertLR&co nu oldL oldR (rest lat)
                      (fn [new-lat left-insertions right-insertions]
                        (f
                         (cons (first lat) new-lat)
                         left-insertions
                         right-insertions)))))

(multiinsertLR&co
 "fried" "chicken" "deep"
 ["chicken" "chicken" "chickken" "chhwe" "ggonnnaa" "eat" "chicken" "and" "deep" "oreos" "HEEEH" "chicken" "we" "LIKE" "that" "in" "deep" "south"]
 (fn [x y z]
   (println x)
   (println y)
   (println z)))

(defn evens-only* [lat]
  (cond
    (not (seq lat)) '()

    (not (coll? (first lat)))
    (if (odd? (first lat))

      (evens-only* (rest lat))
      (cons (first lat) (evens-only* (rest lat))))

    :else
    (cons (evens-only* (first lat)) (evens-only* (rest lat)))))

(defn evens-only*&co
  "Combines the concepts of * functions (handle nested collections)
   and &co functions where collectors are making lists & return values
   as the function recurses.

   I do not understand this fully.  Need to take some time with it."
  [lat f]
  (let [idx0 (first lat)
        remaining (rest lat)]
    (cond
      (not (seq lat))
      (f lat 1 0)

      ;; a singular atom at index 0!
      (not (coll? idx0))
      (if (even? idx0)
        ;; EVEN!
        (evens-only*&co remaining
                        (fn [new-lat evens-product odds-sum]
                          (f (cons idx0 new-lat) (* idx0 evens-product) odds-sum)))

        ;; ODD!
        (evens-only*&co remaining
                        (fn [new-lat evens-product odds-sum]
                          (f new-lat evens-product (+ idx0 odds-sum)))))

      :else
      ;; a collection at index 0!
      (evens-only*&co idx0
                      (fn [nl ep os]
                        (evens-only*&co remaining
                                        (fn [new-lat evens-product odds-sum]
                                          (f (cons nl new-lat) (* ep evens-product) (+ os odds-sum)))))))))

