specification IEL;

options{
name.separator=
}

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

tableau IEL{
//equality rules
@l{l2} / @l2{l} priority 1$;
@l~{l2} / @l2{l2} priority 1$;
@l P / @l{l} priority 1$;
@l <q> A {l2} / @l2 {l2} priority 1$;
@l <k> A {l2} / @l2 {l2} priority 1$;
@l <x> A {l2} / @l2 {l2} priority 1$;
@l P  @l{l2} /  @l2 P priority 2$;
@l <x> A {l2}  @l2 {l3} / @l <x> A {l3} priority 2$;
@l <q> A {l2}  @l2 {l3} / @l <q> A {l3} priority 2$;
@l <k> A {l2}  @l2 {l3} / @l <k> A {l3} priority 2$;

//Decomposition rules
@l ~(~P) / @l P priority 1$;
@l(P|Q) / @l P $| @l Q priority 3$;
@l~(P|Q) / @l~P @l~Q priority 1$;
@l<q> A P / @l <q> A {fq(l,A,P)} @fq(l,A,P)P priority 7$;
@l<k> A P / @l <k> A {fk(l,A,P)} @fk(l,A,P)P priority 7$;
@l<x> A P / @l <x> A {fx(l,A,P)} @fx(l,A,P)P priority 7$;
@l ~(<q> A P) @l <q> A {l2} / @l2~P priority 2$;
@l ~(<k> A P) @l <k> A {l2} / @l2~P priority 2$;
@l ~(<x> A P) @l <x> A {l2} / @l2~P priority 2$;

@l ([?P] A #B) / @l #B priority 2$;
@l ~([?P] A #B) / @l ~(#B) priority 2$;
@l ([?P] A {l2}) / @l ({l2}) priority 2$;
@l ~([?P] A {l2}) / @l ~{l2} priority 2$;
@l ([!] A {l2}) / @l {l2} priority 2$;
@l ~([!] A {l2}) / @l ~{l2} priority 2$;
@l ([!] A #B) / @l #B priority 2$;
@l ~([!] A #B) / @l ~(#B) priority 2$;

@l ([?P] A ~Q) / @l ~([?P] A Q)$;
@l ([!] A ~Q) / @l ~([!] A Q)$;
@l ([?P] A (P1 | P2)) / @l ([?P] A P1) $| @l ([?P] A P2)$;
@l ([!] A (P1 | P2)) / @l ([!] A P1) $| @l ([!] A P2)$;
@l ([!] A ~(<q> A ~P)) / @l ~(<q> A ~([!] A P))$;
@l ([!] A ~(<x> A ~P)) / @l ~(<x> A ~([!] A P))$;
@l ([?P] A ~(<k> A ~Q)) / @l ~(<k> A ~([?P] A Q))$;
@l ([!] A ~(<k> A ~P)) / @l ~(<x> A ~([!] A P))$;
@l ([?P] A ~(<q> A ~Q)) / @l ~(~P | <q> A ~(~P | [?P] A Q)) $| @l ~(P | <q> A ~(P | [?P] A Q))$;
@l ([?P] A ~(<x> A ~Q)) / @l ~(~P | <x> A ~(~P | [?P] A Q)) $| @l ~(P | <x> A ~(P | [?P] A Q))$;

@l ~([?P] A ~Q) / @l ([?P] A Q)$;
@l ~([!] A ~Q) / @l ([!] A Q)$;
@l ~([?P] A (P1 | P2)) / @l ~([?P] A P1)  @l ~([?P] A P2)$;
@l ~([!] A (P1 | P2)) / @l ~([!] A P1)  @l ~([!] A P2)$;
@l ~([!] A ~(<q> A ~P)) / @l <q> A ~([!] A P)$;
@l ~([!] A ~(<x> A ~P)) / @l <x> A ~([!] A P)$;
@l ~([?P] A ~(<k> A ~Q)) / @l (<k> A ~([?P] A Q))$;
@l ~([!] A ~(<k> A ~P)) / @l (<x> A ~([!] A P))$;
@l ~([?P] A ~(<q> A ~Q)) / @l (~P | <q> A ~(~P | [?P] A Q)) @l (P | <q> A ~(P | [?P] A Q))$;
@l ~([?P] A ~(<x> A ~Q)) / @l (~P | <x> A ~(~P | [?P] A Q)) @l (P | <x> A ~(P | [?P] A Q))$;

// Theory rules
@l<q> A {l2} @l<k> A {l2} / @l<x> A {l2} priority 2$;
@l<x> A {l2} / @l<q> A {l2} @l<k> A {l2} priority 2$;
@l {l} / @l <q> A {l} priority 1$;
@l {l} / @l <k> A {l} priority 1$;
@l {l} / @l <x> A {l} priority 1$;
@l <q> A {l2} @l2 <q> A {l3} / @l <q> A {l3} priority 2$;
@l <q> A {l2} / @l2 <q> A {l} priority 3$;
@l <k> A {l2} @l2 <k> A {l3} / @l <k> A {l3} priority 2$;
@l <k> A {l2} / @l2 <k> A {l} priority 3$;
@l <x> A {l2} @l2 <x> A {l3} / @l <x> A {l3} priority 2$;
@l <x> A {l2} / @l2 <x> A {l} priority 3$;

// Closure rule
@l P  @l~P /  priority 0$;

//Blocking related
@l{l0} / [l=l0] priority 1$;
[l=l0] / @l{l0} priority 1$;
@l~{l0} / ~([l=l0]) priority 1$;
~([l=l0]) / @l~{l0} priority 1$;
@l{l} @l0{l0} / [l=l0] $| ~([l=l0]) priority 6$;
}
