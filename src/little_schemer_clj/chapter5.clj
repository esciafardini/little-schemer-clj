(ns little-schemer-clj.chapter5
  "*Oh my Gawd*: It's Full of Stars")

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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;The Sixth Commandment
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;  ONLY simplify after the function works
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

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

(defn leftmost
  "Finds the leftmost atom in a non-empty list of S-expressions that does not contain the empty list
   This is not a (*) function because it only recurs on (first l)"
  [l]
  (let [idx0 (first l)]
    (if (not (coll? idx0))
      idx0
      (leftmost idx0))))

(defn example
  "checks is a a collection? + is the first item in list a?"
  [a l]
  (and (not (coll? a))
       (= (first l) a)))

(declare eqlist?)

(defn equal? [s1 s2]
  (cond
    (and (not (coll? s1))
         (not (coll? s2)))
    (= s1 s2)

    (or (not (coll? s1)) (not (coll? s2)))
    false

    :else
    (eqlist? s1 s2)))

(defn eqlist?
  "Checks if two lists are equivalent"
  [l1 l2]
  (let [idx0_a (first l1)
        idx0_b (first l2)
        remaining_a (rest l1)
        remaining_b (rest l2)]
    (cond
      (and (not (seq l1)) (not (seq l2)))
      true

      (or (not (seq l1)) (not (seq l2)))
      false

      :else
      (and
       (equal? idx0_a idx0_b)
       (eqlist? remaining_a remaining_b)))))
