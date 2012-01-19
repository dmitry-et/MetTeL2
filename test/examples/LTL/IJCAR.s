// Specification of IJCAR
specification IJCAR;

syntax IJCAR{
	sort formula;
	sort list;
	sort element;

	list empty = '0';
	list oneElement  =  '@' element list;
	list combination =  list '*' list;

	formula ftrue 	= 'true';
	formula ffalse 	= 'false';

	formula elementEq = '{' element '=' element '}';
	formula listEq    = '[' list    '=' list ']';
	formula negation = '~' formula;
}
