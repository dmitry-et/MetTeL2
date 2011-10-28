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
	formula equivalence = formula '<->' formula;
	formula until = formula 'U' formula;
	//Tableau additions
	formula eventuality0 = 'Ev0' '(' formula ',' formula ',' nominal ')';
	formula eventuality1 = 'Ev1' '(' formula ',' formula ',' nominal ')';
	formula equality = '[' nominal '=' nominal ']';
	nominal f = 'f' '('  nominal ')'; //Skolem function == successor function
}
