(ns little-schemer-clj.chapter8
  (:require
   [clojure.string :as string]))

(defn multirember&co
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


;From Chapter 8....;
(multirember&co 'tuna ['strawberries 'with 'tuna 'cream] (fn [newlat removed-items] (str (string/join " " newlat) " is better without " (apply str removed-items))))

; (define a-friend
;   (lambda (x y)
;     (null? y)))
;
; ; or, in modern notation
;
; (define (a-friend x y)
;   (null? y))
;
; In the example this returns #f, since tuna appears in the list and so the second argument to a-friend is not empty.
;
; (define foods '(strawberries tuna and swordfish))
;
; (multirember&co 'tuna foods a-friend)
