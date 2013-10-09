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
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
abstract class MettelAbstractTableauManager implements MettelTableauManager {

	protected MettelAnnotator annotator = MettelSimpleAnnotator.ANNOTATOR;

	protected SortedSet<MettelTableauState> unexpandedStates = null;

	protected MettelBranchSelectionStrategy strategy = null;

	protected MettelTableauStateAcceptor acceptor = null;

	//protected Set<MettelGeneralTableauRule> calculus = null;

	private class MettelSatisfiableTableauStateAcceptor implements MettelTableauStateAcceptor{

		/* (non-Javadoc)
		 * @see mettel.core.tableau.MettelTableauStateAcceptor#accept(mettel.core.tableau.MettelTableauState)
		 */
		@Override
		public boolean accept(MettelTableauState state) {
			return state.isSatisfiable();
		}
	}

	/**
	 * @author Dmitry Tishkovsky
	 *
	 * Provides depth-first left-to-right strategy if the states are in the natural order.
	 */
	private class MettelSimpleBranchSelectionStrategy extends MettelSimpleLIFOBranchSelectionStrategy{}

		/* (non-Javadoc)
		 * @see mettel.core.tableau.MettelBranchChoiceStrategy#chooseTableauState(java.util.SortedSet)
		 *
		@Override
		public MettelTableauState chooseTableauState(SortedSet<MettelTableauState> s) {
			if(s.isEmpty()) return null;
			return s.last();
/*			final Iterator<MettelTableauState> i = s.iterator();
			if(i.hasNext()){
				//MettelTableauState state = i.next();
//System.out.println("Looking at state "+state);
				//if(state.isSatisfiable()) return state;
				//i.remove();
				return i.next();
//System.out.println("The state "+state+" is unsatisfiable");
			}
//System.out.println("No states to select from");
			return null;*
		}

	}*/

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauManager#isSatisfiable(mettel.core.tableau.MettelAnnotatedExpression)
	 */
	@Override
	public boolean isSatisfiable(MettelExpression e) {
		Set<MettelAnnotatedExpression> set = new LinkedHashSet<MettelAnnotatedExpression>(1);
		if(set.add(annotator.annotate(e,state))) return isSatisfiable(set);
		throw new MettelCoreRuntimeException("Failed to create a set containing "+e);
	}


	protected MettelTableauState state = null, root = null;

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauManager#isSatisfiable(java.util.Set)
	 */
	@Override
	public boolean isSatisfiable(Collection<? extends MettelExpression> input) {
		Set<MettelAnnotatedExpression> set = new LinkedHashSet<MettelAnnotatedExpression>(input.size());
		for(MettelExpression e:input){
			if(!set.add(annotator.annotate(e,state)))
				throw new MettelCoreRuntimeException("Failed to create a set containing "+e);
		}
		return isSatisfiable(set);
	}

	boolean isSatisfiable(Set<MettelAnnotatedExpression> input) {

		if(strategy == null){
			strategy = new MettelSimpleBranchSelectionStrategy();
		}

		if(acceptor == null){
			acceptor = new MettelSatisfiableTableauStateAcceptor();
		}

		if(state.addAll(input)){

			unexpandedStates.add(state);

			do{
//System.out.println("Unexpanded states:"+unexpandedStates);
//System.out.println("Expanding "+state);
//System.out.println("Expressions "+state.expressions());
				//final MettelTableauState[] children =
//System.out.println("Processing actions for "+state+"...");

				unexpandedStates.remove(state);

				Set<MettelTableauState> children = null;
				boolean actionExecuted = false;
//				final Set<MettelTableauAction> actions = state.actions();
//System.out.println("Potential actions "+actions);
//				if(actions != null){
					final Iterator<MettelTableauAction> ia = state.actions().iterator();
					while(ia.hasNext()){
						final MettelTableauAction a = ia.next();
						final boolean f = a.isFor(state);
						a.retainAll(unexpandedStates);
						if(f){
//							a.add(state);
							children = a.execute(state);
							actionExecuted = true;
							if(a.completed()){
								ia.remove();
							}
							break;
						}
						if(a.completed()){
							ia.remove();
						}
					}
//				}
//System.out.println("Done");
				if(!actionExecuted)	children = state.expand();
				addActions(state);
				if(acceptor.accept(state)){
					if(state.isComplete()) return true;

					addChildren(state,children);
					children = null;

					//	throw new MettelCoreRuntimeException("Failed to add children of "+state);

				}
//				else{
//System.out.println("Removing "+state);
//System.out.println("Satisfiable? "+state.isSatisfiable());
//System.out.println("Expressions: "+state.expressions());
//System.out.println("Explanation: "+state.explanation());
//				}
/*
					unexpandedStates.remove(state);
//					final Iterator<MettelTableauAction> ia = actions.iterator();
//					while(ia.hasNext()){
//						final MettelTableauAction a = ia.next();
//						a.remove(state);
//						if(a.completed()) ia.remove();
//					}
				}

				cleanUp();
*/

//				final Iterator<MettelTableauAction> ia = actions.iterator();
//					final MettelTableauAction a = ia.next();
//					a.retainAll(unexpandedStates);
//					if(a.completed()) ia.remove();
//				}

				state = strategy.chooseTableauState(unexpandedStates);
			}while(state != null);

			return false;
		}

		if(input == null || input.isEmpty()){
			throw new MettelCoreRuntimeException("Input set cannot be empty");
		}
		throw new MettelCoreRuntimeException("Failed to create a state containing "+input);
	}


