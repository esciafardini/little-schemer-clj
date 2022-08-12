(ns little-schemer-clj.chapter4
  (:require
   [little-schemer-clj.chapter1 :refer [car cdr]]))

;; Numbers Games

;; All numbers are atoms
;; This book only onsiders non-negative integers (including 0)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;The First Commandment (first revision)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; When recurring on a list of atoms (lat),
;;  Ask 2 questions about it:
;;   1. (null? lat)
;;   2. else
;;
;; When recurring on a number (n),
;;  Ask 2 questions about it:
;;   1. (zero? n)
;;   2. else
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def add1 inc)

(defn sub1
  "Little schemer only deals with non-negative integers"
  [x]
  (if (> x 0)
    (dec x)
    nil))

;Recursive plus function using sub1, add1, zero?

(defn plus-from-book
  "Terminal condition is when a number reaches zero...
   Subtracting 1 from each until one of them reaches zero,
   ping ponging btw sub1-ing x & sub1-ing y
   all the while wrapping in `add1` on each iteration"
  [x y]
  (if (zero? x)
    y
    (add1 (plus-from-book y (sub1 x))))) ;=> "y" eventually reaches zero

;almost like do this x + y times
;cons builds lists, add1 builds numbers

(defn plus
  "Keep It Simple, Schemer.
   Rather than ping ponging between the two (and hurting my head) - 
   just inc x times to y
   recurses smaller # of times
   Think of plus as cons for building numbers"
  [x y]
  (let [smaller (min x y)
        bigger (max x y)]
    (if (zero? smaller) bigger
        (add1 (plus bigger (sub1 smaller))))))

;ends up like thissss:
;; (plus 4 7)
;; (add1 (add1 (add1 (add1 7)
(plus 4 7)
(add1 (add1 (add1 (add1 7))))

(defn minus
  "Subtraction: [minuend - subtrahend = difference]
  Terminal condition is when subtrahend reaches zero
  Just keep decrementing until subtrahend = 0"
  [x y]
  (if (zero? y)
    x
    (sub1 (minus x (sub1 y)))))

(defn tup?
  "Let's define this recursively.
   Check each element with int?
   `tuple` in this context: list of numbers"
  [coll]
  (cond
    (string? coll) false
    (int? coll) false
    (not (seq coll)) true
    (int? (car coll)) (tup? (cdr coll))
    :else false))

(defn addtup
  "My remix - before looking @ book's solution
   Adds up all numbers in tup & returns single value"
  [tup]
  (when (tup? tup)
    (if
     (not (seq tup)) 0
     (plus (car tup) (addtup (cdr tup))))))

;TODO - move to test file
(plus 3 (plus 3 4))
(addtup [15 6 7 12 3])
(addtup [99 33])
(tup? [1 2 3 4])
(tup? 3)
(tup? "whattt")
(tup? [1 2 3 4])

(tup? [1 2 3 4 5])
(cons (plus 1 2) (addtup [3 4 5]))
