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
import java.util.Set;

import mettel.util.MettelTreeSetLinkedHashMap;
/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelGeneralTableauState implements MettelTableauState {

	//private MettelTableauManager manager = null;

	private MettelTableauState parent = null;

//	private LinkedHashSet<MettelTableauState> children = null;

	private MettelAnnotator annotator = MettelSimpleAnnotator.ANNOTATOR;

	private static int idCounter = 0;

	private final int ID = idCounter++;

	private MettelTableauStatePool expressions = null;
//		new MettelTableauTreeSetLinkedHashMap<MettelTableauState, MettelAnnotatedExpression>();

	private MettelTreeSetLinkedHashMap<MettelTableauState, MettelTableauAction> actions =
		new MettelTreeSetLinkedHashMap<MettelTableauState, MettelTableauAction>();

	private MettelTableauObjectFactory factory = null;

	private MettelReplacement replacement = null;

	private Collection<? extends MettelTableauRule> calculus = null;

	public MettelGeneralTableauState(MettelTableauObjectFactory factory, Collection<? extends MettelTableauRule> calculus) {
		super();
		this.factory = factory;
		ruleSelectionStrategy  = new MettelSimpleRuleSelectionStrategy();
		this.calculus = calculus;
		initRuleStates(calculus);
		expressions = new MettelTableauStatePool(/*this*/);
		expressions.init(this);
		actions.init(this);
		replacement = factory.createReplacement();
	}

	/**
	 * @param set
	 */
	public MettelGeneralTableauState(MettelTableauObjectFactory factory, Collection<? extends MettelTableauRule> calculus,
			Set<MettelAnnotatedExpression> set) {
		super();
		this.factory = factory;
		ruleSelectionStrategy  = new MettelSimpleRuleSelectionStrategy();
		this.calculus = calculus;
		initRuleStates(calculus);
		expressions = new MettelTableauStatePool(/*this*/);
		expressions.addAll(set);
		expressions.init(this);
		actions.init(this);
		replacement = factory.createReplacement();
	}

	/**
	 * @param set
	 * @param strategy
	 */
	public MettelGeneralTableauState(MettelTableauObjectFactory factory,
			Collection<? extends MettelTableauRule> calculus,
			MettelRuleSelectionStrategy strategy) {
		super();
		this.factory = factory;
		ruleSelectionStrategy  = strategy;
		this.calculus = calculus;
		initRuleStates(calculus);
		expressions = new MettelTableauStatePool(/*this*/);
		expressions.init(this);;
		actions.init(this);
		replacement = factory.createReplacement();
	}

	/**
	 * @param set
	 * @param strategy
	 */
	public MettelGeneralTableauState(MettelTableauObjectFactory factory,
			Collection<? extends MettelTableauRule> calculus,
			Set<MettelAnnotatedExpression> set,
			MettelRuleSelectionStrategy strategy) {
		super();
		this.factory = factory;
		ruleSelectionStrategy  = strategy;
		this.calculus = calculus;
		initRuleStates(calculus);
		expressions = new MettelTableauStatePool(/*this*/);
		expressions.addAll(set);
		expressions.init(this);
		actions.init(this);
		replacement = factory.createReplacement();
	}

	/**
	 * @param state
	 */
	public MettelGeneralTableauState(MettelGeneralTableauState state){
		super();
		this.factory = state.factory;
		parent = state;
		ruleSelectionStrategy = state.ruleSelectionStrategy;
		this.calculus = state.calculus;
		initRuleStates(state.ruleStates);
		expressions = new MettelTableauStatePool(/*this*/);
		expressions.embed(state.expressions);// first to embed
		expressions.init(this);
		actions.embed(state.actions);
		actions.init(this);
		replacement = factory.createReplacement();
		replacement.append(state.replacement);
		state.replacement = null;//Forget about old replacements
	}

	/**
	 * @param state
	 * @param set
	 *
	public MettelGeneralTableauState(
			MettelGeneralTableauState state,
			Set<MettelAnnotatedExpression> set) {
		this(state,set,state.ruleSelectionStrategy);
	}

	/**
	 * @param state
	 * @param set
	 * @param strategy
	 *
	public MettelGeneralTableauState(
			MettelGeneralTableauState state,
			Set<MettelAnnotatedExpression> set,
			MettelRuleSelectionStrategy strategy) {
		super();
		this.factory = state.factory;
		parent = state;
		ruleSelectionStrategy  = strategy;
		this.calculus = state.calculus;
		initRuleStates(state.ruleStates);
		expressions = new MettelTableauStatePool();
		expressions.embed(state.expressions);// first to embed
		expressions.addAll(set);
		expressions.init(this);
		actions.embed(state.actions);
		actions.init(this);
		replacement = factory.createReplacement();
		replacement.append(state.replacement);
		state.replacement = null;//Forget about old replacements
	}*/

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauState#id()
	 */
	@Override
	public int id() {
		return ID;
	}

	private transient boolean satisfiable = true;

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauState#isSatisfiable()
	 */
	@Override
	public boolean isSatisfiable() {
		return satisfiable;
	}

	private transient boolean complete = false;

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauState#isComplete()
	 */
	@Override
	public boolean isComplete() {
		return complete;
	}

	private final class MettelSimpleRuleSelectionStrategy implements MettelRuleSelectionStrategy{

		private int index = -1;

		/* (non-Javadoc)
		 * @see mettel.core.MettelRuleChoiceStrategy#select(mettel.core.MettelGeneralTableauRuleState[])
		 */
		@Override
		public final MettelTableauRuleState select(
				MettelTableauRuleState[] ruleStates) {
			final int LENGTH = ruleStates.length;
			index++;
			if(index >= LENGTH) index = 0;
			int i = index;
			do{
				final MettelTableauRuleState rs = ruleStates[i];
				if(rs.isApplicable()){
					index = i;
					return rs;
				}
				i++;
				if(i >= LENGTH) i = 0;
			}while(i != index);
			return null;
		}

	}


	private transient MettelGeneralTableauRuleState[] ruleStates = null;

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
		for(int i = 0; i< LENGTH; i++){
			ruleStates[i] = new MettelGeneralTableauRuleState(this,rs[i]);
		}
	}

	private MettelRuleSelectionStrategy ruleSelectionStrategy = null;

	private boolean expanded = true;

	public boolean isExpanded(){
		return expanded;
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauState#expand()
	 */
	@Override
	public Set<MettelTableauState> expand() {
		expanded = false;
//System.out.println("Expanding "+this+" which has the expression pool "+expressions);

/*		Iterator<MettelTableauAction> i = actions.iterator();//TODO replace for an action selection strategy
		while(i.hasNext()){
			MettelTableauAction a = i.next();
			if(a.isFor(this)){
				Set<MettelTableauState> children = a.execute(this);
				if(children != null) a.addAll(children);
				return children;
			}
		}//xXXX: Infinite loop
*/

		final MettelTableauRuleState rs = ruleSelectionStrategy.select(ruleStates);
		if(rs == null){
//System.out.println("None of rules are applicable");
			complete = true;
			return null;
		}
//System.out.println(rs);


		final Set<MettelAnnotatedExpression>[] branches = rs.apply();
		if(rs.isTerminal()){
//System.out.println("Unsatisfiable: terminal rule at "+this+"with the expression pool "+expressions);
			satisfiable = false;
			actionsToAdd.add(new MettelTableauTerminateAction(rs.applicationState()));
			return null;
		}

		if(branches == null) return null; //Cannot be applied or terminal

		final int BRANCHES_NUMBER = branches.length;
		if(BRANCHES_NUMBER > 1){
			actionsToAdd.add(new MettelTableauExpansionAction(rs.applicationState(),branches));
		}

		return expand(branches);
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauState#add(mettel.core.MettelAnnotatedExpression)
	 */
	@Override
	public boolean add(MettelAnnotatedExpression e) {
//System.out.println("Adding "+e);
		final boolean result = expressions.add(e);
		if(result){
			for(MettelTableauRuleState rs:ruleStates) rs.add(e);
			final MettelExpression exp = e.expression();
			if(exp instanceof MettelEqualityExpression){
				final MettelEqualityExpression eq = (MettelEqualityExpression)exp;
				if(replacement.append(eq.left(), eq.right())){
					final MettelTableauRewriteAction action = new MettelTableauRewriteAction(this);
					action.add(this);
					actions.add(action);
//System.out.println("Rewriting action added");
				}
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauState#addAll(java.util.Set)
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
	 * @see mettel.core.MettelTableauState#children()
	 *
	@Override
	public Set<MettelTableauState> children() {
		return children;
	}*/

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauState#expressions()
	 */
	@Override
	public Set<MettelAnnotatedExpression> expressions() {
		return expressions;
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauState#addToRulePools(mettel.core.MettelAnnotatedExpression)
	 */
	@Override
	public void addToRulePools(MettelAnnotatedExpression e) {
		for(MettelTableauRuleState rs:ruleStates) rs.add(e);
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauState#setSatisfiable(boolean)
	 *
	@Override
	public void setSatisfiable(boolean v) {
		this.satisfiable = v;

	}*/

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauState#expand(java.util.Set<mettel.core.MettelAnnotatedExpression>[])
	 */
	@Override
	public Set<MettelTableauState> expand(Set<MettelAnnotatedExpression>[] branches) {
//		if(branches == null) return null; //Cannot be applied or terminal

		final int BRANCHES_NUMBER = branches.length;
		//MettelTableauState[] result = new MettelGeneralTableauState[BRANCHES_NUMBER];
		if(BRANCHES_NUMBER == 1){
			expanded = this.addAll(branches[0]);
			//result[0] = this;
		}else{
			boolean redundant = false;
			for(int i = 0; i < BRANCHES_NUMBER; i++){
				redundant |= expressions.containsAll(branches[i]);
			}
			if(!redundant){
				LinkedHashSet<MettelTableauState> children = new LinkedHashSet<MettelTableauState>(BRANCHES_NUMBER);
				for(int i = 0; i < BRANCHES_NUMBER; i++){
					MettelGeneralTableauState ts = new MettelGeneralTableauState(this);
					ts.addAll(annotator.reannotate(branches[i],ts));
					//result[i] = ts;
					children.add(ts);
//System.out.println("branches["+i+"] = "+branches[i]);
				}
				//for(MettelTableauRuleState r:ruleStates) r.setApplicable(true);
				return children;
			}
		}
//		return result;
		return null;
	}

	public Set<MettelTableauState> rewrite(){
//System.out.println("Rewrite action execution");
		if(replacement.isEmpty()) return null;
//System.out.println("Rewriting: "+replacement);
		final MettelTableauStatePool pool = expressions;
		expressions = new MettelTableauStatePool();
		expressions.init(this);
		for(MettelAnnotatedExpression ae:pool){
			expressions.add(annotator.annotate(replacement.rewrite(ae.expression()),this));
		}
		final int LENGTH = ruleStates.length;
		for(int i=0;i<LENGTH;i++){
			ruleStates[i].rewrite(this, replacement);
		}
		return null;
/*
		MettelGeneralTableauState ts = new MettelGeneralTableauState(this.factory,this.calculus,this.ruleSelectionStrategy);
		//ts.initRuleStates(this.ruleStates);
		for(MettelAnnotatedExpression ae:expressions){
			ts.add(annotator.annotate(replacement.rewrite(ae.expression()),ts));
		}
		final LinkedHashSet<MettelTableauState> children = new LinkedHashSet<MettelTableauState>(1);
		children.add(ts);
		return children;
*/	}

	private Set<MettelTableauAction> actionsToAdd = new LinkedHashSet<MettelTableauAction>();

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauState#actionsToAdd()
	 */
	@Override
	public Set<MettelTableauAction> actionsToAdd() {
		return actionsToAdd;
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauState#isChildOf(mettel.core.MettelTableauState)
	 */
	@Override
	public boolean isChildOf(MettelTableauState key){
//System.out.print("isChildOf for "+key+"...");
		if(key == null) return false;
		//MettelTableauState p = parent;
		MettelTableauState current = this;
		while(current != null && current.id() >= key.id()){
			if(current.equals(key)){
//System.out.println("Done(true)");
				return true;
			}
			current = current.parent();
		}
//System.out.println("Done(false)");
		return false;
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauState#actions()
	 */
	@Override
	public Set<MettelTableauAction> actions() {
		return actions;
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauState#parent()
	 */
	@Override
	public MettelTableauState parent() {
		return parent;
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauState#setUnsatisfiable()
	 */
	@Override
	public void setUnsatisfiable() {
		satisfiable = false;
	}
}
