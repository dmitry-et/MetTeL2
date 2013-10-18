specification ITLRel;

options{
	equality.keywords={equality}
	name.separator=

	tableau.rule.delimiter=;
	tableau.rule.branch.delimiter=||
	
//	branch.selection.strategy=mettel.core.tableau.MettelSimpleLIFOBranchSelectionStrategy
}

syntax ITLRel{
	sort formula, interval;
	
	interval min = 'min';
	interval max = 'max';
	
	formula false = 'false';
	
	formula R =  'R' interval interval;
	formula equality =  '{{' interval '=' interval '}}';
	
	formula negation = '~' formula;
	formula diamond = '<A>' formula;
	formula box = '[A]' formula;
	formula at = '@' interval formula;
	
	formula conjunction = formula '&' formula;
	formula disjunction = formula '|' formula;
	formula implication = formula '->' formula;
	
	interval F = 'f' '(' interval ',' formula ')';
	interval G = 'g' '(' interval ',' interval ')';
}

tableau ITLRel{
	R i i / priority 0;
	R i j R j i / priority 0;
	R max i / priority 0;
	R i min / priority 0;
	
	R i j R k g(i,j) / R k i priority 4;
	R i j R k i / R k g(i,j) priority 10;
	R i j R g(i,j) k / R j k priority 4;
	R i j R j k / R g(i,j) k priority 10;
	
	R i j R j k R i l R l k / {{ j = l }} priority 6;

	@i false / priority 0;
	@i p @i ~p / priority 0;
	
    @i ~(~p) / @i p priority 1;
	
	@i (p&q) / @i p @i q priority 3;
	@i ~(p&q) / @i (~p|~q) priority 1;

	@i (p|q) / @i p || @i q priority 5;
	@i (~p|q) @i p / @i q priority 4;
	@i (p|~q) @i q / @i p priority 4;
	@i (p|q) @i ~p / @i q priority 4;
	@i (p|q) @i ~q / @i p priority 4;
	@i ~(p|q) / @i ~p @i ~q priority 3;

	@i (p->q) / @i (~p|q) priority 1;
	@i ~(p->q) / @i p @i ~q priority 3;
	
	@i <A>p / R i f(i,p) @ f(i,p) p priority 9;
	@i ~(<A>p) R i j / @j ~p priority 4;

	@i ~([A]p) / R i f(i,p) @f(i,p) ~p priority 9;
	@i [A]p R i j / @j p priority 4;
}