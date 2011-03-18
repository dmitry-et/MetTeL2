specification ALCO;

syntax ALCO{

	sort individual;
	sort concept;
	sort role;

	concept singleton = '{' individual '}';
    concept	negation = '~' concept;
	concept	conjunction = concept '&' concept;
	concept	disjunction = concept '|' concept;
	concept	implication = concept '->' concept;
	concept	equivalence = concept '<->' concept;
	concept	existentialRestriction = 'exists'
									 role '.'
									 concept;
	concept universalRestriction = 'forall'
									 role '.'
									 concept;
	role composition = role ';' role;
	role union = role '|' role;
	role converse = role '-';
	role weirdRole = 'weird' '(' role ',' individual ';' concept ')';
	role = 'empty' role '.' concept;

}
