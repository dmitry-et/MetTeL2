specification KmNotAtomKEUP;

syntax KmNot{
	sort formula, relation, individual;

	formula false = 'false';

	formula	negation = '~' formula;
	formula box = '[' relation ']' formula;
	formula domain = 'Dom' formula;
	formula relation = 'Rel' relation;

	formula	disjunction = formula '|' formula;

	formula at = '@' individual formula;

	individual successor = 'f' '(' individual ',' relation ',' formula ')';
	
	formula link = '!' relation '(' individual ',' individual ')';
	
	relation negation = '~' relation;
	
	formula equality = '{' individual '=' individual '}';
}
