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

import java.util.Set;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public interface MettelAnnotator {

	MettelAnnotatedExpression substitute(MettelExpression e, MettelAnnotatedSubstitution s);

	/**
	 * @param s0
	 * @param s1
	 * @return
	 */
	MettelAnnotatedSubstitution merge(MettelAnnotatedSubstitution s0, MettelAnnotatedSubstitution s1);

	/**
	 * @param mettelExpression
	 * @param e
	 * @return
	 */
	MettelAnnotatedSubstitution match(MettelExpression e, MettelAnnotatedExpression ae);

	/**
	 * @param set
	 * @param mettelGeneralTableauState
	 * @return
	 */
	Set<MettelAnnotatedExpression> reannotate(
			Set<MettelAnnotatedExpression> set,
			MettelTableauState state);

	/**
	 * @param e
	 * @param state
	 * @return
	 */
	MettelAnnotatedExpression annotate(MettelExpression e,
			MettelTableauState state);

	/**
	 * @param rewrite
	 * @param s
	 * @return
	 */
	MettelAnnotatedSubstitution annotate(MettelSubstitution sub,
			MettelTableauState s);

}
