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
public class MettelUndeclaredSortException extends MettelRecognitionException {

	/**
	 *
	 */
	private static final long serialVersionUID = -8253966884272432661L;

	/**
	 *
	 */
	private String sortName = null;
	/**
	 * @param sortName
	 */
	public MettelUndeclaredSortException(String sortName) {
		super();
		this.sortName =  sortName;
	}
	
	public MettelUndeclaredSortException(int line, int pos, String sortName) {
		super(line, pos);
		this.sortName =  sortName;
	}

	public String getMessage(){
		return "The sort '"+sortName+"' is not declared.";// + super.getMessage();
	}
}
