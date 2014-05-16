specification P;

options{
equality.keywords={equivalence}

name.separator=

//branch.selection.strategy = mettel.core.tableau.MettelSimpleFIFOBranchSelectionStrategy

}

syntax P{
	sort formula;
	sort nominal;

	//Connectives in a decreasing priority order
	formula true = 'true';
	formula false = 'false';
	formula singleton = '{' nominal '}';
	formula	negation = '~' formula;
	formula O = 'O' formula;
	formula at = '@' nominal formula;
	formula	conjunction = formula '&' formula;
	formula	disjunction = formula '|' formula;
	formula	implication = formula '->' formula;
	formula	equivalence = formula '<->' formula;
	nominal f = 'f' '('  nominal ',' formula ',' formula ')'; //Skolem function
	nominal g = 'g' '('  nominal ',' formula ')'; //Skolem function
	nominal h = 'h' '('  nominal ',' formula ')'; //Skolem function

}

tableau P{
@l P @l ~P /  priority 0 $;
@l false / priority 0 $;
@l ~true / priority 0 $;
@l ~(~P) / @l P priority  1$;
@l ~(P | Q) / @l ~P @l ~Q  priority 1 $;
@l (P & Q) / @l P @l Q priority 1 $;

@l ~P (P|Q) / @l Q  priority 2 $;
@l P @l (~P|Q) / @l Q priority 2$;
@l ~Q @l (P|Q) / @l P priority 2$;
@l Q @l (P|~Q) / @l P priority 2$;

@l (P | Q) @l (~P | R) / @l Q $| @l R priority 3$;
@l (Q | P) @l (~P | R) / @l Q $| @l R priority 3$;
@l (P | Q) @l (R | ~P) / @l Q $| @l R priority 3$;
@l (Q | P) @l (R | ~P) / @l Q $| @l R priority 3$;

//~(P & Q) (P | R) / ~Q $| R priority 3$;
//~(Q & P) (P | R) / ~Q $| R priority 3$;
//~(P & Q) (R | P) / ~Q $| R priority 3$;
//~(Q & P) (R | P) / ~Q $| R priority 3$;

@l (P | Q) / @l P $| @l Q priority 4$;
@l ~(P & Q) / @l ~P $| @l ~Q priority 4$;

@l (O P) @l ~(O Q) / @f(l,P,~Q) P @f(l,P,~Q) ~Q priority 1$;
@l (O P) / @g(l,P) P priority 1$;
@l ~(O P) / @h(l,P) ~P priority 1$;
}
