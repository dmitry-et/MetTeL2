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
import java.util.LinkedHashSet;
import java.util.Set;
import mettel.util.MettelTreeSetLinkedHashMap;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelGeneralTableauRuleState implements MettelTableauRuleState {

//		private MettelTableauState tstate = null;

		private MettelAnnotator annotator = MettelSimpleAnnotator.ANNOTATOR;

//		private LinkedHashMap<MettelGeneralTableauState,MettelGeneralTableauRuleState> states = null;

    	private MettelTreeSetLinkedHashMap<MettelTableauState,MettelAnnotatedExpression> pool = null;
    	private MettelAnnotatedExpression e = null;

    	//TODO change the structure for substitutions
    	private MettelExpression[] premises = null;
    	private Set<? extends Set<? extends MettelExpression>> branches = null;

//    	MettelAnnotatedSubstitution s = null;

    	private MettelTreeSetLinkedHashMap<MettelTableauState,MettelAnnotatedSubstitution> newSubstitutions = null;
    	private MettelTreeSetLinkedHashMap<MettelTableauState,MettelAnnotatedSubstitution>[] oldSubstitutions = null;

    	private int index = 0;

    	private final int PREMISES_NUMBER;
    	protected final int BRANCHES_NUMBER;
    	private final boolean TERMINAL;

    	@SuppressWarnings("unchecked")
		private void init(MettelTableauState s){
//    		if(BRANCHES_NUMBER >= 1){
    			pool = new MettelTreeSetLinkedHashMap<MettelTableauState,MettelAnnotatedExpression>();
        		pool.init(s);

    			oldSubstitutions = new MettelTreeSetLinkedHashMap[PREMISES_NUMBER];
    			for(int i = 0; i < PREMISES_NUMBER; i++){
    				oldSubstitutions[i] = new MettelTreeSetLinkedHashMap<MettelTableauState,MettelAnnotatedSubstitution>();
    				oldSubstitutions[i].init(s);
    			}
    			newSubstitutions = new MettelTreeSetLinkedHashMap<MettelTableauState,MettelAnnotatedSubstitution>();
        		newSubstitutions.init(s);
//    		}else{
//    			pool = new TreeSet<MettelAnnotatedExpression>();
//
//    			oldSubstitutions = new TreeSet[PREMISES_NUMBER];
//    			for(int i = 0; i < PREMISES_NUMBER; i++){
//   				oldSubstitutions[i] = new TreeSet<MettelAnnotatedSubstitution>();
//    			}
//    			newSubstitutions = new TreeSet<MettelAnnotatedSubstitution>();
//    		}
    	}

    	@SuppressWarnings("unchecked")
		private void init(MettelTableauState s, MettelGeneralTableauRuleState state){
//    		if(BRANCHES_NUMBER >= 1){
    			pool = new MettelTreeSetLinkedHashMap<MettelTableauState,MettelAnnotatedExpression>();
    			pool.embed((MettelTreeSetLinkedHashMap<MettelTableauState,MettelAnnotatedExpression>)state.pool);
        		pool.init(s);

    			oldSubstitutions = new MettelTreeSetLinkedHashMap[PREMISES_NUMBER];
        		for(int i = 0; i < PREMISES_NUMBER; i++){
        			oldSubstitutions[i] = new MettelTreeSetLinkedHashMap<MettelTableauState,MettelAnnotatedSubstitution>();
        			oldSubstitutions[i].embed((MettelTreeSetLinkedHashMap<MettelTableauState,MettelAnnotatedSubstitution>)state.oldSubstitutions[i]);
        			oldSubstitutions[i].init(s);
        		}

        		newSubstitutions = new MettelTreeSetLinkedHashMap<MettelTableauState,MettelAnnotatedSubstitution>();
        		newSubstitutions.embed((MettelTreeSetLinkedHashMap<MettelTableauState,MettelAnnotatedSubstitution>)state.newSubstitutions);
        		newSubstitutions.init(s);
//    		}else{
//    			pool = new TreeSet<MettelAnnotatedExpression>(state.pool);
//
//    			oldSubstitutions = new TreeSet[PREMISES_NUMBER];
//    			for(int i = 0; i < PREMISES_NUMBER; i++){
//    				oldSubstitutions[i] = new TreeSet<MettelAnnotatedSubstitution>(state.oldSubstitutions[i]);
//    			}
//    			newSubstitutions = new TreeSet<MettelAnnotatedSubstitution>(state.newSubstitutions);
//    		}
    	}


    	@SuppressWarnings("unused")
    	private MettelGeneralTableauRuleState(){ PREMISES_NUMBER = 0; BRANCHES_NUMBER = 0; TERMINAL = false;}

    	MettelGeneralTableauRuleState(MettelTableauState tstate, MettelTableauRule rule){
    		super();
    		//this.tstate = tstate;

    		this.premises = rule.premises();
    		this.branches = rule.branches();

    		BRANCHES_NUMBER = branches.size();
    		PREMISES_NUMBER = this.premises.length;
    		TERMINAL = (BRANCHES_NUMBER == 0);

    		init(tstate);
//    		states = new LinkedHashMap<MettelGeneralTableauState,MettelGeneralTableauRuleState>();
    	}

    	MettelGeneralTableauRuleState(MettelTableauState tstate, MettelGeneralTableauRuleState state){
    		super();
    		//this.tstate = tstate;

    		this.premises = state.premises;
    		this.branches = state.branches;

    		//TODO make it syncronised

    		this.PREMISES_NUMBER = state.PREMISES_NUMBER;
    		this.BRANCHES_NUMBER = state.BRANCHES_NUMBER;
    		this.TERMINAL = state.TERMINAL;

    		init(tstate, state);

    		this.index = state.index;
    		this.e = state.e;
    		this.applicable = state.applicable;
    		this.terminal = state.terminal;

//    		states = new LinkedHashMap<MettelGeneralTableauState,MettelGeneralTableauRuleState>(state.states.size()+1);
//    		states.putAll(state.states);
//    		states.put(tstate, state);
    	}

    	private boolean applicable = true;

    	/* (non-Javadoc)
		 * @see mettel.core.MettelTableauRuleState#isApplicable()
		 */
    	@Override
		public boolean isApplicable() {
    		return applicable;
    	}


    	private boolean terminal = false;

    	/* (non-Javadoc)
		 * @see mettel.core.MettelTableauRuleState#isTerminal()
		 */
    	@Override
		public boolean isTerminal() {
    		return terminal;
    	}

    	private MettelTableauState applicationState = null;

		private Set<MettelAnnotatedExpression> dependencies;

    	/* (non-Javadoc)
		 * @see mettel.core.MettelTableauRuleState#apply()
		 */
    	@Override
		public Set<MettelAnnotatedExpression>[] apply(){

     		MettelAnnotatedSubstitution s0 = newSubstitutionsPoll();
    		while(s0 == null){
    		    switch(processExpression()){
    		    	case EMPTY_QUEUE:
//System.out.println("Empty queue");
    		    	    applicable = false;
						return null;
    		    	default:
//System.out.println("Processed");
    		    	    s0 = newSubstitutionsPoll();
    		    }
    		}
//System.out.println("New substitution "+s0);

    	    applicationState = s0.key();
    	    dependencies = s0.annotation().dependencies();

    		if(TERMINAL){
//System.out.println("Terminal state");
    			terminal = true;
    			return null;
    		}else{
//System.out.println(""+ result.size()+" vs "+branches.size());
	    		@SuppressWarnings("unchecked")
				LinkedHashSet<MettelAnnotatedExpression>[] result = new LinkedHashSet[BRANCHES_NUMBER];
	    		int i = 0;
	    		for(Set<? extends MettelExpression> set:branches){
//System.out.println("Branch "+set);
	    			result[i] = new LinkedHashSet<MettelAnnotatedExpression>(set.size());
	    		    for(MettelExpression e:set){
	    		    	result[i].add(annotator.substitute(e,s0));
	    		    }
//System.out.println(result[i]);
	    		    i++;
	    		}
	    		return result;
    		}
    	}

//************************************************************************************
    	private final int EMPTY_QUEUE = -1;
//    	private final int DOES_NOT_MATCH = -2;

    	private int processExpression(){

    		MettelAnnotatedSubstitution s = null;
    		boolean doesNotMatch = false;
    		do{
    			doesNotMatch = false;
	    		if(e == null){
	    		    e = expressionsPoll();
	    		    index = 0;
	    		    if(e == null) return EMPTY_QUEUE;

//	    		    MettelGeneralTableauRuleState st = states.get(e.annotation().state());
//	    		    if(st != null) st.queue.remove(e);
	    		}
//System.out.println("Chosen expression: "+e);

//	    		s = null;
	    		while(s == null && index < PREMISES_NUMBER){
	    			s = annotator.match(premises[index],e);
//System.out.println("Match is "+(s != null) +": "+premises[index]+" vs "+e);
//System.out.println("##Substitution is "+s+" index = "+index);
	    			index++;
	    		}

	    		if(index == PREMISES_NUMBER){

//System.out.println("#Substitution is "+s+" index = "+index);
	    		    e = null;
	    		    if(s == null){
	    		    	doesNotMatch = true;
	    		    }
	    		}
    		}while(doesNotMatch);

//System.out.println("Substitution is "+s+" index = "+index);

    		final int ind = index - 1;
    		if(!oldSubstitutions[ind].contains(s)){
    			newSubstitutions.addAll(mergeWithOldSubstitutions(s,ind));//first merge, than add to old
    			oldSubstitutions[ind].add(s);
    		}

    		return 0;
    	}


    	private LinkedHashSet<MettelAnnotatedSubstitution> mergeWithOldSubstitutions(MettelAnnotatedSubstitution s, int ind){
    		LinkedHashSet<MettelAnnotatedSubstitution> result = new LinkedHashSet<MettelAnnotatedSubstitution>();
			result.add(s);
			for(int i = 0; i < PREMISES_NUMBER; i++){
				if(i != ind){
//System.out.println("Merging: "+result);
					LinkedHashSet<MettelAnnotatedSubstitution> ss0 = result;
					result = new LinkedHashSet<MettelAnnotatedSubstitution>();
					for(MettelAnnotatedSubstitution s0:ss0){
						for(MettelAnnotatedSubstitution s1:oldSubstitutions[i]){
							MettelAnnotatedSubstitution s2 = annotator.merge(s0,s1);
							if(s2 != null) result.add(s2);
						}
					}
				}
			}
//System.out.println("Merged: " + result);
			return result;
    	}


		/* (non-Javadoc)
		 * @see mettel.core.MettelTableauRuleState#addAll(java.util.Set)
		 */
		@Override
		public void addAll(Set<MettelAnnotatedExpression> expressions) {
			applicable = true;
			pool.addAll(expressions);
		}

		/* (non-Javadoc)
		 * @see mettel.core.MettelTableauRuleState#add(mettel.core.MettelAnnotatedExpression)
		 */
		@Override
		public void add(MettelAnnotatedExpression e) {
			applicable = true;
			pool.add(e);
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

		private MettelAnnotatedExpression expressionsPoll(){
			Iterator<MettelAnnotatedExpression> i = pool.iterator();
			if(i.hasNext()){
				MettelAnnotatedExpression s = i.next();
				//if(BRANCHES_NUMBER <= 1 || !s.key().equals(tstate))
				i.remove();
//System.out.println(pool);
				return s;
			}else{
				return null;
			}
		}

		public String toString(){
	    	String s = "Rule: "+MettelGeneralTableauRule.toString(premises,branches)+"\nPool: "+pool+"\nOld Substitutions:";
	    	for(int i = 0; i< PREMISES_NUMBER; i++) s=s+' '+oldSubstitutions[i];
	    	s += "\nNew Substitutions: "+newSubstitutions;
	    	return s;
	    }

		/* (non-Javadoc)
		 * @see mettel.core.MettelTableauRuleState#setApplicable(boolean)
		 */
		@Override
		public void setApplicable(boolean b) {
			applicable = b;
		}

		/* (non-Javadoc)
		 * @see mettel.core.MettelTableauRuleState#applicationState()
		 */
		@Override
		public MettelTableauState applicationState() {
			return applicationState;
		}

		/* (non-Javadoc)
		 * @see mettel.core.MettelTableauRuleState#branchingFactor()
		 */
		@Override
		public int branchingFactor() {
			return BRANCHES_NUMBER;
		}

		/* (non-Javadoc)
		 * @see mettel.core.MettelTableauRuleState#rewrite(mettel.core.MettelReplacement)
		 */
		@Override
		public void rewrite(MettelTableauState s,MettelReplacement r) {
			final MettelTreeSetLinkedHashMap<MettelTableauState,MettelAnnotatedExpression> pool0 = pool;
			pool = new MettelTreeSetLinkedHashMap<MettelTableauState,MettelAnnotatedExpression>();
			pool.init(s);
			for(MettelAnnotatedExpression ae:pool0){
				final MettelExpression exp = ae.expression();
				pool.add(annotator.annotate(r.rewrite(exp), s));
			}
			if(e != null){
				e = annotator.annotate(r.rewrite(e.expression()), s);
			}
			final MettelTreeSetLinkedHashMap<MettelTableauState,MettelAnnotatedSubstitution> newSubstitutions0 = newSubstitutions;
			newSubstitutions = new MettelTreeSetLinkedHashMap<MettelTableauState,MettelAnnotatedSubstitution>();
    		newSubstitutions.init(s);
    		for(MettelAnnotatedSubstitution as:newSubstitutions0){
    			final MettelSubstitution sub = as.substitution();
    			newSubstitutions.add(annotator.annotate(r.rewrite(sub), s));
    		}

//    		oldSubstitutions = new MettelTreeSetLinkedHashMap[PREMISES_NUMBER];
			for(int i = 0; i < PREMISES_NUMBER; i++){
				final MettelTreeSetLinkedHashMap<MettelTableauState,MettelAnnotatedSubstitution> subs = oldSubstitutions[i];
				oldSubstitutions[i] = new MettelTreeSetLinkedHashMap<MettelTableauState,MettelAnnotatedSubstitution>();
				oldSubstitutions[i].init(s);
	    		for(MettelAnnotatedSubstitution as:subs){
	    			final MettelSubstitution sub = as.substitution();
	    			oldSubstitutions[i].add(annotator.annotate(r.rewrite(sub), s));
	    		}
			}
		}

		/* (non-Javadoc)
		 * @see mettel.core.MettelTableauRuleState#dependencies()
		 */
		@Override
		public Set<MettelAnnotatedExpression> dependencies() {
			return dependencies;
		}
}
