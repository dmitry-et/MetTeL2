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

import mettel.util.MettelJavaNames;


/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public final class MettelEqualityBNFStatement extends MettelBNFStatement {

	public static final String EQUALITY = "equality";
	public static final String EQUIVALENCE = "equivalence";
	public static final String CONGRUENCE = "congruence";

	public static final boolean isEquality(String id){
		if(id == null) return false;
		return (EQUALITY.equalsIgnoreCase(id)||EQUIVALENCE.equalsIgnoreCase(id)||CONGRUENCE.equalsIgnoreCase(id));
	}

	/**
	 *
	 */
	MettelEqualityBNFStatement(String identifier) {
		super(identifier);
	}

	private MettelSort sort = null;

	public MettelSort sort(){
		return sort;
	}

	public void setSort(MettelSort sort){
		this.sort = sort;
	}

	public String identifier(){
		return sort+MettelJavaNames.firstCharToUpperCase(super.identifier());
	}

}
