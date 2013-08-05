specification ALBOid;

options{
name.separator=
}

syntax ALBOid{

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
	concept equality = '[' individual '=' individual ']';

	role id = 'id';
	role converse = role '-';
	role negation = '~' role;
	role intersection = role '&' role;
	role union = role '|' role;

	individual skolemF = 'f' '(' individual ',' role ',' concept ')';
}

tableau ALBOid{
//equality rules

@l {l2} / @l2 {l} priority 1$;
@l ~{l2} / @l2 {l2} priority 1$;

@l P / @l{l} priority 1$;

@l exists r.{l2} / @l2 {l2} priority 1$;
@l P  @l {l2} / @l2 P priority 2$;
@l exists r.{l2}  @l2 {l3} / @l exists r.{l3} priority 2$;

//At rules
@l (@l0 P) / @l0 P priority 1$;
@l ~(@l0 P)/ @l0 ~P priority 1$;

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

//Unit propagation and resolution
//@l ~P @l (P|Q) / @l Q  priority 2$;
//@l P @l (~P|Q) / @l Q priority 2$;
//@l ~Q @l (P|Q) / @l P priority 2$;
//@l Q @l (P|~Q) / @l P priority 2$;

//@l(P | Q) @l(~P | R) / @l Q $| @l R priority 3$;
//@l(Q | P) @l(~P | R) / @l Q $| @l R priority 3$;
//@l(P | Q) @l(R | ~P) / @l Q $| @l R priority 3$;
//@l(Q | P) @l(R | ~P) / @l Q $| @l R priority 3$;
//===============

@l forall r.P / @l ~(exists r.~P) priority 1$;
@l ~(forall r.P) / @l exists r.~P priority 1$;

@l exists r.P / @l exists r.{f(l,r,P)} @f(l,r,P) P priority 7$;
@l ~(exists r.P) @l exists r.{l2} / @l2 ~P priority 2$;

//Roles
//@l exists r|s.{l2} @l ~(exists r.{l2}) / @l exists s.{l2} priority 2$;
//@l exists r|s.{l2} @l ~(exists s.{l2}) / @l exists r.{l2} priority 2$;
@l exists r|s.{l2} / @l exists r.{l2} $| @l exists s.{l2} priority 3$;
@l ~(exists r|s.P) / @l ~(exists r.P) @l ~(exists s.P) priority 1$;

@l exists r-.{l2} / @l2 exists r.{l} priority 1$;
@l ~(exists r-.P) @l2 exists r.{l} / @l2 ~P priority 2$;
@l ~(exists r--.P) / @l exists r.P priority 2$;
@l ~(exists (r|s)-.P) / @l ~(exists (r-)|(s-).P) priority 1$;
@l ~(exists (r&s)-.P) / @l ~(exists (r-)&(s-).P) priority 1$;
@l ~(exists (~r)-.P) / @l ~(exists ~(r-).P) priority 1$;

//id
@l exists id.{l0} / @l{l0} priority 1$;
@l ~(exists id.P) / @l ~P priority 1$;

//role negation
@l exists ~R.{l0} / @l ~(exists R.{l0}) priority 1$;
@l ~(exists ~R.P) @l0 P / @l exists R.{l0} priority 2$;
@l ~(exists ~R.P) @l ~(exists R.{l0}) / @l0 ~P priority 2$;
@l ~(exists ~R.P) @l0 {l0} / @l exists R.{l0} $| @l0 ~P priority 4$;

//role intersection

@l exists (r&s).{l0} / @l exists r.{l0} @l exists s.{l0} priority 1$;
@l ~(exists (r&s).P) / @l ~(exists ~(~r|~s).P) priority 1$;

// Theory rules

// Closure rule
@l P  @l ~P /  priority 0$;
@l false /  priority 0$;
@l ~true /  priority 0$;

//Blocking related
@l{l0} / [l=l0] priority 1$;
[l=l0] / @l{l0} priority 1$;
@l~{l0} / ~([l=l0]) priority 1$;
~([l=l0]) / @l~{l0} priority 1$;
@l{l} @l0{l0} / [l=l0] $| ~([l=l0]) priority 6$;
// last rule may not have $;
// no comma between formulae
}
