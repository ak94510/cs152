(define testa (randoms 42 100))
(define testb (cc-superimpose (colorize (filled-rectangle 100 100) "blue") (rectangle 100 100)))
(define testc (vc-append(cc-superimpose (colorize (filled-rectangle 100 50) "white") (rectangle 100 50)) (cc-superimpose (colorize (filled-rectangle 100 50) "red") (rectangle 100 50))))
(define testd (hc-append(cc-superimpose (colorize (filled-rectangle 50 100) "white") (rectangle 50 100)) (cc-superimpose (colorize (filled-rectangle 50 100) "yellow") (rectangle 50 100))))
(define teste (mondrian 90 90 '(0 0.5 0.5 1 0.1 1 0.3 1 0.3 1 0.3)))
(define (save-pict picture filename)
  (send (pict->bitmap picture) save-file filename 'png))
(save-pict testb "test1.png")
(save-pict testc "test2.png")
(save-pict testd "test3.png")
(save-pict (first teste) "test4.png")
(save-pict (first(mondrian 200 200 (randoms 42 20000))) "mondrian.png")
