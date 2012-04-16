specification Kleene;

syntax Kleene{

	sort prop2;
    sort prop; //The first sort is the main sort
	sort individual;


  
    individual in1= 'f' ;
    individual in2= 't' ; 
    individual in3= 'FU'; // fu
    individual in4= 'TU'; // tu
  
	//Connectives in a decreasing priority order
    prop    true ='true';
	prop	negation = '~' prop;
	prop	disjunction = prop '|' prop;
    prop2    lab = '@' individual prop;
 
  
}
