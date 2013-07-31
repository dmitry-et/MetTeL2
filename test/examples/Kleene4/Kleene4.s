specification Kleene4;

syntax Kleene4{
	sort valuation, formula;
	//Connectives in a decreasing priority order
	valuation true = 'T' formula | undecidedTrue = 'UT' formula | undecidedFalse = 'UF' formula | false = 'F' formula;
	formula true = 'true' | false = 'false';
	formula	negation = '~' formula;
	formula	disjunction = formula '|' formula;
}

tableau Kleene4{
T P  F P /  priority 0 $;                 T P  UF P /  priority 0 $;
UT P  F P /  priority 0 $;                UT P  UF P /  priority 0 $;

T ~P / F P priority  1$;                  UT ~P / UF P priority 1$;
UF ~P / UT P priority 1$;                 F ~P / T P priority 1$;

T (P | Q) / T P $| T Q priority 2$;       UT (P | Q) / UT P $| UT Q priority 2$;
UF (P | Q) / UF P $| UF Q priority 2$;    F (P | Q) / F P $| F Q priority 2$;
}