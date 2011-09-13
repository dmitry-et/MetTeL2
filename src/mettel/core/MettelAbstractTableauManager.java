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
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelAbstractTableauManager implements MettelTableauManager {

	protected MettelAnnotator annotator = MettelSimpleAnnotator.ANNOTATOR;

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
			while(i.hasNext()){
				MettelTableauState state = i.next();
				if(state.isSatisfiable()) return state;
				i.remove();
			}
			return null;
		}

	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauManager#isSatisfiable(mettel.core.MettelAnnotatedExpression)
	 */
	@Override
	public boolean isSatisfiable(MettelExpression e) {
		Set<MettelAnnotatedExpression> set = new LinkedHashSet<MettelAnnotatedExpression>(1);
		if(set.add(annotator.annotate(e,state))) return isSatisfiable(set);
		throw new MettelCoreRuntimeException("Failed to create a set containing "+e);
	}


	protected MettelTableauState state = null;

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauManager#isSatisfiable(java.util.Set)
	 */
	@Override
	public boolean isSatisfiable(Collection<? extends MettelExpression> input) {
		Set<MettelAnnotatedExpression> set = new LinkedHashSet<MettelAnnotatedExpression>(1);
		for(MettelExpression e:input){
			if(!set.add(annotator.annotate(e,state)))
				throw new MettelCoreRuntimeException("Failed to create a set containing "+e);
		}
		return isSatisfiable(set);
	}

	boolean isSatisfiable(Set<MettelAnnotatedExpression> input) {

		if(strategy == null){
			strategy = new MettelSimpleBranchChoiceStrategy();
		}

		if(state.addAll(input)){

			unexpandedStates.add(state);

			do{
//System.out.println("Expanding "+state);
				//final MettelTableauState[] children =
				state.expand();
				if(state.isSatisfiable()){
					if(state.isComplete()) return true;

					addChildren(state);
					//	throw new MettelCoreRuntimeException("Failed to add children of "+state);

				}else{
//System.out.println("Removing "+state);
					unexpandedStates.remove(state);
				}
				state = strategy.chooseTableauState(unexpandedStates);
			}while(state != null);

			return false;
		}

		throw new MettelCoreRuntimeException("Failed to create a state containing "+input);
	}


	boolean remove(MettelTableauState state){
		if(unexpandedStates.remove(state)) return true;

		final Set<MettelTableauState> children = state.children();
		if(children == null) return false;

		boolean result = false;
		for(MettelTableauState child:children){
			result |= remove(child);
		}
		return result;
	}

	void addChildren(MettelTableauState state){
		final Set<MettelTableauState> children = state.children();
		if(children == null) return;
		//Expanded succesfully (not terminal and rule was applicable)
		unexpandedStates.remove(state);
		for(MettelTableauState child:children){
			unexpandedStates.add(child);
		}
	}
}
