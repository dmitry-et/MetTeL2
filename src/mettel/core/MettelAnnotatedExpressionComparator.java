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

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelAnnotatedExpressionComparator implements
		Comparator<MettelAnnotatedExpression> {

	public static final MettelAnnotatedExpressionComparator COMPARATOR = new MettelAnnotatedExpressionComparator();

	/**
	 *
	 */
	public MettelAnnotatedExpressionComparator() {
		super();
	}

	/* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(MettelAnnotatedExpression o1,
			MettelAnnotatedExpression o2) {
		if(o1 == null){
			if(o2 == null) return 0;
			return -1;
		}
		if(o2 == null) return 1;

		final int ID1 = o1.key().id();
		final int ID2 = o2.key().id();
		if(ID1 < ID2) return -1;
		if(ID1 > ID2) return 1;

		return o1.compareTo(o2);
	}
}
