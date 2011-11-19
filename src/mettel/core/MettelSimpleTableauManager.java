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
	public MettelSimpleTableauManager(MettelTableauObjectFactory factory, Collection<? extends MettelTableauRule> calculus) {
		this(factory,calculus,null,null);
	}

	public MettelSimpleTableauManager(MettelTableauObjectFactory factory, Collection<? extends MettelTableauRule> calculus,
			MettelTableauStateAcceptor acceptor) {
		this(factory,calculus,null,acceptor);
	}

	public MettelSimpleTableauManager(MettelTableauObjectFactory factory, Collection<? extends MettelTableauRule> calculus,
					MettelBranchSelectionStrategy strategy, MettelTableauStateAcceptor acceptor) {
		unexpandedStates =
				new TreeSet<MettelTableauState>();
				//new LinkedHashSet<MettelTableauState>();
				//new TreeSet<MettelTableauState>(new MettelTableauStateComparator());
		state = new MettelGeneralTableauState(factory, calculus);
		this.strategy = strategy;
		this.acceptor = acceptor;
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelAbstractTableauManager#cleanUp()
	 *
	@Override
	void cleanUp() {
		final TreeSet<MettelTableauState> states = new TreeSet<MettelTableauState>(new MettelTableauStateComparator());
		states.addAll(unexpandedStates);
		unexpandedStates = states;
	}
*/
}
