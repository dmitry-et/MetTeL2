specification S4_dfgsyntax;  // tableau language for S4 (dfg syntax)

options{
name.separator=
}

syntax S4_dfgsyntax {
    sort formula, rel, world;

    formula true = 'true';
    formula false = 'false';
    formula at = '@' '(' world ',' formula ')';
    formula negation = 'not' '(' formula ')';
    formula box = 'box' '(' rel ',' formula ')';
    formula dia = 'dia' '(' rel ',' formula ')';
    formula disjunction  = 'or' '(' formula ',' formula ')';
    formula conjunction  = 'and' '(' formula ',' formula ')';
    formula implication  = 'implies' '(' formula ',' formula ')';

    world   func = 'f' '(' world ',' rel ',' formula  ')';
    formula relation = 'R' '(' rel ',' world ',' world ')';
    formula equality = '[' world '=' world ']';
}

tableau S4_dfgsyntax{
@(s, P)  @(s, (not(P)))            // Closure rule
   /                             priority 0 $; 

// Decomposition rules
@(s, (not((not(P)))))             // double negation
   /  @(s, P)                       priority 1 $;

@(s, (or(P,Q)))                 // or rules
   /  @(s, P)  $|  @(s, Q)             priority 3 $;
@(s, (not( ( or(P,Q) ) ))) 
   /  @(s, (not(P)))  @(s, (not(Q)))   priority 1 $;

@(s, (and(P,Q)))                // and rules
   /  @(s, P)  @(s, Q)                 priority 1 $;
@(s, (not( ( and(P,Q) )))) 
   /  @(s, (not(P)))  $|  @(s, (not(Q)))     priority 3 $;

@(s, (implies(P,Q)))            // implies rules
   /  @(s, (not(P)))  $|  @(s, Q)      priority 3 $;
@(s, (not((implies(P,Q)))))
   /  @(s, P)  @(s, (not(Q)))          priority 1 $;

@(s, (box(r,P)))  R(r,s,t)      // box rule
   /  @(t, P)                       priority 2 $;
@(s, (not((box(r,P))))) 
   /  R(r,s,f(s,r,P))  @(f(s,r,P), (not(P)))  priority 7 $;

@(s, (dia(r,P)))                // dia rule
   /  R(r,s,f(s,r,P))  @(f(s,r,P), (not(P)))  priority 7 $;
@(s, (not((dia(r,P)))))  R(r,s,t)  
   /  @(t, (not(P)))                 priority 2 $;

@(s, P)                     // Reflexivity
   /  R(r,s,s)                     priority 1 $;
R(r,s,t)  R(r,t,u)          // Transitivity
   /  R(r,s,u)                     priority 2 $;

@(s, P)   @(t, Q)              // Blocking rule
    /  [s=t]  $|  (not(([s=t])))        priority 6 $;

}

