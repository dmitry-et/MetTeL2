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

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelSimpleAnnotatedSubstitution extends
		MettelAbstractAnnotatedSubstitution {

	/**
	 * @param e
	 * @param a
	 */
	public MettelSimpleAnnotatedSubstitution(MettelSubstitution s,
			MettelTableauAnnotation a) {
		super(s, a);
	}

	/* (non-Javadoc)
	 * @see mettel.util.MettelAnnotatedObject#key()
	 */
	@Override
	public MettelTableauState key() {
		return this.annotation().state();
	}

	/* (non-Javadoc)
	 * @see mettel.util.MettelAnnotatedObject#object()
	 */
/*	@Override
	public Object object() {
		return this.substitution();
	}*/

	/* (non-Javadoc)
	 * @see mettel.util.MettelAnnotatedObject#create(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Object create(MettelTableauState key, MettelSubstitution s) {
		return new MettelSimpleAnnotatedSubstitution(s, new MettelSimpleTableauAnnotation(key));
	}

}
