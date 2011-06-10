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

    	private int index = 0;
    	private int[] subIndexes = null;

    	private MettelSubstitution s = null;

    	private final int SIZE;

    	@SuppressWarnings("unused")
		private MettelGeneralTableauRuleState(){}

    	MettelGeneralTableauRuleState(MettelGeneralTableauRule rule, Queue<MettelAnnotatedExpression> queue){
    		super();
    		this.premises = rule.premises;
    		this.branches = rule.branches;
    		this.queue = queue;

    		SIZE = this.premises.length;
    		this.subs = new ArrayList[SIZE];
    		for(int i = 0; i < SIZE; i++){
    			subs[i] = new ArrayList<MettelSubstitution>();
    		}

    		subIndexes = new int[SIZE];
    	}

    	private void expand(){
    		if(e == null){
    			e = queue.poll();
    			if(e == null) return;
    		}
    		MettelSubstitution s = null;
    		while(s == null && index < SIZE){
    			s = premises[index].match(e.expression());
    			index++;
    		}
    		if(s == null) return;
    		for(int i = 0; i < SIZE; i++){
    			if(i != index-1){
    				MettelSubstitution si = null;
    				final int SIZEi = subs[i].size();
    				while(!s.isCompatible(si) && subIndexes[i] < SIZEi){
    					si = subs[i].get(subIndexes[i]);
    					subIndexes[i]++;
    				}
    				if(s.isCompatible(si)){
    					s = s.append(si);
    				}
    			}
    		}

    		if(index == SIZE){
    			index = 0;
    			e = null;
    		}

    	}

}
