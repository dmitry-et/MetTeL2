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

import static mettel.util.MettelStrings.BASIC_STRING;
import static mettel.util.MettelStrings.VARIABLE_STRING;
import mettel.generator.MettelANTLRGrammarGeneratorProperties;
import mettel.generator.antlr.MettelANTLRGrammar;
import mettel.generator.antlr.MettelANTLRMultiaryBNFStatement;
import mettel.generator.antlr.MettelANTLRRule;
import mettel.generator.antlr.MettelANTLRRuleReference;
import mettel.generator.antlr.MettelANTLRToken;
import mettel.generator.antlr.MettelANTLRUnaryBNFStatement;
import mettel.util.MettelJavaNames;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelSort implements MettelToken {

	private String name = null;
	/**
	 *
	 */
	@SuppressWarnings("unused")
	private	MettelSort() {};

	MettelSort(String name){
		super();
		this.name = name;
	}

	/**
	 * @returns the name
	 */
	public String name(){
		return name;
	}

	/**
	 * @param buf
	 */
	public void toBuffer(StringBuilder buf) {
		//buf.append("sort ");
		buf.append(name);
		//buf.append(';');
	}

	public String toString(){
		return name;
	}

	
	private MettelANTLRGrammarGeneratorProperties properties = null;
	
	private String grammarName = null;
	
	
	void process(MettelANTLRGrammar grammar, MettelANTLRGrammarGeneratorProperties properties) {
		this.grammarName = grammar.name();
		this.properties = properties;
		
		grammar.addRule(makeANTLREntryRule());
		grammar.addRule(makeANTLRVariableRule());
		grammar.addRule(makeANTLRBasicRule());
	}
	
	private MettelANTLRRule makeANTLREntryRule() {
		MettelANTLRMultiaryBNFStatement s = new MettelANTLRMultiaryBNFStatement();
		MettelANTLRRuleReference ruleRef = new MettelANTLRRuleReference(name,"e0");
		ruleRef.appendLineToPostfix("a0.add(e0);");
		s.addExpression(new MettelANTLRUnaryBNFStatement(
						ruleRef,MettelANTLRUnaryBNFStatement.STAR));
		s.addExpression(MettelANTLRToken.EOF);
		return new MettelANTLRRule(name+'s',s/*,false*/,
				new String[]{"Collection<"+grammarName+MettelJavaNames.firstCharToUpperCase(name, properties.nameSeparator)+'>'},null);
	}
	
	private MettelANTLRRule makeANTLRBasicRule() {
		MettelANTLRMultiaryBNFStatement s = new MettelANTLRMultiaryBNFStatement(
				MettelANTLRMultiaryBNFStatement.OR);
		s.addExpression(new MettelANTLRRuleReference(name+VARIABLE_STRING,"e0"));
		MettelANTLRMultiaryBNFStatement s0 = new MettelANTLRMultiaryBNFStatement();
		s0.addExpression(new MettelANTLRToken("'"+properties.expressionLeftDelimiter+"'"));//MettelANTLRToken.LBRACE);
		s0.addExpression(new MettelANTLRRuleReference(name,"e0"));
		s0.addExpression(new MettelANTLRToken("'"+properties.expressionRightDelimiter+"'"));//MettelANTLRToken.RBRACE);
		s.addExpression(s0);
		MettelANTLRRule r = new MettelANTLRRule(BASIC_STRING+MettelJavaNames.firstCharToUpperCase(name, properties.nameSeparator),s/*,true*/,
				new String[]{grammarName+MettelJavaNames.firstCharToUpperCase(name, properties.nameSeparator)});
		r.appendLineToAfterBlock("r0 = e0;");
		return r;
	}

	private MettelANTLRRule makeANTLRVariableRule() {
		MettelANTLRRule r = new MettelANTLRRule(name+VARIABLE_STRING,MettelANTLRToken.ID/*,true*/,
				new String[]{grammarName+MettelJavaNames.firstCharToUpperCase(name, properties.nameSeparator)});
		r.appendLineToAfterBlock("r0 = factory.create"+MettelJavaNames.firstCharToUpperCase(name, properties.nameSeparator)+"Variable(t.getText());");
		return r;
	}
}
