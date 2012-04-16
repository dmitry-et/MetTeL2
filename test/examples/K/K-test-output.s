specification K;

syntax K{

	sort formula;

	formula true = 'true'
	| false = 'false'
	| negation = '~' formula
	| conjunction = formula '&' formula
	| disjunction = formula '|' formula
	| implication = formula '->' formula
	| equivalence = formula '<->' formula;

}
