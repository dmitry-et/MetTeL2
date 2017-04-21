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

import java.util.LinkedList;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 * Provides depth-first left-to-right strategy if the states are in the natural order.
 *
 */
public class MettelSimpleLIFOBranchSelectionStrategy implements
		MettelBranchSelectionStrategy {

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelBranchSelectionStrategy#chooseTableauState(java.util.SortedSet)
	 */
	@Override
	public MettelTableauState selectTableauState(LinkedList<MettelTableauState> s) {
		if(s.isEmpty()) return null;
		return s.getLast();
	}

	@Override
	public int selectTableauStateIndex(LinkedList<MettelTableauState> s) {
		return s.size() - 1;
	}
		
}
