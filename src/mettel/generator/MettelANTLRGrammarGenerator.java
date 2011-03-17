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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mettel.generator.antlr.MettelANTLRGrammar;
import mettel.generator.antlr.MettelANTLRRule;
import mettel.generator.antlr.MettelANTLRToken;
import mettel.generator.antlr.MettelANTLRRuleReference;
import mettel.generator.antlr.MettelANTLRUnaryBNFStatement;
import mettel.generator.antlr.MettelANTLRMultiaryBNFStatement;

import mettel.language.MettelStringLiteral;
import mettel.language.MettelToken;
import mettel.language.MettelBNFStatement;
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
	public MettelANTLRGrammarGenerator(){}

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
			processBNFs(grammar,sort,syn.getBNFs(sort));
		}

		return grammar;
	}

	/**
	 * @param grammar
	 * @param sort
	 */
	private void processBNFs(MettelANTLRGrammar grammar, MettelSort sort, List<MettelBNFStatement> bnfs) {
		final String SORT_NAME = sort.name();
		String s0 = BASIC_STRING+SORT_NAME;
		String s1 = null;
		for(MettelBNFStatement s:bnfs){
			String id = s.identifier();
			s1 = SORT_NAME+id.substring(0, 0).toUpperCase()+id.substring(1);

			MettelToken[] tokens = (MettelToken[])s.tokens().toArray();
			final int SIZE = tokens.length;
			if( (SIZE == 3) &&
					  (tokens[0] instanceof MettelSort) &&
					  (SORT_NAME.equals(((MettelSort)tokens[0]).name())) &&
					  (tokens[1] instanceof MettelStringLiteral) &&
					  (tokens[2] instanceof MettelSort) &&
					  (SORT_NAME.equals(((MettelSort)tokens[2]).name()))
					){

				MettelANTLRMultiaryBNFStatement st = new MettelANTLRMultiaryBNFStatement();
				st.addExpression(new MettelANTLRRuleReference(s0));

				MettelANTLRMultiaryBNFStatement st1 = new MettelANTLRMultiaryBNFStatement();
				st1.addExpression(new MettelANTLRToken(tokens[1].toString()));
				st1.addExpression(new MettelANTLRRuleReference(s0));

				MettelANTLRUnaryBNFStatement st0 = new MettelANTLRUnaryBNFStatement(
						st1,MettelANTLRUnaryBNFStatement.STAR);
				st.addExpression(st0);

				grammar.addRule(new MettelANTLRRule(s1,st));

			}else if( (SIZE == 2) &&
					  (tokens[0] instanceof MettelSort) &&
					  (SORT_NAME.equals(((MettelSort)tokens[0]).name())) &&
					  (tokens[1] instanceof MettelStringLiteral)
					){
				MettelANTLRMultiaryBNFStatement st = new MettelANTLRMultiaryBNFStatement();
				st.addExpression(new MettelANTLRRuleReference(s0));

				MettelANTLRUnaryBNFStatement st0 = new MettelANTLRUnaryBNFStatement(
						new MettelANTLRToken(tokens[1].toString()),MettelANTLRUnaryBNFStatement.STAR);
				st.addExpression(st0);

				grammar.addRule(new MettelANTLRRule(s1,st));

			}else{ //if(tokens[0] instanceof MettelStringLiteral){
				MettelANTLRMultiaryBNFStatement st = new MettelANTLRMultiaryBNFStatement(
						MettelANTLRMultiaryBNFStatement.OR);
				st.addExpression(new MettelANTLRRuleReference(s0));
				MettelANTLRMultiaryBNFStatement st0 = new MettelANTLRMultiaryBNFStatement();
				//st0.addExpression(new MettelANTLRToken(tokens[0].toString()));
				for(int i = 0; i < SIZE; i++){
					if(tokens[i] instanceof MettelStringLiteral){
						st0.addExpression(new MettelANTLRToken(tokens[i].toString()));
					}else if(tokens[i] instanceof MettelSort){
						String name = ((MettelSort)tokens[i]).name();
						if(SORT_NAME.equals(name)){
							st0.addExpression(new MettelANTLRRuleReference(s0));
						}else{
							st0.addExpression(new MettelANTLRRuleReference(tokens[i].toString()));
						}
					}
				}
				st.addExpression(st0);

				grammar.addRule(new MettelANTLRRule(s1,st));
			}//TODO other alternatives
			s0 = s1;
		}

	}

	/**
	 * @param grammar
	 * @param sort
	 */
	private void processSort(MettelANTLRGrammar grammar, MettelSort sort) {

		grammar.addRule(makeANTLREntryRule(sort));
		grammar.addRule(makeANTLRVariableRule(sort));
		grammar.addRule(makeANTLRBasicRule(sort));

	}

	/**
	 * @param sort
	 * @return
	 */
	private MettelANTLRRule makeANTLRBasicRule(MettelSort sort) {
		final String NAME =sort.name();
		MettelANTLRMultiaryBNFStatement s = new MettelANTLRMultiaryBNFStatement(
				MettelANTLRMultiaryBNFStatement.OR);
		s.addExpression(new MettelANTLRRuleReference(NAME+VARIABLE_STRING));
		MettelANTLRMultiaryBNFStatement s0 = new MettelANTLRMultiaryBNFStatement();
		s0.addExpression(MettelANTLRToken.LBRACE);
		s0.addExpression(new MettelANTLRRuleReference(NAME));
		s0.addExpression(MettelANTLRToken.RBRACE);
		s.addExpression(s0);
		return new MettelANTLRRule(BASIC_STRING+NAME,s);
	}

	/**
	 * @param sort
	 * @return
	 */
	private MettelANTLRRule makeANTLRVariableRule(MettelSort sort) {
		return new MettelANTLRRule(sort.name()+VARIABLE_STRING,MettelANTLRToken.ID);
	}

	/**
	 * @param sort
	 * @return
	 */
	private MettelANTLRRule makeANTLREntryRule(MettelSort sort) {
		final String NAME = sort.name();
		MettelANTLRMultiaryBNFStatement s = new MettelANTLRMultiaryBNFStatement();
		s.addExpression(new MettelANTLRUnaryBNFStatement(
						new MettelANTLRRuleReference(NAME),MettelANTLRUnaryBNFStatement.STAR));
		s.addExpression(MettelANTLRToken.EOF);
		return new MettelANTLRRule(NAME+'s',s);
	}

	/**
	 *
	 */
	public Collection<MettelANTLRGrammar> processSyntaxes() {
		ArrayList<MettelANTLRGrammar> grammars = new ArrayList<MettelANTLRGrammar>();
		for(MettelSyntax syn:spec.syntaxes()){
			grammars.add(processSyntax(syn.name()));
		}
		return grammars;
	}



}
