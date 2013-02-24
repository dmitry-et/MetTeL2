specification Km;

syntax Km{
	sort formula, relation, individual;

	//Connectives in a decreasing priority order
	//formula true = 'true';
	formula false = 'false';

	formula	negation = '~' formula;
	formula diamond = '<' relation '>' formula;

	//formula	conjunction = formula '&' formula;
	formula	disjunction = formula '|' formula;
	
	
	//formula	implication = formula '->' formula;
	//formula	equivalence = formula '<->' formula;

	formula at = '@' individual formula;

	individual successor = 'f' '(' individual ',' relation ',' formula ')';
	
	formula link = '!' relation '(' individual ',' individual ')';
}
