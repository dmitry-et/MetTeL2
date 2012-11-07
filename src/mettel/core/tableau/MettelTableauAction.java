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
package mettel.core.tableau;

import java.util.Set;

import mettel.core.util.MettelAnnotatedObject;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public interface MettelTableauAction extends MettelAnnotatedObject<MettelTableauState>, Comparable<MettelTableauAction>{

	//Set<MettelTableauState> states();
	int id();

	boolean isFor(MettelTableauState s);

	boolean remove(MettelTableauState s);

	boolean add(MettelTableauState s);

	Set<MettelTableauState> execute(MettelTableauState s);

	/**
	 * @param children
	 */
	boolean addAll(Set<MettelTableauState> children);

	/**
	 * @return
	 */
	boolean completed();

	/**
	 * @param unexpandedStates
	 */
	boolean retainAll(Set<MettelTableauState> unexpandedStates);

}
