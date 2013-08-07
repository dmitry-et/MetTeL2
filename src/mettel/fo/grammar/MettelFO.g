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
// k=1;
backtrack = true;
}

@header{
package mettel.fo;

import java.util.Collection;

//import mettel.core.language.MettelLogicParser;
import mettel.core.language.MettelAbstractLogicParser;

import mettel.core.tableau.MettelExpression;
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

private MettelAbstractLogicParser islandParser = null;
private Lexer islandLexer = null;

private MettelExpression expression() throws RecognitionException {
    CharStream in = ((Lexer)this.input.getTokenSource()).getCharStream();
    int index = in.index();
    System.out.println(index);
    int line = in.getLine();
    int charPositionInLine = in.getCharPositionInLine();
    islandLexer.setCharStream(in);
    in.seek(index);
    in.setLine(line);
    in.setCharPositionInLine(charPositionInLine);
    MettelExpression e = islandParser.expression();
    System.out.println(in.index());
    return e;
}

private MettelFOObjectFactory factory = MettelFOObjectFactory.DEFAULT;

public MettelFOParser(TokenStream input, MettelAbstractLogicParser parser){
    this(input, MettelFOObjectFactory.DEFAULT, parser);
}

public MettelFOParser(TokenStream input, MettelFOObjectFactory factory, MettelAbstractLogicParser parser){
    this(input);
    this.factory = factory;
    //System.out.println("parser = " + parser);
    this.islandParser = parser;
    //System.out.println("parser.input != null?" + (parser.input == null));    
    this.islandLexer = (Lexer)parser.input.getTokenSource();
}

public MettelFOParser(TokenStream input, RecognizerSharedState state, MettelFOObjectFactory factory, MettelAbstractLogicParser parser){
    this(input,state);
    this.factory = factory;
    this.islandParser = parser;
    this.islandLexer = (Lexer)parser.input.getTokenSource();
}
}

formulae[Collection<MettelFOFormula> c]
	:
	(f  = formula
		{ c.add(f); }
	)+
	;

formula
returns [MettelFOFormula f]
	:
	f0 = equivalenceFormula
		{f = f0;}
	;


equivalenceFormula
returns [MettelFOFormula f]
@after{
	f = f0;
}
	:
	f0  = implicationFormula
	('<->'
	f1  = implicationFormula
		{ f0  = factory.createEquivalenceFormula(f0,f1); }
	)*
	;


implicationFormula
returns [MettelFOFormula f]
@after{
	f = f0;
}
	:
	f0  = disjunctionFormula
	('->'
	f1 = disjunctionFormula
		{ f0  = factory.createImplicationFormula(f0,f1); }
	)*
	;


disjunctionFormula
returns [MettelFOFormula f]
@after{
	f = f0;
}
	:
	f0  = conjunctionFormula
	('|'
	f1 = conjunctionFormula
		{ f0  = factory.createDisjunctionFormula(f0,f1); }
	)*
	;

conjunctionFormula
returns [MettelFOFormula f]
@after{
	f = f0;
}
	:
	f0  = existentialFormula
	('&'
	f1 = existentialFormula
		{ f0  = factory.createConjunctionFormula(f0,f1); }
	)*
	;

existentialFormula
returns [MettelFOFormula f]
	:
	('exists' variableList)*
//	  (ID ('exists' ID)*
//	  |
//	  '(' ID+ ')'
//	  )
//	)?
	universalFormula
	;

universalFormula
returns [MettelFOFormula f]
	:
	('forall' variableList)*
//	  (ID ('forall' ID)*
//	  |
//	  '(' ID+ ')'
//	  )
//	)?
	negationFormula
	;

variableList
	:
		ID
		|
		'[' ID+ ']'
		|
		'(' ID+ ')'
	;

negationFormula
returns [MettelFOFormula f]
@init{
	int n = 0;
}
	:
	('~'
		{n++;}
	)*
	f0  = basicFormula
		{if( (n \% 2) == 1){
			f = factory.createNegationFormula(f0);
		  }else{
		     f = f0;
		  }
		}
	;

basicFormula
returns [MettelFOFormula f]
	:
	'true'
		{f = factory.createTrueFormula(); }
	|
	'false'
		{f = factory.createFalseFormula(); }
	|
	'(' f0 = formula ')'
		{f = f0;} 
	|
	f0 = atomicFormula
		{f = f0;}
	;

atomicFormula
returns [MettelFOFormula f]
	:
	'holds' '(' {expression() != null}? (',' term)+ ')'
	|
	ID '(' termList? ')'
	|
   	f0  = equalityFormula
   		{f = f0;}
	;

equalityFormula
returns [MettelFOFormula f]
	:
	'[' term '=' term ']'
	|
	'(' term '=' term ')'
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
