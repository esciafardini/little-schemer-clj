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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;The Fourth Commandment (first revision)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Always change at least one argument while recurring.
;; Bring the argument closer and closer to terminal condition.
;;  With lists, test termination with (not (seq x))
;;  With numbers, test termination with (zero? x)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;The Fifth Commandment
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; When building a value with `plus` always use 0 for terminal condition
;;  as this doesn't effect the value of addition
;; When building a value with `multiply` always use 1 for terminal condition
;;  as this doesn't effect the value of multiplication
;; When building a value with `cons` always use '() for the terminal condition
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
;decrement the smaller number until it's zero
;; all the while, adding 1 to the larger number for each decrement

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
  "tup(le) in this context: list of numbers
   Let's define this recursively.
   Check each element with int?"
  [coll]
  (cond
    (string? coll) false
    (int? coll) false
    (int? (car coll)) (tup? (cdr coll))
    (not (seq coll)) true ;terminal condition
    :else false))

(defn addtup
  "My remix - before looking @ book's solution
   Adds up all numbers in tup & returns single value"
  [tup]
  (when (tup? tup)
    (if (not (seq tup))
      0
      (plus (car tup) (addtup (cdr tup))))))

;TODO - move to test file
(plus 3 (plus 3 4))
(addtup [15 6 7 12 3])

(= (plus 1 0) (addtup [1]))

(addtup [99 33])
(tup? [1 2 3 4])
(tup? 3)
(tup? "whattt")
(tup? [1 "2" 3 4])

(tup? [1 2 3 4 5])
(cons (plus 1 2) (addtup [3 4 5]))

(addtup [1 2 3 "4"])

(defn multiply
  "To define multiply recursively using addition,
   one must think....
   Add x to x, y times"
  [x y]
  (if
   (zero? y) 0
   (plus x (multiply x (sub1 y)))))
; (multiply x (sub1)) => "the natural recursion"
; natural recurstion = "the part where the function calls itself"

(multiply 8 9)
(multiply 13 4)
(multiply 3 5)
(multiply 12 3)
;; (+ 12 (multiply 12 2))
;; (+ 12 (+ 12 (multiply 12 1)))
;; (+ 12 (+ 12 (+ 12 (multiply 12 0))))
;; (+ 12 (+ 12 (+ 12 0)))

(minus 10 3)

(defn tup+
  "This will recursively add values at set idx's in a tuple
   to create a new tuple,
   behaves like Clojure where additional vals are ignored"
  [tup1 tup2]
  (cond
    (or
     (not (seq tup2))
     (not (seq tup1))) '()
    :else
    (cons (plus (car tup1) (car tup2)) (tup+ (cdr tup1) (cdr tup2)))))

(defn tup+
  "The book's version
   which is a lil clever IMO, and finishes the tup+ regardless of differing lengths"
  [tup1 tup2]
  (cond
    (not (seq tup2)) tup1
    (not (seq tup1)) tup2
    :else
    (cons (plus (car tup1) (car tup2)) (tup+ (cdr tup1) (cdr tup2)))))

;so when one tuple returns empty, you cons everything onto the other tuple
;it would like kind of like
(tup+ [3 1] [9 6 5 4 3 2 1 0])
; (cons 12 (cons 7 (5 4 3 2 1 0)))
(cons 12 (cons 7 '(5 4 3 2 1)))

(tup+ [3 6 9 11 4] [8 5 2 0 7])
(tup+ [3 2 1 0] [9 85 100 7])
(tup+ [2 3 4 5 7 6 100 3102 30124 2321324 43] [3 4 5 6])

(defn >>
  "using >> because > is defined in clj already"
  [x y]
  (cond
    (zero? x) false
    (zero? y) true
    :else
    (>> (sub1 x) (sub1 y))))

(>> 6 5)
(>> 1 23)
(>> 2349 2)
(>> 3 3)
(>> 4 3)

(defn <<
  "using << because > is defined in clj already"
  [x y]
  (cond
    (zero? y) false
    (zero? x) true
    :else
    (<< (sub1 x) (sub1 y))))

(<< 3 4)
(<< 4 4)
(<< 4 3)
