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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelGeneralTableauState implements MettelTableauState {

//	private static int counter = 0;

//	private final int ID = counter++;

	final int SIZE;
	private MettelGeneralTableauRuleState[] ruleStates = null;

	private IdentityHashMap<MettelGeneralTableauState, TreeSet<MettelAnnotatedExpression>> sets = null;

	private MettelRuleChoiceStrategy ruleChoice = null;

	public MettelGeneralTableauState(Set<MettelGeneralTableauRule> calculus) {
		super();
		SIZE = calculus.size();
		ruleStates = new MettelGeneralTableauRuleState[SIZE];
		int i = 0;
		for(MettelGeneralTableauRule r:calculus){
			ArrayList<HashSet<MettelAnnotatedExpression>> l = new ArrayList<HashSet<MettelAnnotatedExpression>>(r.branches.size());
//			Queue<MettelAnnotatedExpression> q = new PriorityQueue<MettelAnnotatedExpression>(input);
/*			for(MettelExpression e:input){
				q.add(new MettelSimpleAnnotatedExpression(e, new MettelSimpleTableauAnnotation()));
			}
*/
			ruleStates[i] = new MettelGeneralTableauRuleState(r, /*q,*/ l);
		}
		sets = new IdentityHashMap<MettelGeneralTableauState, TreeSet<MettelAnnotatedExpression>>(1);
		sets.put(this, new TreeSet<MettelAnnotatedExpression>());
	}

	public MettelGeneralTableauState(Set<MettelGeneralTableauRule> calculus, Set<MettelAnnotatedExpression> input) {
		this(calculus);
		addAll(input);
	}

	public MettelGeneralTableauState(Set<MettelGeneralTableauRule> calculus,
			MettelRuleChoiceStrategy ruleChoice, Set<MettelAnnotatedExpression> input) {
		this(calculus,input);
		this.ruleChoice = ruleChoice;
	}

	public MettelGeneralTableauState(MettelGeneralTableauState state,
			MettelRuleChoiceStrategy ruleChoice, Set<MettelAnnotatedExpression> input) {
		super();
		SIZE = state.SIZE;
		ruleStates = new MettelGeneralTableauRuleState[SIZE];
		final MettelGeneralTableauRuleState[] rstates = state.ruleStates;
		for(int i = 0; i < SIZE; i++){
			ruleStates[i] = new MettelGeneralTableauRuleState(rstates[i]);
		}
		this.ruleChoice = ruleChoice;
		final IdentityHashMap<MettelGeneralTableauState, TreeSet<MettelAnnotatedExpression>> sets = state.sets;
		this.sets = new IdentityHashMap<MettelGeneralTableauState, TreeSet<MettelAnnotatedExpression>>(sets.size()+1);
		this.sets.putAll(sets);
		this.sets.put(this, new TreeSet<MettelAnnotatedExpression>());
		addAll(input);
	}

	public MettelGeneralTableauState(MettelGeneralTableauState state, Set<MettelAnnotatedExpression> input) {
		this(state,state.ruleChoice,input);
	}

	public void addAll(Set<MettelAnnotatedExpression> expressions){
		final Iterator<MettelAnnotatedExpression> i = expressions.iterator();
		while(i.hasNext()){
			MettelAnnotatedExpression e = i.next();
			if(!sets.get(e.annotation().state()).add(e)){
				i.remove();
			}
		}
		for(int j = 0; j < SIZE; j++){
			ruleStates[j].addAll(expressions);
		}
	}


	private int index = 0;

//	private static int UNSAT = -1;
//	private static int SAT = 1;

	public boolean isSatisfiable(){
		MettelGeneralTableauRuleState rs;
		while(true){
			if(ruleChoice == null){
				rs = ruleStates[index];
				index++;
				if(index == SIZE){ index = 0; }
			}else{
				rs = ruleChoice.select(ruleStates);
			}
			if(rs.evolve()){
				if(rs.isTerminal()) return false;
				List<? extends Set<MettelAnnotatedExpression>> result = rs.result;
				final int RESULT_SIZE = result.size();
				if(RESULT_SIZE == 1){
					addAll(result.get(0));
				}else if(RESULT_SIZE > 1){
					ArrayList<MettelGeneralTableauState> children = new ArrayList<MettelGeneralTableauState>(RESULT_SIZE);
					for(int i = 0; i < RESULT_SIZE; i++){
						children.add(new MettelGeneralTableauState(this,result.get(i)));
					}
					for(MettelGeneralTableauState child:children){//TODO branch choice strategy
						if(child.isSatisfiable()) return true;//TODO model set
					}
					return false;//TODO dependency set
				}
//				return true;
			}else{
				boolean dead = rs.isDead();
				if(dead){
					for(int i = 0; i < SIZE; i++){
						dead &= ruleStates[i].isDead();
						if(!dead) break;
					}
					if(dead) return true;
				}
			}
//			return false;
		}
	}

}
