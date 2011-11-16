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
package mettel.core.acceptor;

import mettel.core.MettelTableauState;
import mettel.core.MettelTableauStateAcceptor;

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
	 * @see mettel.core.MettelTableauStateAcceptor#accept(mettel.core.MettelTableauState)
	 */
	@Override
	public boolean accept(MettelTableauState state) {
		return state.isSatisfiable() && (state.expressions().size() <= size);
	}

}
