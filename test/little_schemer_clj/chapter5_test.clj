(ns little-schemer-clj.chapter5-test
  (:require
   [clojure.test :refer [deftest is]]
   [little-schemer-clj.chapter3 :refer [multirember]]
   [little-schemer-clj.chapter5 :refer [eqlist? insertL* insertR* leftmost
                                        member* occur* rember*
                                        subst*]]))

(deftest breaking-down-rember*
  (deftest smallest-parts-rember*
    ; "cup" is not part of data structure
    ; notice surrounding parens remain in tact
    ; why?
    (is (= (rember* "cup" '("coffee"))
           '("coffee")))

    ; "cup" is not part of data structure
    ; notice surrounding parens remain in tact
    ; why?
    (is (= (rember* "cup" [["tea"]])
           [["tea"]]))

    ; This is because whenever the first item passed is a collection, the following function is called:
    ;(cons (rember* a idx0) (rember* a remaining))
    ;In these instances:
    (is (= (rember* "cup" [["tea"]])
           ;a series of first & rest
           (cons (rember* "cup" ["tea"]) (rember* "cup" []))
           (cons (rember* "cup" ["tea"]) [])
           ; when idx0 is an atom, and does not match:
           ; (cons idx0 (rember* a remaining))
           (cons (cons "tea" (rember* "cup" [])) [])
           (cons (cons "tea" []) [])
           [["tea"]]))))

(deftest chapter-5
  (is
   (=
    [["how" "much" []]
     "could"
     [["a" [] "chuck"]]
     ["if" ["a"] [[["chuck"]]] "could"]
     [["chuck"]]]
    (rember* "wood" [["how" "much" ["wood"]]
                     "could"
                     [["a" ["wood"] "chuck"]]
                     ["if" ["a"] "wood" [[["chuck"]]] "could"]
                     [["chuck"] "wood"]])))

  (is
   (=
    (multirember "wood" ["wood" ["how" "much" ["wood"]]
                         "could"
                         "wood"
                         [["a" ["wood"] "chuck"]]
                         ["if" ["a"] "wood" [[["chuck"]]] "could"]
                         [["chuck"] "wood"]])
    '(["how" "much" ["wood"]]
      "could"
      [["a" ["wood"] "chuck"]]
      ["if" ["a"] "wood" [[["chuck"]]] "could"]
      [["chuck"] "wood"])))
  (is
   (=
    nil
    (leftmost [[[[] "four"] 17 ["seventeen"]]])
    (leftmost '())))
  (is
   (=
    "deep nest"
    (leftmost [[[[[[[["deep nest"]]]]] "four"] 17 ["seventeen"]]])))

  (is
   (=
    true
    (eqlist? [1 29 [1 2 [3 4]] 2 [2 2 3] 2 3] [1 29 [1 2 [3 4]] 2 [2 2 3] 2 3])))

  (is
   (=
    false
    (eqlist? [2 29 [1 2 [3 4]] 2 [2 2 3] 2 3] [1 29 [1 2 [3 4]] 2 [2 2 3] 2 3])))

  (is
   (=
    (insertR* "roast" "chuck" [["how" "much" ["wood"]]
                               "could"
                               [["a" ["wood"] "chuck"]]
                               ["if" ["a"] "wood" [[["chuck"]]] "could"]
                               [["chuck"] "wood"]])
    '(("how" "much" ("wood"))
      "could"
      (("a" ("wood") "chuck" "roast"))
      ("if" ("a") "wood" ((("chuck" "roast"))) "could")
      (("chuck" "roast") "wood"))))

  (is
   (=
    (occur* "banana" [["banana"] "yas" ["split" ["omg" "its" "banana" ["splut" "ice" "cream"]] "banana"]])
    (+ (occur* "banana" ["banana"]) (occur* "banana" ["yas" ["split" ["omg" "its" "banana" ["splut" "ice" "cream"]] "banana"]]))
    (+ (inc 0) (occur* "banana" ["yas" ["split" ["omg" "its" "banana" ["splut" "ice" "cream"]] "banana"]]))
    (+ (inc 0) (occur* "banana" ["split" ["omg" "its" "banana" ["splut" "ice" "cream"]] "banana"]))
    (+ (inc 0) (occur* "banana" [["omg" "its" "banana" ["splut" "ice" "cream"]] "banana"]))
    (+ (inc 0) (+ (occur* "banana" ["omg" "its" "banana" ["splut" "ice" "cream"]]) (occur* "banana" ["banana"])))
    (+ (inc 0) (+ (occur* "banana" ["omg" "its" "banana" ["splut" "ice" "cream"]]) (inc 0)))
    (+ (inc 0) (+ (occur* "banana" ["its" "banana" ["splut" "ice" "cream"]]) (inc 0)))
    (+ (inc 0) (+ (occur* "banana" ["banana" ["splut" "ice" "cream"]]) (inc 0)))
    (+ (inc 0) (+ (inc (occur* "banana" [["splut" "ice" "cream"]])) (inc 0)))
    (+ (inc 0) (+ (inc (+ 0 0)) (inc 0)))))

  (is
   (=
    (subst* "orange" "banana" [["banana"] "yas" ["split" ["omg" "its" "banana" ["splut" "ice" "cream"]] "banana"]])
    '(("orange")
      "yas"
      ("split" ("omg" "its" "orange" ("splut" "ice" "cream")) "orange"))))

  (insertL* "fire" "wood"
            [["how" "much" ["wood"]]
             "could"
             [["a" ["wood"] "chuck"]]
             ["if" ["a"] "wood" [[["chuck"]]] "could"]
             [["chuck"] "wood"]])

  (is
   (=
    (leftmost
     [["how" "much" ["wood"]]
      "could"
      [["a" ["wood"] "chuck"]]
      ["if" ["a"] "wood" [[["chuck"]]] "could"]
      [["chuck"] "wood"]])
    "how"))

  (is
   (=
    true
    (member*
     "if"
     [["how" "much" ["wood"]]
      "could"
      [["a" ["wood"] "chuck"]]
      ["if" ["a"] "wood" [[["chuck"]]] "could"]
      [["chuck"] "wood"]]))))
