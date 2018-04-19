 last(X,[X]).
 last(X,[_|Z]) :- last(X,Z).

notlast([], [_]).
 notlast([X|WithoutLast], [X|Xs]) :- notlast(WithoutLast, Xs).

subseq([],[]).
subseq([X|T1], [X|T2]) :- subseqhelper([X|T1], [X|T2]).
subseq(Z, [_|T1]) :- subseq(Z, T1).

subseqhelper([],_).
subseqhelper([X|T1],[X|T2]) :- subseqhelper(T1,T2).

sublist( [], _ ).
sublist( [X|T1], [X|T2] ) :- sublist( T1, T2 ).
sublist( [X|T1], [_|T2] ) :- sublist( [X|T1], T2 ).


without([X|T1],X, T1).
without([X|T1],Y, [X|T2]) :- X \==Y, without(T1,Y,T2).


permutation([],[]).
permutation(X,[Y|T1]) :- append(V,[Y|U],X), append(V,U,W), permutation(W,T1).

split([],[],[]).
split([X|T1],[X|T2],Z) :- split(T1,T2,Z).
split([X|T1],Z,[X|T3]) :- split(T1,Z,T3).


