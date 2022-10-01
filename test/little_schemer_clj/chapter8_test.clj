(ns little-schemer-clj.chapter8-test
  (:require
   [clojure.string :as string]
   [little-schemer-clj.chapter3 :refer [insertL subst]]
   [little-schemer-clj.chapter8 :refer [curried= equals-chicken? evens-only*
                                        evens-only*&co insert-g
                                        insert-g-my-first-pass insertL-f insertR-f multi-rember-f multiinsertLR multirember&co
                                        multiremberT rember-f seqL seqR value yyy yyyy]]))

(def rember= (rember-f =))
(rember= 5 [1 2 3 4 5 6 7 8])
((curried= "yes") "yes")
((curried= "no") "yes")

(insertL "jeff" "daniel" ["hey" "my" "mans" "or" "were" "IT" "daniel" "?"])

(def insertL-on-equal (insertL-f =))

(insertL-on-equal "jeff" "daniel" ["hey" "my" "mans" "or" "were" "IT" "daniel" "?"])
(def insertR-on-equal (insertR-f =))

(insertR-on-equal "jeff" "daniel" ["hey" "my" "mans" "or" "were" "IT" "daniel" "?"])
(def insertRG-on-equal (insert-g-my-first-pass = :right))

(insertRG-on-equal "jeff" "daniel" ["hey" "my" "mans" "or" "were" "IT" "daniel" "?"])

(def insertLG-on-equal (insert-g-my-first-pass = :left))

(insertLG-on-equal "jeff" "daniel" ["hey" "my" "mans" "or" "were" "IT" "daniel" "?"])
(def insertL2 (insert-g seqL))
(def insertR2 (insert-g seqR))

(def insertL3 (insert-g (fn [nu old l] (cons nu (cons old l)))))
(def insertR3 (insert-g (fn [nu old l] (cons old (cons nu l)))))

(insertR3 "tenders" "chicken" ["I" "like" "chicken"])
(insertL3 "buffalo" "chicken" ["I" "like" "chicken"])

;RECALL subst
(subst "carrot" "chicken" ["i" "eat" "chicken"])
(yyy "carrot" ["ok" "carrot" "now"])
(yyyy "carrot" ["forgive" "yesterday" "carrot" "idk"])

(value [* 4 5])
(def remove-when-equals (multi-rember-f (fn [x y] (= x y))))
(remove-when-equals "chicken" ["apples" "ginger" "chicken" "coconut" "chicken"])
(remove-when-equals "salad" ["shrimp" "salad" "tuna" "salad" "and" "tuna"])

(def remove-when-includes (multi-rember-f (fn [x y] (string/includes? y x))))
(remove-when-includes "chicken" ["apples" "ginger" "chicken" "carrots" "coconut chicken" "chickens"])
(multiremberT equals-chicken? ["potatoes" "chicken" "biscuit" "chicken" "korn"])
(multirember&co "tuna" ["strawberries" "tuna" "and" "swordfish"] (fn [x y] (not (seq y))))

(multirember&co 'tuna ['strawberries 'with 'tuna 'cream] (fn [newlat removed-items] (str (string/join " " newlat) " is better without " (apply str removed-items))))
(multirember&co 'tuna ['strawberries 'in 'tuna 'with 'tuna 'cream] (fn [newlat removed-items] (println newlat) (println removed-items)))
(multirember&co 'tuna ['strawberries 'in 'tuna 'with 'tuna 'cream] (fn [newlat removed-items] (println (str "Your new list is: " newlat)) (println (str "Removed " (count removed-items) " instances of ") (first removed-items))))

(multiinsertLR "fried" "chicken" "deep" ["we" "ggonnnaa" "eat" "chicken" "and" "deep" "oreos"])

(evens-only* [1 2 3 4 [4 5 6 [7 8 9 [5 4 3] 4 3]]])

(evens-only*&co [1 2 [1 2 3 [4 3 5 [6]]] 3 4] (fn [x y z]
                                                (println x)
                                                (println y)
                                                (println z)))
