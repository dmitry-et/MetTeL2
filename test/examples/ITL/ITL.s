specification ITL;

options{
	equality.keywords={equality, equivalence}
	name.separator=

	tableau.rule.delimiter=;
	tableau.rule.branch.delimiter=||
}

syntax ITL{
	sort formula, point, interval;
	
	interval interval = '[' point ',' point ']';
	
	formula false = 'false';
	formula less = point '<' point;
	formula equality = point '=' point;
	formula relationA = 'R' interval interval;
	formula interval = '@' interval formula;
	
	formula negation = '~' formula;
	formula disjunction = formula '|' formula;
	
	formula diamond = '<A>' formula;
	
}

tableau ITL
{
	@i false / priority 0;
	@i p @i ~p / priority 0;
	
	a < a / priority 0;
	a < b b < c / a < c priority 3;
	
	@[a,b] p / a < b priority 1;
	
	@ i (p|q) / @i p || @i q priority 5;
	@ i ~(p|q) / @i ~p @i ~q priority 3;
	
	@[a,b] <A>p / @[b,f(b,p)] p priority 9;
	@[a,b] ~(<A>p) b < c / @[b,c] ~p priority 4;
	
	a < b c < d / 
		c = a || c < a || a < c priority 7;
	a < b c < d / 
		d = a || d < a || a < d priority 7;
}