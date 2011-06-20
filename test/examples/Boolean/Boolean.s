specification Boolean;

syntax Boolean{
	sort formula;

	//Connectives in a decreasing priority order
	formula true = 'true';
	formula false = 'false';
	formula	negation = '~' formula;
	formula	conjunction = formula '&' formula;
	formula	disjunction = formula '|' formula;
	formula	implication = formula '->' formula;
	formula	equivalence = formula '<->' formula;
}
