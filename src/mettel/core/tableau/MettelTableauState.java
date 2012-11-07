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

import java.util.Set;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public interface MettelTableauState extends Comparable<MettelTableauState>{

	int id();

	boolean isSatisfiable();

	boolean isComplete();

	boolean isExpanded();

	Set<MettelTableauState> expand(Set<MettelAnnotatedExpression>[] branches, Set<MettelAnnotatedExpression> dependencies);

	boolean add(MettelAnnotatedExpression e);

	boolean addAll(Set<MettelAnnotatedExpression> s);

//	Set<MettelTableauState> children();

	/**
	 * @return
	 */
	Set<MettelAnnotatedExpression> expressions();

	/**
	 * @param e
	 */
	void addToRulePools(MettelAnnotatedExpression e);

//	void setSatisfiable(boolean v);

	/**
	 * @param branches
	 */
	Set<MettelTableauState> expand();

	/**
	 * @return
	 */
	Set<MettelTableauAction> actionsToAdd();

	/**
	 * @param key
	 * @return
	 */
	boolean isChildOf(MettelTableauState key);

	/**
	 * @return
	 */
	Set<MettelTableauAction> actions();

	/**
	 * @return
	 */
	MettelTableauState parent();

	/**
	 * @return
	 */
	Set<MettelTableauState> rewrite();

	/**
	 *
	 */
	void setUnsatisfiable();

	/**
	 * @param dependencies
	 * @return
	 */
	MettelTableauState addLemma(Set<MettelAnnotatedExpression> dependencies);

	/**
	 *
	 */
	MettelTableauExplanation explanation();

	Set<MettelAnnotatedExpression> equalities();

}
