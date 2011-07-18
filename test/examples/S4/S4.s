// Specification of modal logic S4
specification S4;

syntax S4{
	sort concept;
	sort nominal;

	concept true = 'true';
	concept false = 'false';
	concept	negation = '~' concept;
	concept diamond = '<>' concept;
	concept satisfactory= '@' nominal concept;
	concept	disjunction  = concept '|' concept;
	nominal f = 'f' '('  nominal ',' concept  ')'; //Skolem function
}
