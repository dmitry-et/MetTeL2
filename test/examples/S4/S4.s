// Specification of modal logic S4
specification S4;

syntax S4{
	sort formula;
	sort nominal;

	formula true = 'true';
	formula false = 'false';
	formula singleton = '{' nominal '}';
	formula	negation = '~' formula;
	formula diamond = '<>' formula;
	formula at = '@' nominal formula;
	formula	disjunction  = formula '|' formula;
//	formula formulaEquality = formula '=' formula; //Testing some features
//	formula nominalEquality = nominal '=' nominal;
	nominal f = 'f' '('  nominal ',' formula  ')'; //Skolem function
}
