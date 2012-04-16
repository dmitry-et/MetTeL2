// Specification of modal logic S4
specification S4;

syntax S4{
	sort concept;
	sort nominal;

	concept true = 'true';
	concept false = 'false';
	concept	negation = '~' concept;
	concept singleton = '{' nominal '}';
	concept diamond = 'dia' concept; // had problem with <>
	concept satisfactory= '@' nominal concept;
	concept	disjunction  = concept '|' concept; // has tested using + instead of | 
	nominal skolem = 'f' '['  nominal ',' concept  ']'; 
	
}


//semantic S4{

 //   	Var x,y;
  //  	holds (=);
//	holds (~C,x) <=> ~ holds (C,x);
//	holds (C|D,x) <=>  holds (C,x) | holds (D,x);
//	holds (<>C,x) <=>  exist  y (R(x,y) &  holds(C,y));
//	holds (R(x,x));
//	holds (~R(x,y) | ~R(y,z) | R(x,z));

//}
