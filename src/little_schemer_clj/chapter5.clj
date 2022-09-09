(ns little-schemer-clj.chapter5
  (:require
   [little-schemer-clj.chapter3 :refer [multirember]]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;The First Commandment (final version)
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
;;
;; When recurring on a list of s-expressions,
;;  Ask 3 questions about it:
;;   1. (null? l)
;;   2. (not (coll? (first l)))
;;   3. else
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;The Fourth Commandment (final version)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Always change at least one argument while recurring.
;; Bring the argument closer and closer to terminal condition.
;;
;; When recurring on list of atoms lat:
;;  (first lat)
;; When recurring on a number n:
;;  (dec n)
;; When recurring on a list of s-expressions l:
;;  (not (coll? (first l)))
;;  (first l) & (rest l)
;;
;;  Recall First Commandment:
;;  With lists, test termination with (not (seq x))
;;  With numbers, test termination with (zero? x)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; What is a * function?
;;  One that recurs on both the first and the rest

(defn rember*
  "Retains data structure while recursively checking each collection for existence
   of atom a & removing it"
  [a coll]
  (let [idx0 (first coll)
        remaining (rest coll)]
    (cond
      (not (seq coll)) '()

      (not (coll? idx0))
      (cond
        (= idx0 a)
        (rember* a remaining)
        :else
        (cons idx0 (rember* a remaining)))

      :else
      (cons (rember* a idx0) (rember* a remaining)))))

(rember* "wood" [["how" "much" ["wood"]]
                 "could"
                 [["a" ["wood"] "chuck"]]
                 ["if" ["a"] "wood" [[["chuck"]]] "could"]
                 [["chuck"] "wood"]])

(multirember "wood" ["wood" ["how" "much" ["wood"]]
                     "could"
                     "wood"
                     [["a" ["wood"] "chuck"]]
                     ["if" ["a"] "wood" [[["chuck"]]] "could"]
                     [["chuck"] "wood"]])

(defn insertR*
  "Insert nu to the ~R~ight of old item wherever it shows up in coll"
  [nu old coll]
  (let [idx0 (first coll)
        remaining (rest coll)]
    (cond
      (not (seq coll)) '()

      (not (coll? idx0))
      (cond
        (= idx0 old) (cons old (cons nu (insertR* nu old remaining)))
        :else (cons idx0 (insertR* nu old remaining)))

      :else
      (cons (insertR* nu old idx0) (insertR* nu old remaining)))))

(insertR* "roast" "chuck" [["how" "much" ["wood"]]
                           "could"
                           [["a" ["wood"] "chuck"]]
                           ["if" ["a"] "wood" [[["chuck"]]] "could"]
                           [["chuck"] "wood"]])

(def counter (atom 0))

(defn occur-with-atom* [a l]
  (let [idx0 (first l)
        remaining (rest l)]
    (cond
      (not (seq l)) @counter

      (not (coll? idx0))
      (cond
        (= idx0 a) (do
                     (swap! counter inc)
                     (occur-with-atom* a remaining))
        :else
        (occur-with-atom* a remaining))

      :else
      (do
        (occur-with-atom* a idx0)
        (occur-with-atom* a remaining)))))

(occur-with-atom* "banana" [["banana"] "yas" ["split" ["omg" "its" "banana" ["splut" "ice" "cream"]] "banana"]])

(defn occur*
  "grok this you must"
  [a l]
  (let [idx0 (first l)
        remaining (rest l)]
    (cond
      (not (seq l)) 0

      (not (coll? idx0))
      (cond
        (= idx0 a) (inc (occur* a remaining))
        :else
        (occur* a remaining))

      :else
      (+ (occur* a idx0) (occur* a remaining)))))

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
(+ (inc 0) (+ (inc (+ 0 0)) (inc 0)))

(defn subst* [nu old l]
  (let [idx0 (first l)
        remaining (rest l)]
    (cond
      (not (seq l)) '()

      (not (coll? idx0))
      (if
       (= idx0 old) (cons nu (subst* nu old remaining))
       (cons idx0 (subst* nu old remaining)))

      :else
      (cons (subst* nu old idx0) (subst* nu old remaining)))))

(subst* "orange" "banana" [["banana"] "yas" ["split" ["omg" "its" "banana" ["splut" "ice" "cream"]] "banana"]])

(defn insertL* [nu old l]
  (let [idx0 (first l)
        remaining (rest l)]
    (cond
      (not (seq l)) '()

      (not (coll? idx0))
      (if (= old idx0)
        (cons nu (cons old (insertL* nu old remaining)))
        (cons idx0 (insertL* nu old remaining)))

      :else
      (cons (insertL* nu old idx0) (insertL* nu old remaining)))))

(insertL* "fire" "wood"
          [["how" "much" ["wood"]]
           "could"
           [["a" ["wood"] "chuck"]]
           ["if" ["a"] "wood" [[["chuck"]]] "could"]
           [["chuck"] "wood"]])

(defn member*
  "walks through data structure and checks for existence of a"
  [a l]
  (let [idx0 (first l)
        remaining (rest l)]
    (cond
      (not (seq l)) false

      (not (coll? idx0))
      (if (= idx0 a)
        true
        (member* a remaining))

      :else
      (or
       (member* a idx0)
       (member* a remaining)))))

(member*
 "if"
 [["how" "much" ["wood"]]
  "could"
  [["a" ["wood"] "chuck"]]
  ["if" ["a"] "wood" [[["chuck"]]] "could"]
  [["chuck"] "wood"]])

(defn leftmost
  "Finds the leftmost atom in a non-empty list of S-expressions that does not contain the empty list
   This is not a (*) function because it only recurs on (first l)"
  [l]
  (let [idx0 (first l)]
    (if (not (coll? idx0))
      idx0
      (leftmost idx0))))

(leftmost
 [["how" "much" ["wood"]]
  "could"
  [["a" ["wood"] "chuck"]]
  ["if" ["a"] "wood" [[["chuck"]]] "could"]
  [["chuck"] "wood"]])

(leftmost [[[[] "four"] 17 ["seventeen"]]])
(leftmost '())
(leftmost [[[[[[[["deep nest"]]]]] "four"] 17 ["seventeen"]]])

(defn example [a l]
  (and (not (coll? a))
       (= (first l) a)))

(example "cheese" ["cheese" "pizza"])
