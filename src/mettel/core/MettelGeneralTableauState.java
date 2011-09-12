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
import java.util.Set;

import mettel.util.MettelTreeSetLinkedHashMap;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelGeneralTableauState implements MettelTableauState {

	private MettelAnnotator annotator = MettelSimpleAnnotator.ANNOTATOR;

	private static int idCounter = 0;

	private final int ID = idCounter++;

	private final MettelTreeSetLinkedHashMap<MettelTableauState, MettelAnnotatedExpression> expressions =
		new MettelTreeSetLinkedHashMap<MettelTableauState, MettelAnnotatedExpression>();

	public MettelGeneralTableauState(Collection<? extends MettelTableauRule> calculus) {
		super();
		ruleChoiceStrategy  = new MettelSimpleRuleChoiceStrategy();
		initRuleStates(calculus);
	}

	/**
	 * @param set
	 */
	public MettelGeneralTableauState(Collection<? extends MettelTableauRule> calculus,
			Set<MettelAnnotatedExpression> set) {
		super();
		ruleChoiceStrategy  = new MettelSimpleRuleChoiceStrategy();
		initRuleStates(calculus);
		expressions.addAll(set);
	}

	/**
	 * @param set
	 * @param strategy
	 */
	public MettelGeneralTableauState(Collection<? extends MettelTableauRule> calculus,
			Set<MettelAnnotatedExpression> set,
			MettelRuleChoiceStrategy strategy) {
		super();
		ruleChoiceStrategy  = strategy;
		initRuleStates(calculus);
		expressions.addAll(set);
	}

	/**
	 * @param state
	 */
	public MettelGeneralTableauState(MettelGeneralTableauState state){
		super();
		ruleChoiceStrategy = state.ruleChoiceStrategy;
		initRuleStates(state.ruleStates);
		expressions.embed(state.expressions);// first to embed
	}

	/**
	 * @param state
	 * @param set
	 */
	public MettelGeneralTableauState(
			MettelGeneralTableauState state,
			Set<MettelAnnotatedExpression> set) {
		this(state,set,state.ruleChoiceStrategy);
	}

	/**
	 * @param state
	 * @param set
	 * @param strategy
	 */
	public MettelGeneralTableauState(
			MettelGeneralTableauState state,
			Set<MettelAnnotatedExpression> set,
			MettelRuleChoiceStrategy strategy) {
		super();
		ruleChoiceStrategy  = strategy;
		initRuleStates(state.ruleStates);
		expressions.embed(state.expressions);// first to embed
		expressions.addAll(set);
	}



	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauState#id()
	 */
	@Override
	public int id() {
		return ID;
	}

	private boolean satisfiable = true;

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauState#isSatisfiable()
	 */
	@Override
	public boolean isSatisfiable() {
		return satisfiable;
	}

	private boolean complete = false;

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauState#isComplete()
	 */
	@Override
	public boolean isComplete() {
		return complete;
	}

	private class MettelSimpleRuleChoiceStrategy implements MettelRuleChoiceStrategy{

		private int index = -1;

		/* (non-Javadoc)
		 * @see mettel.core.MettelRuleChoiceStrategy#select(mettel.core.MettelGeneralTableauRuleState[])
		 */
		@Override
		public MettelTableauRuleState select(
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


	private MettelGeneralTableauRuleState[] ruleStates = null;

	private void initRuleStates(Collection<? extends MettelTableauRule> c){
		final int LENGTH = c.size();
		ruleStates = new MettelGeneralTableauRuleState[LENGTH];
		int i = 0;
		for(MettelTableauRule r:c){
			ruleStates[i] = new MettelGeneralTableauRuleState(r);
			i++;
		}
	}

	private void initRuleStates(MettelGeneralTableauRuleState[] rs){
		final int LENGTH = rs.length;
		ruleStates = new MettelGeneralTableauRuleState[LENGTH];
		for(int i = 0; i< LENGTH; i++){
			ruleStates[i] = new MettelGeneralTableauRuleState(rs[i]);
		}
	}

	private MettelRuleChoiceStrategy ruleChoiceStrategy = null;

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauState#expand()
	 */
	@Override
	public MettelTableauState[] expand() {

		MettelTableauRuleState rs = ruleChoiceStrategy.select(ruleStates);
		if(rs == null){
System.out.println("None of rules are applicable");
			complete = true;
			return null;
		}
System.out.println(rs);


		Set<MettelAnnotatedExpression>[] branches = rs.apply();
		if(rs.isTerminal()){
System.out.println("Unsatisfiable: terminal rule at "+this);
			satisfiable = false;
			return null;
		}

		if(branches == null) return null; //Cannot be applied or terminal

		final int BRANCHES_NUMBER = branches.length;
		MettelTableauState[] result = new MettelGeneralTableauState[BRANCHES_NUMBER];
		if(BRANCHES_NUMBER <= 1){
			this.addAll(branches[0]);
			result[0] = this;
		}else{
			for(int i = 0; i < BRANCHES_NUMBER; i++){
				MettelGeneralTableauState ts = new MettelGeneralTableauState(this);
				ts.addAll(annotator.reannotate(branches[i],this));
				result[i] = ts;
System.out.println("branches["+i+"] = "+branches[i]);
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauState#add(mettel.core.MettelAnnotatedExpression)
	 */
	@Override
	public boolean add(MettelAnnotatedExpression e) {
		for(MettelTableauRuleState rs:ruleStates) rs.add(e);
		return expressions.add(e);
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauState#addAll(java.util.Set)
	 */
	@Override
	public boolean addAll(Set<MettelAnnotatedExpression> s) {
		for(MettelTableauRuleState rs:ruleStates) rs.addAll(s);
		return expressions.addAll(s);
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
}
