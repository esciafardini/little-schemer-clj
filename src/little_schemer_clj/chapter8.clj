(ns little-schemer-clj.chapter8
  "Lambda The Ultimate"
  (:require
   [clojure.string :as string]
   [little-schemer-clj.chapter1 :refer [atom?]]
   [little-schemer-clj.chapter3 :refer [insertL subst]]
   [little-schemer-clj.chapter6 :refer [exp third]]))

;;this chapter be kinda hard brother

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;The Ninth Commandment
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; Abstract common patterns with a new function
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;The Tennth Commandment
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; Build functions to collect more than one value at a time
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn rember-f
  "Takes a predicate function...
   Returns a function that will recursively call predicate function
   on first item & if f resolves truthy, item is removed"
  [test-f]
  (fn [a l]
    (cond
      (not (seq l)) '()

      (test-f (first l) a)
      (rest l)

      :else
    ;kinda weird but ultimately just recursion
      (cons (first l) ((rember-f test-f) a (rest l))))))

(def rember= (rember-f =))
(rember= 5 [1 2 3 4 5 6 7 8])

;Currying
(defn curried=
  "Currying is a function that returns a function with a passed-in argument
   Useful when not all arguments are provided right away at function call"
  [a]
  (fn [x] (= x a)))

((curried= "yes") "yes")
((curried= "no") "yes")

(insertL "jeff" "daniel" ["hey" "my" "mans" "or" "were" "IT" "daniel" "?"])

(defn insertL-f
  "Curry with the Sauce
   Returns a FUNCTION that will add an item to the left of FIRST occurrence in list
   based on predicate function passed in."
  [f]
  (fn [nu old lat]

    (cond
      (not (seq lat))
      '()

      (f old (first lat))
      (cons nu lat)

      :else
      (cons (first lat) ((insertL-f f) nu old (rest lat))))))

(def insertL-on-equal (insertL-f =))

(insertL-on-equal "jeff" "daniel" ["hey" "my" "mans" "or" "were" "IT" "daniel" "?"])

(defn insertR-f
  "more curry for the sauce
   to the right hand side"
  [f]
  (fn [nu old l]
    (cond
      (not (seq l)) '()

      (f old (first l))
      (cons old (cons nu (rest l)))

      :else
      (cons (first l) ((insertR-f f) nu old (rest l))))))

(def insertR-on-equal (insertR-f =))

(insertR-on-equal "jeff" "daniel" ["hey" "my" "mans" "or" "were" "IT" "daniel" "?"])

(defn insert-g-my-first-pass
  "inserts either at left or right depending on second argument"
  [f direction]
  (if (= direction :right)
    (insertR-f f)
    (insertL-f f)))

(def insertRG-on-equal (insert-g-my-first-pass = :right))

(insertRG-on-equal "jeff" "daniel" ["hey" "my" "mans" "or" "were" "IT" "daniel" "?"])

(def insertLG-on-equal (insert-g-my-first-pass = :left))

(insertLG-on-equal "jeff" "daniel" ["hey" "my" "mans" "or" "were" "IT" "daniel" "?"])

; Book's version of insert-g

(defn seqR
  "adds nu item to the right of old item"
  [nu old l]
  (cons old (cons nu l)))

(defn seqL
  "adds nu item to the left of old item"
  [nu old l]
  (cons nu (cons old l)))

