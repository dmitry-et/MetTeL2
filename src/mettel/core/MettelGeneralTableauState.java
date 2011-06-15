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
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelGeneralTableauState {

//	private static int counter = 0;

//	private final int ID = counter++;

	private MettelGeneralTableauRuleState[] ruleStates = null;

	private IdentityHashMap<MettelGeneralTableauState, TreeSet<MettelAnnotatedExpression>> sets = null;

	public MettelGeneralTableauState(Set<MettelGeneralTableauRule> calculus, Set<MettelAnnotatedExpression> input) {
		super();
		final int SIZE = calculus.size();
		ruleStates = new MettelGeneralTableauRuleState[SIZE];
		int i = 0;
		for(MettelGeneralTableauRule r:calculus){
			ArrayList<HashSet<MettelAnnotatedExpression>> l = new ArrayList<HashSet<MettelAnnotatedExpression>>(r.branches.size());
			Queue<MettelAnnotatedExpression> q = new PriorityQueue<MettelAnnotatedExpression>(input);
/*			for(MettelExpression e:input){
				q.add(new MettelSimpleAnnotatedExpression(e, new MettelSimpleTableauAnnotation()));
			}
*/
			ruleStates[i] = new MettelGeneralTableauRuleState(r, q, l);
		}
		sets = new IdentityHashMap<MettelGeneralTableauState, TreeSet<MettelAnnotatedExpression>>(1);
		sets.put(this, new TreeSet<MettelAnnotatedExpression>(input));
	}

	public MettelGeneralTableauState(MettelGeneralTableauState state, Set<MettelAnnotatedExpression> input) {
		super();
		this.ruleStates = state.ruleStates;
		final IdentityHashMap<MettelGeneralTableauState, TreeSet<MettelAnnotatedExpression>> sets = state.sets;
		this.sets = new IdentityHashMap<MettelGeneralTableauState, TreeSet<MettelAnnotatedExpression>>(sets.size());
		this.sets.putAll(sets);
		this.sets.put(this, new TreeSet<MettelAnnotatedExpression>(input));

	}

	public boolean evolve(){
	    
	    	java.util.Random
	    
		return false;
	}

}
