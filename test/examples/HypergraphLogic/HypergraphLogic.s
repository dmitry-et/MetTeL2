specification HypergraphLogic;

options {

    name.separator=

}

syntax HypergraphLogic {

   sort formula, world;
   
   //Connectives in decreasing order of priority

   //formula true = 'true';
   formula false = 'false';
   
   formula trueValue = 'T' formula;
   formula falseValue = 'F' formula;
   
   formula at = '@' world formula;
   
   formula negation = '-' formula;
   formula dualNegation = '~' formula;
   formula box = '[]' formula;
   formula dualBox = '[[]]' formula;
   formula diamond = '<>' formula;
   formula dualDiamond = '<<>>' formula;
   formula conjunction = formula '&' formula;
   formula disjunction = formula '|' formula;
   formula implication = formula '->' formula;
   formula dualImplication = formula '>-' formula;
   
   world successorImp = 'fi' '(' world ',' formula ',' formula ')';
   world successorDualImp = 'fDi' '(' world ',' formula ',' formula ')';
   world successorNot = 'fn' '(' world ',' formula ')';
   world successorDualNot = 'fDn' '(' world ',' formula ')';
   world successorDia = 'fd' '(' world ',' formula ')';
   world successorDualDia = 'fDd' '(' world ',' formula ')';
   world successorg1 = 'g1' '(' world ',' formula ')';
   world successorg2 = 'g2' '(' world ',' formula ')';
   world successorh1 = 'h1' '(' world ',' world ')';
   world successorh2 = 'h2' '(' world ',' world ')';

   // intuitionistic relation
   formula greater_than = 'GT' '(' world ',' world ')';
   // accessibility relation to define semantics of modalities
   formula relation = 'R' '(' world ',' world ')';

   // for blocking
   formula equality = '[' world '=' world ']';

}

// Tableau calculus specification for hypergraph logic

tableau HypergraphLogic {

// closure rules
@i(T false) 
    / 
        priority 0 $;
//@i(F true) / 
//        priority 0 $;

@i(T P) @i(F P) 
    /  
        priority 1 $;

// or rules
@i(T(P|Q)) 
    / @i(T P) $| @i(T Q) 
        priority 7 $;
@i(F(P|Q)) 
    / @i(F P) @i(F Q) 
        priority 3 $;

// and rules
@i(T(P&Q)) 
    / @i(T P) @i(T Q) 
        priority 3 $;
@i(F(P&Q)) 
    / @i(F P) $| @i(F Q) 
        priority 7 $;

// rules for intuitionistic negation
@i(T(-P)) GT(i,j) 
    / @j(F P) 
        priority 4 $;
@i(F(-P)) 
    / GT(i,fn(i,P)) @fn(i,P)(T P) 
        priority 10 $;

// rules for intuitionistic implication
@i(T(P->Q)) GT(i,j) 
    / @j(F P) $| @j(T Q) 
        priority 8 $;
@i(F(P->Q)) 
    / GT(i,fi(i,P,Q)) @fi(i,P,Q)(T P) @fi(i,P,Q)(F Q) 
        priority 10 $;

// box operator
@i(T([]P)) R(i,j) 
    / @j (T P) 
        priority 2 $;
@i(F([]P)) 
    / R(i,fd(i,P)) @fd(i,P)(F P) 
        priority 7 $;

// diamond operator
// <>alpa = alpha + (GT;R^;GT)
// + is dilation operator or image of alpha under (GT;R^;GT)
// u \in X + A  iff  \exists x \in X such that (x,u) \in A
// u \in <>alpha  iff  \exists x \in alpha such that (x,u) \in (GT;R^;GT)
//                iff  \exists x \in alpha such that (u,x) \in (GT^;R;GT^)
// not u \in <>alpha  iff  \forall x ((x,u) \in (GT;R^;GT) implies not x \in alpha)
//                    iff  \forall x ((u,x) \in (GT^;R;GT^) implies not x \in alpha)
@i(T(<>P))
    / GT(g1(i,P),i) R(g1(i,P),g2(i,P)) GT(fd(i,P),g2(i,P)) @fd(i,P)(T P)
        priority 7 $;
@i(F(<>P)) GT(j,i) R(j,k) GT(m,k)
    / @m(F P)
        priority 2 $;

// GT is a preorder
// reflexivity
@i P 
    / GT(i,i) 
        priority 3 $;
// transitivity
GT(i,j) GT(j,k) 
    / GT(i,k) 
        priority 4 $;
// GT is antisymmetric (needed?)
// GT(i,j) GT(j,i) 
//     / [i=j] 
//         priority 4 $;

// sets form downsets
@i(T P) GT(i,j) 
    / @j(T P) 
        priority 4 $;

// R satisfies: GT;R;GT subset R
GT(i,j1) R(j1,j2) GT(j2,k) 
    / R(i,k) 
        priority 4 $;
// John: not needed since GT is reflexive
// R(i,j)
//     / GT(i,h1(i,j)) R(h1(i,j),h2(i,j)) GT(h2(i,j),j) 
//         priority 10 $;

// for blocking
// GT(i,j) 
//     / [i=j] $| (F([i=j])) 
//         priority 9 $;
@i P @j Q
    / [i=j] $| (F([i=j])) 
        priority 9 $;
(F([i=i])) 
    / 
        priority 0 $;

}
