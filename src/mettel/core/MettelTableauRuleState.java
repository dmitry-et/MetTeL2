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
public interface MettelTableauRuleState {

//	public boolean isDead();

	public boolean isTerminal();

//	public boolean evolve();

//	public void addSubstitutions(Set<MettelAnnotatedSubstitution> ss0,
//			MettelAnnotatedSubstitution s, int index);

//	public void addSubstitutions();

	/**
	 * @param expressions
	 */
	public void addAll(Set<MettelAnnotatedExpression> expressions);

	public void add(MettelAnnotatedExpression e);

	/**
	 * @return
	 */
	public Set<MettelAnnotatedExpression>[] apply();

	/**
	 * @return
	 */
	public boolean isApplicable();

	/**
	 * @param b
	 */
	public void setApplicable(boolean b);

	MettelTableauState applicationState();

	/**
	 * @return
	 */
	public int branchingFactor();

	public void rewrite(MettelTableauState s, MettelReplacement r);

	public Set<MettelAnnotatedExpression> dependencies();

	/**
	 * @return
	 */
	public int arity();
}