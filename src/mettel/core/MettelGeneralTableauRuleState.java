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
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelGeneralTableauRuleState {

    	private Queue<MettelAnnotatedExpression> queue = null;

    	private MettelAnnotatedExpression e = null;

    	//TODO change the structure for substitutions
    	private ArrayList<MettelSubstitution>[] subs = null;

//    	private MettelGeneralTableauRule rule = null;

    	private MettelExpression[] premises = null;
    	private Set<? extends Set<MettelExpression>> branches = null;
    	private List<? extends Set<MettelExpression>> result = null;

    	private Queue<MettelSubstitution[]> newSubstitutions = null;
    	private Set<MettelSubstitution[]> oldSubstitutions = null;

    	private int index = 0;
//    	private int[] subIndexes = null;

//    	private MettelSubstitution s = null;

    	private final int SIZE;

    	@SuppressWarnings("unused")
		private MettelGeneralTableauRuleState(){}

    	MettelGeneralTableauRuleState(MettelGeneralTableauRule rule, Queue<MettelAnnotatedExpression> queue,
    			List<? extends Set<MettelExpression>> result){
    		super();
    		this.premises = rule.premises;
    		this.branches = rule.branches;
    		this.queue = queue;
    		this.result = result;

    		SIZE = this.premises.length;
    		this.subs = new ArrayList[SIZE];
    		for(int i = 0; i < SIZE; i++){
    			subs[i] = new ArrayList<MettelSubstitution>();
    		}

//    		subIndexes = new int[SIZE];
    	}

    	private boolean dead = false;

    	private void step(){
    		for(Set<MettelExpression> set:result){
    			set.clear();
    		}

    		if(dead) return;
    		MettelSubstitution[] tuple = newSubstitutions.poll();
    		if(tuple == null){
    			processExpression();
    			tuple = newSubstitutions.poll();
    			if(tuple == null){
    				dead = true;
    				return;
    			}
    		}

    		MettelSubstitution s = tuple[0].merge(tuple);
    		while(s == null || !oldSubstitutions.add(tuple)){
    			tuple = newSubstitutions.poll();
    			if(tuple == null){
    				dead = true;
    				return;
    			}
    			s = tuple[0].merge(tuple);
    		}

			Iterator<? extends Set<MettelExpression>> i = result.iterator();
			for(Set<MettelExpression> set:branches){
				Set<MettelExpression> rset = i.next();
				for(MettelExpression e:set){
					rset.add(e.substitute(s));
				}
			}

    	}

    	private void processExpression(){
    		if(e == null){
    			e = queue.poll();
    			if(e == null) return;
    		}

		    MettelSubstitution s = null;
			while(s == null && index < SIZE){
				s = premises[index].match(e.expression());
				index++;
			}

			if(index == SIZE){
				e = null;
				index = 0;
				if(s == null) return;
			}

			for(MettelSubstitution[] oldTuple:oldSubstitutions){
				MettelSubstitution[] subs = new MettelSubstitution[SIZE];
				final int ind = index - 1;
				System.arraycopy(oldTuple, 0, subs, 0, ind);
				subs[ind] = s;
				System.arraycopy(oldTuple, index, subs, index, SIZE - index);
				newSubstitutions.add(subs);
			}
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
