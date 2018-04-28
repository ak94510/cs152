flipped(Click, Columns, [Left, Click, Right]) :- Click > 1, Click < Columns, Left is Click - 1, Right is Click + 1.
flipped(Click, Columns, [Left, Click]) :- Click > 1, Click = Columns, Left is Click - 1.
flipped(Click, Columns, [Click, Right]) :- Click = 1, Click < Columns, Right is Click + 1.
flipped(Click, Columns, [Click]) :- Columns = 1.

% doFlips(Cells, Row, Columns, Result)
% Result is a list of red positions resulting from flipping all
% Cells (list of positions) in a Row (list of red positions)
% of the given number of columns.

doFlips([],Y,_,R) :- Y=R.
doFlips( [X|Xt], Y, Z, R) :- flipped(X,Z,S),ord_symdiff(Y,S,Ys) , doFlips(Xt,Ys,Z,R).

allFlips(Flips, Row, Rows, Columns, [Flips | MoreFlips]) :- Rows > 0,
    doFlips(Flips,Row,Columns,R),
    numlist(1, Columns, All),
    ord_symdiff(R,All,Z),
    Rows1 is Rows - 1,
    allFlips(Z,Flips,Rows1,Columns,MoreFlips).
allFlips([], _, 0, _, []).

sublist([],_).
sublist([E|T1], L) :- append(_, [E|T2], L), sublist(T1, T2).

solution(Rows, Columns, Solution) :- numlist(1, Columns, L), sublist(Solution, L), allFlips(Solution, [], Rows, Columns, _). 

