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

import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelGeneralTableauState implements MettelTableauState, Comparable<MettelTableauState> {
	
	private static int counter = 0;

	private final int ID = counter++;

	public int id(){ return ID; }

	final int SIZE;
	private MettelGeneralTableauRuleState[] ruleStates = null;

	private TreeMap<MettelGeneralTableauState, TreeSet<MettelAnnotatedExpression>> sets = null;

	private MettelRuleChoiceStrategy ruleChoice = null;

	public MettelGeneralTableauState(Set<MettelGeneralTableauRule> calculus) {
		super();
		SIZE = calculus.size();
		//System.out.println(calculus);
		ruleStates = new MettelGeneralTableauRuleState[SIZE];
		int i = 0;
		for(MettelGeneralTableauRule r:calculus){
/*			ArrayList<LinkedHashSet<MettelAnnotatedExpression>> l = new ArrayList<LinkedHashSet<MettelAnnotatedExpression>>(r.branches.size());
			final int BSIZE = r.branches.size();
//System.out.println("BSIZE = "+BSIZE);
			for(int j = 0; j < BSIZE; j++){
				l.add(new LinkedHashSet<MettelAnnotatedExpression>());
			}
*/
//			Queue<MettelAnnotatedExpression> q = new PriorityQueue<MettelAnnotatedExpression>(input);
/*			for(MettelExpression e:input){
				q.add(new MettelSimpleAnnotatedExpression(e, new MettelSimpleTableauAnnotation()));
			}
*/
			ruleStates[i] = new MettelGeneralTableauRuleState(this, r);//, /*q,*/ l);
			i++;
		}
		sets = new TreeMap<MettelGeneralTableauState, TreeSet<MettelAnnotatedExpression>>();
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
			ruleStates[i] = new MettelGeneralTableauRuleState(this, rstates[i]);
		}
		this.ruleChoice = ruleChoice;
		final TreeMap<MettelGeneralTableauState, TreeSet<MettelAnnotatedExpression>> sts = state.sets;
		this.sets = new TreeMap<MettelGeneralTableauState, TreeSet<MettelAnnotatedExpression>>(sts);
		//this.sets.putAll(sts);
		this.sets.put(this, new TreeSet<MettelAnnotatedExpression>());
//System.out.println("NEW "+this+" from "+state);
		for(MettelAnnotatedExpression e:input){
			addAndReannotate(e);
		}
//System.out.println(this.sets);
	}

	public MettelGeneralTableauState(MettelGeneralTableauState state, Set<MettelAnnotatedExpression> input) {
		this(state,state.ruleChoice,input);
	}

	/*public void add(MettelAnnotatedExpression e){
		MettelGeneralTableauState state0 = (MettelGeneralTableauState)e.annotation().state();
		boolean exists = false;
		for(TreeSet<MettelAnnotatedExpression> value:state0.sets.values()){
			if(value.contains(e)){
				exists = true;
				break;
			}
		}
		if(!exists){
			sets.get(state0).add(e);
			for(int j = 0; j < SIZE; j++){
				ruleStates[j].add(e);
			}
		}
	}*/

	public void addAndReannotate(MettelAnnotatedExpression e){
		boolean exists = false;
		for(TreeSet<MettelAnnotatedExpression> value:sets.values()){
			if(value.contains(e)){
				exists = true;
				break;
			}
		}
		if(!exists){
			MettelAnnotatedExpression ae = e.annotation().newInstance(this).annotate(e.expression());
			sets.get(this).add(ae);
			for(int j = 0; j < SIZE; j++){
				ruleStates[j].add(ae);
			}
		}
	}

	public void addAll(Set<MettelAnnotatedExpression> expressions){
//System.out.println(this.sets);
//System.out.println("Expressions to add: "+expressions);

		final Iterator<MettelAnnotatedExpression> i = expressions.iterator();
		while(i.hasNext()){
			MettelAnnotatedExpression e = i.next();
			MettelGeneralTableauState state0 = (MettelGeneralTableauState)e.annotation().state();
//System.out.println("Expression "+e+" must go into "+state0);
			//boolean exists = false;
/*			for(Entry<MettelGeneralTableauState,TreeSet<MettelAnnotatedExpression>> entry:state0.sets.entrySet()){
				if(entry.getValue().contains(e)){
					i.remove();
					//exists = true;
					break;
				}
				MettelGeneralTableauState state1 = entry.getKey();
System.out.println("Expression "+e+" goes into "+state1);
				sets.get(state1).add(e);
				for(int j = 0; j < SIZE; j++){
					state1.ruleStates[j].add(e);
				}
			}
			//if(!exists) sets.get(state0).add(e);
*/
			if(sets.get(state0).add(e)){
				for(MettelGeneralTableauState key:sets.tailMap(state0).keySet()){
					for(int j = 0; j < SIZE; j++){
						key.ruleStates[j].add(e);
					}
				}
			}else{
				i.remove();
			}

		}
/*		for(int j = 0; j < SIZE; j++){
			ruleStates[j].addAll(expressions);
		}
System.out.println("Added expressions: "+expressions);
*/
	}

	/*public boolean contains(MettelAnnotatedExpression e){
		MettelGeneralTableauState state0 = (MettelGeneralTableauState)e.annotation().state();
		for(TreeSet<MettelAnnotatedExpression> value:state0.sets.values()){
			if(value.contains(e)){
				return true;
			}
		}
		return false;
	}*/

	private int index = 0;

//	private static int UNSAT = -1;
//	private static int SAT = 1;

	public boolean isSatisfiable(){
		//System.out.println(ruleStates);
		//System.out.println(SIZE);
		MettelGeneralTableauRuleState rs;
		while(true){
			if(ruleChoice == null){
				rs = ruleStates[index];
				index++;
				if(index == SIZE){ index = 0; }
			}else{
				rs = ruleChoice.select(ruleStates);
			}
//System.out.println(rs);

			if(rs.evolve()){
				if(rs.isTerminal()){
//System.out.println("Unsatisfiable: terminal rule at "+this);
					return false;
				}
				Set<MettelAnnotatedExpression>[] result = rs.result;
//System.out.println(result);
				final int RESULT_SIZE = rs.BRANCHES_SIZE;
				if(RESULT_SIZE == 1){
					addAll(result[0]);
					rs.addSubstitutions();
				}else if(RESULT_SIZE > 1){
					boolean useOtherRule = false;
					for(int i = 0; i < RESULT_SIZE; i++){
//System.out.println("Result["+i+"] = "+result[i]+ ' '+RESULT_SIZE);
						MettelGeneralTableauState child = new MettelGeneralTableauState(this,result[i]);
						if(child.sets.get(child).isEmpty()){
							child = null;
							useOtherRule = true;
//							if(this.isSatisfiable()){
//								return true;
//							}
						}else{
							if(child.isSatisfiable()){
								return true;//TODO Model set
							}
//System.out.println("BACKTRACKING AT "+this);
						}
					}
					if(!useOtherRule){
//System.out.println("Unsatisfiable. Childern are all unsatisfiable at "+this);
						return false;//TODO dependency set
					}
//System.out.println("PROCEEDING WITH SAME "+this);
//System.out.println(this.sets);
				}
//				return true;
			}else{
				boolean dead = rs.isDead();
				if(dead){
					for(int i = 0; i < SIZE; i++){
						dead &= ruleStates[i].isDead();
						if(!dead) break;
					}
					if(dead){
//System.out.println("Satisfiable: all rules are dead.");
						return true;
					}
				}
			}
//			return false;
		}
	}

	public String toString(){
		return "TableauState #"+ID;
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
