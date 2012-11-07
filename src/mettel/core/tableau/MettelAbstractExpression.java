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
public abstract class MettelAbstractExpression implements MettelExpression {

	public MettelAnnotatedExpression substitute(MettelAnnotatedSubstitution s){
		return s.annotation().annotate(substitute(s.substitution()));
	}

	public MettelAnnotatedSubstitution match(MettelAnnotatedExpression e){
		MettelSubstitution s = match(e.expression());
		if(s == null) return null;
		return e.annotation().annotate(s);
	}

}
