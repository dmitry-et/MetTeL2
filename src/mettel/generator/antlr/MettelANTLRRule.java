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
public class MettelANTLRRule {

	/**
	 *
	 */
	private String name = null;

	private MettelANTLRExpression e = null;

	private boolean fragment = false;

	private String[] argumentTypes = null;

	private String[] returns =null;

	public MettelANTLRRule(String name, MettelANTLRExpression e){
		super();
		this.name = name;
		this.e = e;
	}

	/**
	 * @param b
	 */
	void toStringBuilder(MettelIndentedStringBuilder b){
		if(fragment){
			b.append("fragment");
			b.append(' ');
		}
		b.appendLine(name);
		if(argumentTypes != null){
			final int SIZE = argumentTypes.length;
			if(SIZE > 0){
				b.append('[');
				b.append(argumentTypes[0]);
				b.append(" a0");
				for(int i = 1; i < SIZE; i++){
					b.append(", ");
					b.append(argumentTypes[i]);
					b.append("a"+i);
				}
				b.append(']');
			}
		}
		MettelIndentedStringBuilder ib = new MettelIndentedStringBuilder(b);
		ib.appendLine(':');
		e.toStringBuilder(ib);
		b.appendEOL();
		b.appendLine(';');
	}
}
