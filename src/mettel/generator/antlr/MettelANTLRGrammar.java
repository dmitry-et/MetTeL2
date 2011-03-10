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

import java.util.ArrayList;

import mettel.util.MettelIndentedStringBuilder;

import static mettel.util.MettelStrings.GRAMMAR;

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
	 void addRule(MettelANTLRRule rule){
		 rules.add(rule);
	 }

	 private MettelANTLRGrammarOptions options = new MettelANTLRGrammarOptions();
	 
	 void toStringBuilder(StringBuilder b){
	         MettelIndentedStringBuilder ib = new MettelIndentedStringBuilder(b);
	     
		 ib.append(GRAMMAR);
		 ib.append(' ');
		 ib.append(name);
		 ib.append(';');
		 ib.appendEOL();

		 options.toStringBuilder(ib);
		 
		 for(MettelANTLRRule rule:rules){
			 rule.toStringBuilder(ib);
			 ib.appendEOL();
		 }
	 }
}
