specification lists;

syntax lists{
    sort formula, element, list;
    
    list empty = '<>' |
         composite = '<' element list '>';
    list concatenation = list '*' list;
    
    formula elementInequality = '[' element '!=' element ']' |
            listInequality =  '{' list '!=' list '}';
    
}

tableau lists{
[a != a] / priority 0$;
{L != L} / priority 0$;

{L0 != L1} / {L1 != L0} priority 1$;

{L0*<> != L1} / {L0 != L1} priority 1$;
{<>*L0 != L1} / {L0 != L1} priority 1$;

{<a <>>*L0 != L1} / {<a L0> != L1} priority 1$;
{<a L0>*L1 != L2} / {<a (L0*L1)> != L2} priority 1$;

{<a L0> != <b L1>} / [a != b] $| {L0 != L1} priority 2$;

{ (L0 * <>) *L1 != L } / { L0 * L1 != L } priority 1$;
{ (<> * L0) *L1 != L } / { L0 * L1 != L } priority 1$;
{ L0 * (L1 * L2) != L } / { (L0 * L1) *L2 != L } priority 1$;
}