grammar Test;

options{
	k=1;
//	backtrack=true;
//	memoize=true;
}

concepts
	:
	concept*
	EOF
	;

individualVariable
	:
	ID
	;

basicIndividual
	:
	individualVariable
	|
	'(' individual ')'
	;

individual
	:
	basicIndividual
	;

concept
	:
	universalRestrictionConcept
	;

conceptVariable
	:
	ID
	;

basicConcept
	:
	conceptVariable
	|
	'(' concept ')'
	;

falseConcept
	:
	basicConcept | 'false'
	;

trueConcept
	:
	falseConcept | 'true'
	;

singletonConcept
	:
	trueConcept | '{' individual '}'
	;

negationConcept
	:
	singletonConcept | '~' negationConcept
	;

conjunctionConcept
	:
	negationConcept ('&'  negationConcept)*
	;

disjunctionConcept
	:
	conjunctionConcept ('|' conjunctionConcept)*
	;

implicationConcept
	:
	disjunctionConcept ( '->' disjunctionConcept)*
	;

weirdConstantConcept
	:
	implicationConcept | 'weird'
	;

equivalenceConcept
	:
	weirdConstantConcept ( '<->' weirdConstantConcept)*
	;

existentialRestrictionConcept
	:
	('exists' role '.')* equivalenceConcept
	;

universalRestrictionConcept
	:
	('forall' role '.')* existentialRestrictionConcept
	;

role
	:
	emptyRole
	;

roleVariable
	:
	ID
	;


basicRole
	:
	roleVariable
	|
	'(' role ')'
	;

topRole
	:
	basicRole | 'top'
	;

compositionRole
	:
	topRole (';' topRole)*
	;

unionRole
	:
	compositionRole ('|' compositionRole)*
	;

converseRole
	:
	unionRole ('-')*
	;

weirdRole
	:
	converseRole | 'weird' '(' converseRole ',' individual ';' concept ')'
	;

weirdConstantRole
	:
	weirdRole | 'weirdConstant'
	;

emptyRole
	:
	weirdConstantRole | 'empty' weirdConstantRole '.' concept
	;

ID  :	('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
    ;

COMMENT
    :   '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    |   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    ;

WS  :   ( ' '
        | '\t'
        | '\r'
        | '\n'
        ) {$channel=HIDDEN;}
    ;

STRING
    :  '"' ( ESC_SEQ | ~('\\'|'"') )* '"'
    ;

CHAR:  '\'' ( ESC_SEQ | ~('\''|'\\') ) '\''
    ;

fragment
HEX_DIGIT : ('0'..'9'|'a'..'f'|'A'..'F') ;

fragment
ESC_SEQ
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UNICODE_ESC
    |   OCTAL_ESC
    ;

fragment
OCTAL_ESC
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment
UNICODE_ESC
    :   '\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
    ;
