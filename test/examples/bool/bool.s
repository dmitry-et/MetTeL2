specification bool;

options{
equality.keywords={equivalence}

name.separator=

//branch.selection.strategy = mettel.core.tableau.MettelSimpleFIFOBranchSelectionStrategy

}

syntax Boolean{
	sort formula;

	//Connectives in a decreasing priority order
	formula true = 'true';
	formula false = 'false';
	formula	negation = '~' formula;
	formula	conjunction = formula '&' formula;
	formula	disjunction = formula '|' formula;
	formula	implication = formula '->' formula;
	formula	equivalence = formula '<->' formula;

}

tableau Boolean{
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
~(P & Q) / ~P $| ~Q priority 4
}
