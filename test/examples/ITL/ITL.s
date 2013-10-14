specification ITL;

options{
	equality.keywords={equality}
	name.separator=

	tableau.rule.delimiter=;
	tableau.rule.branch.delimiter=||
	
	branch.selection.strategy=mettel.core.tableau.MettelSimpleLIFOBranchSelectionStrategy
}

syntax ITL{
	sort formula, point;
	
	formula false = 'false';
	
	formula less =  '{' point '<' point '}';
	formula equality =  '{{' point '=' point '}}';
	
	formula negation = '~' formula;
	formula diamond = '<A>' formula;
	formula box = '[A]' formula;
	formula at = '[' point ',' point ']' ':' formula;
	
	formula conjunction = formula '&' formula;
	formula disjunction = formula '|' formula;
	formula implication = formula '->' formula;
	
	point function = 'f' '(' point ',' formula ')';
}

tableau ITL{
	[a,b]:false / priority 0;
	[a,b]:p [a,b]:~p / priority 0;
	
	{a < a} / priority 0;
	{a < b} {b < c} / {a < c} priority 3;
	
	[a,b]:p / {a < b} priority 1;
	
	[a,b]:~(~p) / [a,b]:p priority 1;
	
	[a,b]:(p&q) / [a,b]:p [a,b]:q priority 3;
	[a,b]:~(p&q) / [a,b]:(~p|~q) priority 1;
//	[a,b]:~(p&q) / [a,b]:~p || [a,b]:~q priority 5;
//	[a,b]:~(p&q) [a,b]:p / [a,b]:~q priority 4;
//	[a,b]:~(p&q) [a,b]:q / [a,b]:~p priority 4;

	[a,b]:(p|q) / [a,b]:p || [a,b]:q priority 5;
	[a,b]:(~p|q) [a,b]:p / [a,b]:q priority 4;
	[a,b]:(p|~q) [a,b]:q / [a,b]:p priority 4;
	[a,b]:(p|q) [a,b]:~p / [a,b]:q priority 4;
	[a,b]:(p|q) [a,b]:~q / [a,b]:p priority 4;
	[a,b]:~(p|q) / [a,b]:~p [a,b]:~q priority 3;

//	[a,b]:(p->q) / [a,b]:~p || [a,b]:q priority 5;
	[a,b]:(p->q) / [a,b]:(~p|q) priority 1;
//	[a,b]:(p->q) [a,b]:p / [a,b]:q priority 4;
//	[a,b]:(p->q) [a,b]:~q / [a,b]:~p priority 4;
	[a,b]:~(p->q) / [a,b]:p [a,b]:~q priority 3;
	
	[a,b]:<A>p / [b,f(b,p)]:p priority 9;
	[a,b]:~(<A>p) {b < c} / [b,c]:~p priority 4;

	[a,b]:~([A]p) / [b,f(b,p)]:~p priority 9;
	[a,b]:[A]p {b < c} / [b,c]:p priority 4;
	
	{a < b} {c < d} / 
		{{c = a}} || {c < a} || {a < c} {c < b} ||
		{{c = b}} || {b < c} priority 7;
	{a < b} {c < d} / 
		{{d = a}} || {d < a} || {a < d} {d < b} ||
		{{d = b}} || {b < d} priority 7;
}