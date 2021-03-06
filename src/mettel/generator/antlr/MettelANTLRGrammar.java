/**
 * This file is part of MetTeL.
 *
 * MetTeL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MetTeL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MetTeL.  If not, see <http://www.gnu.org/licenses/>.
 */
package mettel.generator.antlr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import mettel.util.MettelIndentedStringBuilder;

import static mettel.util.MettelStrings.GRAMMAR_STRING;
//import static mettel.util.MettelStrings.PARSER_STRING;
/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelANTLRGrammar {

	/**
	 *
	 */
	private String name = null;

	@SuppressWarnings("unused")
	private MettelANTLRGrammar(){}

	public MettelANTLRGrammar(String name){
		super();
		this.name = name;
		this.options = new MettelANTLRGrammarOptions();
	}

	public MettelANTLRGrammar(String name, MettelANTLRGrammarOptions options){
		super();
		this.name = name;
		if(options == null){
			this.options = new MettelANTLRGrammarOptions();
		}else{
			this.options = options;
		}
	}

	/**
	 *
	 */
	 private ArrayList<MettelANTLRRule> rules = new ArrayList<MettelANTLRRule>();

	 /**
	  *
	  */
	 public void addRule(MettelANTLRRule rule){
		 rules.add(rule);
	 }

	 public List<MettelANTLRRule> rules(){
		 return rules;
	 }

	 private MettelANTLRGrammarOptions options = new MettelANTLRGrammarOptions();

	 private MettelANTLRHeader header = new MettelANTLRHeader(MettelANTLRHeader.PARSER);
	 //TODO split on parser and lexer to allow inheritance
	 private MettelANTLRHeader lexerHeader = new MettelANTLRHeader(MettelANTLRHeader.LEXER);

	 private MettelANTLRMembers members = new MettelANTLRMembers(MettelANTLRMembers.PARSER);

	 public void addToHeader(String statement){
		 header.addStatement(statement);
	 }

	 public void addToLexerHeader(String statement){
		 lexerHeader.addStatement(statement);
	 }

	 public void addToMembers(String statement){
		 members.addStatement(statement);
	 }

	 public void toStringBuilder(StringBuilder b){
	         MettelIndentedStringBuilder ib = new MettelIndentedStringBuilder(b);

//		 ib.append(PARSER_STRING);
//		 ib.append(' ');
		 ib.append(GRAMMAR_STRING);
		 ib.append(' ');
		 ib.append(name);
		 ib.append(';');
		 ib.appendEOL();

		 options.toStringBuilder(ib);
		 header.toStringBuilder(ib);
		 lexerHeader.toStringBuilder(ib);

		 ib.appendLine("@rulecatch{");
		 ib.appendLine("catch (RecognitionException e) {");
		 ib.incrementIndentLevel();
		 ib.appendLine("reportError(e);");
		 ib.appendLine("throw e;");
		 ib.decrementIndentLevel();
		 ib.appendLine('}');
		 ib.appendLine('}');

		 members.toStringBuilder(ib);

		 for(MettelANTLRRule rule:rules){
			 rule.toStringBuilder(ib);
		 }

		 ib.appendEOL();
		 //ib.append("//Trivial lexer");
		 //ib.appendEOL();

		 BufferedReader r = new BufferedReader(
			 new InputStreamReader(
				 this.getClass().getResourceAsStream("resources/lexer")));
		 String s;
		 try {
		    while((s = r.readLine()) != null){
		         ib.append(s);
		         ib.appendEOL();
		     }
		 } catch (IOException e) {
		    e.printStackTrace();
		 }
	 }

	/**
	 * @return
	 */
	public StringBuilder toStringBuilder() {
		StringBuilder b = new StringBuilder();
		toStringBuilder(b);
		return b;
	}

	/**
	 * @return
	 */
	public String name() {
		return name;
	}

}
