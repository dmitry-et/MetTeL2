specification Lukasiewicz3;

syntax Lukasiewicz{
	sort valuation, formula;
	//Connectives in a decreasing priority order
	valuation true = 'T' formula | unknown = 'U' formula | false = 'F' formula;
	formula true = 'true' | false = 'false';
	formula	negation = '~' formula;
	formula	conjunction = formula '&' formula;
	formula	disjunction = formula '|' formula;
	formula	implication = formula '->' formula;
}
