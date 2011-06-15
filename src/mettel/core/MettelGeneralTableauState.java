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

	final int SIZE;
	private MettelGeneralTableauRuleState[] ruleStates = null;

	private IdentityHashMap<MettelGeneralTableauState, TreeSet<MettelAnnotatedExpression>> sets = null;

	public MettelGeneralTableauState(Set<MettelGeneralTableauRule> calculus, Set<MettelAnnotatedExpression> input) {
		super();
		SIZE = calculus.size();
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
		sets.put(this, new TreeSet<MettelAnnotatedExpression>());
		addAll(input);
	}

	public MettelGeneralTableauState(MettelGeneralTableauState state, Set<MettelAnnotatedExpression> input) {
		super();
		ruleStates = state.ruleStates;
		SIZE = ruleStates.length;
		final IdentityHashMap<MettelGeneralTableauState, TreeSet<MettelAnnotatedExpression>> sets = state.sets;
		this.sets = new IdentityHashMap<MettelGeneralTableauState, TreeSet<MettelAnnotatedExpression>>(sets.size()+1);
		this.sets.putAll(sets);
		this.sets.put(this, new TreeSet<MettelAnnotatedExpression>());
		addAll(input);
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

	public boolean evolve(){
		MettelGeneralTableauRuleState rs = ruleStates[index];
		if(rs.evolve()){
			index++;
			if(index == SIZE){ index = 0; }
			List<? extends Set<MettelAnnotatedExpression>> result = rs.result;
			final int RESULT_SIZE = result.size();
			if(RESULT_SIZE == 1){
				addAll(result.get(0));
			}else if(RESULT_SIZE > 1){
				ArrayList<MettelGeneralTableauState> children = new ArrayList<MettelGeneralTableauState>(RESULT_SIZE);
				for(int i = 0; i < RESULT_SIZE; i++){
					children.add(new MettelGeneralTableauState(this,result.get(i)));
				}
			}
			return true;
		}
		return false;
	}

}
