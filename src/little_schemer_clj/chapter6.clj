(ns little-schemer-clj.chapter6
  "Shadows"
  (:require
   [little-schemer-clj.chapter1 :refer [atom?]]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;The Seventh Commandment
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Recur on the subparts that are of the same nature
;;  1. On the ublists of a list
;;  2. On the subexpressions of an arithmetic expression
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;The Eighth Commandment
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Use help functions to abstract from representations
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;I think this chapter revolves around infix notation
; (7 + 2)

;numbers are arithmetic expressions
;equations are arithmetic expressions

(defn exp
  "Exponent- represents up arrow in Little Schemer"
  [x n]
  (reduce * (repeat n x)))

(defn arithmetic-expression? [x]
  (let [arithmetic-expressions #{'* '+ 'exp}]
    (contains? arithmetic-expressions x)))

(defn third
  "Like first and second- but third
   8th Commandment Style"
  [l]
  (if (coll? l)
    (first (rest (rest l)))
    nil))

(defn numbered?
  "Predicate function that checks whether a single number (not in a
   list) is passed in or an arithmetic expression with in-fix
   notation in a list, such as (3 * (4 + 1)).
   Not sure what the purpose of this is.
   Checks first and third are numbers.
   Checks that center is an arithmetic expression.
   Recurses if the third or first is an infix equation itself."
  [aexp]
  (cond
    ;first and third of each eq will be checked here as #s
    (atom? aexp) (number? aexp)

    ;otherwise...
    :else
    (if
     (and
      ;infix should look like this: (9 * 5)
      (= 3 (count aexp))
      ;make sure first one is a number or fits the shape (n exp n)
      (numbered? (first aexp))
      ;make sure second one is an arithmetic expression
      (arithmetic-expression? (second aexp))
      ;make sure third one is a number or fits the shape (n exp n)
      (numbered? (third aexp)))
      true
      false)))

(defn first-sub-exp
  "Provided in Clojure as 'second"
  [aexp]
  (first (rest aexp)))

(defn value-infix
  "The 7th Commandment says to recur on the subparts that are the same in nature.
   The second item in any 'numbered' list is always on of the arithmetic-expressions"
  [aexp]
  (when (numbered? aexp)
    (cond
      (atom? aexp) aexp

      (= (second aexp) '+)
      (+ (value-infix (first aexp))
         (value-infix (third aexp)))

      (= (second aexp) 'exp)
      (exp (value-infix (first aexp))
           (value-infix (third aexp)))

      :else
      (* (value-infix (first aexp))
         (value-infix (third aexp))))))

(defn value
  "This is value for regular s-expressions"
  [aexp]

  (cond
    (atom? aexp) aexp

    :else
    (let [expr (first aexp)
          x (second aexp)
          y (third aexp)]

      (cond
        (= expr '+)
        (+ (value x)
           (value y))

        (= expr '*)
        (* (value x)
           (value y))

        :else
        (exp (value x)
             (value y))))))

;LOL THIS BOOK

;a new way to represent numbers
; 0: ()
; 1: (())
; 2: (()())

(defn sero? [n]
  (not (seq n)))

(defn edd1 [n]
  (cons '() n))

(defn zub1 [n]
  (rest n))
