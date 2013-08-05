// Specification of linear temporal logic LTL
specification LTL;

options{
branch.bound=((int)(java.lang.Math.pow(2,%l+1)+java.lang.Math.pow(4,%l+1)))

equality.keywords={equality,equivalence}

name.separator=
}

syntax LTL{
	sort formula;
	sort nominal;

	formula badLoop = 'badLoop';//We need this for tableau

	formula true = 'true';
	formula false = 'false';

	formula singleton = '{' nominal '}';

	formula negation = '~' formula;
	formula next = '()' formula;

	formula diamond = '<>' formula;
	formula box = '[]' formula;

	formula at = '@' nominal formula;

	formula conjunction  = formula '&' formula;
	formula disjunction  = formula '|' formula;

	formula implication = formula '->' formula;

	formula equivalence = formula '<->' formula;

	formula until = formula 'U' formula;
	formula waitFor = formula 'W' formula;

	//Tableau additions
	formula eventualityDiamond = 'E' '(' nominal ',' formula ')';
	formula eventualityU = 'EU' '(' nominal ',' formula ',' formula ')';

	formula equality = '[' nominal '=' nominal ']';
	nominal f = 'f' '('  nominal ')'; //Skolem function == successor function
}

tableau LTL{
//equality rules
//@l{l2} / @l2{l} priority 1$;
//@l~{l2} / @l2{l2} priority 1$;

@l P / @l{l} priority 1 $;

//@l P  @l{l2} /  @l2 P  priority 2$;

//Decomposition rules
@l ~(~P) / @l P  priority 1$;

@l~P @l(P|Q) / @l Q priority 2$;
@l P @l(~P|Q) / @l Q priority 2$;
@l~Q @l(P|Q) / @l P priority 2$;
@l Q @l(P|~Q) / @l P priority 2$;

@l P @l~(P&Q) / @l~Q priority 2$;
@l~P @l~(~P&Q) / @l~Q priority 2$;
@l Q @l~(P&Q) / @l~P priority 2$;
@l~Q @l~(P&~Q) / @l~P priority 2$;

@l P @l(P->Q) / @l Q priority 2$;
@l~Q @l(P -> Q) / @l~P priority 2$;
@l Q @l(P -> ~Q) / @l~P priority 2$;

@l(P|Q) / @l P $| @l Q  priority 3$;
@l~(P|Q) / @l~P @l~Q  priority 1$;

@l(P&Q) / @l P @l Q priority 1$;
@l ~(P&Q) / @l ~P $| @l ~Q priority 3$;

//@l (P&Q) @l ~P/ priority 0$;
//@l (P&Q) @l ~Q/ priority 0$;
//@l (~P&Q) @l P/ priority 0$;
//@l (P&~Q) @l Q/ priority 0$;

@l(P->Q) / @l ~P $| @l Q priority 3$;
@l~(P->Q) / @l P @l ~Q priority 1$;

//@l(P->Q) / @l(~P|Q) priority 1$;
//@l~(P->Q) / @l(P&~Q) priority 1$;

@l(P<->Q) / @l P @l Q | @l ~P @l ~Q priority 3$;
@l~(P<->Q) / @l ~P @l Q $| @l ~Q @l P priority 3$;

@l(()P) / @f(l)P  priority 7$;
@l ~(()P) / @l ()(~P) priority 1$;
//@l ~(()P) @f(l){f(l)} / @f(l)~P  priority 2$;

//Diamond
@l (<>P) / @l (E(l,P)) priority 1 $;
@l (E(l0,P)) / @l P ((E(l0,P)) <-> (<>P)) $| @f(l) (E(l0,P)) priority 7$;

//@l (~(<>P)) / @l ([](~P)) priority 1$;
@l (~(<>P)) / @l (~P) @f(l)(~(<>P)) priority 7$;

//Box
//@l []P / @l P @f(l)[]P priority 7$;
@l []P / @l (~(<>(~P))) priority 1$;
@l ~([]P) / @l <>(~P) priority 1$;

//Until
@l(C U D) / @l (EU(l,C,D)) priority 1$;
@l (EU(l0,C,D)) / @l D ((EU(l0,C,D))<->(C U D)) $| @l C @f(l) (EU(l0,C,D)) priority 7$;
@l~(C U D) / @l((~D) W ((~C) & (~D))) priority 1$;

//Wait for
//@l(C W D) / @l (EW(C,D,l)) priority 1$;
@l(C W D) / @l D $| @l C @f(l) (C W D) priority 7$;

//@l~(C U D)/ @l (~D U ~(C | D)) $| @l ~(~false U ~C) priority 4$;

// Closure rule
@l P  @l (~P) / priority 0$;
@l false / priority 0$;
@l ~(true)/ priority 0$;

//Blocking related
//@l{l0} / [l=l0]  priority 6$;
//[l=l0] / @l{l0}  priority 6$;
//@l~{l0} / ~([l=l0])  priority 6$;
//~([l=l0]) / @l~{l0}  priority 6$;
@l{l} @l0{l0} / [l=l0] $| ~([l=l0])  priority 6$;
~([l=l])/ priority 0$;

@l (EU(l0,C,D)) / ((EU(l0,C,D))<->(badLoop)) priority 8$;
//@l (EW(C,D,l)) /  priority 8$;
@l (E(l0,C)) / ((E(l0,C))<->(badLoop))  priority 8$;

@l (badLoop) / priority 8$;
}
