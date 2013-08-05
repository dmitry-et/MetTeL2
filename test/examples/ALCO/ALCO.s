specification ALCO;

options{
name.separator=
}

syntax ALCO{

	sort concept; //The first sort is the main sort
	sort individual;
	sort role;

	//Connectives in a decreasing priority order
	concept true = 'true';
	concept false = 'false';
	concept singleton = '{' individual '}';
	concept	negation = '~' concept;
	concept	existentialRestriction = 'exists'
									 role '.'
									 concept;
	concept universalRestriction = 'forall'
									 role '.'
									 concept;
	concept at = '@' individual concept;

	concept	conjunction = concept '&' concept;
	concept	disjunction = concept '|' concept;
	concept	implication = concept '->' concept;
	concept	equivalence = concept '<->' concept;

	role composition = role ';' role;
	role union = role '|' role;
	role converse = role '-';

	individual skolemF = 'f' '(' individual ',' role ',' concept ')';
	individual skolemG = 'g' '(' individual ',' individual ',' role ',' role ')';

	concept equality =  '[' individual '=' individual ']';
}

tableau ALCO{
//equality rules
@l {l2} / @l2 {l} priority 1$;
@l ~{l2} / @l2 {l2} priority 1$;

@l P / @l {l} priority 1$;

@l exists r.{l2} / @l2 {l2} priority 1$;
@l P  @l {l2} / @l2 P priority 2$;
@l exists r.{l2}  @l2 {l3} / @l exists r.{l3} priority 2$;

//Decomposition rules

@l ~(~P) / @l P priority 1$;
@l (P|Q) / @l P $| @l Q priority 3$;
@l ~(P|Q) / @l ~P @l ~Q priority 1$;
@l (P&Q) / @l P @l Q priority 1$;
@l ~(P&Q) / @l ~P $| @l ~Q priority 3$;
@l (P -> Q) / @l ~P $| @l Q priority 3$;
@l ~(P -> Q) / @l P  @l ~Q priority 1$;
@l (P <-> Q) / @l P @l Q $| @l ~P @l ~Q priority 4$;
@l ~(P <-> Q) / @l P  @l ~Q $| @l ~P @l Q priority 4$;

@l forall r.P / @l ~(exists r.~P) priority 1$;
@l ~(forall r.P) / @l exists r.~P priority 1$;

@l exists r.P / @l exists r.{f(l,r,P)} @f(l,r,P) P priority 7$;
@l ~(exists r.P) @l exists r.{l2} / @l2 ~P priority 2$;

//Roles
@l exists r;s.{l2} / @l exists r.{g(l,l2,r,s)} @g(l,l2,r,s) {l2} priority 7$;
@l ~(exists r;s.P) / @l ~(exists r.(exists s.P)) priority 1$;

@l exists r|s.{l2} / @l exists r.{l2} $| @l exists s.{l2} priority 3$;
@l ~(exists r|s.P) / @l ~(exists r.P) @l ~(exists s.P) priority 1$;

@l exists r-.{l2} / @l2 exists r.{l} priority 1$;
@l ~(exists r-.P) @l2 exists r.{l} / @l2 ~P priority 2$;
@l ~(exists (r|s)-.P) / @l ~(exists (r-)|(s-).P) priority 1$;
@l ~(exists (r;s)-.P) / @l ~(exists (s-);(r-).P) priority 1$;

// Theory rules

// Closure rule
@l P  @l ~P /  priority 0$;
// last rule may not have $;
// no comma between formulae

//Blocking related
@l{l0} / [l=l0] priority 1$;
//[l=l0] / @l{l0} priority 1$;
//@l~{l0} / ~([l=l0]) priority 1$;
//~([l=l0]) / @l~{l0} priority 1$;
//@l{l} @l0{l0} / [l=l0] $| ~([l=l0]) priority 6$;
}
