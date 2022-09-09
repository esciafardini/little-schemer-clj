(ns little-schemer-clj.chapter5-test
  (:require
   [clojure.test :refer [deftest is]]
   [little-schemer-clj.chapter5 :refer [rember*]]))

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

(comment "FIX DEEZ"

(= [["coffee"] [["tea"]] ["and" ["hick"]]]
   (rember* "cup" [["coffee"] "cup" [["tea"] "cup"] ["and" ["hick"]] "cup"]))

; the * walks through each collection, I suppose...

; Right away, I am noticing Chapter 5 is more difficult than previous chapters

;step by step
(rember* "cup" [["coffee"] "cup" [["tea"] "cup"] ["and" ["hick"]] "cup"])
; 1
; ["coffee"] is idx0
; (coll? idx0) => true
; (cons (rember* "cup" ["coffee"]) (rember* "cup" [[["tea"] "cup"] ["and" ["hick"]] "cup"])

(cons
 (rember* "cup" ["coffee"])
 (rember* "cup" [[["tea"]] ["and" ["hick"]]]))

; (rember* "cup" ["coffee"])
; idx0 is "coffee"
; "coffee" != "cup"
; (cons "coffee" (rember* '()))

; RECALL: (rember* '()) => '()
; (cons "coffee" '())

(cons "coffee" '())

;ultimately, returns '(coffee) as it was in its original form

;if
(rember* "cup" ["coffee"]) = '("coffee")
;then
(cons (rember* "cup" ["coffee"]) (rember* "cup" [[["tea"] "cup"] ["and" ["hick"]] "cup"]))
; =
(cons '("coffee") (rember* "cup" [[["tea"] "cup"] ["and" ["hick"]] "cup"]))
;
;
;; ok, so what happens here then.....
(rember* "cup" [[["tea"]] ["and" ["hick"] "cup"]])

; idx0 is
[["tea"]]
; remaining is
["and" ["hick"] "cup"]

;interesting...so idx0 it is a collection
;I just want to see how this pans out...
(cons (rember* "cup" [["tea"]]) (rember* "cup" ["and" ["hick"] "cup"]))
;
(rember* "cup" [["tea"]])
; =
(cons (rember* "cup" ["tea"]) (rember* "cup" '()))

;now rember* hits else block bc "tea" is not a collection
(cons
 (cons "tea" (rember* "cup" '()))
 (rember* "cup" '()))

(cons
 (cons "tea" '())
 '())

;Ultimately, items are recursively cons'd back into '() empty seqs
)
