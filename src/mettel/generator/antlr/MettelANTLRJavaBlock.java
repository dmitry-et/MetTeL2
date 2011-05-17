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
class MettelANTLRJavaBlock extends MettelIndentedStringBuilder{

/*	private ArrayList<CharSequence> statements = new ArrayList<CharSequence>();

	void append(CharSequence statement){
		statements.add(statement);
	}
*/
	/**
	 *
	 */
	MettelANTLRJavaBlock() {
		super(new StringBuilder());
	}
	/**
	 * @param ib
	 */
	public void toStringBuilder(MettelIndentedStringBuilder b) {
		MettelIndentedStringBuilder ib = new MettelIndentedStringBuilder(b);
		ib.appendLine('{');
		ib.indent();ib.append(this.builder());
/*		for(CharSequence s:statements){
			ib.appendLine(s);
		}
*/
		ib.appendLine('}');
	}

}
