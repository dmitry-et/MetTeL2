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

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelStringLiteral implements MettelToken {

	private String literal = null;

	@SuppressWarnings("unused")
	private MettelStringLiteral(){};

	MettelStringLiteral(String literal){
		this.literal = literal;
	}

	/* (non-Javadoc)
	 * @see mettel.language.MettelToken#toBuffer(java.lang.StringBuffer)
	 */
	public void toBuffer(StringBuilder buf) {
		//buf.append('"');
		buf.append(literal);
		//buf.append('"');
	}

	public String toString(){
		return literal;
	}

}
