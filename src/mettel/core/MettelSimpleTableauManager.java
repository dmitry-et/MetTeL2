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
package mettel.core;

import java.util.Collection;
import java.util.TreeSet;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelSimpleTableauManager extends MettelAbstractTableauManager {

	/**
	 *
	 */
	public MettelSimpleTableauManager(Collection<? extends MettelTableauRule> calculus) {
		unexpandedStates = new TreeSet<MettelTableauState>(new MettelTableauStateComparator());
		state = new MettelGeneralTableauState(/*this,*/ calculus);
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelAbstractTableauManager#cleanUp()
	 */
	@Override
	void cleanUp() {
		final TreeSet<MettelTableauState> states = new TreeSet<MettelTableauState>(new MettelTableauStateComparator());
		states.addAll(unexpandedStates);
		unexpandedStates = states;
	}

}
