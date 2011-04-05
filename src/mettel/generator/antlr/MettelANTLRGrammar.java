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

import mettel.util.MettelIndentedStringBuilder;

import static mettel.util.MettelStrings.GRAMMAR_STRING;

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

	public MettelANTLRGrammar(String name){
		this.name = name;
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

	 private MettelANTLRGrammarOptions options = new MettelANTLRGrammarOptions();

	 private MettelANTLRHeader header = new MettelANTLRHeader(MettelANTLRHeader.PARSER);
	 private MettelANTLRHeader lexerHeader = new MettelANTLRHeader(MettelANTLRHeader.LEXER);

	 public void addToHeader(String statement){
		 header.addStatement(statement);
	 }

	 public void addToLexerHeader(String statement){
		 lexerHeader.addStatement(statement);
	 }

	 public void toStringBuilder(StringBuilder b){
	         MettelIndentedStringBuilder ib = new MettelIndentedStringBuilder(b);

		 ib.append(GRAMMAR_STRING);
		 ib.append(' ');
		 ib.append(name);
		 ib.append(';');
		 ib.appendEOL();

		 options.toStringBuilder(ib);
		 header.toStringBuilder(ib);
		 lexerHeader.toStringBuilder(ib);

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

}
