(ns little-schemer-clj.chapter4-test
  (:require
   [clojure.test :refer :all]
   [little-schemer-clj.chapter4 :refer [add1 minus plus sub1]]))

(deftest plus-minus-equivalences

  (is (= 7 (plus 2 5)))
  (is (= 7 (add1 (plus 5 1))))
  (is (= 7 (add1 (add1 (plus 1 4)))))
  (is (= 7 (add1 (add1 (add1 (plus 4 0))))))
  (is (= 7 (add1 (add1 (add1 (add1 (plus 0 3)))))))
  ;terminal condition met
  (is (= 3 (plus 0 3)))
  (is (= 7 (add1 (add1 (add1 (add1 3))))))

  (is (= 7 (plus 4 3)))
  (is (= 7 (add1 (plus 3 3))))
  (is (= 7 (add1 (add1 (plus 3 2)))))
  (is (= 7 (add1 (add1 (add1 (plus 2 2))))))
  (is (= 7 (add1 (add1 (add1 (add1 (plus 2 1)))))))
  (is (= 7 (add1 (add1 (add1 (add1 (add1 (plus 1 1))))))))
  (is (= 7 (add1 (add1 (add1 (add1 (add1 (add1 (plus 0 1)))))))))
  ;terminal condition met
  (is (= 1 (plus 0 1)))
  (is (= 7 (add1 (add1 (add1 (add1 (add1 (add1 1))))))))

  (is (= 7 (minus 10 3)))
  (is (= 7 (sub1 (minus 10 2))))
  (is (= 7 (sub1 (sub1 (minus 10 1)))))
  (is (= 7 (sub1 (sub1 (sub1 (minus 10 0))))))
  ;terminal condition met
  (is (= 10 (minus 10 0)))
  (is (= 7 (sub1 (sub1 (sub1 10))))))
