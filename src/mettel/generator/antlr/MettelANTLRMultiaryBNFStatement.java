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

import java.util.ArrayList;

import mettel.util.MettelIndentedStringBuilder;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelANTLRMultiaryBNFStatement extends MettelANTLRExpression {

	public static char OR = '|';
	private static char  SEQ = ';';

	private char operator = SEQ;

	private ArrayList<MettelANTLRExpression> expressions = new ArrayList<MettelANTLRExpression>();

	public MettelANTLRMultiaryBNFStatement(){
		super();
	}

	public MettelANTLRMultiaryBNFStatement(char operator){
		super();
		this.operator = operator;
	}

	/**
	 *
	 */
	public void addExpression(MettelANTLRExpression e){
		expressions.add(e);
	}

	/* (non-Javadoc)
	 * @see mettel.generator.antlr.MettelANTLRExpression#toStringBuilder(mettel.util.MettelIndentedStringBuilder)
	 */
	@Override
	void toStringBuilder(MettelIndentedStringBuilder b) {
		MettelIndentedStringBuilder ib = new MettelIndentedStringBuilder(b);
		prefixOutput(ib);
		MettelANTLRExpression[] es = (MettelANTLRExpression[]) expressions.toArray();
		final int SIZE = es.length;
		if(SIZE > 0){
			es[0].toStringBuilder(ib);
			for(int i = 1; i < SIZE; i++){
				if(i < SIZE)ib.appendLine(operator);
				es[i].toStringBuilder(ib);
			}
		}
		postfixOutput(ib);
	}
}
