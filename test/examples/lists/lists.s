specification lists;

syntax lists{
    sort formula, element, list;
    
    list empty = '<>' |
         composite = '<' element list '>';
    list concatenation = list '*' list;
    
    formula elementInequality = '[' element '!=' element ']' |
            listInequality =  '{' list '!=' list '}';
    
}