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
package mettel.core.tableau.acceptor;

import mettel.core.tableau.MettelTableauState;
import mettel.core.tableau.MettelTableauStateAcceptor;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelSmallTableauStateAcceptor implements
		MettelTableauStateAcceptor {

	private int size = Integer.MAX_VALUE;

	public MettelSmallTableauStateAcceptor(int size){
		super();
		this.size = size;
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauStateAcceptor#accept(mettel.core.tableau.MettelTableauState)
	 */
	@Override
	public boolean accept(MettelTableauState state) {
		return state.isSatisfiable() && (state.expressions().size() <= size);
	}

}
