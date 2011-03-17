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
class MettelANTLRBinaryBNFStatement extends MettelANTLRExpression {

	public static char OR = '|';

	private char operator = OR;

	private MettelANTLRExpression left = null, right = null;

	@SuppressWarnings("unused")
	private MettelANTLRBinaryBNFStatement(){}

	/**
	 *
	 */
	MettelANTLRBinaryBNFStatement(MettelANTLRExpression left,MettelANTLRExpression right) {
		super();
		this.left = left;
		this.right = right;
	}

	/* (non-Javadoc)
	 * @see mettel.generator.antlr.MettelANTLRExpression#toStringBuilder(mettel.util.MettelIndentedStringBuilder)
	 */
	@Override
	void toStringBuilder(MettelIndentedStringBuilder b) {
		MettelIndentedStringBuilder ib = new MettelIndentedStringBuilder(b);
		prefixOutput(ib);
		left.toStringBuilder(ib);
		ib.appendLine(operator);
		right.toStringBuilder(ib);
		postfixOutput(ib);
	}
}
