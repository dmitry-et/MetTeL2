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
package mettel.language;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import mettel.generator.MettelANTLRGrammarGeneratorProperties;
import mettel.generator.antlr.MettelANTLRGrammar;
import mettel.generator.antlr.MettelANTLRMultiaryBNFStatement;
import mettel.generator.antlr.MettelANTLRRule;
import mettel.generator.antlr.MettelANTLRRuleReference;
import mettel.generator.antlr.MettelANTLRStandardGrammar;
import mettel.generator.antlr.MettelANTLRToken;
import mettel.generator.antlr.MettelANTLRUnaryBNFStatement;
import mettel.generator.java.MettelJavaPackageStructure;
import mettel.util.MettelJavaNames;

import static mettel.util.MettelStrings.BASIC_STRING;
import static mettel.util.MettelStrings.LINE_SEPARATOR;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelSyntax implements MettelBlock {

	private String name = null;

	private List<MettelSyntax> parents = null;

	public String name(){
		return name;
	}

	public List<MettelSyntax> parents(){
		return parents;
	}

	/*TODO implement hierarchical extension mechanism
	 *
    private MettelSyntax parent = null;
	*/

	private ArrayList<MettelSort> sorts = new ArrayList<MettelSort>();

    private HashMap<String,MettelSort> sortMap = new HashMap<String,MettelSort>();

    private LinkedHashMap<MettelSort,ArrayList<MettelBNFStatement>> bnfs = new LinkedHashMap<MettelSort,ArrayList<MettelBNFStatement>>();
	/**
	 *
	 */
    @SuppressWarnings("unused")
	private MettelSyntax(){};

	MettelSyntax(String name) {
		this(name, null);
	}

	MettelSyntax(String name, List<MettelSyntax> parents) {
		super();
		this.name = name;
		this.parents = parents;
	}

/*TODO implement hierarchical extension mechanism
 *
  	MettelSyntax(String identifier, MettelSyntax parent){
		this(identifier);
		this.parent = parent;
	}
*/
	/**
	 * @returns the parent
     *TODO implement hierarchical extension mechanism
     *
	protected MettelSyntax parent() {
		return parent;
	}
	*/

	/**
	 * @param sort A sort to add
	 */
/*	void append(MettelSort sort) {
		if(sort == null) return;
		sorts.put(sort.name(),sort);
	}
*/

	/**
	 * @param sortName A sort name to add
	 */
	boolean addSort(String sortName) {
		MettelSort sort = getSort(sortName);
		if(sort != null) return false;
		final MettelSort SORT = new MettelSort(sortName);
		sorts.add(SORT);
		sortMap.put(sortName, SORT);
		final ArrayList<MettelBNFStatement> BNFS = new ArrayList<MettelBNFStatement>();
		bnfs.put(SORT,BNFS);
		return true;
	}

	/**
	 * @param sortName A sort name to check for existence
	 * @returns true if the sort exists
	 */
	boolean sortExists(String sortName) {
		return (getSort(sortName) != null);
	}

	/**
	 * @param sortName A sort name to get
	 * @returns sort
	 */
	MettelSort getSort(String sortName) {
		MettelSort s = sortMap.get(sortName);
		if(s == null){
			if(parents != null){
				for(MettelSyntax syn:parents){
					s = syn.getSort(sortName);
					if(s != null) return s;
				}
			}
		}
		return s;
	}

	/**
	 * @param sorts A collection of sorts to add
	 */
/*	void append(Collection<MettelSort> sorts) {
		if(sorts == null) return;
		for(MettelSort sort: sorts){
			this.sorts.put(sort.name(),sort);
		}
	}
*/

//	/**
//	 * @return the sorts
//	 */
//	HashMap<String,MettelSort> sortTable() {
//		return sorts;
//	}

//	/**
//	 * @return the sorts
//	 */
//	Collection<MettelSort> sorts() {
//		return sorts.values();
//	}

	/**
	 * @param statement A BNF statement to add
	 */
	void addBNF(MettelSort sort, MettelBNFStatement statement) {
		if(statement == null || sort == null) return;
		if(sort.equals(getSort(sort.name()))){
			ArrayList<MettelBNFStatement> statements = bnfs.get(sort);
			if(statements == null){
				statements = new ArrayList<MettelBNFStatement>();
				bnfs.put(sort,statements);
			}
			statements.add(statement);
		}
	}

	/**
	 * @param statements A list of BNF statements to add
	 */
	/*void append(List<MettelBNFStatement> statements) {
		if(statements == null) return;
		this.statements.addAll(statements);
	}*/

	/**
	 * @param buf
	 */
	public void toBuffer(StringBuilder buf) {
		buf.append("syntax ");
		buf.append(name);
		if(parents != null){
			final int SIZE = parents.size();
			if(SIZE >0){
				buf.append(" extends ");
				buf.append(parents.get(0).name);
				for(int i = 1; i < SIZE; i++){
					buf.append(", ");
					buf.append(parents.get(i).name);
				}
			}
		}
		buf.append('{');
		buf.append(LINE_SEPARATOR);
		int i = 0;
		buf.append(LINE_SEPARATOR);
		buf.append('\t');
		for(MettelSort sort:sorts){
			if(i == 0){
				buf.append("sort ");
			}else{
				buf.append(',');
			}
			sort.toBuffer(buf);
			i++;
		}
		if(i > 0){
			buf.append(';');
			buf.append(LINE_SEPARATOR);
		}

		for(MettelSort sort:bnfs.keySet()){
			ArrayList<MettelBNFStatement> statements = bnfs.get(sort);
			final int SIZE = statements.size();
			if(SIZE > 0){
				buf.append(LINE_SEPARATOR);
				buf.append('\t');
				sort.toBuffer(buf);
				buf.append(' ');
				for(int j = 0; j < SIZE; j++){
					if(j > 0){
						buf.append(LINE_SEPARATOR);
						buf.append("\t| ");
					}
					statements.get(j).toBuffer(buf);
				}
				buf.append(';');
				buf.append(LINE_SEPARATOR);
			}
		}

		buf.append(LINE_SEPARATOR);
		buf.append('}');
	}

	/**
	 * @return
	 */
	public List<MettelSort> sorts() {
		return sorts;
	}

	/**
	 * @param sort
	 * @return
	 */
	public List<MettelBNFStatement> getBNFs(MettelSort sort) {
		return bnfs.get(sort);
	}

	/*public MettelSyntax unravel(){
		if(parents == null) return this;
		MettelSyntax syn = new MettelSyntax(name);
		for(MettelSyntax s:parents){
			MettelSyntax s0 = s.unravel();
			for(MettelSort sort:s0.sorts){
				if(! syn.sortExists(sort.name())){
					syn.sorts.add(sort);
				}
			}
			for(MettelSort sort:s0.bnfs.keySet()){
				List<MettelBNFStatement> statements = s0.getBNFs(sort);
				syn.bnfs.put(sort,new ArrayList<MettelBNFStatement>(statements));
			}
		}
		return syn;
	}*/

	public List<MettelSort> allSorts(){
		if(parents == null) return sorts;
		HashMap<String, MettelSort> map = new HashMap<String, MettelSort>();
		ArrayList<MettelSort> list = new ArrayList<MettelSort>();
		for(MettelSort sort:sorts){
			list.add(sort);
			map.put(sort.name(), sort);
		}
		for(MettelSyntax s:parents){
			for(MettelSort sort:s.allSorts()){
				MettelSort s0 = map.get(sort.name());
				if(s0 == null){
					list.add(sort);
					map.put(sort.name(), sort);
				}
			}
		}
		return list;
	}

	private MettelANTLRGrammarGeneratorProperties properties = null;
	private MettelJavaPackageStructure pStructure = null;
	String[] sortStrings = null;

	public void process(MettelJavaPackageStructure pStructure, MettelANTLRGrammarGeneratorProperties properties){
		this.pStructure = pStructure;
		this.properties = properties;

		//unravel();
		final String NAME = MettelJavaNames.firstCharToUpperCase(name);

		pStructure.appendStandardClasses(NAME);
		pStructure.appendStandardLanguageClasses(name, NAME, properties.nameSeparator);

		MettelANTLRStandardGrammar grammar =
				new MettelANTLRStandardGrammar(NAME, pStructure.grammarPackage(name).path(), properties.grammarOptions);

		sortStrings = new String[sorts.size()];
		int i = 0;
		for(MettelSort sort:sorts){
			sortStrings[i++] = sort.name();
			sort.process(grammar, properties);
			processBNFs(grammar, sort);
		}
		//for(String s:sortStrings) System.out.print(s+ " "); System.out.println();
		pStructure.appendStandardLanguageClasses(name, NAME, sortStrings, properties.branchBound);

		grammar.addRule(makeANTLRExpressionListRule(NAME));
		grammar.addRule(makeANTLRExpressionRule(NAME, sorts));
		grammar.addRule(makeANTLRTableauRule(NAME));
		grammar.addRule(makeANTLRTableauCalculusRule(NAME));

		pStructure.appendParser(name, grammar);
	}

	private void processBNFs(MettelANTLRGrammar grammar, MettelSort sort) {
		final String SORT_NAME = sort.name();
		String s0 = BASIC_STRING + MettelJavaNames.firstCharToUpperCase(SORT_NAME,properties.nameSeparator);
		String s1 = null;
		for(MettelBNFStatement s:bnfs.get(sort)){
			s1 = SORT_NAME +
					MettelJavaNames.firstCharToUpperCase(s.identifier(),properties.nameSeparator);

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

				MettelANTLRMultiaryBNFStatement st = new MettelANTLRMultiaryBNFStatement();
				st.addExpression(new MettelANTLRRuleReference(s0,"e0"));

				MettelANTLRMultiaryBNFStatement st1 = new MettelANTLRMultiaryBNFStatement();
				st1.addExpression(new MettelANTLRToken(tokens[1].toString()));

				MettelANTLRRuleReference ref = new MettelANTLRRuleReference(s0,"e1");
				ref.appendLineToPostfix("e0 = factory.create"+
						MettelJavaNames.firstCharToUpperCase(id,properties.nameSeparator)+
						MettelJavaNames.firstCharToUpperCase(SORT_NAME,properties.nameSeparator)+"(e0, e1);");
				st1.addExpression(ref);

				MettelANTLRUnaryBNFStatement st0 = new MettelANTLRUnaryBNFStatement(
						st1,MettelANTLRUnaryBNFStatement.STAR);
				st.addExpression(st0);

				MettelANTLRRule r =new MettelANTLRRule(s1,st,/*true,*/new String[]{grammar.name()+
						MettelJavaNames.firstCharToUpperCase(SORT_NAME, properties.nameSeparator)});
				r.appendLineToAfterBlock("r0 = e0;");
				grammar.addRule(r);

				pStructure.appendConnectiveLanguageClass(name, grammar.name(), SORT_NAME, id,
						new String[]{SORT_NAME, SORT_NAME}, s.tokens(), (s instanceof MettelEqualityBNFStatement));

			}else if( (SIZE == 2) &&
					  (tokens[0] instanceof MettelSort) &&
					  (SORT_NAME.equals(((MettelSort)tokens[0]).name())) &&
					  (tokens[1] instanceof MettelStringLiteral)
					){
				MettelANTLRMultiaryBNFStatement st = new MettelANTLRMultiaryBNFStatement();
				st.addExpression(new MettelANTLRRuleReference(s0,"e0"));

				MettelANTLRToken t = new MettelANTLRToken(tokens[1].toString());
				t.appendLineToPostfix("e0 = factory.create"+
						MettelJavaNames.firstCharToUpperCase(s.identifier(),properties.nameSeparator)+
						MettelJavaNames.firstCharToUpperCase(SORT_NAME, properties.nameSeparator)+"(e0);");

				MettelANTLRUnaryBNFStatement st0 = new MettelANTLRUnaryBNFStatement(
						t ,MettelANTLRUnaryBNFStatement.STAR);
				st.addExpression(st0);

				MettelANTLRRule r =new MettelANTLRRule(s1,st,/*true,*/new String[]{grammar.name()+
						MettelJavaNames.firstCharToUpperCase(SORT_NAME, properties.nameSeparator)});
				r.appendLineToAfterBlock("r0 = e0;");
				grammar.addRule(r);

				pStructure.appendConnectiveLanguageClass(name, grammar.name(), SORT_NAME, s.identifier(),
						new String[]{((MettelSort)tokens[0]).name()}, s.tokens(), false);


			}else{ //if(tokens[0] instanceof MettelStringLiteral){

				String id = s.identifier();

				MettelANTLRMultiaryBNFStatement st = new MettelANTLRMultiaryBNFStatement(
						MettelANTLRMultiaryBNFStatement.OR);
				MettelANTLRRuleReference ref = new MettelANTLRRuleReference(s0,"e00");
				ref.appendLineToPostfix("r0 = e00;");
				st.addExpression(ref);
				MettelANTLRMultiaryBNFStatement st0 = new MettelANTLRMultiaryBNFStatement();

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

				st0.appendToPostfix("r0 = factory.create"+
						MettelJavaNames.firstCharToUpperCase(id,properties.nameSeparator)+
						MettelJavaNames.firstCharToUpperCase(SORT_NAME, properties.nameSeparator)+'(');
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
						MettelJavaNames.firstCharToUpperCase(SORT_NAME, properties.nameSeparator)});
				grammar.addRule(r);

				pStructure.appendConnectiveLanguageClass(name, grammar.name(), SORT_NAME, id , sortStrings.toArray(new String[sortStrings.size()]),
						s.tokens(), (s instanceof MettelEqualityBNFStatement));

			}//TODO other alternatives: ~~P, etc
			s0 = s1;
		}
		MettelANTLRRule r = new MettelANTLRRule(SORT_NAME,new MettelANTLRRuleReference(s0,"e0"),
				new String[]{grammar.name()+MettelJavaNames.firstCharToUpperCase(SORT_NAME, properties.nameSeparator)});
		r.appendLineToAfterBlock("r0 = e0;");
		grammar.addRule(r);

	}

	private MettelANTLRRule makeANTLRTableauCalculusRule(String grammarName) {
		final String NAME = "tableauRule";

		MettelANTLRMultiaryBNFStatement s = new MettelANTLRMultiaryBNFStatement();
		MettelANTLRRuleReference ruleRef = new MettelANTLRRuleReference(NAME,"e0");
		ruleRef.appendLineToPostfix("a0.add(e0);");

		s.addExpression(ruleRef);

		MettelANTLRMultiaryBNFStatement s0 = new MettelANTLRMultiaryBNFStatement();
		s0.addExpression(new MettelANTLRToken("'"+properties.tableauRuleDelimiter+"'"));
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
		s.addExpression(new MettelANTLRToken("'"+properties.tableauRulePremiseDelimiter+"'"));

		MettelANTLRRuleReference ruleRef = new MettelANTLRRuleReference(EXPRESSIONS,"conclusion");
		ruleRef.appendLineToPostfix("branches.add(new LinkedHashSet<"+grammarName+"Expression>(conclusion));");
		s.addExpression(ruleRef);

		MettelANTLRMultiaryBNFStatement s0 = new MettelANTLRMultiaryBNFStatement();
		s0.addExpression(new MettelANTLRToken("'"+properties.tableauRuleBranchDelimiter+"'"));
		s0.addExpression(ruleRef);
		s.addExpression(new MettelANTLRUnaryBNFStatement(s0,MettelANTLRUnaryBNFStatement.STAR));

		MettelANTLRMultiaryBNFStatement s1 = new MettelANTLRMultiaryBNFStatement();
		s1.addExpression(new MettelANTLRToken("'priority'"));
		s1.addExpression(new MettelANTLRToken("INT",true));
		s1.appendLineToPostfix("priority = Integer.valueOf(t.getText());");
		s.addExpression(new MettelANTLRUnaryBNFStatement(s1,MettelANTLRUnaryBNFStatement.TEST));

		MettelANTLRRule r = new MettelANTLRRule("tableauRule",s,
				new String[]{"MettelGeneralTableauRule"});
		r.appendLineToInitBlock("LinkedHashSet<LinkedHashSet<"+grammarName+
				"Expression>> branches = new LinkedHashSet<LinkedHashSet<"+grammarName+"Expression>>();");
		r.appendLineToInitBlock("int priority = 0;");
		r.appendLineToAfterBlock("r0 = new MettelGeneralTableauRule(new LinkedHashSet<"+grammarName+"Expression>(premises),branches,priority);");
		return r;
	}
}
