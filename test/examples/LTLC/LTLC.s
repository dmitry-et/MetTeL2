//Specification of linear temporal logic LTL with Boolean constraints
specification LTLC;

options{
branch.bound=((int)(java.lang.Math.pow(2,%l+1)+java.lang.Math.pow(4,%l+1)))

equality.keywords={equality,equivalence}

name.separator=
}

syntax LTLC{
	sort formula;
	sort nominal;

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
	formula eventualityDiamond = 'E' '(' nominal ','  formula ')';
	formula eventualityU = 'EU' '(' nominal ',' formula ',' formula ')';

	formula equality = '[' nominal '=' nominal ']';
	nominal f = 'f' '('  nominal ')'; //Skolem function == successor function
	formula badLoop = 'badLoop';

	//Constraints
	formula C02 = 'C02' '(' formula ',' formula ')';
	formula C12 = 'C12' '(' formula ',' formula ')';
	formula C22 = 'C22' '(' formula ',' formula ')';

	formula C03 = 'C03' '(' formula ',' formula ',' formula ')';
	formula C13 = 'C13' '(' formula ',' formula ',' formula ')';
	formula C23 = 'C23' '(' formula ',' formula ',' formula ')';
	formula C33 = 'C33' '(' formula ',' formula ',' formula ')';

	formula C04 = 'C04' '(' formula ',' formula ',' formula ',' formula ')';
	formula C14 = 'C14' '(' formula ',' formula ',' formula ',' formula ')';
	formula C24 = 'C24' '(' formula ',' formula ',' formula ',' formula ')';
	formula C34 = 'C34' '(' formula ',' formula ',' formula ',' formula ')';
	formula C44 = 'C44' '(' formula ',' formula ',' formula ',' formula ')';
}

