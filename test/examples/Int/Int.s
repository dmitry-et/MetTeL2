specification Int;

syntax Int{
	sort formula, individual;

	//Connectives in a decreasing priority order
	formula true = 'true';
	formula false = 'false';
	
	formula trueValue = 'T' formula;
	formula falseValue = 'F' formula;

	formula at = '@' individual formula;

	formula	negation = '~' formula;
	formula	conjunction = formula '&' formula;
	formula	disjunction = formula '|' formula;
	formula	implication = formula '->' formula;
	formula	equivalence = formula '<->' formula;

	individual successor = 'f' '(' individual ',' formula ')';
	formula ordering = individual '<=' individual;
}
