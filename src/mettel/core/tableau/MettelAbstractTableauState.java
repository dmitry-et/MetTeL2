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
import java.util.Set;

import mettel.core.tableau.strategy.MettelPriorityRuleSelectionStrategy;
import mettel.core.util.MettelTreeSetLinkedHashMap;
/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public abstract class MettelAbstractTableauState implements MettelTableauState {

	private MettelTableauState parent = null;

	protected MettelAnnotator annotator = MettelSimpleAnnotator.ANNOTATOR;

	private static int idCounter = 0;

	private final int ID = idCounter++;

	protected MettelTableauStatePool expressions = null;

	private MettelTreeSetLinkedHashMap<MettelTableauState, MettelTableauAction, MettelTableauAction> actions =
		new MettelTreeSetLinkedHashMap<MettelTableauState, MettelTableauAction, MettelTableauAction>();

	protected MettelTableauObjectFactory factory = null;

	//private MettelReplacement replacement = null;

	private Collection<? extends MettelTableauRule> calculus = null;

	protected MettelTableauExplanation explanation = null;

	//private MettelTableauStatePool equalities = null;

	public MettelAbstractTableauState(MettelTableauObjectFactory factory, Collection<? extends MettelTableauRule> calculus) {
		super();
		this.factory = factory;
		this.calculus = calculus;
		initRuleStates(calculus);
		ruleSelectionStrategy  = new MettelPriorityRuleSelectionStrategy(ruleStates);
		expressions = new MettelTableauStatePool(/*this*/);
		expressions.init(this);
		actions.init(this);
		//replacement = factory.createReplacement();
		//equalities = new MettelTableauStatePool();
		//equalities.init(this);
	}

	/**
	 * @param set
	 */
	public MettelAbstractTableauState(MettelTableauObjectFactory factory, Collection<? extends MettelTableauRule> calculus,
			Set<MettelAnnotatedExpression> set) {
		super();
		this.factory = factory;
		this.calculus = calculus;
		initRuleStates(calculus);
		ruleSelectionStrategy  = new MettelPriorityRuleSelectionStrategy(ruleStates);
		expressions = new MettelTableauStatePool(/*this*/);
		expressions.addAll(set);
		expressions.init(this);
		actions.init(this);
		//replacement = factory.createReplacement();
		//equalities = new MettelTableauStatePool();
		//equalities.init(this);
	}

	/**
	 * @param state
	 */
	public MettelAbstractTableauState(MettelAbstractTableauState state){
		super();
		this.factory = state.factory;
		parent = state;
		this.calculus = state.calculus;
		initRuleStates(state.ruleStates);
		ruleSelectionStrategy = state.ruleSelectionStrategy.create(ruleStates);
		expressions = new MettelTableauStatePool(/*this*/);
		expressions.embed(state.expressions);// first to embed
		expressions.init(this);
		actions.embed(state.actions);
		actions.init(this);
		//replacement = factory.createReplacement();
		//replacement.append(state.replacement);
		//if(!replacement.isEmpty()){
		//	final MettelTableauRewriteAction action = new MettelTableauRewriteAction(this);
		//	action.add(this);
		//	actions.add(action);
		//}
//System.out.println("*****Replacement="+replacement);		
		//equalities = new MettelTableauStatePool();
		//equalities.embed(state.equalities);
		//equalities.init(this);
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauState#id()
	 */
	@Override
	public int id() {
		return ID;
	}

	private transient boolean satisfiable = true;

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauState#isSatisfiable()
	 */
	@Override
	public boolean isSatisfiable() {
		return satisfiable;
	}

	private transient boolean complete = false;

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauState#isComplete()
	 */
	@Override
	public boolean isComplete() {
		return complete;
	}

	protected transient MettelGeneralTableauRuleState[] ruleStates = null;

	private void initRuleStates(Collection<? extends MettelTableauRule> c){
		final int LENGTH = c.size();
		ruleStates = new MettelGeneralTableauRuleState[LENGTH];
		int i = 0;
		for(MettelTableauRule r:c){
			ruleStates[i] = new MettelGeneralTableauRuleState(this,r);
			i++;
		}
	}

	private void initRuleStates(MettelGeneralTableauRuleState[] rs){
		final int LENGTH = rs.length;
		ruleStates = new MettelGeneralTableauRuleState[LENGTH];
		for(int i = 0; i< LENGTH;i++){
			ruleStates[i] = new MettelGeneralTableauRuleState(this,rs[i]);
		}
	}

	private MettelRuleSelectionStrategy ruleSelectionStrategy = null;

	protected boolean expanded = true;

	public boolean isExpanded(){
		return expanded;
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauState#expand()
	 */
	@Override
	public Set<MettelTableauState> expand() {
		expanded = false;
//System.out.println("Expanding "+this+" which has the expression pool "+expressions);

		final MettelTableauRuleState rs = ruleSelectionStrategy.select();
		if(rs == null){
//System.out.println("None of rules are applicable: ");
			complete = true;
			return null;
		}
//System.out.println(rs);

		final Set<MettelAnnotatedExpression>[] branches = rs.apply();
		if(rs.isTerminal()){
//System.out.println("Unsatisfiable: terminal rule "+rs);
//System.out.println("Application state: "+rs.applicationState());
//+" at "+this+" with the expression pool "+expressions);
			satisfiable = false;
			explanation = new MettelSimpleTableauExplanation(this,1);
//System.out.println("*Dependencies: "+rs.dependencies());
			explanation.append(rs.dependencies());
//System.out.println("*Explanation: "+explanation);
//System.out.println(explanation.state().explanation());
//if(explanation.state().id() < rs.applicationState().id()){
//System.out.println();
//System.out.println("***Backjumping to "+explanation.state()+" from "+rs.applicationState()+" at "+this);
//System.out.println("Explanation: "+explanation.state().explanation());
//}
			actionsToAdd.add(new MettelTableauTerminateAction(explanation.state()));//rs.applicationState()));
			return null;
		}

		if(branches == null) return null; //Cannot be applied or terminal

//System.out.println(rs);

		final int BRANCHES_NUMBER = branches.length;
		final Set<MettelAnnotatedExpression> dependencies = rs.dependencies();
		if(BRANCHES_NUMBER > 1){
			actionsToAdd.add(new MettelTableauExpansionAction(rs.applicationState(),branches,dependencies));
		}

		return expand(branches,dependencies);
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauState#add(mettel.core.tableau.MettelAnnotatedExpression)
	 */
	@Override
	public boolean add(MettelAnnotatedExpression e) {
//System.out.println("Adding "+e);
//System.out.println("Replacement is "+replacement);
		final boolean result = expressions.add(e);
		if(result){
			for(MettelTableauRuleState rs:ruleStates) rs.add(e);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauState#addAll(java.util.Set)
	 */
	@Override
	public boolean addAll(Set<MettelAnnotatedExpression> s) {
		boolean result = false;
		for(MettelAnnotatedExpression e:s){
			result |= add(e);
		}
		return result;
	}

	public String toString(){
		return "TableauState#"+ID;
	}

	public int compareTo(MettelTableauState state){
		if(this == state) return 0;
		return (ID - state.id());
	}

	public boolean equals(MettelTableauState state){
		if(this == state) return true;
		if(state == null) return false;
		return (ID - state.id()) == 0;
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauState#expressions()
	 */
	@Override
	public Set<MettelAnnotatedExpression> expressions() {
		return expressions;
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauState#addToRulePools(mettel.core.tableau.MettelAnnotatedExpression)
	 */
	@Override
	public void addToRulePools(MettelAnnotatedExpression e) {
		for(MettelTableauRuleState rs:ruleStates) rs.add(e);
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauState#expand(java.util.Set<mettel.core.tableau.MettelAnnotatedExpression>[])
	 */
	@Override
	public Set<MettelTableauState> expand(Set<MettelAnnotatedExpression>[] branches, Set<MettelAnnotatedExpression> dependencies) {

		final int BRANCHES_NUMBER = branches.length;
		if(BRANCHES_NUMBER == 1){
			expanded = this.addAll(branches[0]);
		}else{
			boolean redundant = false;
			for(int i = 0; i < BRANCHES_NUMBER; i++){
				redundant |= expressions.containsAll(branches[i]);
			}
			if(!redundant){
				LinkedHashSet<MettelTableauState> children = new LinkedHashSet<MettelTableauState>(BRANCHES_NUMBER);
				for(int i = BRANCHES_NUMBER -1; i>= 0; i--){
					children.add(makeChild(branches[i]));
					//MettelAbstractTableauState ts = new MettelAbstractTableauState(this);
					//ts.addAll(annotator.reannotate(branches[i],ts));
					//children.add(ts);
//System.out.println("branches["+i+"] = "+branches[i]);
				}
				explanation = new MettelSimpleTableauExplanation(this,BRANCHES_NUMBER+1);//XXX associate branches with dependencies
				explanation.append(dependencies);

				return children;
			}
		}
		return null;
	}
	
	protected MettelTableauState makeChild(Set<MettelAnnotatedExpression> expressions){
		MettelTableauState ts = makeChildOf(this);
		ts.addAll(annotator.reannotate(expressions,ts));
		return ts;
	}
	
	abstract protected MettelTableauState makeChildOf(MettelTableauState state);

	private Set<MettelTableauAction> actionsToAdd = new LinkedHashSet<MettelTableauAction>();

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauState#actionsToAdd()
	 */
	@Override
	public Set<MettelTableauAction> actionsToAdd() {
		return actionsToAdd;
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauState#isChildOf(mettel.core.tableau.MettelTableauState)
	 */
	@Override
	public boolean isChildOf(MettelTableauState key){
		return expressions.subset(key) != null;
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauState#actions()
	 */
	@Override
	public Set<MettelTableauAction> actions() {
		return actions;
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauState#parent()
	 */
	@Override
	public MettelTableauState parent() {
		return parent;
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauState#setUnsatisfiable()
	 */
	@Override
	public void setUnsatisfiable() {
		satisfiable = false;
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauState#addLemma(java.util.Set)
	 */
	@Override
	public MettelTableauState addLemma(Set<MettelAnnotatedExpression> dependencies) {
		explanation.append(dependencies);
		final MettelTableauState state = explanation.state();
		if(state == null) return this;
		return state;
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauState#explanation()
	 */
	@Override
	public MettelTableauExplanation explanation() {
		return explanation;
	}

}
