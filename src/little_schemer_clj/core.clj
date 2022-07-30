(ns little-schemer-clj.core)

; My workflow is as follows:
;
;
;1. Write the test as it appears in the book, 
;2. eval the test
;3. (run-tests)
;
; When I have a failing test, I fix the implementation only as much as to get the test to pass. Following the book, I continue this pattern until the chapter is over.
;
; I am using the with-test macro and write my tests and implementation all in the main core namespace. Normally, we tend to write our tests in separate files but I've grown fond of with-test because of
;less switching between buffers
;having all code right in front of me.
