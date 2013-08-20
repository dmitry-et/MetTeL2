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
import java.util.LinkedHashSet;
import java.io.StringReader;
import java.io.InputStream;

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
//private TokenStream islandTokens = null;

/*private MettelExpression expression(CommonToken t) throws RecognitionException {
    CharStream in = ((Lexer)this.input.getTokenSource()).getCharStream();
    int index = in.index();
    //System.out.println(index);
    int line = in.getLine();
    int charPositionInLine = in.getCharPositionInLine();
    islandLexer.setCharStream(in);
    in.seek(t.getStartIndex() + 1);
    in.setLine(line);
    in.setCharPositionInLine(charPositionInLine);
    MettelExpression e = islandParser.expression();
    //System.out.println(in.index());
    return e;
}*/

private MettelExpression expression(Token t){
	islandLexer.setCharStream(new ANTLRStringStream(t.getText()));
	//parser.input.setTokenSource(islandLexer);
	MettelExpression result = null;
	try{
		result = islandParser.expressionEOF();
	}catch(RecognitionException e){
		throw new MettelFOParserException(e, "Cannot parse an expression " + t.getText(), t);
	}
	return result;
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
	f0  = unaryFormula
	('&'
	f1 = unaryFormula
		{ f0  = factory.createConjunctionFormula(f0,f1); }
	)*
	;

fragment
unaryFormula
returns [MettelFOFormula f = null]
	:
	(
	f0 = basicFormula
	|
	f0 = existentialFormula
	|
	f0 = universalFormula
	|
	f0 = negationFormula
	)
	{f = f0;}
	;

existentialFormula
returns [MettelFOFormula f]
	:
	('exists' vars = variableList)+
	f0 = unaryFormula
	{f = factory.createExistentialFormula(vars, f0);}
	;

universalFormula
returns [MettelFOFormula f]
	:
	('forall' vars = variableList)+
	f0 = unaryFormula
	{f = factory.createExistentialFormula(vars, f0);}
	;

variableList
returns [MettelFOIndividualVariable[\] vars = null]
	:
		list = idList
		|
		'[' list = idList ']'
		|
		'(' list = idList ')'
		{vars = new MettelFOIndividualVariable[list.length()];
		 int i = 0;
		 for(String id:list){
		 	vars[i] = factory.createIndividualVariable(id);
		 }
		}
	;
	
fragment idList
returns [LinkedHashSet<String> ids = new LinkedHashSet<String>()]
	:
		(t = ID
		{ids.add(t.getText());}
		)+
	;

negationFormula
returns [MettelFOFormula f]
@init{
	int n = 0;
}
	:
	('~'
		{n++;}
	)+
	f0  = unaryFormula
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
returns [MettelFOFormula f = null]
@init{MettelExpression exp = null;}
	:
	'holds' '(' 
	t = EXPRESSION
	{
	exp = expression(t);
	}
	','?
	terms = termList ')'
	{
	f = factory.createHoldsPredicate(exp, terms);
	}
	|
	t = ID '(' terms = termList? ')'
	{
	final String name = t.getText();
	final MettelFOFormulaVariable p = factory.createFormulaVariable(name);
	f = factory.createAtomicFormula(p, terms);
	}
	|
   	f0  = equalityFormula
   		{f = f0;}
	;
	


equalityFormula
returns [MettelFOFormula f = null]
	:
	'[' t0 = term '=' t1 = term ']'
	|
	'(' t0 = term '=' t1 = term ')'
	{f = factory.createEqualityFormula(t0, t1);}
	;

term
returns [MettelFOTerm t = null]
	:
	id = ID
	( '(' terms = termList? ')' )?
	{
	final String name = id.getText();
	final MettelFOFunctionVariable f = factory.createFunctionVariable(name);
	t = factory.createTerm(f, terms);
	}
	;

termList
returns [ArrayList<MettelFOTerm> terms = new ArrayList<MettelFOTerm>()]
	:
	t = term
	{terms.add(t);}
	(','?
	term
	{terms.add(t);}
	)*
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

EXPRESSION
	:
	'(*' 
	{state.tokenStartCharIndex = getCharIndex();}
   	(options{greedy=false;} : . )*
   	{setText(getText());}
   	'*)'
	;
