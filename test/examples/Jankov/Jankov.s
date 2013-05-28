specification Jankov;

syntax Jankov{
	sort formula, individual;

	//Connectives in a decreasing priority order
	//formula true = 'true';
	formula false = 'false';

	formula trueValue = 'T' formula;
	formula falseValue = 'F' formula;

	formula at = '@' individual formula;

	formula	negation = '~' formula;
	formula	conjunction = formula '&' formula;
	formula	disjunction = formula '|' formula;
	formula	implication = formula '->' formula;
	//formula	equivalence = formula '<->' formula;

	individual successorImp = 'f' '(' individual ',' formula ',' formula ')';
	//individual successorNot = 'g' '(' individual ',' formula ')';
	formula relation = 'R' '(' individual ',' individual ')';
	formula equality = '[' individual '=' individual ']';

	individual successorH = 'h' '(' individual ',' individual ',' individual ')';
}
