//Specification of linear temporal logic LTL with Boolean constraints
specification LTLC;

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
