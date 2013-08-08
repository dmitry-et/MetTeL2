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
public class MettelANTLRGrammarGeneratorProperties implements MettelANTLRGrammarGeneratorDefaultOptions {

	/**
	 *
	 */
	public MettelANTLRGrammarGeneratorProperties() {
		super();
	}
	
	public MettelANTLRGrammarGeneratorProperties(MettelANTLRGrammarGeneratorProperties p) {
		super();
		init(p);
	}

	public MettelANTLRGrammarGeneratorProperties(Reader reader) throws IOException {
		this();
		init(reader);
	}

	public String tableauRuleDelimiter = TABLEAU_RULE_DELIMITER;
	public String tableauRuleBranchDelimiter = TABLEAU_RULE_BRANCH_DELIMITER;
	public String tableauRulePremiseDelimiter = TABLEAU_RULE_PREMISE_DELIMITER;

	public String listLeftDelimiter = LIST_LEFT_DELIMITER;
	public String listRightDelimiter = LIST_RIGHT_DELIMITER;

	public String expressionLeftDelimiter = EXPRESSION_LEFT_DELIMITER;
	public String expressionRightDelimiter = EXPRESSION_RIGHT_DELIMITER;

	public String branchBound = null;

	public String nameSeparator = NAME_SEPARATOR;

	public MettelANTLRGrammarOptions grammarOptions = new MettelANTLRGrammarOptions();

	public MettelEqualityKeywords equalityKeywords = EQUALITY_KEYWORDS;

	public void init(Reader reader) throws IOException{
		if(reader == null) return;

		final Properties p = new Properties();
		p.load(reader);

		tableauRuleDelimiter = p.getProperty("tableau.rule.delimiter", tableauRuleDelimiter);
		tableauRuleBranchDelimiter = p.getProperty("tableau.rule.branch.delimiter", tableauRuleBranchDelimiter);
		tableauRulePremiseDelimiter = p.getProperty("tableau.rule.premise.delimiter", tableauRulePremiseDelimiter);

		listLeftDelimiter = p.getProperty("list.left.delimiter", listLeftDelimiter);
		listRightDelimiter = p.getProperty("list.right.delimiter", listRightDelimiter);

		expressionLeftDelimiter = p.getProperty("expression.left.delimiter", expressionLeftDelimiter);
		expressionRightDelimiter = p.getProperty("expression.right.delimiter", expressionRightDelimiter);

		branchBound = p.getProperty("branch.bound", branchBound);

		nameSeparator = p.getProperty("name.separator", nameSeparator);

		final String antlrK = p.getProperty("antlr.k",String.valueOf(ANTLR_K));

		//TODO: more careful check for properties
        grammarOptions = new MettelANTLRGrammarOptions(grammarOptions);
        grammarOptions.setK(antlrK.indexOf('*') >= 0? 0: Integer.parseInt(antlrK));
        grammarOptions.setSuperClass(p.getProperty("antlr.superClass", ANTLR_SUPERCLASS));
        grammarOptions.setBacktrack(Boolean.parseBoolean(p.getProperty("antlr.backtrack",String.valueOf(ANTLR_BACKTRACK))));
        grammarOptions.setMemoize(Boolean.parseBoolean(p.getProperty("antlr.memoize",String.valueOf(ANTLR_MEMOIZE))));

        String keywords = p.getProperty("equality.keywords");
        if(keywords == null){
        	equalityKeywords = EQUALITY_KEYWORDS;
        }else{
        	equalityKeywords = new MettelEqualityKeywords(keywords.trim());
        }
	}
	
	public void init(MettelANTLRGrammarGeneratorProperties p){
		if(p == null) return;

		tableauRuleDelimiter = p.tableauRuleDelimiter;
		tableauRuleBranchDelimiter = p.tableauRuleBranchDelimiter;
		tableauRulePremiseDelimiter = p.tableauRulePremiseDelimiter;

		listLeftDelimiter = p.listLeftDelimiter;
		listRightDelimiter = p.listRightDelimiter;

		expressionLeftDelimiter = p.expressionLeftDelimiter;
		expressionRightDelimiter = p.expressionRightDelimiter;

		branchBound = p.branchBound;

		nameSeparator = p.nameSeparator;

		grammarOptions = new MettelANTLRGrammarOptions(p.grammarOptions);
       	
		equalityKeywords = new MettelEqualityKeywords(p.equalityKeywords);

	}

}
