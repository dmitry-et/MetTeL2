// Specification of modal logic S4
specification S4;

options{
name.separator=
}

syntax S4{
	sort formula;
	sort nominal;

	formula true = 'true';
	formula false = 'false';
	formula singleton = '{' nominal '}';
	formula	negation = '~' formula;
	formula diamond = '<>' formula;
	formula at = '@' nominal formula;
	formula	disjunction  = formula '|' formula;
	formula equivalence = formula  '<->' formula; //Testing some features
	formula equality = '[' nominal '=' nominal ']';
	nominal f = 'f' '('  nominal ',' formula  ')'; //Skolem function
}

tableau S4{
//equality rules
@l{l2} / @l2{l} priority 1$;
@l~{l2} / @l2{l2} priority 1$;

@l P / @l{l} priority 1$;

@l<>{l2} / @l2{l2} priority 1$;
@l P  @l{l2} /  @l2 P priority 2$;
@l<>{l2}  @l2{l3} / @l<>{l3} priority 2$;

//Decomposition rules

@l ~(~P) / @l P priority 1$;
@l(P|Q) / @l P $| @l Q priority 3$;
@l~(P|Q) / @l~P @l~Q priority 1$;
@l<>P / @l<>{f(l,P)} @f(l,P)P priority 7$;
@l ~(<>P) @l<>{l2} / @l2~P priority 2$;

// Theory rules
@l{l} / @l<>{l} priority 1$;
@l<>{l2} @l2<>{l3} / @l<>{l3} priority 2$;

// Closure rule
@l P  @l~P /  priority 0$; $; $;
// last rule should not have $;
// no comma between formulae

//Blocking related
@l{l0} / [l=l0] priority 1$;
[l=l0] / @l{l0} priority 1$;
@l~{l0} / ~([l=l0]) priority 1$;
~([l=l0]) / @l~{l0} priority 1$;
@l{l} @l0{l0} / [l=l0] $| ~([l=l0]) priority 6$;
}
