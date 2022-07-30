(ns little-schemer-clj.chapter1)

(defn atom?
  "Atoms are not null && not surrounded by parens
    Strings allowed
    Symbols allowed
    Lists not allowed
    For Clojure purposes, no collections allowed also (since they implement ISeq)"
  [x]
  (and (not (coll? x)) (some? x)))

(defn listp?
  "enclosed by parenthesis"
  [s]
  (list? s))

(defn s-expression?
  "All atoms are s-expressions
     All lists are s-expressions"
  [x]
  (not (nil? x)))

(defn eq?
  "Take two non-numeric atoms as arguments, returns true if they are equal"
  [atom1 atom2]
  (if
   (and
    (not (number? atom1))
    (not (number? atom2))
    (atom? atom1)
    (atom? atom2)
    (= atom1 atom2))
    true
    nil))

(def not-eq? (complement eq?))

(def car first)

(def cdr rest)

(defn null?
  "Is x an empty list?"
  [x]
  (if (listp? x)
    (empty? x)
    nil))

(comment
  (s-expression? 5)
  (s-expression? nil)
  (s-expression? (list 5 4 3 2 1)))