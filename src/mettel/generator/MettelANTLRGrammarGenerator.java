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
//import mettel.generator.antlr.MettelANTLRHeader;
import mettel.generator.antlr.MettelANTLRRule;
import mettel.generator.antlr.MettelANTLRToken;
import mettel.generator.antlr.MettelANTLRRuleReference;
import mettel.generator.antlr.MettelANTLRUnaryBNFStatement;
import mettel.generator.antlr.MettelANTLRMultiaryBNFStatement;
import mettel.generator.java.MettelJavaPackageStructure;

import mettel.language.MettelStringLiteral;
import mettel.language.MettelToken;
import mettel.language.MettelBNFStatement;
import mettel.language.MettelSpecification;
import mettel.language.MettelSyntax;
import mettel.language.MettelSort;

import mettel.util.MettelJavaNames;
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

/*	//TODO use of output path
	private String outputPath = null;

	public void setOutputPath(String path){
		this.outputPath = path;
	}

	private MettelJavaPackage langPackage = null;
	private MettelJavaPackage grammarPackage = null;
*/
	public MettelJavaPackageStructure processSyntax(String name){
		MettelSyntax syn = spec.getSyntax(name);
		if(syn == null) return null;

		MettelANTLRGrammar grammar = new MettelANTLRGrammar(name);
		final String PATH = spec.path();
		final String s = PACKAGE_STRING+' '+PATH+';';

		/*langPackage = new MettelJavaPackage(PATH);
		grammarPackage = new MettelJavaPackage(PATH+'.'+GRAMMAR_STRING);
*/
		MettelJavaPackageStructure pStructure = new MettelJavaPackageStructure(PATH);
		pStructure.appendStandardClasses(name);

		//new java.io.File("dir").
		grammar.addToHeader(s);

//		MettelANTLRHeader lexerHeader = new MettelANTLRHeader(MettelANTLRHeader.LEXER);
//		lexerHeader.addStatement(s);
		grammar.addToLexerHeader(s);

		final Collection<MettelSort> sorts = syn.sorts();//TODO refactoring needed
		final int SIZE = sorts.size();
		String[] sortStrings = new String[SIZE];
		int i = 0;
		for(MettelSort sort:sorts){
			sortStrings[i++] = sort.name();
			processSort(grammar,sort);
			processBNFs(grammar,pStructure,sort,syn.getBNFs(sort));
		}
		pStructure.appendStandardClasses(name,sortStrings);

//		grammarPackage.createFile(name+".g").append(grammar.toStringBuilder());

		pStructure.appendParser(grammar);
//		pStructure.appendLexer("Trivial", lexerHeader, this.getClass().getResourceAsStream("antlr/resources/lexer"));

		return pStructure;
	}

	/**
	 * @param grammar
	 * @param sort
	 */
	private void processBNFs(MettelANTLRGrammar grammar, MettelJavaPackageStructure pStructure, MettelSort sort, List<MettelBNFStatement> bnfs) {
		final String SORT_NAME = sort.name();
		String s0 = BASIC_STRING + MettelJavaNames.firstCharToUpperCase(SORT_NAME);
		String s1 = null;
		for(MettelBNFStatement s:bnfs){
			s1 = SORT_NAME + MettelJavaNames.firstCharToUpperCase(s.identifier());

			MettelToken[] tokens = s.tokens().toArray(new MettelToken[0]);
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

				pStructure.appendConnectiveClass(grammar.name(), SORT_NAME, s.identifier(),
						new String[]{((MettelSort)tokens[0]).name(), ((MettelSort)tokens[2]).name()});

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

				pStructure.appendConnectiveClass(grammar.name(), SORT_NAME, s.identifier(),
						new String[]{((MettelSort)tokens[0]).name()});


			}else{ //if(tokens[0] instanceof MettelStringLiteral){
				MettelANTLRMultiaryBNFStatement st = new MettelANTLRMultiaryBNFStatement(
						MettelANTLRMultiaryBNFStatement.OR);
				st.addExpression(new MettelANTLRRuleReference(s0));
				MettelANTLRMultiaryBNFStatement st0 = new MettelANTLRMultiaryBNFStatement();
				//st0.addExpression(new MettelANTLRToken(tokens[0].toString()));

				String[] sortStrings = new String[SIZE];
				for(int i = 0; i < SIZE; i++){
					if(tokens[i] instanceof MettelStringLiteral){
						st0.addExpression(new MettelANTLRToken(tokens[i].toString()));
					}else if(tokens[i] instanceof MettelSort){
						String name = ((MettelSort)tokens[i]).name();
						sortStrings[i] = name;
						if(SORT_NAME.equals(name)){
							st0.addExpression(new MettelANTLRRuleReference(s0));
						}else{
							st0.addExpression(new MettelANTLRRuleReference(tokens[i].toString()));
						}
					}
				}
				st.addExpression(st0);

				grammar.addRule(new MettelANTLRRule(s1,st));

				pStructure.appendConnectiveClass(grammar.name(), SORT_NAME, s.identifier(),sortStrings);

			}//TODO other alternatives
			s0 = s1;
		}
		grammar.addRule(new MettelANTLRRule(SORT_NAME,new MettelANTLRRuleReference(s0)));

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
		final String NAME = sort.name();
		MettelANTLRMultiaryBNFStatement s = new MettelANTLRMultiaryBNFStatement(
				MettelANTLRMultiaryBNFStatement.OR);
		s.addExpression(new MettelANTLRRuleReference(NAME+VARIABLE_STRING));
		MettelANTLRMultiaryBNFStatement s0 = new MettelANTLRMultiaryBNFStatement();
		s0.addExpression(MettelANTLRToken.LBRACE);
		s0.addExpression(new MettelANTLRRuleReference(NAME));
		s0.addExpression(MettelANTLRToken.RBRACE);
		s.addExpression(s0);
		return new MettelANTLRRule(BASIC_STRING+MettelJavaNames.firstCharToUpperCase(NAME),s);
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
	public Collection<MettelJavaPackageStructure> processSyntaxes() {
		ArrayList<MettelJavaPackageStructure> grammars = new ArrayList<MettelJavaPackageStructure>();
		for(MettelSyntax syn:spec.syntaxes()){
			grammars.add(processSyntax(syn.name()));
		}
		return grammars;
	}

}
