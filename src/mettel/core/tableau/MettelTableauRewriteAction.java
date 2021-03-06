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
package mettel.core.tableau;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelTableauRewriteAction extends MettelAbstractTableauAction {

	/**
	 *
	 */
	public MettelTableauRewriteAction(MettelTableauState state) {
		this.state = state;
		states = new TreeSet<MettelTableauState>();
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauAction#execute()
	 */
	@Override
	public Set<MettelTableauState> execute(MettelTableauState s) {
//System.out.println("Executing rewrite action");
//		if(isFor(s)){
//System.out.println("The rewrite action is for "+s);
//			remove(s);
			return s.rewrite();
//		}
//		return null;
	}

	/* (non-Javadoc)
	 * @see mettel.util.MettelAnnotatedObject#create(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Object create(MettelTableauState key, Object o) {
		return o;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(MettelTableauAction o) {
		if(o == null) return 1;
		if(o instanceof MettelTableauTerminateAction) return 1;
		if(o instanceof MettelTableauExpansionAction) return o.id() - id();
		return -1;
	}

}
