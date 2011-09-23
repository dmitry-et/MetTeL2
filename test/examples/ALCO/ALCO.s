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
	concept	existentialRestriction = 'exists'
									 role '.'
									 concept;
	concept universalRestriction = 'forall'
									 role '.'
									 concept;
	concept at = '@' individual concept;

	concept	conjunction = concept '&' concept;
	concept	disjunction = concept '|' concept;
	concept	implication = concept '->' concept;
	concept	equivalence = concept '<->' concept;

	role composition = role ';' role;
	role union = role '|' role;
	role converse = role '-';

	individual skolemF = 'f' '(' individual ',' role ',' concept ')';
	individual skolemG = 'g' '(' individual ',' individual ',' role ',' role ')';

}

/*syntax ALCOTableau extends ALCO{

	individual skolemFunction = 'f' '(' individual ',' role ',' concept ')';

}*/
