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
public interface MettelReplacement extends Comparable<MettelReplacement>{

//	boolean isCompatible(MettelSubstitution s);

    boolean append(MettelExpression e0, MettelExpression e1);

	/**
	 * @param replacement
	 */
	boolean append(MettelReplacement replacement);

	/**
	 * @return
	 */
	boolean isEmpty();

	/**
	 * @return
	 */
	MettelExpression rewrite(MettelExpression e);

	/**
	 * @param sub
	 * @return
	 */
	MettelSubstitution rewrite(MettelSubstitution sub);

//    MettelReplacement append(MettelAnnotatedExpression e0, MettelAnnotatedExpression e1);

//	MettelReplacement merge(MettelReplacement s);

//	MettelReplacement merge(MettelReplacement[] subs);

//	MettelReplacement mergeArray(MettelReplacement[] subs);

}
