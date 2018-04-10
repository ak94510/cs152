#lang slideshow
(define (lcg x) (modulo (+ (* x 1664525) 1013904223) 4294967296))
(define (randomshelper existinglist len seed)  (if (< (length existinglist) len) (randomshelper (append (list (lcg (first existinglist))) existinglist) len seed) existinglist))
(define (underone fulllist) (map (lambda (n) (exact->inexact(/ n 4294967296))) fulllist))
(define (randoms seed length) (underone (randomshelper (list (lcg seed)) length seed)))
(define (wide-enough w r width) (and (> w 50) (> (/ w width) (* 0.5 r))))
(define (tall-enough h r height) (and (> h 50) (> (/ h height) (* 0.5 r))))
(define (random-config r) (+ (* r 0.33) 0.33))
(define (choose-color r) (cond
   [(< r 0.0833) "red"]
   [(< r 0.1667) "blue"]
   [(< r 0.25) "yellow"]
   [else "white"]))

(define (mondrian-helper width height rands canvasw canvash) (cond
   [(and (wide-enough width (first rands) canvasw) (tall-enough height (first rands) canvash)) (let*([w (mondrian-helper(* width (random-config (second rands))) (* height (random-config (third rands))) (cdddr rands) canvasw canvash)]
                                                                                                     [x (mondrian-helper(- width (* width (random-config (second rands)))) (- height (* height (random-config (third rands)))) (second w) canvasw canvash)]
                                                                                                     [y (mondrian-helper (- width (* width (random-config (second rands)))) (* height (random-config (third rands))) (second x) canvasw canvash)]
                                                                                                     [z (mondrian-helper (* width (random-config (second rands))) (- height (* height (random-config (third rands)))) (second y) canvasw canvash)])
                                                                                                  (list (hc-append (vc-append (first w) (first z)) (vc-append (first x) (first y))) (second z)))]
   [(wide-enough width (first rands) canvasw) (let*([x (mondrian-helper(* width (random-config (second rands))) height (cddr rands) canvasw canvash)]
                                                   [y (mondrian-helper(- width (* width (random-config (second rands)))) height (second x) canvasw canvash)])
                                                (list (hc-append (first x) (first y)) (second y)))]
   [(tall-enough height (first rands) canvash) (let* ([x (mondrian-helper width (* height (random-config (second rands))) (cddr rands) canvasw canvash)]
                                                     [y (mondrian-helper width (- height (* height (random-config (second rands)))) (second x) canvasw canvash)])
                                                 (list (vc-append (first x) (first y)) (second y)))]
   [else (list (cc-superimpose (colorize (filled-rectangle width height) (choose-color (second rands))) (rectangle width height)) (cddr rands))]))


(define (mondrian width height rands)(mondrian-helper width height rands width height))
