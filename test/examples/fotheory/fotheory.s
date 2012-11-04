specification fotheory;
syntax SomeFOTheory{
	sort formula, term, var;

	term var = '!' var;
	term c = 'c';
	term f = 'f' '(' term ')';
	term g = 'g' '(' term ',' term ')';

	//Connectives in a decreasing priority order
	formula true = 'true';
	formula false = 'false';
	formula P = 'P' '(' term ')';
	formula Q = 'Q' '(' term ',' term ')';
	formula	negation = '~' formula;
	formula	conjunction = formula '&' formula;
	formula	disjunction = formula '|' formula;
	formula	implication = formula '->' formula;
	formula	equivalence = formula '<->' formula;
	formula universalQuantifier = 'forall' var formula;
	formula existentialQuantifier = 'exists' var formula;
}
