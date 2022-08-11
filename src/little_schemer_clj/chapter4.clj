(ns little-schemer-clj.chapter4
  (:require
   [clojure.inspector :refer [atom?]]))

;; Numbers Games

(atom? 1414)
;; All numbers are atoms
;; This book only onsiders non-negative integers (including 0)

(def add1 inc)

(defn sub1
  "Little schemer only deals with non-negative integers"
  [x]
  (if (> x 0)
    (dec x)
    nil))

(add1 4)
(sub1 5)
(sub1 1)
(zero? 0)

;Recursive plus function using sub1, add1, zero?

(defn plus
  "Terminal condition is when a number reaches zero...
   Subtracting 1 from each until one of them reaches zero,
   all the while wrapping in `add1` on each iteration"
  [x y]
  (if (zero? x)
    y
    (add1 (plus y (sub1 x))))) ;=> "y" eventually reaches zero

;almost like do this x + y times

;cons builds lists, add1 builds numbers

(defn minus
  "Subtraction: [minuend - subtrahend = difference]
  Terminal condition is when subtrahend reaches zero
  Just keep decrementing until subtrahend = 0"
  [x y]
  (if (zero? y)
    x
    (sub1 (minus x (sub1 y)))))

;damn why is this so hard and weird to understand?
;it's very "interesting" to read a book that looks like its for children
;; that challenges my intelligence lol
