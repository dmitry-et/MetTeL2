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

import static mettel.language.MettelSpecification.LINE_SEPARATOR;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
class MettelSort implements MettelToken {

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
	String name(){
		return name;
	}

	/**
	 * @param buf
	 */
	public void toBuffer(StringBuffer buf) {
		//buf.append("sort ");
		buf.append(name);
		//buf.append(';');
	}

}
