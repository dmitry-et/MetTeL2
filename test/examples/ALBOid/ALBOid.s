specification ALBOid;

syntax ALBOid{

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
	concept equality = '[' individual '=' individual ']';

	role id = 'id';
	role converse = role '-';
	role negation = '~' role;
	role intersection = role '&' role;
	role union = role '|' role;

	individual skolemF = 'f' '(' individual ',' role ',' concept ')';
}
