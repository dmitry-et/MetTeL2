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
import mettel.core.strategy.MettelPriorityRuleSelectionStrategy;
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

	private MettelTableauExplanation explanation = null;

	private MettelTableauStatePool equalities = null;

	public MettelGeneralTableauState(MettelTableauObjectFactory factory, Collection<? extends MettelTableauRule> calculus) {
		super();
		this.factory = factory;
//		ruleSelectionStrategy  = new MettelSimpleRuleSelectionStrategy();
								 //new MettelSimpleRandomRuleSelectionStrategy();
		this.calculus = calculus;
		initRuleStates(calculus);
		ruleSelectionStrategy  = new MettelPriorityRuleSelectionStrategy(ruleStates);
		expressions = new MettelTableauStatePool(/*this*/);
		expressions.init(this);
		actions.init(this);
		replacement = factory.createReplacement();
		equalities = new MettelTableauStatePool();
		equalities.init(this);
	}

	/**
	 * @param set
	 */
	public MettelGeneralTableauState(MettelTableauObjectFactory factory, Collection<? extends MettelTableauRule> calculus,
			Set<MettelAnnotatedExpression> set) {
		super();
		this.factory = factory;
								 //new MettelSimpleRandomRuleSelectionStrategy();
		this.calculus = calculus;
		initRuleStates(calculus);
		ruleSelectionStrategy  = new MettelPriorityRuleSelectionStrategy(ruleStates);
		expressions = new MettelTableauStatePool(/*this*/);
		expressions.addAll(set);
		expressions.init(this);
		actions.init(this);
		replacement = factory.createReplacement();
		equalities = new MettelTableauStatePool();
		equalities.init(this);
	}

	/**
	 * @param set
	 * @param strategy
	 *
	public MettelGeneralTableauState(MettelTableauObjectFactory factory,
			Collection<? extends MettelTableauRule> calculus,
			MettelRuleSelectionStrategy strategy) {
		super();
		this.factory = factory;
		ruleSelectionStrategy  = strategy;
		this.calculus = calculus;
		initRuleStates(calculus);
		expressions = new MettelTableauStatePool();
		expressions.init(this);;
		actions.init(this);
		replacement = factory.createReplacement();
	}*/

	/**
	 * @param set
	 * @param strategy
	 *
	public MettelGeneralTableauState(MettelTableauObjectFactory factory,
			Collection<? extends MettelTableauRule> calculus,
			Set<MettelAnnotatedExpression> set,
			MettelRuleSelectionStrategy strategy) {
		super();
		this.factory = factory;
		ruleSelectionStrategy  = strategy;
		this.calculus = calculus;
		initRuleStates(calculus);
		expressions = new MettelTableauStatePool();
		expressions.addAll(set);
		expressions.init(this);
		actions.init(this);
		replacement = factory.createReplacement();
	}*/

	/**
	 * @param state
	 */
	public MettelGeneralTableauState(MettelGeneralTableauState state){
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
		replacement = factory.createReplacement();
		replacement.append(state.replacement);
		if(!replacement.isEmpty()){
			final MettelTableauRewriteAction action = new MettelTableauRewriteAction(this);
			action.add(this);
			actions.add(action);
		}
		equalities = new MettelTableauStatePool();
		equalities.embed(state.equalities);
		equalities.init(this);
		//state.replacement = null;//Forget about old replacements
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
		for(int i = 0; i< LENGTH;i++){
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
			//rs.dependencies().addAll(equalities);
			explanation.append(rs.dependencies());
//			if(!replacement.isEmpty()) explanation.rewrite(annotator, this, replacement);
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
	 * @see mettel.core.MettelTableauState#add(mettel.core.MettelAnnotatedExpression)
	 */
	@Override
	public boolean add(MettelAnnotatedExpression e) {
//System.out.println("Adding "+e);
//System.out.println("Replacement is "+replacement);
		final MettelExpression exp = e.expression();
		final MettelExpression exp0 = replacement.rewrite(exp);//XXX: Does not work as expected, needs fix!
		if(exp0 != exp){
			e = annotator.annotate(exp0, this);//TODO: Needs specific annotations to be effective
		}
		final boolean result = expressions.add(e);
		if(result){
			for(MettelTableauRuleState rs:ruleStates) rs.add(e);
			if(exp instanceof MettelEqualityExpression){
				equalities.add(e);
				final MettelEqualityExpression eq = (MettelEqualityExpression)exp;
				if(replacement.append(eq.left(), eq.right())){
					//final MettelTableauRewriteAction action = new MettelTableauRewriteAction(this);
//					action.add(e.key());
					//action.add(this);
					//actions.add(action);
					this.rewrite();//Immediate rewrite is faster on the specified examples
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
	public Set<MettelTableauState> expand(Set<MettelAnnotatedExpression>[] branches, Set<MettelAnnotatedExpression> dependencies) {
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
				for(int i = BRANCHES_NUMBER -1; i>= 0; i--){
					MettelGeneralTableauState ts = new MettelGeneralTableauState(this);
					ts.addAll(annotator.reannotate(branches[i],ts));
					//result[i] = ts;
					children.add(ts);
//System.out.println("branches["+i+"] = "+branches[i]);
				}
				//for(MettelTableauRuleState r:ruleStates) r.setApplicable(true);
				explanation = new MettelSimpleTableauExplanation(this,BRANCHES_NUMBER+1);//XXX associate branches with dependencies
				explanation.append(dependencies);

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
		final MettelTableauStatePool pool = new MettelTableauStatePool();
		pool.init(this);
		expanded = false;
//		final LinkedHashSet<MettelAnnotatedExpression> rewritten = new LinkedHashSet<MettelAnnotatedExpression>();
		for(MettelAnnotatedExpression ae:expressions){
			final MettelExpression e0 = ae.expression();
			final MettelExpression e1 = replacement.rewrite(e0);
			if(e0 == e1){//Only single instance of expression exists!
				pool.add(ae);
			}else{
				expanded = true;
//				final MettelTableauState aeKey = ae.key();
//				final MettelTableauState max = ID > aeKey.id()? this:aeKey;
				final MettelAnnotatedExpression ae1 = annotator.annotate(e1,this/*max*/);
				if(!expressions.contains(ae1)){
					ae1.annotation().dependencies().addAll(equalities);
					pool.add(ae1);
//					rewritten.add(ae1);
				}
			}
		}
		if(expanded){
			expressions = pool;
			//expressions.addAll(rewritten);
			for(MettelGeneralTableauRuleState rs:ruleStates){
				rs.rewrite(this, replacement);
/*				if(rs.TERMINAL){
					rs.addAll(rewritten);
//System.out.println("Rule state is applicable: "+rs.isApplicable());
				}
*/
//System.out.println("Rule state: "+rs);
			}
//			if(explanation != null){
//				explanation.rewrite(annotator, this, replacement);
//			}
		}
		/*else{
			expressions = pool;
		}*/
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
		return expressions.subset(key) != null;
/*
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
*/
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

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauState#addLemma(java.util.Set)
	 */
	@Override
	public MettelTableauState addLemma(Set<MettelAnnotatedExpression> dependencies) {
		explanation.append(dependencies);
		final MettelTableauState state = explanation.state();
		if(state == null) return this;
		return state;
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauState#explanation()
	 */
	@Override
	public MettelTableauExplanation explanation() {
		return explanation;
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauState#equalities()
	 */
	@Override
	public Set<MettelAnnotatedExpression> equalities() {
		return equalities;
	}
}
