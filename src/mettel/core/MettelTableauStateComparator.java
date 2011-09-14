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

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelTableauStateComparator implements Comparator<MettelTableauState> {

	/**
	 *
	 */
	public MettelTableauStateComparator() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(MettelTableauState s1, MettelTableauState s2) {
		if(s1 == null){
			if(s2 == null) return 0;
			return -1;
		}
		if(s2 == null) return 1;

		Iterator<MettelAnnotatedExpression> i1 = s1.expressions().iterator();
		Iterator<MettelAnnotatedExpression> i2 = s2.expressions().iterator();
		while(i1.hasNext()){
			if(!i2.hasNext()) return 1;
			MettelAnnotatedExpression e1 = i1.next();
			MettelAnnotatedExpression e2 = i2.next();
			int result  = e1.compareTo(e2);
			if(result != 0) return result;
		}
		if(i2.hasNext()) return -1;
		return 0;
	}

}
