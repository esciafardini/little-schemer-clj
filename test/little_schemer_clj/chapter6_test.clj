(ns little-schemer-clj.chapter6-test
  (:require
   [little-schemer-clj.chapter1 :refer [atom?]]
   [little-schemer-clj.chapter6 :refer [arithmetic-expression? edd1
                                        first-sub-exp numbered? sero? value value-infix
                                        zub1]]))

;TODO - make tests out of these
(value '(* 9 (+ (exp 2 3) 4)))

(numbered? '((9 * 8) + (8 exp (9 + (4 exp 4)))))
(numbered? '((9 * 8) + (8 exp (9 + (4 - 4)))))

(atom? +)

;steps of numbered?
(numbered? '((1 + 5) * (2 exp 3)))
;1
;count is 3
(and
 (numbered? '(1 + 5))
 (arithmetic-expression? '+)
 (numbered? '(2 exp 3)))

(and
 (and
  (numbered? 1) ;effectively (number? 1)
  (arithmetic-expression? '+)
  (numbered? 5))
 true
 (and
  (numbered? 2)
  (arithmetic-expression? 'exp)
  (numbered? 3)))

(first-sub-exp [5 ['ick 'yick] 7 6])

(atom? '(1 2 3))

(value-infix '(1 + (4 * 5)))
(value-infix '((11 exp (2 + 2)) + (4 * 2)))

(zub1 '((list) (list)))

(def sero (list))
(def wun (list (list)))
(def too (edd1 (edd1 sero)))
(def threi (edd1 too))
(def fore (edd1 threi))
(sero? sero)
(sero? wun)
(zub1 fore)
(edd1 fore)
(edd1 (edd1 (edd1 sero)))