(defn insert-g
  "List altering function
   does the function you pass into it when
   (= old (first l)

   This abstraction can be used for insertL, insertR, subst"
  [seq-fn]
  (fn [nu old l]
    (cond
      (not (seq l)) '()

      (= old (first l)) (seq-fn nu old (rest l))

      :else
      (cons (first l) ((insert-g seq-fn) nu old (rest l))))))

(def insertL2 (insert-g seqL))
(def insertR2 (insert-g seqR))

(def insertL3 (insert-g (fn [nu old l] (cons nu (cons old l)))))
(def insertR3 (insert-g (fn [nu old l] (cons old (cons nu l)))))

(insertR3 "tenders" "chicken" ["I" "like" "chicken"])
(insertL3 "buffalo" "chicken" ["I" "like" "chicken"])

;RECALL subst
(subst "carrot" "chicken" ["i" "eat" "chicken"])

(defn seqS [nu old l]
  (cons nu (rest l)))

(def subst2 (insert-g seqS))
(subst2 "carrot" "chicken" ["i" "eat" "chicken"])

(defn seqrem [nu old l]
  l)

(defn yyy [a l]
  ((insert-g seqrem) false a l))

(yyy "carrot" ["ok" "carrot" "now"])

(defn yyyy [a l]
  ((insert-g (fn [_nu _old l] l)) 777 a l))

(yyyy "carrot" ["forgive" "yesterday" "carrot" "idk"])

;Makes sense - nu is always false or IRRELEVANT - whenever seqfn is called, its ignored
; nu could be anything
; when (= old first item), the list gets cons'd onto rest without it

(defn atom-to-fn [x]
  (cond
    (= x *)
    (fn [arg1 arg2] (* arg1 arg2))

    (= x +)
    (fn [arg1 arg2] (+ arg1 arg2))

    :else
    (fn [arg1 arg2] (exp arg1 arg2))))

(defn value
  "This is value for regular s-expressions"
  [aexp]
  (if
   (atom? aexp)
    aexp
    ((atom-to-fn (first aexp)) (second aexp) (third aexp))))

(value [* 4 5])

(defn multi-rember-f
  "After all people come and people go away
   God with me He stays

   Pass in a function to remove items on"
  [test-f]
  (fn [a lat]
    (cond
      (not (seq lat)) '()

      (test-f a (first lat))
      ((multi-rember-f test-f) a (rest lat))

      :else
      (cons (first lat) ((multi-rember-f test-f) a (rest lat))))))

(def remove-when-equals (multi-rember-f (fn [x y] (= x y))))
(remove-when-equals "chicken" ["apples" "ginger" "chicken" "coconut" "chicken"])
(remove-when-equals "salad" ["shrimp" "salad" "tuna" "salad" "and" "tuna"])

(def remove-when-includes (multi-rember-f (fn [x y] (string/includes? y x))))
(remove-when-includes "chicken" ["apples" "ginger" "chicken" "carrots" "coconut chicken" "chickens"])

(defn multiremberT
  "takes a test-f and does the work on the lat"
  [test-f lat]
  (cond
    (not (seq lat))
    '()

    (test-f (first lat))
    (multiremberT test-f (rest lat))

    :else
    (cons (first lat) (multiremberT test-f (rest lat)))))

(defn equals-chicken? [x]
  (= x "chicken"))

(multiremberT equals-chicken? ["potatoes" "chicken" "biscuit" "chicken" "korn"])

(defn multirember&co
  "This will rember and then some
   takes an atom, a list, and a function

   ultimately function is called on two args -
   1st arg: the new list with items removed
   2nd arg: a list made of all removed items "
  [a lat f]
  (cond
    (not (seq lat))
    (f '() '())

    (= (first lat) a)
    (multirember&co a
                    (rest lat)
                    (fn [newlat removed-items]
                      (f
                       newlat
                       (cons (first lat) removed-items))))

    :else
    (multirember&co a
                    (rest lat)
                    (fn [newlat removed-items]
                      (f
                       (cons (first lat) newlat)
                       removed-items)))))

;& co stands for 'collector function'
; the function is collecting two sequences

;should be false
(multirember&co "tuna" ["strawberries" "tuna" "and" "swordfish"] (fn [x y] (not (seq y))))

(multirember&co 'tuna ['strawberries 'with 'tuna 'cream] (fn [newlat removed-items] (str (string/join " " newlat) " is better without " (apply str removed-items))))
(multirember&co 'tuna ['strawberries 'in 'tuna 'with 'tuna 'cream] (fn [newlat removed-items] (println newlat) (println removed-items)))
(multirember&co 'tuna ['strawberries 'in 'tuna 'with 'tuna 'cream] (fn [newlat removed-items] (println (str "Your new list is: " newlat)) (println (str "Removed " (count removed-items) " instances of ") (first removed-items))))

(defn multiinsertLR
  "will insert nu to the left of oldL and to the right of oldR"
  [nu oldL oldR lat]
  (cond
    (not (seq lat)) '()

    (= oldL (first lat))
    (cons nu (cons oldL (multiinsertLR nu oldL oldR (rest lat))))

    (= oldR (first lat))
    (cons oldR (cons nu (multiinsertLR nu oldL oldR (rest lat))))

    :else
    (cons (first lat) (multiinsertLR nu oldL oldR (rest lat)))))

(multiinsertLR "fried" "chicken" "deep" ["we" "ggonnnaa" "eat" "chicken" "and" "deep" "oreos"])

(defn multiinsertLR&co
  "builds a new collection and counts the number of L inserts + R inserts separately"
  [nu oldL oldR lat f]
  (cond
    (not (seq lat))
    (f '() 0 0)

    (= oldL (first lat))
    (multiinsertLR&co nu oldL oldR (rest lat)
                      (fn [new-lat left-insertions right-insertions]
                        (f
                         (cons nu (cons oldL new-lat))
                         (inc left-insertions)
                         right-insertions)))

    (= oldR (first lat))
    (multiinsertLR&co nu oldL oldR (rest lat)
                      (fn [new-lat left-insertions right-insertions]
                        (f
                         (cons oldR (cons nu new-lat))
                         left-insertions
                         (inc right-insertions))))

    :else
    (multiinsertLR&co nu oldL oldR (rest lat)
                      (fn [new-lat left-insertions right-insertions]
                        (f
                         (cons (first lat) new-lat)
                         left-insertions
                         right-insertions)))))

(multiinsertLR&co
 "fried" "chicken" "deep"
 ["chicken" "chicken" "chickken" "chhwe" "ggonnnaa" "eat" "chicken" "and" "deep" "oreos" "HEEEH" "chicken" "we" "LIKE" "that" "in" "deep" "south"]
 (fn [x y z]
   (println x)
   (println y)
   (println z)))
