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
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelGeneralTableauRuleState {

		private MettelGeneralTableauState tstate = null;

		private LinkedHashMap<MettelGeneralTableauState,MettelGeneralTableauRuleState> states = null;

    	private Queue<MettelAnnotatedExpression> queue = null;
    	private MettelAnnotatedExpression e = null;

    	//TODO change the structure for substitutions
//    	private ArrayList<MettelSubstitution>[] subs = null;

//    	private MettelGeneralTableauRule rule = null;

    	private MettelExpression[] premises = null;
    	private Set<? extends Set<? extends MettelExpression>> branches = null;
//    	List<? extends Set<MettelAnnotatedExpression>> result = null;
    	LinkedHashSet<MettelAnnotatedExpression>[] result = null;
    	LinkedHashSet<MettelAnnotatedSubstitution> rsubs = new LinkedHashSet<MettelAnnotatedSubstitution>();
    	MettelAnnotatedSubstitution s = null;

    	private LinkedHashSet<MettelAnnotatedSubstitution> newSubstitutions = null;
    	private LinkedHashSet<MettelAnnotatedSubstitution>[] oldSubstitutions = null;

    	private int index = 0;
//    	private int[] subIndexes = null;

//    	private MettelSubstitution s = null;

    	private final int SIZE;
    	protected final int BRANCHES_SIZE;
    	private final boolean TERMINAL;

    	@SuppressWarnings("unused")
    	private MettelGeneralTableauRuleState(){ SIZE = 0; BRANCHES_SIZE = 0; TERMINAL = false;}

    	@SuppressWarnings("unchecked")
		MettelGeneralTableauRuleState(MettelGeneralTableauState tstate, MettelGeneralTableauRule rule){//, //Queue<MettelAnnotatedExpression> queue,
    			//List<? extends Set<MettelAnnotatedExpression>> result){
    		super();
    		this.tstate = tstate;
    		this.premises = rule.premises;
    		this.branches = rule.branches;
    		this.queue = new PriorityQueue<MettelAnnotatedExpression>();

    		BRANCHES_SIZE = branches.size();
    		this.result = new LinkedHashSet[BRANCHES_SIZE];
    		for(int i = 0; i< BRANCHES_SIZE; i++){
    			this.result[i] = new LinkedHashSet<MettelAnnotatedExpression>();
    		}

    		SIZE = this.premises.length;
    		TERMINAL = (BRANCHES_SIZE == 0);
//if(TERMINAL) System.out.println("Terminal rule "+rule);
/*    		this.subs = new ArrayList[SIZE];
    		for(int i = 0; i < SIZE; i++){
    			subs[i] = new ArrayList<MettelSubstitution>();
    		}
*/
//    		subIndexes = new int[SIZE];
    		oldSubstitutions = (LinkedHashSet<MettelAnnotatedSubstitution>[]) new LinkedHashSet[SIZE];
    		for(int i = 0; i < SIZE; i++){
    			oldSubstitutions[i] = new LinkedHashSet<MettelAnnotatedSubstitution>();
    		}
    		newSubstitutions = new LinkedHashSet<MettelAnnotatedSubstitution>();

    		states = new LinkedHashMap<MettelGeneralTableauState,MettelGeneralTableauRuleState>();
    	}

    	@SuppressWarnings("unchecked")
		MettelGeneralTableauRuleState(MettelGeneralTableauState tstate, MettelGeneralTableauRuleState state){
    		super();
    		this.tstate = tstate;
    		this.premises = state.premises;
    		this.branches = state.branches;
    		this.queue = new PriorityQueue<MettelAnnotatedExpression>(state.queue);
    		//TODO make it syncronised
//    		this.result = state.result;
    		this.SIZE = state.SIZE;
    		this.BRANCHES_SIZE = state.BRANCHES_SIZE;
    		this.TERMINAL = state.TERMINAL;
    		this.result = new LinkedHashSet[BRANCHES_SIZE];
    		for(int i = 0; i< BRANCHES_SIZE; i++){
    			this.result[i] = new LinkedHashSet<MettelAnnotatedExpression>();
    		}

    		oldSubstitutions = (LinkedHashSet<MettelAnnotatedSubstitution>[]) new LinkedHashSet[SIZE];
    		for(int i = 0; i < SIZE; i++){
    			oldSubstitutions[i] = new LinkedHashSet<MettelAnnotatedSubstitution>(state.oldSubstitutions[i]);
    		}
    		newSubstitutions = new LinkedHashSet<MettelAnnotatedSubstitution>(state.newSubstitutions);
    		this.index = state.index;
    		this.e = state.e;

    		states = new LinkedHashMap<MettelGeneralTableauState,MettelGeneralTableauRuleState>(state.states.size()+1);
    		states.putAll(state.states);
    		states.put(tstate, state);
    	}

    	private boolean dead = false;

    	public boolean isDead() { return dead; }

    	public boolean isTerminal() { return TERMINAL; }

    	public boolean evolve(){

    		for(LinkedHashSet<MettelAnnotatedExpression> set:result){
    		    set.clear();
    		}

    		if(dead){
//System.out.println("Dead");
    			return false;
    		}
    		MettelAnnotatedSubstitution s0 = newSubstitutionsPoll();
    		if(s0 == null){
    		    switch(processExpression()){
    		    	case EMPTY_QUEUE:
//System.out.println("Empty queue");
    		    	    dead = true;
//    		    	case DOES_NOT_MATCH:
//System.out.println("Does not match");
						return false;
    		    	default:
//System.out.println("Processed");
    		    	    s0 = newSubstitutionsPoll();
    		    	    if(s0 == null){
//    		    	    	dead = true;
    		    	    	return false;
    		    	    }
//System.out.println("New substitution "+s);
    		    }
    		}

/*    		MettelAnnotatedSubstitution s = tuple[0].mergeArray(tuple);
    		while(s == null || !oldSubstitutions.add(tuple)){
    		    tuple = newSubstitutions.poll();
    		    if(tuple == null){
    		    	dead = true;
    		    	return false;
    		    }
    		    s = tuple[0].mergeArray(tuple);
    		}
*/
//System.out.println(""+ result.size()+" vs "+branches.size());
    		int i = 0;
    		for(Set<? extends MettelExpression> set:branches){
    		    for(MettelExpression e:set){
    		    	result[i].add(e.substitute(s0));
    		    }
//System.out.println(result[i]);
    		    i++;
    		}

    		return true;
    	}

//************************************************************************************
    	private final int EMPTY_QUEUE = -1;
//    	private final int DOES_NOT_MATCH = -2;

    	private int processExpression(){

    		boolean doesNotMatch = false;
    		do{
    			doesNotMatch = false;
	    		if(e == null){
	    		    e = queue.poll();
	    		    index = 0;
	    		    if(e == null) return EMPTY_QUEUE;
	    		    MettelGeneralTableauRuleState st = states.get(e.annotation().state());
	    		    if(st != null) st.queue.remove(e);
	    		}
//System.out.println("Chosen expression: "+e);

	    		s = null;
	    		while(s == null && index < SIZE){
	    			s = premises[index].match(e);
//System.out.println("Match is "+(s != null) +": "+premises[index]+" vs "+e);
	    			index++;
	    		}

	    		if(index == SIZE){
	    		    e = null;
	    		    if(s == null){
	    		    	//index = 0;
	    		    	//return DOES_NOT_MATCH;
	    		    	doesNotMatch = true;
	    		    }
	    		}
    		}while(doesNotMatch);

//System.out.println("Substitution is "+s+" index = "+index);


			rsubs.clear();
			rsubs.add(s);
			for(int i = 0; i < SIZE; i++){
				if(i != index - 1){
//System.out.println("Merging: "+rsubs);
					LinkedHashSet<MettelAnnotatedSubstitution> ss0 = rsubs;
					rsubs = new LinkedHashSet<MettelAnnotatedSubstitution>();
					for(MettelAnnotatedSubstitution s0:ss0){
						for(MettelAnnotatedSubstitution s1:oldSubstitutions[i]){
							MettelAnnotatedSubstitution s2 = s0.merge(s1);
							if(s2 != null) rsubs.add(s2);
						}
					}
				}
			}

/*			//LinkedHashSet<MettelAnnotatedSubstitution> ss = result;
			//result = new LinkedHashSet<MettelAnnotatedSubstitution>();
			for(MettelAnnotatedSubstitution s0:result){
System.out.println("To merge with "+s0);
				MettelAnnotatedSubstitution s1 = s.merge(s0);
				if(s1 != null) newSubstitutions.add(s1);
			}
*/
			for(MettelAnnotatedSubstitution s0:rsubs){
//System.out.println("Merged: "+s0);
				newSubstitutions.add(s0);
			}

			oldSubstitutions[index-1].add(s);
//for(int i=0; i < SIZE; i++){
//System.out.println("oldSubstitutions["+i+"] = "+oldSubstitutions[i]);
//}

//    		if(index == SIZE) index = 0;


/*			for(MettelAnnotatedSubstitution[] oldTuple:oldSubstitutions){
    		    MettelAnnotatedSubstitution[] subs = new MettelAnnotatedSubstitution[SIZE];
    		    final int ind = index - 1;
    		    System.arraycopy(oldTuple, 0, subs, 0, ind);
    		    subs[ind] = s;
    		    System.arraycopy(oldTuple, index, subs, index, SIZE - index);
    		    newSubstitutions.add(subs);
    		}
*/
    		return 0;
    	}


    	public void addSubstitutions(Set<MettelAnnotatedSubstitution> ss0, MettelAnnotatedSubstitution s, int index){
    		for(MettelAnnotatedSubstitution s0:ss0){
				newSubstitutions.add(s0);
			}
			oldSubstitutions[index-1].add(s);
    	}

    	public void addSubstitutions(){
    		MettelGeneralTableauRuleState state = states.get(s.annotation().state());
    		if(state != null) state.oldSubstitutions[index-1].add(s);

    		for(MettelAnnotatedSubstitution s0:rsubs){
    			state = states.get(s0.annotation().state());
				if(state != null) state.newSubstitutions.add(s0);
			}
		}

		/**
		 * @param expressions
		 */
		public void addAll(Set<MettelAnnotatedExpression> expressions) {
			dead = false;
			queue.addAll(expressions);
		}

		public void add(MettelAnnotatedExpression e) {
			dead = false;
			queue.add(e);
		}

		private MettelAnnotatedSubstitution newSubstitutionsPoll(){
			Iterator<MettelAnnotatedSubstitution> i = newSubstitutions.iterator();
			if(i.hasNext()){
				MettelAnnotatedSubstitution s = i.next();
				i.remove();
				return s;
			}else{
				return null;
			}
		}

		public String toString(){
	    	String s = "Rule: "+MettelGeneralTableauRule.toString(premises,branches)+"\nQueue: "+queue+"\nOld Substitutions:";
	    	for(int i = 0; i< SIZE; i++) s=s+' '+oldSubstitutions[i];
	    	s += "\nNew Substitutions: "+newSubstitutions;
	    	return s;
	    }

/*    	private void expand(){

    		for(Set<MettelExpression> set:result){
    			set.clear();
    		}

    		if(s == null){

    			if(e == null){
    				e = queue.poll();
    				if(e == null) return;
    			}

    			while(s == null && index < SIZE){
    				s = premises[index].match(e.expression());
    				index++;
    			}
    			if(s == null) return;
    		}

    		MettelSubstitution s0 = s;
    		for(int i = 0; i < SIZE; i++){
    			if(i != index - 1){
    				MettelSubstitution si = null;
    				final int SIZEi = subs[i].size();
    				while((s0 = s0.append(si)) == null && subIndexes[i] < SIZEi){
    					si = subs[i].get(subIndexes[i]);
    					subIndexes[i]++;
    				}
    				if(s0 == null) break;
    			}
    		}

    		if(s0 == null){


    		}else{
    			Iterator<? extends Set<MettelExpression>> i = result.iterator();
    			for(Set<MettelExpression> set:branches){
    				Set<MettelExpression> rset = i.next();
    				for(MettelExpression e:set){
    					rset.add(e.substitute(s0));
    				}
    			}
    		}

    		if(index == SIZE){
    			index = 0;
    			e = null;
    		}

    	}
*/
}
