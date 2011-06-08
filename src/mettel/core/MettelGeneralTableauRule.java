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
import java.util.Set;
import java.util.List;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelGeneralTableauRule<T extends MettelExpression, S extends MettelSubstitution> {

	/**
	 *
	 */
	public MettelGeneralTableauRule(Set<T> premises, Set<Set<T>> branches) {
		super();
//		if(premises == null || premises.size() == 0) throw new MettelCoreRuntimeException("Empty premises in tableau rule");
		this.premises = new ArrayList<T>(premises);
//		if(conclusions != null && conclusions.size() > 0){
			this.branches = branches;
//		}
	}

	private List<T> premises = null;
	private Set<Set<T>> branches = null;

	HashSet<HashSet<T>> result(S s){
		if(branches == null) return null;
		HashSet<HashSet<T>> r = new HashSet<HashSet<T>>(branches.size());
		for(Set<T> b:branches){
			//TODO nomalise the rule to avoid empty branches
			HashSet<T> rb = new HashSet<T>(b.size());
			for(T t:b){
				rb.add((T)t.substitute(s));
			}
			r.add(rb);
		}
		return r;
	}


}
