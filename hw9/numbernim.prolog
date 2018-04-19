move(X,Y) :- Y is X-1, Y >= X/2, move(Y,Y1).
win(X) :- X=:=2.
win(X) :- move(X,Y), not(win(Y)).