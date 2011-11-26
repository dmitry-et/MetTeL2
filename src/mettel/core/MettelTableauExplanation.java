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
import java.util.SortedSet;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public interface MettelTableauExplanation {

	boolean isComplete();

	SortedSet<MettelAnnotatedExpression> lemma();
	SortedSet<MettelAnnotatedExpression> incompleteLemma();

	void append(Set<MettelAnnotatedExpression> sublemma);

	/**
	 * @return
	 */
	MettelTableauState state();

	/**
	 * @param mettelGeneralTableauState
	 * @param replacement
	 *
	void rewrite(MettelAnnotator annotator, MettelTableauState state,
			MettelReplacement replacement);
	*/
}
