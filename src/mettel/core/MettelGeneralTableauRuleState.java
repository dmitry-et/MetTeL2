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
//    	private ArrayList<MettelSubstitution>[] subs = null;

//    	private MettelGeneralTableauRule rule = null;

    	private MettelExpression[] premises = null;
    	private Set<? extends Set<MettelExpression>> branches = null;
    	private List<? extends Set<MettelAnnotatedExpression>> result = null;

    	private Queue<MettelAnnotatedSubstitution[]> newSubstitutions = null;
    	private Set<MettelAnnotatedSubstitution[]> oldSubstitutions = null;

    	private int index = 0;
//    	private int[] subIndexes = null;

//    	private MettelSubstitution s = null;

    	private final int SIZE;

    	@SuppressWarnings("unused")
	private MettelGeneralTableauRuleState(){ SIZE = 0;}

    	MettelGeneralTableauRuleState(MettelGeneralTableauRule rule, Queue<MettelAnnotatedExpression> queue,
    			List<? extends Set<MettelAnnotatedExpression>> result){
    		super();
    		this.premises = rule.premises;
    		this.branches = rule.branches;
    		this.queue = queue;
    		this.result = result;

    		SIZE = this.premises.length;
/*    		this.subs = new ArrayList[SIZE];
    		for(int i = 0; i < SIZE; i++){
    			subs[i] = new ArrayList<MettelSubstitution>();
    		}
*/
//    		subIndexes = new int[SIZE];
    	}

    	private boolean dead = false;

    	public boolean evolve(){
    	    
    		for(Set<MettelAnnotatedExpression> set:result){
    		    set.clear();
    		}

    		if(dead) return false;
    		MettelAnnotatedSubstitution[] tuple = newSubstitutions.poll();
    		if(tuple == null){
    		    switch(processExpression()){
    		    	case EMPTY_QUEUE:
    		    	    dead = true;
    		    	case DOES_NOT_MATCH:
    		    	    return false;
    		    	default:
    		    	    tuple = newSubstitutions.poll();
    		    	    if(tuple == null){
    		    		dead = true;
    		    		return false;
    		    	    }
    		    }
    		}

    		MettelAnnotatedSubstitution s = tuple[0].merge(tuple);
    		while(s == null || !oldSubstitutions.add(tuple)){
    		    tuple = newSubstitutions.poll();
    		    if(tuple == null){
    			dead = true;
    			return false;
    		    }
    		    s = tuple[0].merge(tuple);
    		}

    		Iterator<? extends Set<MettelAnnotatedExpression>> i = result.iterator();
    		for(Set<MettelExpression> set:branches){
    		    Set<MettelAnnotatedExpression> rset = i.next();
    		    for(MettelExpression e:set){
    			rset.add(e.substitute(s));
    		    }
    		}
    		
    		return true;

    	}

    	private final int EMPTY_QUEUE = -1;
    	private final int DOES_NOT_MATCH = -2;
    	
    	private int processExpression(){
    		if(e == null){
    		    e = queue.poll();
    		    if(e == null) return EMPTY_QUEUE;
    		}

    		MettelAnnotatedSubstitution s = null;
    		while(s == null && index < SIZE){
    		    s = premises[index].match(e);
    		    index++;
    		}

    		if(index == SIZE){
    		    e = null;
    		    index = 0;
    		    if(s == null) return DOES_NOT_MATCH;
    		}

    		for(MettelAnnotatedSubstitution[] oldTuple:oldSubstitutions){
    		    MettelAnnotatedSubstitution[] subs = new MettelAnnotatedSubstitution[SIZE];
    		    final int ind = index - 1;
    		    System.arraycopy(oldTuple, 0, subs, 0, ind);
    		    subs[ind] = s;
    		    System.arraycopy(oldTuple, index, subs, index, SIZE - index);
    		    newSubstitutions.add(subs);
    		}
    		
    		return 0;
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
