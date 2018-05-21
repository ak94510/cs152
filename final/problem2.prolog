shiftOnce([H|T],[H2|T2]) :- last(T,H2), subtract([H|T], [H2], T2).

shiftCheck(X,E,[E|T2]).
shift(X, E, [_|Z]) :- shiftOnce(X,Y), shiftCheck(Y,E,Z).

%normalizeCycle(Cycle, X) :- ... .

%normalize(Cycles, X) :- ... .
