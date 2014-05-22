specification ext;

options{
equality.keywords={equality}

name.separator=

//branch.selection.strategy = mettel.core.tableau.MettelSimpleFIFOBranchSelectionStrategy

}

syntax ext{
	sort formula, set;

	//Connectives in a decreasing priority order
	set emptyset = 'emptyset';
	set complement = '~' set;
	set intersection = set '&' set;
	set union = set 'U' set;
	set f = 'f' '(' set ',' set ')';
	formula belong = '<' set 'in' set '>';
	formula equality = '[' set '=' set ']';
	formula negation = '~' formula;
	formula	conjunction = formula '&' formula;
	formula	disjunction = formula '|' formula;
	formula	implication = formula '->' formula;
	formula	equivalence = formula '<->' formula;

}

tableau ext{
P ~P /  priority 0 $;
~(~P) / P priority  1$;
~(P | Q) / ~P ~Q  priority 1 $;
P & Q / P Q priority 1 $;

~P (P|Q) / Q  priority 2 $;
P (~P|Q) / Q priority 2$;
~Q (P|Q) / P priority 2$;
Q (P|~Q) / P priority 2$;

(P | Q) (~P | R) / Q $| R priority 3$;
(Q | P) (~P | R) / Q $| R priority 3$;
(P | Q) (R | ~P) / Q $| R priority 3$;
(Q | P) (R | ~P) / Q $| R priority 3$;

//~(P & Q) (P | R) / ~Q $| R priority 3$;
//~(Q & P) (P | R) / ~Q $| R priority 3$;
//~(P & Q) (R | P) / ~Q $| R priority 3$;
//~(Q & P) (R | P) / ~Q $| R priority 3$;

P | Q / P $| Q priority 4$;
~(P & Q) / ~P $| ~Q priority 4$;

~[x = y] / <f(x,y) in x> ~<f(x,y) in y> $| <f(x,y) in y> ~<f(x,y) in x> priority 3$;
<x in a U b> / <x in a> $| <x in b> priority 3$;
~<x in a U b> / ~<x in a> ~<x in b> priority 2$;
<x in a & b> / <x in a> <x in b> priority 2$;
~<x in a & b> / ~<x in a> $| ~<x in b> priority 3$;
}
