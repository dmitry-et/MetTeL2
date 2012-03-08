// Specification of modal logic KEn discussed with michal
specification KE3;

syntax KE3{
	sort formula;

	// to express pi
	sort nominal;
	 
	formula singleton = '{' nominal '}';
	formula at = '@' nominal formula;
	nominal f = 'f' '('  nominal ',' formula  ')'; //Skolem function
	//for rules which generate more than one nominal
	nominal f1 = 'f1' '('  nominal ',' formula  ')';
	nominal f2 = 'f2' '('  nominal ',' formula  ')';
	nominal f3 = 'f3' '('  nominal ',' formula  ')';
	nominal f4 = 'f4' '('  nominal ',' formula  ')';
	
	formula true = 'true';
	formula false = 'false';
	formula	negation = '~' formula;
	formula	conjunction  = formula '&' formula;
	formula	disjuntion  = formula '|' formula;
	
	formula diamond = '<>' formula;
	formula existl1 = 'E<1' formula;
	formula existl2 = 'E<2' formula;
	formula existl3 = 'E<3' formula;
	formula existe0 = 'E=0' formula;
	formula existe1 = 'E=1' formula;
	formula existe2 = 'E=2' formula;
	formula existe3 = 'E=3' formula;
	formula existg0 = 'E>0' formula;
	formula existg1 = 'E>1' formula;
	formula existg2 = 'E>2' formula;
	formula existg3 = 'E>3' formula;

	

	formula equivalence = formula  '<->' formula; //Testing some features
	formula equality = '[' nominal '=' nominal ']';
}
