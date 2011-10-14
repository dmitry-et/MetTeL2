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

import mettel.language.MettelEqualityBNFStatement;
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
	private MettelANTLRGrammar processSyntax0(MettelJavaPackageStructure pStructure, String name){
		final String PATH = spec.path();
		final String s = PACKAGE_STRING+' '+PATH+';';

		MettelANTLRGrammar grammar = new MettelANTLRGrammar(name);

		MettelSyntax syn = spec.getSyntax(name);
		if(syn == null) return null;

		List<MettelSyntax> parents = syn.parents();
		if(parents != null){
			for(MettelSyntax ps:parents){
				MettelJavaPackageStructure pStr = new MettelJavaPackageStructure(PATH);
				MettelANTLRGrammar g = processSyntax0(pStr,ps.name());
				for(MettelANTLRRule r:g.rules()){
					grammar.addRule(r);
				}//TODO Do smth with factories
			}
		}

		pStructure.appendStandardClasses(name);

		grammar.addToHeader(s);
		grammar.addToHeader("");
		grammar.addToHeader("import java.util.Collection;");

		grammar.addToLexerHeader(s);

		grammar.addToMembers("private "+name+"ObjectFactory factory = "+name+"ObjectFactory.DEFAULT;");

		grammar.addToMembers("public "+name+"Parser(TokenStream input, "+name+"ObjectFactory factory){");
        grammar.addToMembers("    this(input);");
        grammar.addToMembers("    this.factory = factory;");
        grammar.addToMembers("}");

		grammar.addToMembers("public "+name+"Parser(TokenStream input, RecognizerSharedState state, "+name+"ObjectFactory factory){");
        grammar.addToMembers("    this(input,state);");
        grammar.addToMembers("    this.factory = factory;");
        grammar.addToMembers("}");

		final Collection<MettelSort> sorts = syn.allSorts();//TODO refactoring needed
		final int SIZE = sorts.size();
		String[] sortStrings = new String[SIZE];
		int i = 0;
		for(MettelSort sort:sorts){
			sortStrings[i++] = sort.name();
			processSort(grammar,sort);
			processBNFs(grammar,pStructure,sort,syn.getBNFs(sort));
		}
		pStructure.appendStandardClasses(name,sortStrings);

		pStructure.appendParser(grammar);

		return grammar;
	}


	public MettelJavaPackageStructure processSyntax(String name){
		MettelSyntax syn = spec.getSyntax(name);
		if(syn == null) return null;
		syn = syn.unravel();

		final String NAME = MettelJavaNames.firstCharToUpperCase(name);

		MettelANTLRGrammar grammar = new MettelANTLRGrammar(NAME);
		final String PATH = spec.path();
		final String s = PACKAGE_STRING+' '+PATH+';';

		/*langPackage = new MettelJavaPackage(PATH);
		grammarPackage = new MettelJavaPackage(PATH+'.'+GRAMMAR_STRING);
*/
		MettelJavaPackageStructure pStructure = new MettelJavaPackageStructure(PATH);
		pStructure.appendStandardClasses(NAME);

		//new java.io.File("dir").
		grammar.addToHeader(s);
		grammar.addToHeader("");
		grammar.addToHeader("import java.util.Collection;");
		grammar.addToHeader("import java.util.ArrayList;");
		grammar.addToHeader("import java.util.LinkedHashSet;");
		grammar.addToHeader("");
		grammar.addToHeader("import mettel.core.MettelGeneralTableauRule;");

//		MettelANTLRHeader lexerHeader = new MettelANTLRHeader(MettelANTLRHeader.LEXER);
//		lexerHeader.addStatement(s);
		grammar.addToLexerHeader(s);

		grammar.addToMembers("private "+NAME+"ObjectFactory factory = "+NAME+"ObjectFactory.DEFAULT;");

		grammar.addToMembers("public "+NAME+"Parser(TokenStream input, "+NAME+"ObjectFactory factory){");
        grammar.addToMembers("    this(input);");
        grammar.addToMembers("    this.factory = factory;");
        grammar.addToMembers("}");

		grammar.addToMembers("public "+NAME+"Parser(TokenStream input, RecognizerSharedState state, "+NAME+"ObjectFactory factory){");
        grammar.addToMembers("    this(input,state);");
        grammar.addToMembers("    this.factory = factory;");
        grammar.addToMembers("}");

		final Collection<MettelSort> sorts = syn.sorts();//TODO refactoring needed
		final int SIZE = sorts.size();
		String[] sortStrings = new String[SIZE];
		int i = 0;
		for(MettelSort sort:sorts){
			sortStrings[i++] = sort.name();
			processSort(grammar,sort);
			processBNFs(grammar,pStructure,sort,syn.getBNFs(sort));
		}
		pStructure.appendStandardClasses(NAME,sortStrings);

		grammar.addRule(makeANTLRExpressionListRule(NAME));
		grammar.addRule(makeANTLRExpressionRule(NAME,sorts));
		grammar.addRule(makeANTLRTableauRule(grammar.name()));
		grammar.addRule(makeANTLRTableauCalculusRule(grammar.name()));
//		grammar.addRule(makeANTLREqualityRule(grammar.name(),sorts));

//		grammarPackage.createFile(name+".g").append(grammar.toStringBuilder());

		pStructure.appendParser(grammar);
//		pStructure.appendLexer("Trivial", lexerHeader, this.getClass().getResourceAsStream("antlr/resources/lexer"));

		return pStructure;
	}

	/*private void addTableauRule(MettelANTLRGrammar grammar){
		MettelANTLRRule r = new
	}*/

	private MettelANTLRRule makeANTLRTableauCalculusRule(String grammarName) {
		final String NAME = "tableauRule";

		MettelANTLRMultiaryBNFStatement s = new MettelANTLRMultiaryBNFStatement();
		MettelANTLRRuleReference ruleRef = new MettelANTLRRuleReference(NAME,"e0");
		ruleRef.appendLineToPostfix("a0.add(e0);");

		s.addExpression(ruleRef);

		MettelANTLRMultiaryBNFStatement s0 = new MettelANTLRMultiaryBNFStatement();
		s0.addExpression(new MettelANTLRToken("'$;'"));
		s0.addExpression(new MettelANTLRUnaryBNFStatement(ruleRef,
							MettelANTLRUnaryBNFStatement.TEST));
		s.addExpression(new MettelANTLRUnaryBNFStatement(
						s0,MettelANTLRUnaryBNFStatement.STAR));
		s.addExpression(MettelANTLRToken.EOF);
		return new MettelANTLRRule("tableauCalculus",s/*,false*/,
				new String[]{"Collection<MettelGeneralTableauRule>"},null);
	}

	private MettelANTLRRule makeANTLRExpressionListRule(String grammarName) {
		final String NAME = "expression";
		MettelANTLRMultiaryBNFStatement s = new MettelANTLRMultiaryBNFStatement();
		MettelANTLRRuleReference ruleRef = new MettelANTLRRuleReference(NAME,"e0");
		ruleRef.appendLineToPostfix("r0.add(e0);");
		s.addExpression(new MettelANTLRUnaryBNFStatement(
						ruleRef,MettelANTLRUnaryBNFStatement.STAR));
		MettelANTLRRule r = new MettelANTLRRule(NAME+'s',s/*,false*/,
				new String[]{"ArrayList<"+grammarName+"Expression>"});
		r.appendLineToInitBlock("r0 = new ArrayList<"+grammarName+"Expression>();");
		//r.appendLineToAfterBlock("r0 = e0;");
		return r;
	}

	private MettelANTLRRule makeANTLRExpressionRule(String grammarName,Collection<MettelSort> sorts){
		MettelANTLRMultiaryBNFStatement s = new MettelANTLRMultiaryBNFStatement(MettelANTLRMultiaryBNFStatement.OR);
		for(MettelSort sort:sorts){
			MettelANTLRRuleReference ruleRef = new MettelANTLRRuleReference(sort.name(),sort.name()+"Expression",true);
			ruleRef.appendLineToPostfix("r0 = "+sort.name()+"Expression;");
			s.addExpression(ruleRef);
		}
		MettelANTLRRule r = new MettelANTLRRule("expression",s,
				new String[]{grammarName+"Expression"});
		//r.appendLineToAfterBlock("r0 = e0;");
		return r;
	}

	private MettelANTLRRule makeANTLRTableauRule(String grammarName){//TODO make tableau rule flexibly specifiable
		MettelANTLRMultiaryBNFStatement s = new MettelANTLRMultiaryBNFStatement();
		final String EXPRESSIONS = "expressions";
		s.addExpression(new MettelANTLRRuleReference(EXPRESSIONS,"premises"));
		s.addExpression(new MettelANTLRToken("'/'"));

		MettelANTLRRuleReference ruleRef = new MettelANTLRRuleReference(EXPRESSIONS,"conclusion");
		ruleRef.appendLineToPostfix("branches.add(new LinkedHashSet<"+grammarName+"Expression>(conclusion));");
		s.addExpression(ruleRef);

		MettelANTLRMultiaryBNFStatement s0 = new MettelANTLRMultiaryBNFStatement();
		s0.addExpression(new MettelANTLRToken("'$|'"));
		s0.addExpression(ruleRef);
		s.addExpression(new MettelANTLRUnaryBNFStatement(s0,MettelANTLRUnaryBNFStatement.STAR));

		MettelANTLRRule r = new MettelANTLRRule("tableauRule",s,
				new String[]{"MettelGeneralTableauRule"});
		r.appendLineToInitBlock("LinkedHashSet<LinkedHashSet<"+grammarName+
				"Expression>> branches = new LinkedHashSet<LinkedHashSet<"+grammarName+"Expression>>();");
		r.appendLineToAfterBlock("r0 = new MettelGeneralTableauRule(new LinkedHashSet<"+grammarName+"Expression>(premises),branches);");
		return r;
	}


	/**
	 * @param grammar
	 * @param sort
	 */
	private void processBNFs(MettelANTLRGrammar grammar, MettelJavaPackageStructure pStructure, MettelSort sort, List<MettelBNFStatement> bnfs) {
		final String SORT_NAME = sort.name();
		String s0 = BASIC_STRING + MettelJavaNames.firstCharToUpperCase(SORT_NAME);
		String s1 = null;
		//System.out.println(bnfs);
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

				String id = s.identifier();
/*				if(id.equals("equality")){
					id = SORT_NAME+MettelJavaNames.firstCharToUpperCase(id);
					s1 = SORT_NAME+MettelJavaNames.firstCharToUpperCase(id);
				}
*/
				MettelANTLRMultiaryBNFStatement st = new MettelANTLRMultiaryBNFStatement();
				st.addExpression(new MettelANTLRRuleReference(s0,"e0"));

				MettelANTLRMultiaryBNFStatement st1 = new MettelANTLRMultiaryBNFStatement();
				st1.addExpression(new MettelANTLRToken(tokens[1].toString()));

				MettelANTLRRuleReference ref = new MettelANTLRRuleReference(s0,"e1");
				ref.appendLineToPostfix("e0 = factory.create"+MettelJavaNames.firstCharToUpperCase(id)+
						MettelJavaNames.firstCharToUpperCase(SORT_NAME)+"(e0, e1);");
				st1.addExpression(ref);

				MettelANTLRUnaryBNFStatement st0 = new MettelANTLRUnaryBNFStatement(
						st1,MettelANTLRUnaryBNFStatement.STAR);
				st.addExpression(st0);

				MettelANTLRRule r =new MettelANTLRRule(s1,st,/*true,*/new String[]{grammar.name()+
						//MettelJavaNames.firstCharToUpperCase(s.identifier())+
						MettelJavaNames.firstCharToUpperCase(SORT_NAME)});
				r.appendLineToAfterBlock("r0 = e0;");
				grammar.addRule(r);

				pStructure.appendConnectiveClass(grammar.name(), SORT_NAME, id,
						new String[]{SORT_NAME, SORT_NAME}, s.tokens(), (s instanceof MettelEqualityBNFStatement));

			}else if( (SIZE == 2) &&
					  (tokens[0] instanceof MettelSort) &&
					  (SORT_NAME.equals(((MettelSort)tokens[0]).name())) &&
					  (tokens[1] instanceof MettelStringLiteral)
					){
				MettelANTLRMultiaryBNFStatement st = new MettelANTLRMultiaryBNFStatement();
				st.addExpression(new MettelANTLRRuleReference(s0,"e0"));

				MettelANTLRToken t = new MettelANTLRToken(tokens[1].toString());
				t.appendLineToPostfix("e0 = factory.create"+MettelJavaNames.firstCharToUpperCase(s.identifier())+
						MettelJavaNames.firstCharToUpperCase(SORT_NAME)+"(e0);");

				MettelANTLRUnaryBNFStatement st0 = new MettelANTLRUnaryBNFStatement(
						t ,MettelANTLRUnaryBNFStatement.STAR);
				st.addExpression(st0);

				MettelANTLRRule r =new MettelANTLRRule(s1,st,/*true,*/new String[]{grammar.name()+
						//MettelJavaNames.firstCharToUpperCase(s.identifier())+
						MettelJavaNames.firstCharToUpperCase(SORT_NAME)});
				r.appendLineToAfterBlock("r0 = e0;");
				grammar.addRule(r);

				pStructure.appendConnectiveClass(grammar.name(), SORT_NAME, s.identifier(),
						new String[]{((MettelSort)tokens[0]).name()}, s.tokens(), false);


			}else{ //if(tokens[0] instanceof MettelStringLiteral){

				String id = s.identifier();

				MettelANTLRMultiaryBNFStatement st = new MettelANTLRMultiaryBNFStatement(
						MettelANTLRMultiaryBNFStatement.OR);
				MettelANTLRRuleReference ref = new MettelANTLRRuleReference(s0,"e00");
				ref.appendLineToPostfix("r0 = e00;");
				st.addExpression(ref);
				MettelANTLRMultiaryBNFStatement st0 = new MettelANTLRMultiaryBNFStatement();
				//st0.addExpression(new MettelANTLRToken(tokens[0].toString()));

				ArrayList<String> sortStrings = new ArrayList<String>();
				for(int i = 0, j = 0; i < SIZE; i++){
					if(tokens[i] instanceof MettelStringLiteral){
						st0.addExpression(new MettelANTLRToken(tokens[i].toString()));
					}else if(tokens[i] instanceof MettelSort){
						String name = ((MettelSort)tokens[i]).name();
						sortStrings.add(name);
						if(SORT_NAME.equals(name)){
							st0.addExpression(new MettelANTLRRuleReference(s0,"e1"+j));
						}else{
							st0.addExpression(new MettelANTLRRuleReference(tokens[i].toString(),"e1"+j));
						}
						j++;
					}
				}
/*				if(id.equals("equality")){
					id = sortStrings.get(0)+MettelJavaNames.firstCharToUpperCase(id);
					s1 = SORT_NAME+MettelJavaNames.firstCharToUpperCase(id);
				}
*/
				st0.appendToPostfix("r0 = factory.create"+MettelJavaNames.firstCharToUpperCase(id)+
						MettelJavaNames.firstCharToUpperCase(SORT_NAME)+'(');
				final int jSIZE = sortStrings.size();
				if(jSIZE >0 ){
					st0.appendToPostfix("e10");
					for(int j = 1; j < jSIZE; j++){
						st0.appendToPostfix(", e1"+j);
					}
				}
				st0.appendLineToPostfix(");");
				st.addExpression(st0);

				MettelANTLRRule r = new MettelANTLRRule(s1,st,/*true,*/new String[]{grammar.name()+
						//MettelJavaNames.firstCharToUpperCase(s.identifier())+
						MettelJavaNames.firstCharToUpperCase(SORT_NAME)});
/*				r.appendToAfterBlock("r0 = factory.create"+MettelJavaNames.firstCharToUpperCase(s.identifier())+
						MettelJavaNames.firstCharToUpperCase(SORT_NAME)+'(');
				final int jSIZE = sortStrings.size();
				if(jSIZE >0 ){
					r.appendToAfterBlock("e0");
					for(int j = 1; j < jSIZE; j++){
						r.appendToAfterBlock(", e"+j);
					}
					r.appendToAfterBlock(");");
				}
*/				grammar.addRule(r);

				pStructure.appendConnectiveClass(grammar.name(), SORT_NAME, id , sortStrings.toArray(new String[sortStrings.size()]),
						s.tokens(), (s instanceof MettelEqualityBNFStatement));

			}//TODO other alternatives: ~~P, etc
			s0 = s1;
		}
		MettelANTLRRule r = new MettelANTLRRule(SORT_NAME,new MettelANTLRRuleReference(s0,"e0"),
				new String[]{grammar.name()+MettelJavaNames.firstCharToUpperCase(SORT_NAME)});
		r.appendLineToAfterBlock("r0 = e0;");
		grammar.addRule(r);

	}

	/**
	 * @param grammar
	 * @param sort
	 */
	private void processSort(MettelANTLRGrammar grammar, MettelSort sort) {
		final String NAME = grammar.name();
		grammar.addRule(makeANTLREntryRule(NAME, sort));
		grammar.addRule(makeANTLRVariableRule(NAME, sort));
		grammar.addRule(makeANTLRBasicRule(NAME, sort));

	}

	/**
	 * @param sort
	 * @return
	 */
	private MettelANTLRRule makeANTLRBasicRule(String grammarName, MettelSort sort) {
		final String NAME = sort.name();
		MettelANTLRMultiaryBNFStatement s = new MettelANTLRMultiaryBNFStatement(
				MettelANTLRMultiaryBNFStatement.OR);
		s.addExpression(new MettelANTLRRuleReference(NAME+VARIABLE_STRING,"e0"));
		MettelANTLRMultiaryBNFStatement s0 = new MettelANTLRMultiaryBNFStatement();
		s0.addExpression(MettelANTLRToken.LBRACE);
		s0.addExpression(new MettelANTLRRuleReference(NAME,"e0"));
		s0.addExpression(MettelANTLRToken.RBRACE);
		s.addExpression(s0);
		MettelANTLRRule r = new MettelANTLRRule(BASIC_STRING+MettelJavaNames.firstCharToUpperCase(NAME),s/*,true*/,
				new String[]{grammarName+MettelJavaNames.firstCharToUpperCase(NAME)});
		r.appendLineToAfterBlock("r0 = e0;");
		return r;
	}

	/**
	 * @param sort
	 * @return
	 */
	private MettelANTLRRule makeANTLRVariableRule(String grammarName, MettelSort sort) {
		final String NAME = sort.name();
		MettelANTLRRule r = new MettelANTLRRule(sort.name()+VARIABLE_STRING,MettelANTLRToken.ID/*,true*/,
				new String[]{grammarName+MettelJavaNames.firstCharToUpperCase(NAME)});
		r.appendLineToAfterBlock("r0 = factory.create"+MettelJavaNames.firstCharToUpperCase(NAME)+"Variable(t.getText());");
		return r;
	}

	/**
	 * @param sort
	 * @return
	 */
	private MettelANTLRRule makeANTLREntryRule(String grammarName, MettelSort sort) {
		final String NAME = sort.name();
		MettelANTLRMultiaryBNFStatement s = new MettelANTLRMultiaryBNFStatement();
		MettelANTLRRuleReference ruleRef = new MettelANTLRRuleReference(NAME,"e0");
		ruleRef.appendLineToPostfix("a0.add(e0);");
		s.addExpression(new MettelANTLRUnaryBNFStatement(
						ruleRef,MettelANTLRUnaryBNFStatement.STAR));
		s.addExpression(MettelANTLRToken.EOF);
		return new MettelANTLRRule(NAME+'s',s/*,false*/,
				new String[]{"Collection<"+grammarName+MettelJavaNames.firstCharToUpperCase(NAME)+'>'},null);
	}

	private MettelANTLRRule makeANTLREqualityRule(String grammarName,Collection<MettelSort> sorts){
		MettelANTLRMultiaryBNFStatement s = new MettelANTLRMultiaryBNFStatement(MettelANTLRMultiaryBNFStatement.OR);
		for(MettelSort sort:sorts){
			final String SORT_NAME = sort.name();
			MettelANTLRMultiaryBNFStatement s0 = new MettelANTLRMultiaryBNFStatement();
			MettelANTLRRuleReference ruleRef0 = new MettelANTLRRuleReference(SORT_NAME,SORT_NAME+"E0");
			MettelANTLRRuleReference ruleRef1 = new MettelANTLRRuleReference(SORT_NAME,SORT_NAME+"E1");
			s0.addExpression(ruleRef0);
			s0.addExpression(new MettelANTLRToken("'='"));
			s0.addExpression(ruleRef1);
			s0.appendLineToPostfix("r0 = factory.create"+MettelJavaNames.firstCharToUpperCase(SORT_NAME)+
					"Equality("+SORT_NAME+"E0, "+SORT_NAME+"E1);");
			s.addExpression(s0);
		}
		MettelANTLRRule r = new MettelANTLRRule("equality",s,
				new String[]{grammarName+"Expression"});
		//r.appendLineToAfterBlock("r0 = e0;");
		return r;
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
