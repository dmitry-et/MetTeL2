specification Kleene4;

syntax Kleene4{
	sort valuation, formula, value;
	//Connectives in a decreasing priority order
	valuation = '[' formula '=' value ']';
	value true = 'T' | undecidedTrue = 'UT' | undecidedFalse = 'UF' | false = 'F';
	formula true = 'true' | false = 'false';
	formula	negation = '~' formula;
	formula	conjunction = formula '&' formula;
	formula	disjunction = formula '|' formula;
}
