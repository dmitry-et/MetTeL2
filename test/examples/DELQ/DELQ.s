// Specification of DELQ
specification DELQ;

syntax DELQ{
  sort formula;
  sort nominal;                                    //Atoms
  sort prop;                	

  formula true = 'T';                              //Top & Bot
  formula false = 'F';
  formula singleton = '{' nominal '}';             //Set
  formula negation = '~' formula;                  //Negation
  formula diamond1 = '<1>' formula;                //Static Modalities
  formula diamond2 = '<2>' formula;
  formula diamond3 = '<3>' formula;
  formula box1 = '[1]' formula;                    //Questions    Q
  formula box2 = '[2]' formula;                    //Knowledge    K
  formula box3 = '[3]' formula;                    //Interaction  X
  formula at = '@' nominal formula;                //At
  formula queryp = '[6p]' formula prop;  
  formula queryn = '[6n]' formula '{' nominal '}';  
  formula resolp = '[7p]' prop;
  formula resoln = '[7n]' '{' nominal '}';
  formula query = '[6]' formula formula;            //Dynamic Modalities
  formula resol = '[7]' formula;
  formula conjunction = formula '&' formula;
  formula disjunction = formula '|' formula;
  formula implication = formula '->' formula;
  formula equality = '[' nominal '=' nominal ']';  //
  nominal f1 = 'f1' '('  nominal ',' formula  ')';   //Skolem function for <1>
  nominal f2 = 'f2' '('  nominal ',' formula  ')';   //Skolem function for <2>
  nominal f3 = 'f3' '('  nominal ',' formula  ')';   //Skolem function for <3>
}