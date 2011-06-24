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

import java.util.Collection;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision: $ $Date: $
 *
 */
public interface MettelTableauAnnotation {

	public void annotate(Collection<MettelAnnotatedExpression> out, Collection<? extends MettelExpression> in);

	public MettelAnnotatedExpression annotate(MettelExpression e);

	public MettelAnnotatedSubstitution annotate(MettelSubstitution s);

	public MettelTableauAnnotation merge(MettelTableauAnnotation a);

	public MettelTableauAnnotation mergeArray(MettelTableauAnnotation[] anns);

	public MettelTableauAnnotation merge(MettelTableauAnnotation[] anns);

	public MettelTableauState state();

	public MettelTableauAnnotation newInstance(MettelTableauState state);

}
