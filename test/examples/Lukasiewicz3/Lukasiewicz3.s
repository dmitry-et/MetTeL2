specification Lukasiewicz3;

syntax Lukasiewicz{
	sort valuation, formula;
	//Connectives in a decreasing priority order
	valuation true = 'T' formula | unknown = 'U' formula | false = 'F' formula;
	formula true = 'true' | false = 'false';
	formula	negation = '~' formula;
	formula	conjunction = formula '&' formula;
	formula	disjunction = formula '|' formula;
	formula	implication = formula '->' formula;
}

tableau Lukasiewicz{
T P  F P /  priority 0 $;                 T P  U P /  priority 0 $;
U P  F P /  priority 0 $;                 U P  F P /  priority 0 $;

T ~P / F P priority  1$;    U ~P / U P priority 1$;   F ~P / T P priority 1$;                

T (P & Q) / T P  T Q priority 2$;       F (P & Q) /  F P $| F Q priority 1$;
    U (P & Q) /  T P  U Q $| U P  T Q $| U P  U Q  priority 3$;

T (P | Q) / T P $| T Q priority 2$;       F (P | Q) /  F P  F Q priority 1$;
    U (P | Q) /  F P  U Q $| U P  F Q $| U P  U Q  priority 3$;

F (P -> Q) /  T P  F Q priority 1$;       U (P -> Q) / U P  F Q $|  T P  U Q  priority 2$;
    T (P -> Q) / T Q $|  F P $| U P  U Q  priority 3$;      

T false / priority 0$;                    U false / priority 0$;
U true / priority 0$;                     F true / priority 0$;
}