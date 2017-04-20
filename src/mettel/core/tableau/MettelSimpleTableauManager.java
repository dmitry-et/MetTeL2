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

import java.util.Collection;
import java.util.Iterator;
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
			MettelBranchSelectionStrategy strategy) {
		this(factory,calculus,strategy,null);
	}
	
	public MettelSimpleTableauManager(MettelTableauObjectFactory factory, Collection<? extends MettelTableauRule> calculus,
					MettelBranchSelectionStrategy strategy, MettelTableauStateAcceptor acceptor) {
		unexpandedStates =
				new TreeSet<MettelTableauState>();
				//new LinkedHashSet<MettelTableauState>();
				//new TreeSet<MettelTableauState>(comparator);
				//new TreeSet<MettelTableauState>(new MettelTableauStateReverseNaturalComparator());
		root = state = new MettelGeneralTableauStateWithRewriting(factory, calculus);//TODO: Good possibility to implement AND-OR graph
		this.strategy = strategy;
		this.acceptor = acceptor;
	}

	private static final MettelTableauStateComparator comparator = new MettelTableauStateComparator();

	protected boolean add(MettelTableauState state){
		if(state.isExpanded()){
			final Iterator<MettelTableauState> i = unexpandedStates.iterator();
			while(i.hasNext()){
				final MettelTableauState s = i.next();
//				if(s.expressions().equals(state.expressions())) {
				if(comparator.compare(state, s) == 0){
					if(state.id() < s.id()){
						i.remove();//unexpandedStates.remove(s);
						unexpandedStates.add(state);
						return true;
					}
					return false;
				}
			}
		}

		return unexpandedStates.add(state);
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelAbstractTableauManager#cleanUp()
	 *
	@Override
	void cleanUp() {
		final TreeSet<MettelTableauState> states = new TreeSet<MettelTableauState>(new MettelTableauStateComparator());
		states.addAll(unexpandedStates);
		unexpandedStates = states;
	}
*/
}
