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
	formula box = '[]' formula;

	formula at = '@' nominal formula;

	formula conjunction  = formula '&' formula;
	formula disjunction  = formula '|' formula;

	formula implication = formula '->' formula;

	formula equivalence = formula '<->' formula;

	formula until = formula 'U' formula;
	formula waitFor = formula 'W' formula;

	//Tableau additions
	formula eventualityDiamond = 'E' '(' formula ')';
	formula eventualityU = 'EU' '(' formula ',' formula ')';

	formula equality = '[' nominal '=' nominal ']';
	nominal f = 'f' '('  nominal ')'; //Skolem function == successor function
	formula badLoop = 'badLoop';
}
