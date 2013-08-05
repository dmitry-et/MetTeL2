// Specification of modal logic KEn discussed with michal
specification KE3;

options{
name.separator=
}

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

tableau KE3{
// Tableau Rules with second refined (Meeting 20th)
//Decomposition rules
@l (~(~P)) / @l P priority 1$;
@l (P&Q) / @l P @l Q priority 1$;
@l (~(P&Q)) / @l (~P) $| @l (~Q) priority 3$;

@l (<>P) / @l (<>{f(l,P)}) @f(l,P) P priority 8$;

@l (~(<>P)) @l (<>{l2}) / @l2 (~P) priority 2$;

@l ( E>0 P) / @f1(l,P) P priority 8$;
@l ( E>1 P) / @f1(l,P) P @f1(l,P) (~({f2(l,P)})) @f2(l,P) P priority 8$;
@l ( E>2 P) / @f1(l,P) P @f1(l,P) (~({f2(l,P)})) @f1(l,P) (~({f3(l,P)})) @f2(l,P) P @f2(l,P) (~({f3(l,P)})) @f3(l,P) P priority 8$;
@l ( E>3 P) / @f1(l,P) P @f1(l,P) (~({f2(l,P)})) @f1(l,P) (~({f3(l,P)})) @f1(l,P) (~({f4(l,P)})) @f2(l,P) P @f2(l,P) (~({f3(l,P)})) @f2(l,P) (~({f4(l,P)})) @f3(l,P) P @f3(l,P) (~({f4(l,P)})) @f4(l,P) P priority 8$;

@l (~( E>0 P)) @l1 ({l1})  / @l1 (~ P) priority 2$;
@l (~( E>1 P)) @l1 ({l1}) @l2 ({l2})  / @l1 (~ P) $| @l2 (~ P) $|  @l1 ({l2}) priority 4$;
@l (~( E>2 P)) @l1 ({l1}) @l2 ({l2}) @l3 ({l3})  / @l1 (~ P) $| @l2 (~ P) $| @l3 (~ P) $|  @l1 ({l2}) $| @l1 ({l3}) $| @l2 ({l3}) priority 5$;
@l (~( E>3 P)) @l1 ({l1}) @l2 ({l2}) @l3 ({l3}) @l4 ({l4})  / @l1 (~ P) $| @l2 (~ P) $| @l3 (~ P) $| @l4 (~ P) $|  @l1 ({l2}) $| @l1 ({l3}) $| @l1 ({l4}) $| @l2 ({l3}) $| @l2 ({l4}) $| @l3 ({l4}) priority 6$;


// Rewrite rules for lessThan and eqaul (from michal's paper)
@l (E<1 P) / @l (~(E>0 P)) priority 1$;
@l (E<2 P) / @l (~(E>1 P)) priority 1$;
@l (E<3 P) / @l (~(E>2 P)) priority 1$;
@l (E=0 P) / @l (~(E>0 P)) priority 1$;
@l (E=1 P) / @l (E<2 P) @l (E>0 P) priority 1$;
@l (E=2 P) / @l (E<3 P) @l (E>1 P) priority 1$;
@l (E=3 P) / @l (~(E>3 P)) @l (E>2 P) priority 1$;

@l (~(E=0 P)) / @l (E>0 P) priority 1$;
@l (~(E=1 P)) / @l (~((E<2 P)&(E>0 P))) priority 1$;
@l (~(E=2 P)) / @l (~((~(E>2 P))&(E>1 P))) priority 1$;
@l (~(E=3 P)) / @l (~((~(E>3 P))&(E>2 P))) priority 1$;

//equality rules
@l{l2} / @l2{l} priority 1$;
@l (~{l2}) / @l2{l2} priority 1$;

@l P / @l{l} priority 1$;

@l (<>{l2}) / @l2{l2} priority 1$;
@l P  @l{l2} /  @l2 P priority 2$;
@l (<>{l2})  @l2{l3} / @l (<>{l3}) priority 2$;


// Closure rule
@l P  @l (~P) /  priority 0$;
false / priority 0$;



//Blocking related
@l{l0} / [l=l0] priority 1$;
[l=l0] / @l{l0} priority 1$;
@l (~{l0}) / (~([l=l0])) priority 1$;
(~([l=l0])) / @l (~{l0}) priority 1$;
@l{l} @l0{l0} / [l=l0] $| (~([l=l0])) priority 7$;
}
