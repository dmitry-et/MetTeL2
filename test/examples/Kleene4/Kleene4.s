specification Kleene4;

syntax Kleene4{
	sort valuation, formula;
	//Connectives in a decreasing priority order
	valuation true = 'T' formula | undecidedTrue = 'UT' formula | undecidedFalse = 'UF' formula | false = 'F' formula;
	formula true = 'true' | false = 'false';
	formula	negation = '~' formula;
	formula	disjunction = formula '|' formula;
}
