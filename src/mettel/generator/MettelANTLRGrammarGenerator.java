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

import mettel.generator.antlr.MettelANTLRGrammar;
import mettel.generator.antlr.MettelANTLRRule;
import mettel.generator.antlr.MettelANTLRToken;
import mettel.generator.antlr.MettelANTLRRuleReference;
import mettel.generator.antlr.MettelANTLRUnaryBNFStatement;
import mettel.language.MettelSpecification;
import mettel.language.MettelSyntax;
import mettel.language.MettelSort;

import static mettel.util.MettelStrings.PACKAGE_STRING;
import static mettel.util.MettelStrings.VARIABLE_STRING;
import static mettel.util.MettelStrings.BASIC_STRING;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelANTLRGrammarGenerator {

	@SuppressWarnings("unused")
	private MettelANTLRGrammarGenerator(){}

	private MettelSpecification spec = null;
	/**
	 *
	 */
	public MettelANTLRGrammarGenerator(MettelSpecification spec) {
		super();
		this.spec = spec;
	}

	//TODO use of output path
	private String outputPath = null;

	public void setOutputPath(String path){
		this.outputPath = path;
	}

	public MettelANTLRGrammar processSyntax(String name){
		MettelSyntax syn = spec.getSyntax(name);
		if(syn == null) return null;

		MettelANTLRGrammar grammar = new MettelANTLRGrammar(name);
		String s = PACKAGE_STRING+' '+spec.path();
		grammar.addToHeader(s);
		grammar.addToLexerHeader(s);

		for(MettelSort sort:syn.sorts()){
			processSort(grammar,sort);
		}

		return grammar;
	}

	/**
	 * @param grammar
	 * @param sort
	 */
	private void processSort(MettelANTLRGrammar grammar, MettelSort sort) {

		grammar.addRule(makeANTLREntryRule(grammar,sort));
		grammar.addRule(makeANTLRVariableRule(sort));
		grammar.addRule(makeANTLRBasicRule(sort));

	}

	/**
	 * @param sort
	 * @return
	 */
	private MettelANTLRRule makeANTLRBasicRule(MettelSort sort) {
		MettelANTLRRule rule = new MettelANTLRRule(BASIC_STRING+sort.name());
		rule.addStatement(
				new MettelANTLRBinaryBNFStatement(
						new MettelANTLRRoleReference(sort.name()+VARIABLE_STRING),
						new );
		return rule;
	}

	/**
	 * @param sort
	 * @return
	 */
	private MettelANTLRRule makeANTLRVariableRule(MettelSort sort) {
		MettelANTLRRule rule = new MettelANTLRRule(sort.name()+VARIABLE_STRING);
		rule.addStatement(MettelANTLRToken.ID);
		return rule;
	}

	/**
	 * @param sort
	 * @return
	 */
	private MettelANTLRRule makeANTLREntryRule(MettelANTLRGrammar grammar, MettelSort sort) {
		MettelANTLRRule rule = new MettelANTLRRule(sort.name()+'s');
		rule.addStatement(
				new MettelANTLRUnaryBNFStatement(
						new MettelANTLRRuleReference(sort.name()),MettelANTLRUnaryBNFStatement.STAR));
		rule.addStatement(MettelANTLRToken.EOF);
		return rule;
	}



}
