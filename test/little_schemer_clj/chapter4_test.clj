(ns little-schemer-clj.chapter4-test
  (:require
   [clojure.test :refer :all]
   [little-schemer-clj.chapter4 :refer [add1 addtup divide greater-than length
                                        less-than minus modulo multiply
                                        no-nums pick plus plus-from-book rempick sub1 tup+ tup?]]))

(deftest plus-minus-equivalences

  (is (= 7 (plus-from-book 2 5)))
  (is (= 7 (add1 (plus-from-book 5 1))))
  (is (= 7 (add1 (add1 (plus-from-book 1 4)))))
  (is (= 7 (add1 (add1 (add1 (plus-from-book 4 0))))))
  (is (= 7 (add1 (add1 (add1 (add1 (plus-from-book 0 3)))))))
  ;terminal condition met
  (is (= 3 (plus-from-book 0 3)))
  (is (= 7 (add1 (add1 (add1 (add1 3))))))

  (is (= 7 (plus-from-book 4 3)))
  (is (= 7 (add1 (plus-from-book 3 3))))
  (is (= 7 (add1 (add1 (plus-from-book 3 2)))))
  (is (= 7 (add1 (add1 (add1 (plus-from-book 2 2))))))
  (is (= 7 (add1 (add1 (add1 (add1 (plus-from-book 2 1)))))))
  (is (= 7 (add1 (add1 (add1 (add1 (add1 (plus-from-book 1 1))))))))
  (is (= 7 (add1 (add1 (add1 (add1 (add1 (add1 (plus-from-book 0 1)))))))))
  ;terminal condition met
  (is (= 1 (plus-from-book 0 1)))
  (is (= 7 (add1 (add1 (add1 (add1 (add1 (add1 1))))))))

  (is (= 7 (minus 10 3)))
  (is (= 7 (sub1 (minus 10 2))))
  (is (= 7 (sub1 (sub1 (minus 10 1)))))
  (is (= 7 (sub1 (sub1 (sub1 (minus 10 0))))))
  ;terminal condition met
  (is (= 10 (minus 10 0)))
  (is (= 7 (sub1 (sub1 (sub1 10)))))

  (is (= (plus 3 4) (plus-from-book 3 4))))

(deftest chapter-4-fns

  (is (= 11 (plus 4 7)))
  (is (= (plus 4 7) (add1 (add1 (add1 (add1 7))))))
  (is (= 10 (plus 3 (plus 3 4))))
  (is (= (plus 1 0) (addtup [1])))
  (is (= 43 (addtup [15 6 7 12 3])))
  (is (= 132 (addtup [99 33])))
  (is (= nil (addtup [1 2 3 "4"])))

  (is (= false (tup? 3)))
  (is (= false (tup? [1 "2" 3 4])))
  (is (= true (tup? [1 2 3 4])))
  (is (= true (tup? [1 2 3 4 5])))

  (is (= 36
         (multiply 12 3)
         (+ 12 (multiply 12 2))
         (+ 12 (+ 12 (multiply 12 1)))
         (+ 12 (+ 12 (+ 12 (multiply 12 0))))
         (+ 12 (+ 12 (+ 12 0)))))

  (is (= 7 (minus 10 3)))

  (is (= [(plus 3 9) (plus 1 6) 5 4 3 2 1 0]
         (tup+ [3 1] [9 6 5 4 3 2 1 0])))

  (is (= '(12 7 5 4 3 2 1)
         (cons 12 '(7 5 4 3 2 1))
         (cons 12 (cons 7 '(5 4 3 2 1)))))

  (is (= false (greater-than 1 23)))
  (is (= false (greater-than 5 5)))
  (is (= true (greater-than 2349 2)))

  (is (= true (less-than 3 4)))
  (is (= false (less-than 4 4)))
  (is (= false (less-than 4 3))))

(deftest divide-and-conquer
  (is (= (divide 15 4)
         (add1 (divide 11 4))
         (add1 (add1 (divide 7 4)))
         (add1 (add1 (add1 (divide 3 4))))
         (add1 (add1 (add1 0)))))
  (is (= 2 (modulo 12 5)))
  (is (= 0 (modulo 5 5))))

(deftest recursive-fns
  (is (= 5 (length [1 2 3 4 5])))
  (is (= 6 (length ["hotdogs" "with" "mustard" "sauerkraut" "and" "pickles"])))
  (is (= ["hotdogs" "with" "mustard"]
         (cons "hotdogs" (cons "with" '("mustard")))
         (rempick 3 ["hotdogs" "with" "hot" "mustard"])))
  (is (= ["pears" "prunes" "dates"] (no-nums '(5 "pears" 6 "prunes" 9 "dates"))))
  (is (= "one" (pick 1 ["one" "two" "three" "four" "five" "six"])))
  (is (= nil (pick 7 ["one" "two" "three" "four" "five" "six"])))
  (is (= nil (pick 0 ["two" "three"])))
  (is (= 'macaroni (pick 4 ['lasagna 'spaghett 'ravioli 'macaroni 'meatball]))))
