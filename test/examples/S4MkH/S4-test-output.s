specification S4;

syntax S4{

	sort concept,nominal;

	concept true = 'true'
	| false = 'false'
	| negation = '~' concept
	| singleton = '{' nominal '}'
	| diamond = 'dia' concept
	| satisfactory = '@' nominal concept
	| disjunction = concept '|' concept;

	nominal skolem = 'f' '[' nominal ',' concept ']';

}
