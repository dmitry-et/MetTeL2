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
package mettel.generator;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import mettel.generator.antlr.MettelANTLRGrammarOptions;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelANTLRGrammarGeneratorProperties implements MettelANTLRGrammarDefaultOptions {

	/**
	 *
	 */
	public MettelANTLRGrammarGeneratorProperties() {
		super();
	}

	public MettelANTLRGrammarGeneratorProperties(Reader reader) throws IOException {
		this();
		init(reader);
	}

	public String tableauRuleDelimiter = "$;";
	public String tableauRuleBranchDelimiter = "$|";
	public String tableauRulePremiseDelimiter = "/";

	public String listLeftDelimiter="";
	public String listRightDelimiter="";

	public String expressionLeftDelimiter="(";
	public String expressionRightDelimiter=")";

	public String branchBound = null;

	//public int antlr_k = 1;

	public MettelANTLRGrammarOptions grammarOptions= null;

//	public static final int NONE = -1;
//	public static final int IGNORE_HUGE_BRANCH = 0;
//	public int acceptanceStrategy = NONE;

	/*public String tableauRuleDelimiter(){
		return tableauRuleDelimiter;
	}

	public String tableauRuleBranchDelimiter(){
		return tableauRuleBranchDelimiter;
	}

	public String tableauRulePremiseDelimiter(){
		return tableauRulePremiseDelimiter;
	}*/

	public void init(Reader reader) throws IOException{
		final Properties p = new Properties();
		p.load(reader);

		tableauRuleDelimiter = p.getProperty("tableau.rule.delimiter", TABLEAU_RULE_DELIMITER);
		tableauRuleBranchDelimiter = p.getProperty("tableau.rule.branch.delimiter", TABLEAU_RULE_BRANCH_DELIMITER);
		tableauRulePremiseDelimiter = p.getProperty("tableau.rule.premise.delimiter", TABLEAU_RULE_PREMISE_DELIMITER);

		listLeftDelimiter = p.getProperty("list.left.delimiter", LIST_LEFT_DELIMITER);
		listRightDelimiter = p.getProperty("list.right.delimiter", LIST_RIGHT_DELIMITER);

		expressionLeftDelimiter = p.getProperty("expression.left.delimiter", EXPRESSION_LEFT_DELIMITER);
		expressionRightDelimiter = p.getProperty("expression.right.delimiter", EXPRESSION_RIGHT_DELIMITER);

		branchBound = p.getProperty("branch.bound", branchBound);

		final String antlrK = p.getProperty("antlr.k",String.valueOf(ANTLR_K));

		//TODO: more careful check for properties
        grammarOptions = new MettelANTLRGrammarOptions(
        		(antlrK.indexOf('*') >= 0? 0: Integer.parseInt(antlrK)),
        		p.getProperty("antlr.superClass", ANTLR_SUPERCLASS),
        		Boolean.parseBoolean(p.getProperty("antlr.backtrack",String.valueOf(ANTLR_BACKTRACK))),
        		Boolean.parseBoolean(p.getProperty("antlr.memoize",String.valueOf(ANTLR_MEMOIZE))));

		//acceptanceStrategy = p.getProperty("ignore.huge.branch") == "Yes"? 0 : -1;

	}
}
