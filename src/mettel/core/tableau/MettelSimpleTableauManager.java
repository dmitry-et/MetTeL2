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

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelSimpleTableauManager extends MettelAbstractTableauManager {
	
	private static final MettelTableauStateComparator comparator = new MettelTableauStateComparator();

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
		super(new MettelGeneralTableauStateWithRewriting(factory, calculus), strategy, acceptor);//TODO: Good possibility to implement AND-OR graph
		//unexpandedStates =
		//		new TreeSet<MettelTableauState>();
				//new LinkedHashSet<MettelTableauState>();
				//new TreeSet<MettelTableauState>(comparator);
				//new TreeSet<MettelTableauState>(new MettelTableauStateReverseNaturalComparator());
	}

	protected boolean add(MettelTableauState state){
		if(state.isExpanded()){
			final Iterator<MettelTableauState> i = unexpandedStates.iterator();
			while(i.hasNext()){
				final MettelTableauState s = i.next();
				if(comparator.compare(state, s) == 0){
					if(state.id() < s.id()) {//state.compareTo(s) < 0){
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

}