tableau LTLC{
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

//Constraints

//permutations
@l (C02(p,q)) / @l (C02(q,p)) priority 1$;
@l (C12(p,q)) / @l (C12(q,p)) priority 1$;
@l (C22(p,q)) / @l (C22(q,p)) priority 1$;

@l (C03(p,q,r)) / @l (C03(q,r,p)) priority 1$;
//@l (C03(p,q,r)) / @l (C03(r,q,p)) priority 1$;
@l (C13(p,q,r)) / @l (C13(q,r,p)) priority 1$;
//@l (C13(p,q,r)) / @l (C13(r,q,p)) priority 1$;
@l (C23(p,q,r)) / @l (C23(q,r,p)) priority 1$;
//@l (C23(p,q,r)) / @l (C23(r,q,p)) priority 1$;
@l (C33(p,q,r)) / @l (C33(q,r,p)) priority 1$;
//@l (C33(p,q,r)) / @l (C33(r,q,p)) priority 1$;

@l (C04(p,q,r,s)) / @l (C04(q,r,s,p)) priority 1$;
//@l (C04(p,q,r,s)) / @l (C04(r,q,p,s)) priority 1$;
//@l (C04(p,q,r,s)) / @l (C04(s,q,r,p)) priority 1$;
@l (C14(p,q,r,s)) / @l (C14(q,r,s,p)) priority 1$;
//@l (C14(p,q,r,s)) / @l (C14(r,q,p,s)) priority 1$;
//@l (C14(p,q,r,s)) / @l (C14(s,q,r,p)) priority 1$;
@l (C24(p,q,r,s)) / @l (C24(q,r,s,p)) priority 1$;
//@l (C24(p,q,r,s)) / @l (C24(r,q,p,s)) priority 1$;
//@l (C24(p,q,r,s)) / @l (C24(s,q,r,p)) priority 1$;
@l (C34(p,q,r,s)) / @l (C34(q,r,s,p)) priority 1$;
//@l (C34(p,q,r,s)) / @l (C34(r,q,p,s)) priority 1$;
//@l (C34(p,q,r,s)) / @l (C34(s,q,r,p)) priority 1$;
@l (C44(p,q,r,s)) / @l (C44(q,r,s,p)) priority 1$;
//@l (C44(p,q,r,s)) / @l (C44(r,q,p,s)) priority 1$;
//@l (C44(p,q,r,s)) / @l (C44(s,q,r,p)) priority 1$;

//Reductions
//@l (C02(p,q)) @l p / priority 0 $;
//@l (C02(p,q)) @l ~p / @l ~q priority 2 $;

@l (C12(p,q)) @l p / @l ~q priority 2 $;
@l (C12(p,q)) @l ~p / @l q priority 2 $;

//@l (C22(p,q)) @l p / @l q priority 2 $;
//@l (C22(p,q)) @l ~p / priority 0 $;

//@l (C03(p,q,r)) @l p / priority 0 $;
//@l (C03(p,q,r)) @l ~p / @l (C02(q,r)) priority 2 $;

@l (C13(p,q,r)) @l p / @l (C02(q,r)) priority 2 $; //Unfortunately this causes a clash when p=q or p=r, so the agreement is that all arguments are syntactically pairwise different
@l (C13(p,q,r)) @l ~p / @l (C12(q,r)) priority 2 $;

@l (C23(p,q,r)) @l p / @l (C12(q,r)) priority 2 $; //Unfortunately this causes a clash when p=q or p=r, so the agreement is that all arguments are syntactically pairwise different
@l (C23(p,q,r)) @l ~p / @l (C22(q,r)) priority 2 $;

//@l (C33(p,q,r)) @l p / @l (C22(q,r)) priority 2 $;
//@l (C33(p,q,r)) @l ~p / priority 0 $;


//@l (C04(p,q,r,s)) @l p / priority 0 $;
//@l (C04(p,q,r,s)) @l ~p / @l (C03(q,r,s)) priority 2 $;

@l (C14(p,q,r,s)) @l p / @l (C03(q,r,s)) priority 2 $; //Unfortunately this causes a clash when p=q,r, or s, so the agreement is that all arguments are syntactically pairwise different
@l (C14(p,q,r,s)) @l ~p / @l (C13(q,r,s)) priority 2 $;

@l (C24(p,q,r,s)) @l p / @l (C13(q,r,s)) priority 2 $; //Unfortunately this causes a clash when p=q,r, or s, so the agreement is that all arguments are syntactically pairwise different
@l (C24(p,q,r,s)) @l ~p / @l (C23(q,r,s)) priority 2 $;

@l (C34(p,q,r,s)) @l p / @l (C23(q,r,s)) priority 2 $; //Unfortunately this causes a clash when p=q,r, or s, so the agreement is that all arguments are syntactically pairwise different
@l (C34(p,q,r,s)) @l ~p / @l (C33(q,r,s)) priority 2 $;

//@l (C44(p,q,r,s)) @l p / @l (C33(q,r,s)) priority 2 $;
//@l (C44(p,q,r,s)) @l ~p / priority 0 $;


//Duplications in view of the above agreement need not to be checked
////@l (C02(p,p)) / @l ~p priority 1$;
//@l (C12(p,p)) / @l p priority 1$;
//@l (C22(p,p)) / priority 0$;

//@l (C03(p,p,q)) / @l (C02(p,q)) priority 1$;
//@l (C13(p,p,q)) / @l (C12(p,q)) priority 1$;
//@l (C23(p,p,q)) / @l (C22(p,q)) priority 1$;
//@l (C33(p,p,q)) / priority 0$;

//@l (C04(p,p,q,r)) / @l (C03(p,q,r)) priority 1$;
//@l (C14(p,p,q,r)) / @l (C13(p,q,r)) priority 1$;
//@l (C24(p,p,q,r)) / @l (C23(p,q,r)) priority 1$;
//@l (C34(p,p,q,r)) / @l (C33(p,q,r)) priority 1$;
//@l (C44(p,p,q,r)) / priority 0$;

//Additions and cut rules for completeness
@l (C02(p,q)) / @l ~p priority 1$;
@l (C12(p,q)) / @l p $| @l ~p priority 4$;
@l (C22(p,q)) / @l p priority 1$;

@l (C03(p,q,r)) / @l ~p priority 1$;
@l (C13(p,q,r)) / @l p $| @l ~p priority 4$;
@l (C23(p,q,r)) / @l p $| @l ~p priority 4$;
@l (C33(p,q,r)) / @l p priority 1$;

@l (C04(p,q,r,s)) / @l ~p priority 1$;
@l (C14(p,q,r,s)) / @l p $| @l ~p priority 4$;
@l (C24(p,q,r,s)) / @l p $| @l ~p priority 4$;
@l (C34(p,q,r,s)) / @l p $| @l ~p priority 4$;
@l (C44(p,q,r,s)) / @l p priority 1$;
}
