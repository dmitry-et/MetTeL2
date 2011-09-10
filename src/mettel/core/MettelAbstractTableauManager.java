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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelAbstractTableauManager implements MettelTableauManager {

	protected Set<MettelTableauState> unexpandedStates = null;

	protected MettelBranchChoiceStrategy strategy = null;

	//protected Set<MettelGeneralTableauRule> calculus = null;

	private class MettelSimpleBranchChoiceStrategy implements MettelBranchChoiceStrategy{

		/* (non-Javadoc)
		 * @see mettel.core.MettelBranchChoiceStrategy#chooseTableauState(java.util.Set)
		 */
		@Override
		public MettelTableauState chooseTableauState(Set<MettelTableauState> s) {
			Iterator<MettelTableauState> i = s.iterator();
			return i.hasNext()? i.next() :null;
		}

	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauManager#isSatisfiable(mettel.core.MettelAnnotatedExpression)
	 */
	@Override
	public boolean isSatisfiable(MettelAnnotatedExpression e) {
		Set<MettelAnnotatedExpression> set = new HashSet<MettelAnnotatedExpression>(1);
		if(set.add(e)) return isSatisfiable(set);
		throw new MettelCoreRuntimeException("Failed to create a set containing "+e);
	}


	protected MettelTableauState state = null;

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauManager#isSatisfiable(java.util.Set)
	 */
	@Override
	public boolean isSatisfiable(Set<MettelAnnotatedExpression> input) {

		if(strategy == null){
			strategy = new MettelSimpleBranchChoiceStrategy();
		}

		if(state.addAll(input)){

			do{
				final MettelTableauState[] children = state.expand();
				if(state.isSatisfiable()){
					if(state.isComplete()) return true;
					final int LENGTH = children.length - 1;
					if(LENGTH > 0){
						unexpandedStates.remove(state);
						for(int i = LENGTH; i >= 0; i--){
							unexpandedStates.add(children[i]);
						}
					}
				}else{
					unexpandedStates.remove(state);
				}
				state = strategy.chooseTableauState(unexpandedStates);
			}while(state != null);

			return false;
		}

		throw new MettelCoreRuntimeException("Failed to create a state containing "+input);
	}

}
