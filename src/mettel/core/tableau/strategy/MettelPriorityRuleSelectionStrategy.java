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
package mettel.core.tableau.strategy;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeMap;

import mettel.core.tableau.MettelRuleSelectionStrategy;
import mettel.core.tableau.MettelTableauRuleState;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelPriorityRuleSelectionStrategy implements
		MettelRuleSelectionStrategy {

	private TreeMap<Integer, LinkedHashSet<MettelTableauRuleState>> states = new TreeMap<Integer,LinkedHashSet<MettelTableauRuleState>>();

	private TreeMap<Integer, Iterator<MettelTableauRuleState>> iterators = new TreeMap<Integer,Iterator<MettelTableauRuleState>>();

	private final Set<Integer> keys;

	private Integer pointer = null;

	public MettelPriorityRuleSelectionStrategy(MettelTableauRuleState[] ruleStates){
		super();

		for(MettelTableauRuleState rs:ruleStates){
			final Integer key = new Integer(rs.priority());
			LinkedHashSet<MettelTableauRuleState> statesForKey = states.get(key);
			if(statesForKey == null){
				statesForKey = new LinkedHashSet<MettelTableauRuleState>();
				states.put(key, statesForKey);
			}
			statesForKey.add(rs);
		}

		keys = states.keySet();

		for(Integer key:keys){
			iterators.put(key,states.get(key).iterator());
		}

		pointer = states.firstKey();
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(MettelTableauRuleState o1, MettelTableauRuleState o2) {
		return 0;
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelRuleSelectionStrategy#select()
	 */
	@Override
	public MettelTableauRuleState select() {

		do{
			Iterator<MettelTableauRuleState> iterator = iterators.get(pointer);
			MettelTableauRuleState state = null;

			final LinkedHashSet<MettelTableauRuleState> set = states.get(pointer);
			final int SIZE = set.size();
			int  i = 0;

			while(iterator.hasNext()){
				i++;
				state = iterator.next();
				if(state.isApplicable()){
					pointer = states.firstKey();
					return state;
				}
			}

			iterator = set.iterator();
			iterators.put(pointer, iterator);
			while(iterator.hasNext() && i <= SIZE){
				i++;
				state = iterator.next();
				if(state.isApplicable()){
					pointer = states.firstKey();
					return state;
				}
			}

			pointer = states.higherKey(pointer);

		}while(pointer != null);

		return null;
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelRuleSelectionStrategy#create(mettel.core.tableau.MettelTableauRuleState[])
	 */
	@Override
	public MettelRuleSelectionStrategy create(
			MettelTableauRuleState[] ruleStates) {
		return new MettelPriorityRuleSelectionStrategy(ruleStates);
	}

}
