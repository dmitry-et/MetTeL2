specification KmNotKE;

syntax KmNot{
	sort formula, relation, individual;

	formula false = 'false';

	formula	negation = '~' formula;
	formula box = '[' relation ']' formula;

	formula	disjunction = formula '|' formula;

	formula at = '@' individual formula;

	individual successor = 'f' '(' individual ',' relation ',' formula ')';
	
	formula link = '!' relation '(' individual ',' individual ')';
	
	relation negation = '~' relation;
	
	formula equality = '{' individual '=' individual '}';
}
