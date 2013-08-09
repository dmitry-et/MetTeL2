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

import mettel.util.MettelJavaNames;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 */
public class MettelANTLRFOGrammar extends MettelANTLRStandardGrammar {

	/**
	 * @param name
	 * @param path
	 * @param options
	 */
	public MettelANTLRFOGrammar(String name, String path,
			MettelANTLRGrammarOptions options) {
		super(name, path, options);
		// TODO Auto-generated constructor stub
	}

	
	private void init(){
		
		MettelANTLRRuleReference ref0 = new MettelANTLRRuleReference("foFormula", "f");
		ref0.appendToPostfix("a0.add(f)");
		MettelANTLRUnaryBNFStatement e0 = new MettelANTLRUnaryBNFStatement(ref0, MettelANTLRUnaryBNFStatement.PLUS);
		MettelANTLRRule r = new MettelANTLRRule("foFormulae", e0, new String[]{"Collection<MettelFOFormula>"}, null);
		addRule(r);
		
		ref0 = new MettelANTLRRuleReference("foEquivalenceFormula", "f");
		ref0.appendLineToPostfix("r0 = f");
		r = new MettelANTLRRule("foFormula", ref0, null, new String[]{"MettelFOFormula"});
		addRule(r);
		
		makeBinaryRule("equivalenceFormula", "imlicationFormula", "<->");
		makeBinaryRule("implicationFormula", "disjunctionFormula", "->");
		makeBinaryRule("disjunctionFormula", "conjunctionFormula", "|");
		makeBinaryRule("conjunctionFormula", "existentialFormula", "&");
		
	}
	
	private void makeBinaryRule(String name, String refName, String token){
		MettelANTLRMultiaryBNFStatement me0 = new MettelANTLRMultiaryBNFStatement();
		String prefixedRefName = "fo" + MettelJavaNames.firstCharToUpperCase(refName); 
		MettelANTLRRuleReference ref0 = new MettelANTLRRuleReference(prefixedRefName, "f0");
		me0.addExpression(ref0);
		MettelANTLRMultiaryBNFStatement me1 = new MettelANTLRMultiaryBNFStatement();
		me1.addExpression(new MettelANTLRToken(token));
		me1.addExpression(new MettelANTLRRuleReference(prefixedRefName, "f1"));
		me1.appendLineToPostfix("f0 = foFactory.create" + MettelJavaNames.firstCharToUpperCase(name) +"(f0, f1)");
		MettelANTLRUnaryBNFStatement e0 = new MettelANTLRUnaryBNFStatement(me1, MettelANTLRUnaryBNFStatement.STAR);
		MettelANTLRRule r = new MettelANTLRRule("fo" + MettelJavaNames.firstCharToUpperCase(name), e0, null, new String[]{"MettelFOFormula"});
		addRule(r);
	}
}
