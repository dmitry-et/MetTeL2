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

import static mettel.util.MettelStrings.LINE_SEPARATOR;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision: $ $Date: $
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
		 b.append("grammar ");
		 b.append(name);
		 b.append(';');
		 b.append(LINE_SEPARATOR);

		 options.toStringBuilder(b);
		 
		 for(MettelANTLRRule rule:rules){
			 rule.toStringBuilder(b);
			 b.append(LINE_SEPARATOR);
		 }
	 }
}
