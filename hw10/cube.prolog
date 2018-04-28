solve([B|_], _, B).

findCycle([_], A, A).
findCycle([H|T], A,B) :- not(H = A), findCycle(T,A,B).
findCycle([A|T], A,B) :- solve(T, A, B).
applyCycle([H|T], A,B) :- append([H|T], [H], X), findCycle(X, A, B).

applyPerm([],A,A).
applyPerm([Cycle|Rest],A,B) :- applyCycle(Cycle,A,X), applyPerm(Rest,X,B).

orbit(Perm,A,[A|Rest]) :- applyPerm(Perm,A,X), orbitInside(Perm,X,Rest,[]), orbitOutside(Perm,A,Rest).
orbitInside(_,A,[A],_).
orbitInside(Perm,A,[A|Rest],Seen) :- not(member(A, Seen)), applyPerm(Perm,A,X), append([A],Seen,Newseen), orbitInside(Perm,X,Rest,Newseen).
orbitOutside(Perm,A,[H]) :- applyPerm(Perm,H,A).
orbitOutside(Perm,A,[_|Tail]) :- orbitOutside(Perm,A,Tail).

allOrbits(_,[],_).
allOrbits(Perm,[H|Lists],Orbits) :- orbit(Perm,H,S), member(S,Orbits), allOrbits(Perm,Lists,Orbits).