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
import java.util.LinkedHashSet;
import java.util.Set;

import mettel.core.util.MettelUncheckedLinkedList;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
abstract class MettelAbstractTableauManager implements MettelTableauManager {

	private final static MettelAnnotator annotator = MettelSimpleAnnotator.ANNOTATOR;

	protected final MettelUncheckedLinkedList<MettelTableauState> unexpandedStates = new MettelUncheckedLinkedList<>();

	private final MettelBranchSelectionStrategy strategy;

	private final MettelTableauStateAcceptor acceptor;
	
	private MettelTableauState state;
	private MettelUncheckedLinkedList.Node<MettelTableauState> node = null;
	private final MettelTableauState root;

	protected MettelAbstractTableauManager(
			MettelTableauState root,
			MettelBranchSelectionStrategy strategy, 
			MettelTableauStateAcceptor acceptor) {
		this.root = this.state = root;
		this.strategy = strategy == null? new MettelSimpleLIFOBranchSelectionStrategy(): strategy;
		this.acceptor = acceptor == null? new MettelSatisfiableTableauStateAcceptor(): acceptor;
	}

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
	 * Provides depth-first left-to-right strategy if the states are in the natural order.
	 */
	//private class MettelSimpleBranchSelectionStrategy extends MettelSimpleLIFOBranchSelectionStrategy{}


	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauManager#isSatisfiable(mettel.core.tableau.MettelAnnotatedExpression)
	 */
	@Override
	public boolean isSatisfiable(MettelExpression e) {
		Set<MettelAnnotatedExpression> set = new LinkedHashSet<MettelAnnotatedExpression>(1);
		if(set.add(annotator.annotate(e,state))) return isSatisfiable(set);
		throw new MettelCoreRuntimeException("Failed to create a set containing "+e);
	}


	

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauManager#isSatisfiable(java.util.Set)
	 */
	@Override
	public boolean isSatisfiable(Collection<? extends MettelExpression> input) {
		if(input == null) return true;
		final int SIZE = input.size();
		if(SIZE == 0) return true;
		final Set<MettelAnnotatedExpression> set = new LinkedHashSet<MettelAnnotatedExpression>(SIZE);
		for(MettelExpression e:input){
			if(!set.add(annotator.annotate(e,state)))
				throw new MettelCoreRuntimeException("Failed to create a set containing "+e);
		}
		return isSatisfiable(set);
	}

	boolean isSatisfiable(Set<MettelAnnotatedExpression> input) {		

		if(state.addAll(input)){

			unexpandedStates.clear();
			unexpandedStates.add(root);
			node = unexpandedStates.firstNode();

			do{
//System.out.println("Unexpanded states:"+unexpandedStates);
//System.out.println("Expanding "+state);
//System.out.println("Expressions "+state.expressions());
//System.out.println("Processing actions for "+state+"...");
				state = node.item();
				unexpandedStates.unlink(node);

				Set<MettelTableauState> children = null;
				boolean actionExecuted = false;
				final Set<MettelTableauAction> actions = state.actions();
//System.out.println("Potential actions "+actions);
				if(actions != null){
					final Iterator<MettelTableauAction> ia = actions.iterator();
					while(ia.hasNext()){
						final MettelTableauAction a = ia.next();
						final boolean f = a.isFor(state);
						a.retainAll(unexpandedStates);
						if(f){
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
				}
//System.out.println("Done");
				if(!actionExecuted)	children = state.expand();
				addActions(state);
				if(acceptor.accept(state)){
					if(state.isComplete()) return true;

					addChildren(state, children);
					//children = null;

				}
//System.out.println("Removing "+state);
//System.out.println("Satisfiable? "+state.isSatisfiable());
//System.out.println("Expressions: "+state.expressions());
//System.out.println("Explanation: "+state.explanation());
				node = strategy.selectTableauStateNode(unexpandedStates);
			}while(node != null);

			return false;
		}
		
		throw new MettelCoreRuntimeException("Failed to create a state containing "+input);
	}

	/**
	 * @param s
	 */
	private void addActions(MettelTableauState s) {
		final Set<MettelTableauAction> actionsToAdd = s.actionsToAdd();
		if(actionsToAdd == null) return;
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

	private void addChildren(MettelTableauState state, Set<MettelTableauState> children){
//System.out.println("Unexpanded states:"+unexpandedStates);
//System.out.println("Children:"+children);
		if(children == null){
			add(state);
			return;
		}
		//Expanded succesfully (not terminal and rule was applicable)
//System.out.println("Removing state "+state);
		for(MettelTableauState child:children){
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