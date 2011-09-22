// Specification of modal logic S4
specification S4;

syntax S4{
	sort concept;
	sort nominal;

	concept true = 'true';
	concept false = 'false';
	concept singleton = '{' nominal '}';
	concept	negation = '~' concept;
	concept diamond = '<>' concept;
	concept at = '@' nominal concept;
	concept	disjunction  = concept '|' concept;
//	concept conceptEquality = concept '=' concept; //Testing some features
//	concept nominalEquality = nominal '=' nominal;
	nominal f = 'f' '('  nominal ',' concept  ')'; //Skolem function
}
