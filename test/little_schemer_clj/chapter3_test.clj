(ns little-schemer-clj.chapter3-test
  (:require
   [little-schemer-clj.chapter3 :refer [firsts insertL insertR multiinsertL
                                        multiinsertR multirember multisubst
                                        rember rember-ERROR seconds subst subst2]]))

;TODO move these into test fns
(=
 (rember "mint" ["lamb" "mint" "jelly" "mint"])
 (cons "lamb" '("jelly" "mint"))) ;=> true

(=
 (rember "mint" ["lamb" "and" "mint" "jelly" "mint"])
 (cons "lamb" (cons "and" '("jelly" "mint")))) ;=> true

(rember "mint" ["lamb" "chops" "and" "mint" "jelly"])
(rember "mint" ["lamb" "chops" "and" "mint" "flavored" "mint" "jelly"])
(rember "toast" ["bacon" "lettuce" "tomato"])
(rember "cup" ["coffee" "cup" "covfefe" "cup" "hick" "cup"])
(rember-ERROR "mint" ["lamb" "chops" "and" "mint" "flavored" "mint" "jelly"])
(rember-ERROR "mint" ["lamb" "chops" "and" "flavored" "jelly"])
(rember-ERROR "and" ["bacon" "lettuce" "and" "tomato"])

(firsts '([0 1 2 3] [8 9 5] ["first" "second" "woof"]))
(firsts '())
(firsts '(["five" "plums"] ["four"] ["eleven" "green" "oranges"]))
(firsts '([["five" "plums"] "four"] ["eleven" "green" "oranges"] [["no"] "more"]))

(seconds '([1 2 3 4] ["oh" "my" "wow"] ["first element" "second element" "third element" "fourth?"]))
(seconds '([14] ["oh" "my" "wow"] ["first element" "second element" "third element" "fourth?"]))

(insertR "topping" "fudge" ["ice" "cream" "with" "fudge" "for" "dessert"])
(insertR "jalapeno" "and" ["tacos" "tamales" "and" "salsa"])
(insertR :e :d [:a :b :c :d :f :g])

(insertL "fudge" "topping" ["ice" "cream" "with" "topping" "for" "dessert"])
(subst "small" "big" ["its" "a" "big" "world" "after" "all"])
(subst2 "vanilla" "chocolate" "banana" ["banana" "ice" "cream" "with" "chocolate" "topping" "for" "dessert"])
(multirember "jon" ["lucy" "dave" "jon" "jon" "clark" "rick" "jon" "adam"])
(multirember "cup" ["coffee" "cup" "covfefe" "cup" "hick" "cup"])

(multiinsertR "fried" "and" ["scallops" "and" "fish" "or" "fish" "and" "pickles"])

(multiinsertL "fried" "fish" ["chips" "and" "fish" "or" "fish" "and" "pickles"])
(multisubst "small" "big" ["its" "a" "big" "world" "after" "all" "and" "all" "the" "big" "people" "are" "so" "big"])