	/**
	 *
	 */
//	abstract void cleanUp();

	/**
	 * @param s
	 */
	private void addActions(MettelTableauState s) {
		final Set<MettelTableauAction> actionsToAdd = s.actionsToAdd();
		if(actionsToAdd == null) return;
//		Set<MettelTableauAction> actions = s.actions();
//System.out.println("Adding actions for "+s+"...");
//System.out.println("Actions to add "+actionsToAdd);
		final Iterator<MettelTableauAction> ia = actionsToAdd.iterator();
		while(ia.hasNext()){
			final MettelTableauAction a = ia.next();
			for(MettelTableauState s0: unexpandedStates){
				if(s0.isChildOf(a.key())){
					a.add(s0);
				}
			}
			a.remove(s);
//System.out.println("    Adding action "+a+"...");
			s.actions().add(a);
//System.out.println("Actions for "+s+" are "+s.actions());
			ia.remove();
		}
//System.out.println("Done");
	}




/*	boolean remove(MettelTableauState state){
		if(unexpandedStates.remove(state)) return true;

		final Set<MettelTableauState> children = state.children();
		if(children == null) return false;

		boolean result = false;
		for(MettelTableauState child:children){
			result |= remove(child);
		}
		return result;
	}
*/
	void addChildren(MettelTableauState state, Set<MettelTableauState> children){
//System.out.println("Unexpanded states:"+unexpandedStates);
		//final Set<MettelTableauState> children = state.children();
//System.out.println("Children:"+children);
		if(children == null){
			//unexpandedStates.add(state);
			add(state);
			return;
		}
		//Expanded succesfully (not terminal and rule was applicable)
//System.out.println("Removing state "+state);
		for(MettelTableauState child:children){
			//unexpandedStates.add(child);
			add(child);
		}

		final Set<MettelTableauAction> actions = state.actions();
		if(actions != null){
			final Iterator<MettelTableauAction> ia = actions.iterator();
			while(ia.hasNext()){
				final MettelTableauAction a = ia.next();
				if(a.isFor(state)){
					a.remove(state);
					a.addAll(children);
				};
			}
		}
	}

	abstract protected boolean add(MettelTableauState state);

//	public abstract boolean isAcceptable(MettelTableauState s);

	public Set<MettelExpression> model(){
		if(state == null) return null;
		final LinkedHashSet<MettelExpression> result = new LinkedHashSet<MettelExpression>();
		final Set<MettelAnnotatedExpression> expressions = state.expressions();
		for(MettelAnnotatedExpression ae:expressions){
			result.add(ae.expression());
		}
		return result;
	}

	public Set<MettelExpression> contradiction(){
		if(root == null) return null;
		final MettelTableauExplanation explanation = root.explanation();
		if(explanation == null) return null;
		final Set<MettelAnnotatedExpression> lemma = explanation.lemma();
		if(lemma == null) return null;
		final LinkedHashSet<MettelExpression> result = new LinkedHashSet<MettelExpression>();
		for(MettelAnnotatedExpression ae:lemma){
			result.add(ae.expression());
		}
		return result;
	}

}
