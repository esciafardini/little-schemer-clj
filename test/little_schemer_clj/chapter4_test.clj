(ns little-schemer-clj.chapter4-test
  (:require
   [clojure.test :refer :all]
   [little-schemer-clj.chapter4 :refer [add1 minus plus plus-from-book sub1]]))

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
  
  (is (= (plus 3 4) (plus-from-book 3 4)))
  )
