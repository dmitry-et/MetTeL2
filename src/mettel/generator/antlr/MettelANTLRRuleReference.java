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

import mettel.util.MettelIndentedStringBuilder;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelANTLRRuleReference extends MettelANTLRExpression {

	private String name = null;

	private String returnVariable = null;

	//private boolean syntacticPredicate = false;

	@SuppressWarnings("unused")
	private MettelANTLRRuleReference(){}

	/**
	 *
	 */
	public MettelANTLRRuleReference(String name) {
		this(name, null, null);
	}

	public MettelANTLRRuleReference(String name, String returnVariable) {
		this(name,returnVariable,null);
	}

	public MettelANTLRRuleReference(String name, String returnVariable, MettelANTLRSyntacticPredicate syntacticPredicate) {
		super(syntacticPredicate);
		this.name = name;
		this.returnVariable = returnVariable;
		//this.syntacticPredicate = syntacticPredicate;
	}

	/* (non-Javadoc)
	 * @see mettel.generator.antlr.MettelANTLRExpression#toStringBuilder(mettel.util.MettelIndentedStringBuilder)
	 */
	@Override
	void toStringBuilder0(MettelIndentedStringBuilder b, boolean omitJavaBlocks) {
		/*if(syntacticPredicate){
			b.append('(');
			b.append(name);
			b.append(')');
			b.append('=');
			b.append('>');
			b.append(' ');
		}*/
		if(returnVariable != null && !omitJavaBlocks){
			b.append(returnVariable);
			b.append(" = ");
		}
		b.append(name);
	}
}
