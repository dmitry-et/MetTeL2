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
public class MettelANTLRUnaryBNFStatement extends MettelANTLRExpression {

	public static char STAR = '*';
	public static char PLUS = '+';
	public static char TEST = '?';

	private char operator = STAR;

	private MettelANTLRExpression expression = null;

	@SuppressWarnings("unused")
	private MettelANTLRUnaryBNFStatement(){}

	/**
	 *
	 */
	public MettelANTLRUnaryBNFStatement(MettelANTLRExpression expression,char operator) {
		super();
		this.expression = expression;
		this.operator = operator;
	}

	/* (non-Javadoc)
	 * @see mettel.generator.antlr.MettelANTLRExpression#toStringBuilder(mettel.util.MettelIndentedStringBuilder)
	 */
	@Override
	void toStringBuilder0(MettelIndentedStringBuilder b) {
		MettelIndentedStringBuilder ib = new MettelIndentedStringBuilder(b);
		//prefixOutput(ib);
		ib.appendEOL();
		ib.indent();
		expression.toStringBuilder(ib);
		ib.append(operator);
		ib.appendEOL();
		b.indent();
		//postfixOutput(ib);
	}
}
