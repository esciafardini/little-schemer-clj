(ns little-schemer-clj.chapter7
  "Friends And Relations"
  (:require
   [little-schemer-clj.chapter2 :refer [member?]]
   [little-schemer-clj.chapter3 :refer [firsts multirember]]))

;Set Theory, anyone?

(defn my-set? [lat]
  (cond
    (not (seq lat)) true

    (member? (first lat) (rest lat))
    false

    :else
    (my-set? (rest lat))))

(defn makeset [lat]
  (cond
    (not (seq lat))
    '()

    (member? (first lat) (rest lat))
    (makeset (rest lat))

    :else
    (cons (first lat) (makeset (rest lat)))))

(defn makeset2 [lat]
  (if (not (seq lat))
    '()
    (cons (first lat) (makeset2 (multirember (first lat) (rest lat))))))


(defn my-subset [set1 set2]

  (cond
    (not (seq set1))
    true

    (member? (first set1) set2)
    (my-subset (rest set1) set2)

    :else
    false))

(defn subset-with-and? [set1 set2]
  (if (not (seq set1)) true
      (and
       (member? (first set1) set2)
       (subset-with-and? (rest set1) set2))))

(defn eqset? [set1 set2]
  (and (my-subset set1 set2)
       (my-subset set2 set1)))

(defn intersect?
  "Do the two sets share a value?"
  [set1 set2]

  (cond
    (not (seq set1)) false

    (member? (first set1) set2)
    true

    :else
    (intersect? (rest set1) set2)))

(defn intersect-with-or? [set1 set2]
  (cond
    (not (seq set1)) false

    :else
    (or
     (member? (first set1) set2)
     (intersect-with-or? (rest set1) set2))))

(defn intersect
  "Returns the set intersection
   Meaning: items that occur in both sets"
  [set1 set2]
  (cond
    (not (seq set1))
    '()

    (member? (first set1) set2)
    (cons (first set1) (intersect (rest set1) set2))

    :else
    (intersect (rest set1) set2)))

(defn union
  "Both sets, together"
  [set1 set2]
  (cond
    (not (seq set1))
    set2

    (member? (first set1) set2)
    (union (rest set1) set2)

    :else
    (cons (first set1) (union (rest set1) set2))))

(defn xxx
  "Returns all atoms that are in set1 that are NOT in set 2"
  [set1 set2]
  (cond
    (not (seq set1)) '()

    (member? (first set1) set2) (xxx (rest set1) set2)

    :else
    (cons (first set1) (xxx (rest set1) set2))))

(defn intersect-all
  "Intersection is atoms present in all sets
   Keeps intersecting until there's nothing left.
   See tests for a drawn out example."
  [l-set]
  (if (not (seq (rest l-set)))
    (first l-set)
    (intersect (first l-set) (intersect-all (rest l-set)))))

(defn a-pair?
  "is is a pair?"
  [l]
  (= 2 (count l)))

(defn build
  "FAILING TO SEE THE POINT SIR"
  [s1 s2]
  (cons s1 (cons s2 (list))))

(defn empty-seq? [l] (not (seq l)))

(defn rel?
  "A rel is a pair: [4 3] for example...
   Remove all pairs, then ask - is there anything left?
   If nothing is left, it is a rel."
  [l]
  (empty-seq? (remove (fn [x] (= (count x) 2)) l)))

(defn revpair
  "Reverse a pair"
  [pair]
  (build (second pair) (first pair)))

(defn revrel
  "Reverse all pairs"
  [rel]
  (if (empty-seq? rel)
    '()
    (cons
     (revpair (first rel))
     (revrel (rest rel)))))

(defn seconds [l]
  (map (fn [x] (second x)) l))

(defn fun?
  "ALL IDX 0 (first) VALUES ARE UNIQUE"
  [rel]
  (my-set? (firsts rel)))

(defn fullfun?
  "ALL IDX 1 (second) VALUES ARE UNIQUE"
  [seq-of-rels]
  (my-set? (seconds seq-of-rels)))

(defn one-to-one?
  "Another way to write fullfun? AKA
   ALL IDX 1 (second) VALUES ARE UNIQUE"
  [fun]
  (fun? (revrel fun)))
