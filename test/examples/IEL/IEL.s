specification IEL;

syntax IEL{
    sort formula, individual, prop, agent;

    formula true = 'true';
    formula false = 'false';
    formula singleton = '{' individual '}';
    formula atom = '#' prop;  
    formula negation = '~' formula;
    formula diamondQ = '<q>' agent formula;      
    formula diamondK = '<k>' agent formula;      
    formula diamondX = '<x>' agent formula;
    formula at = '@' individual formula;
    formula query = '[?' formula ']' agent formula;
    formula resol = '[!]' agent formula;
    formula disjunction  = formula '|' formula;
    formula equality = '[' individual '=' individual ']';

    individual fq = 'fq' '(' individual ',' agent ',' formula ')'; 
    individual fk = 'fk' '(' individual ',' agent ',' formula ')';
    individual fx = 'fx' '(' individual ',' agent ',' formula ')';
}
