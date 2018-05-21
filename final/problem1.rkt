(define (shiftonce cycle) (cons (last cycle) (remove (last cycle) cycle)))
(define (shift cycle element) (if (= (first cycle) element) cycle (shift (shiftonce cycle) element)))
(define (normalizeCycle cycle) (shift cycle (first (sort cycle <))))
(define (normalize list) (sort (map (lambda (cycle) (shift cycle (first (sort cycle <)))) list) (lambda (x y) (< (first x) (first y)))))
