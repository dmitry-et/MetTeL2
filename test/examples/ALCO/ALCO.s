specification ALCO;

syntax ALCO{

	sort concept; //The first sort is the main sort
	sort individual;
	sort role;

	//Connectives in a decreasing priority order
	concept true = 'true';
	concept false = 'false';
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

	//Additional connectives for test purposes
	role weirdRole = 'weird' '(' role ',' individual ';' concept ')';
	role = 'empty' role '.' concept;

}
