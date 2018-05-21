shiftOnce([H|T],[H2|T2]) :- last(T,H2), subtract([H|T], [H2], T2).

%shift(X, E, Y) :- 

%normalizeCycle(Cycle, X) :- ... .

%normalize(Cycles, X) :- ... .
