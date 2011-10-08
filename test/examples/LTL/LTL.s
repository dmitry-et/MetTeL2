// Specification of linear temporal logic LTL
specification LTL;

syntax LTL{
	sort formula;
	sort nominal;

	formula true = 'true';
	formula false = 'false';
	formula singleton = '{' nominal '}';
	formula negation = '~' formula;
	formula next = '()' formula;
	formula diamond = '<>' formula;
	formula at = '@' nominal formula;
	formula disjunction  = formula '|' formula;
	formula until = formula 'U' formula;
	nominal f = 'f' '('  nominal ',' formula  ')'; //Skolem function
}
