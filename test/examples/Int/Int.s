specification Int;

options{
equality.keywords = {equality, equivalence}
name.separator=

tableau.rule.delimiter=;
tableau.rule.branch.delimiter=||
}

syntax Int{
	sort formula, individual;

	//Connectives in a decreasing priority order
	//formula true = 'true';
	formula false = 'false';

	formula trueValue = 'T' formula;
	formula falseValue = 'F' formula;

	formula at = '@' individual formula;

	formula	negation = '~' formula;
	formula	conjunction = formula '&' formula;
	formula	disjunction = formula '|' formula;
	formula	implication = formula '->' formula;
	//formula	equivalence = formula '<->' formula;

	individual successorImp = 'f' '(' individual ',' formula ',' formula ')';
	//individual successorNot = 'g' '(' individual ',' formula ')';
	formula relation = 'R' '(' individual ',' individual ')';
	formula equality = '[' individual '=' individual ']';
}

tableau IPC in syntax Int{
@l(T false) / priority 0;
//@l(F true) / priority 0;

@l(T P)  @l(F P) /  priority 1;

@l(T(P|Q)) @l(F P) / @l(T Q) priority 4;
@l(T(P|Q)) / @l(T P) || @l(T Q) priority 7;
@l(F(P|Q)) / @l(F P) @l(F Q) priority 3;

@l(T(P&Q)) / @l(T P) @l(T Q) priority 3;
@l(F(P&Q)) @l(T P) / @l(F Q) priority 4;
@l(F(P&Q)) / @l(F P) || @l(F Q) priority 7;

@l(T(~P)) R(l,l0) @l0(T P) / priority 2;
@l(T(~P)) R(l,l0) / @l0(F P) priority 4;
@l(F(~P)) / R(l,f(l,P,false)) @f(l,P,false)(T P) priority 10;

@l(T(P->Q)) R(l,l0) @l0(T P) / @l0(T Q) priority 5;
@l(T(P->Q)) R(l,l0) / @l0(F P) || @l0(T Q) priority 8;
@l(F(P->Q)) / R(l,f(l,P,Q)) @f(l,P,Q)(T P) @f(l,P,Q)(F Q) priority 10;


@l P / R(l,l) priority 3;
R(l0,l1) R(l1,l2) / R(l0,l2) priority 4;
R(l0,l1) R(l1,l0) / [l0=l1] priority 4;
@l(T P) R(l,l0) / @l0(T P) priority 4;

R(l,l0) / [l=l0] || (F([l=l0])) priority 9;
(F([l=l])) / priority 0;
}
