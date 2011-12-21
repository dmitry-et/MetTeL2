//
// This file is part of MetTeL.
//
// MetTeL is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// MetTeL is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with MetTeL.  If not, see <http://www.gnu.org/licenses/>.
//
// Author: Dmitry Tishkovsky
// Version: $Revision: 228 $ $Date: 2011-10-09 17:17:45 +0100 (Sun, 09 Oct 2011) $
//
grammar MettelFO;

options{
 k=1;
}

@header{
package mettel.fo;

import mettel.core.MettelExpression;
}

@lexer::header{
package mettel.fo;
}

@rulecatch{
catch (RecognitionException e) {
reportError(e);
throw e;
}
}
@members{
public Object recoverFromMismatchedToken(IntStream input, int ttype, BitSet follow)
       throws RecognitionException{
    MismatchedTokenException e = new MismatchedTokenException(ttype, input);
    reportError(e);
    throw e;
}

private MettelLogicParser islandParser = null;
private Lexer islandLexer = null;

private MettelExpression expression() throws RecognitionException {
		CharStream in = ((Lexer)this.input.getTokenSource()).getCharStream();
		int index = in.index();
		int line = in.getLine();
		int charPositionInLine = in.getCharPositionInLine();
        islandLexer.setCharStream(in);
        in.seek(index);
        in.setLine(line);
        in.setCharPositionInLine(charPositionInLine);

		return islandParser.expression();
	}

}

formulae
	:
	formula+
	;

formula
	:
	equivalenceFormula
	;


equivalenceFormula
	:
	implicationFormula
	('<->'
	implicationFormula)*
	;


implicationFormula
	:
	disjunctionFormula
	('->'
	disjunctionFormula)*
	;


disjunctionFormula
	:
	conjunctionFormula
	('|'
	conjunctionFormula)*
	;

conjunctionFormula
	:
	existentialFormula
	('&'
	existentialFormula)*
	;

existentialFormula
	:
	('exists'
	  (ID ('exists' ID)*
	  |
	  '[' ID+ ']'
	  )
	)?
	universalFormula
	;

universalFormula
	:
	('forall'
	  (ID ('forall' ID)*
	  |
	  '[' ID+ ']'
	  )
	)?
	negationFormula
	;

negationFormula
	:
	('~')*
	basicFormula
	;

basicFormula
	:
	'true'
	|
	'false'
	|
	'(' formula ')'
	|
	atomicFormula
	;

atomicFormula
	:
	'holds' '(' {expression();} (',' term)+ ')'
	|
	ID '(' termList? ')'
	|
	'[' term '=' term ']'
	;

term
	:
	ID
	( '(' termList? ')' )?
	;

termList
	:
	term
	(','
	term)+
	;


//Trivial Lexer

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

/*
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
*/
