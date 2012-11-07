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

import mettel.util.MettelAnnotatedObject;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public abstract class MettelAbstractAnnotatedExpression
		implements MettelAnnotatedExpression, MettelAnnotatedObject<MettelTableauState> {

	@SuppressWarnings("unused")
	private MettelAbstractAnnotatedExpression() {}

	public MettelAbstractAnnotatedExpression(MettelExpression e, MettelTableauAnnotation a) {
		super();
		this.e = e;
		this.a = a;
	}

	private MettelExpression e = null;

	private MettelTableauAnnotation a = null;

    public MettelExpression expression() { return e; }

    public MettelTableauAnnotation annotation() { return a; }

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelAnnotatedExpression#substitute(mettel.core.tableau.MettelAnnotatedSubstitution)
	 */
	@Override
	public MettelAnnotatedExpression substitute(MettelAnnotatedSubstitution s) {
		MettelExpression e0 = e.substitute(s.substitution());
		return a.merge(s.annotation()).annotate(e0);
	}

/*	public String toString(){
		return e.toString();
	}
*/
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(MettelAnnotatedExpression e0) {
		return this.e.compareTo(e0.expression());
	}

	public boolean equals(MettelAnnotatedExpression e0) {
		return this.e.equals(e0.expression());
	}

	public String toString(){
		return e.toString()+'_'+a.toString();
	}

}
