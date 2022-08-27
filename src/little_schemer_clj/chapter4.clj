(ns little-schemer-clj.chapter4
  (:require
   [little-schemer-clj.chapter2 :refer [lat?]]))

;; Numbers Games

;; All numbers are atoms
;; This book only considers non-negative integers (including 0)

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
;;
;;  Recall First Commandment:
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
    (int? (first coll)) (tup? (rest coll))
    (not (seq coll)) true ;terminal condition
    :else false))

(defn addtup
  "My remix - before looking @ book's solution
   Adds up all numbers in tup & returns single value"
  [tup]
  (when (tup? tup)
    (if (not (seq tup))
      0
      (plus (first tup) (addtup (rest tup))))))

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

(defn tup+ignore-extras
  "This will recursively add values at set idx's in a tuple
   to create a new tuple,
   behaves like Clojure where additional vals are ignored"
  [tup1 tup2]
  (cond
    (or
     (not (seq tup2))
     (not (seq tup1))) '()
    :else
    (cons (plus (first tup1) (first tup2)) (tup+ignore-extras (rest tup1) (rest tup2)))))

(defn tup+
  "The book's version
   which is a lil clever IMO, and finishes the tup+ regardless of differing lengths"
  [tup1 tup2]
  (cond
    (not (seq tup2)) tup1
    (not (seq tup1)) tup2
    :else
    (cons (plus (first tup1) (first tup2)) (tup+ (rest tup1) (rest tup2)))))

(defn greater-than
  "using >> because > is defined in clj already"
  [x y]
  (cond
    (zero? x) false
    (zero? y) true
    :else
    (greater-than (sub1 x) (sub1 y))))

(defn less-than
  "using << because > is defined in clj already"
  [x y]
  (cond
    (zero? y) false
    (zero? x) true
    :else
    (less-than (sub1 x) (sub1 y))))

(defn eq-n
  "Numbers only - see `eq?` from chap1 for other atoms
   I think there's a typo in the book?
   Oh well, define = in terms of < and >"
  [x y]
  (cond
    (or (greater-than y x) (less-than y x)) false
    :else true))

(defn exp
  "Recursive fn for finding exponent"
  [x y]
  (if
   (zero? y) 1
   (multiply x (exp x (sub1 y)))))

(defn divide
  "Division without consideration for modulo
   Counts how many times the second arg goes into the first"
  [x y]
  (cond
    (less-than x y) 0
    :else
    (add1 (divide (minus x y) y))))

(defn modulo
  "Recursive definition for mod"
  [x y]
  (cond
    (less-than x y) x
    :else
    (modulo (minus x y) y)))

(defn length [lat]
  (when (lat? lat)
    (if
     (not (seq lat)) 0
     (add1 (length (rest lat))))))

(defn pick
  "recursively decrements n until it gets to 1
   returns nil if n gets to zero (only happens when arg is set to 0)
   returns nil if n is larger than size of lat because:
   (car '()) is nil and n is dec'd on each recursion"
  [n lat]
  (when (lat? lat)
    (cond
      (eq-n n 0) nil
      (eq-n n 1) (first lat)
      :else
      (pick (sub1 n) (rest lat)))))

;; I just decided - no more cdr or car
;; it's too annoying and I am trying to learn Clojure not Scheme
;;
;;let's utilize destructuring for first and rest next to see

(defn no-nums
  "recursively removes all numbers from lat"
  [lat]
  (let [[first-item & remaining-items] lat]
    (cond
      (not (seq lat)) '()
      (number? first-item) (no-nums remaining-items)
      :else
      (cons first-item (no-nums remaining-items)))))
;IMO - the destructuring makes things worse here...
;case by case basis, I guess

(defn all-nums
  "recursively removes all non-numbers from lat"
  [lat]
  (cond
    (not (seq lat)) '()
    (not (number? (first lat))) (all-nums (rest lat))
    :else
    (cons (first lat) (all-nums (rest lat)))))

;;not going to bother with eqan? fn because we have clojure core's =

(defn occur
  "counts the number of times an atom a appears in the lat"
  [a lat]
  (cond
    (not (seq lat)) 0
    (= (first lat) a) (inc (occur a (rest lat)))
    :else
    (occur a (rest lat))))

(defn one? [x]
  (= x 1))

(defn rempick
  "returns seq with nth item removed"
  [n lat]
  (if (one? n) (rest lat)
      (cons (first lat) (rempick (sub1 n) (rest lat)))))
