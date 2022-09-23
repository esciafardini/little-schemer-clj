(ns little-schemer-clj.chapter7-test
  (:require
   [little-schemer-clj.chapter7 :refer [a-pair? build eqset? fullfun? fun?
                                        intersect intersect-all
                                        intersect-with-or? intersect? makeset makeset2 my-set? one-to-one? rel? revrel seconds
                                        subset-with-and? union xxx]]))

(my-set? [1 2 3])
(my-set? '(1 2 3 4 3))
(my-set? [1 2 4 3])
(makeset [1 12 13 1 2 3 13])
(makeset ['apple 'peach 'pear 'peach 'plum 'apple 'lemon 'peach])
(makeset2 ['apple 'peach 'pear 'peach 'plum 'apple 'lemon 'peach])

(subset-with-and?
 [1 2 3]
 [1 2 3 4])

(subset-with-and?
 [1 2 3]
 [2 3 4])

(eqset? [3 1 2] [1 2 3])

(intersect? ['mav 'mac 'stewed 'tomato 'wit 'cheese]
            ['mac 'oroni 'chickey])
(intersect-with-or? ['mav 'stewed 'tomato 'wit 'cheese]
                    ['mac 'oroni 'chickey])

(intersect [5 6 1 12] [4 5 6 3 2])

(union [1 2 3] [3 4 5 6])
(xxx [1 2 3 4 11] [3 4 5 9 8])

(intersect [1 2 3] [2 3 4])

(intersect-all
 [[7 "pears" "and"]
  [3 "peaches" "and" 7 "peppers"]
  [8 "pears" "and" 7 "plums"]
  ["and" 7 "prunes" "with" 9 "apples"]])

(intersect
 [7 "pears" "and"]
 (intersect-all
  [[3 "peaches" "and" 7 "peppers"]
   [8 "pears" "and" 7 "plums"]
   ["and" 7 "prunes" "with" 9 "apples"]]))

(intersect
 [7 "pears" "and"]
 (intersect-all
  [[3 "peaches" "and" 7 "peppers"]
   [8 "pears" "and" 7 "plums"]
   ["and" 7 "prunes" "with" 9 "apples"]]))

(intersect
 [7 "pears" "and"]
 (intersect
  [3 "peaches" "and" 7 "peppers"]
  (intersect
   [8 "pears" "and" 7 "plums"]
   ["and" 7 "prunes" "with" 9 "apples"])))

(a-pair? [3 7])
(a-pair? ["full" ["house"]])
(build [23 34 2] [3 "ok"])
(concat [23 34 2] [3 "ok"])
(into '() [[23 34 3] [3 "ok"]])

(fun? [[8 3] [4 2] [7 6]])
(fun? [[8 5] [8 3] [4 2] [7 6]])


(rel? [[1 2] [4 3] [43 2]])

(rel? [[1 2] [31 4 3] [43 2]])

(revrel [['up "LISTEN"] ["cuz" "yawl"] ["flipdagizsm" "I Am"]])

(seconds [[8 5] [8 3] [4 2] [7 6]])

(fullfun? [["grape" "raisin"] ["plum" "prune"] ["stewed" "grape"]])
(fullfun? [["plum" "raisin"] ["plum" "prune"] ["stewed" "grape"]])
(fullfun? [["grap" "prune" "raisin"] ["plum" "prune"] ["stewed" "grape"]])

(one-to-one? [["chocolate" "chip"] ["doughy" "cookie"]])
