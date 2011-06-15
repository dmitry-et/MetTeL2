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

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelAbstractAnnotatedExpresssion implements
		MettelAnnotatedExpression {

	@SuppressWarnings("unused")
	private MettelAbstractAnnotatedExpresssion() {}

	public MettelAbstractAnnotatedExpresssion(MettelExpression e, MettelTableauAnnotation a) {
		super();
		this.e = e;
		this.a = a;
	}

	private MettelExpression e = null;

	private MettelTableauAnnotation a = null;

    public MettelExpression expression() { return e; }

    public MettelTableauAnnotation annotation() { return a; }

	/* (non-Javadoc)
	 * @see mettel.core.MettelAnnotatedExpression#substitute(mettel.core.MettelAnnotatedSubstitution)
	 */
	@Override
	public MettelAnnotatedExpression substitute(MettelAnnotatedSubstitution s) {
		MettelExpression e0 = e.substitute(s.substitution());
		return a.merge(s.annotation()).annotate(e0);
	}

}
