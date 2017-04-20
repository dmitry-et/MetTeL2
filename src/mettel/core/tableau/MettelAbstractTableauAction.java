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

import java.util.Collection;
import java.util.Set;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
abstract class MettelAbstractTableauAction implements MettelTableauAction {

	private static int counter = -1;

	private final int ID = counter++;

	public int id(){
		return ID;
	}

	protected Set<MettelTableauState> states = null;

	protected MettelTableauState state = null;

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauAction#isFor(mettel.core.tableau.MettelTableauState)
	 */
	@Override
	public boolean isFor(MettelTableauState s) {
		return states.contains(s);
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauAction#remove(mettel.core.tableau.MettelTableauState)
	 */
	@Override
	public boolean remove(MettelTableauState s) {
		return states.remove(s);
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauAction#add(mettel.core.tableau.MettelTableauState)
	 */
	@Override
	public boolean add(MettelTableauState s) {
		return states.add(s);
	}

	/* (non-Javadoc)
	 * @see mettel.util.MettelAnnotatedObject#key()
	 */
	@Override
	public MettelTableauState key() {
		return state;
	}

	/* (non-Javadoc)
	 * @see mettel.util.MettelAnnotatedObject#object()
	 */
	@Override
	public Object object() {
		return this;
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauAction#addAll(java.util.Set)
	 */
	@Override
	public boolean addAll(Collection<MettelTableauState> children) {
		return states.addAll(children);
	}

	public String toString(){
		return super.toString()+" for states "+states;
	}

	public boolean completed(){
		return states.isEmpty();
	}

	public boolean retainAll(Collection<MettelTableauState> set) {
		return states.retainAll(set);
	}
}
