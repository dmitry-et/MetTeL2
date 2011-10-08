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
public class MettelEqualityArgumentTypeException extends MettelRecognitionException {

	/**
	 *
	 */
	private static final long serialVersionUID = -7862542364020630201L;

	/**
	 *
	 */
	private String sortName0 = null;

	private String sortName1 = null;

	/**
	 * @param sortName
	 */
	public MettelEqualityArgumentTypeException(String sortName0, String sortName1) {
		this.sortName0 =  sortName0;
		this.sortName1 =  sortName1;
	}

	public String toString(){
		return "Expected argument of the sort "+sortName0+
		       " but the sort "+sortName1+" is detected "+super.toString();
	}
}
