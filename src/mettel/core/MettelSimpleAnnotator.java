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

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelSimpleAnnotator implements MettelAnnotator {

	public final static MettelSimpleAnnotator ANNOTATOR = new MettelSimpleAnnotator();

	/**
	 *
	 */
	private MettelSimpleAnnotator() {
		super();
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelAnnotator#substitute(mettel.core.MettelExpression, mettel.core.MettelAnnotatedSubstitution)
	 */
	@Override
	public MettelAnnotatedExpression substitute(MettelExpression e,
			MettelAnnotatedSubstitution s) {
		return new MettelSimpleAnnotatedExpression(e.substitute(s.substitution()),s.annotation());
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelAnnotator#merge(mettel.core.MettelAnnotatedSubstitution, mettel.core.MettelAnnotatedSubstitution)
	 */
	@Override
	public MettelAnnotatedSubstitution merge(MettelAnnotatedSubstitution s0,
			MettelAnnotatedSubstitution s1) {
		MettelSubstitution s = s0.substitution().merge(s1.substitution());
		if(s == null) return null;
		return new MettelSimpleAnnotatedSubstitution(s,s0.annotation().merge(s1.annotation()));
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelAnnotator#match(mettel.core.MettelExpression, mettel.core.MettelAnnotatedExpression)
	 */
	@Override
	public MettelAnnotatedSubstitution match(MettelExpression e,
			MettelAnnotatedExpression ae) {
		return new MettelSimpleAnnotatedSubstitution(e.match(ae.expression()),ae.annotation());
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelAnnotator#reannotate(java.util.Set, mettel.core.MettelTableauState)
	 */
	@Override
	public Set<MettelAnnotatedExpression> reannotate(
			Set<MettelAnnotatedExpression> set, MettelTableauState state) {
		LinkedHashSet<MettelAnnotatedExpression> result = new LinkedHashSet<MettelAnnotatedExpression>(set.size());
		MettelSimpleTableauAnnotation a = new MettelSimpleTableauAnnotation(state);
		for(MettelAnnotatedExpression ae:set){
			result.add(new MettelSimpleAnnotatedExpression(ae.expression(),a));
		}
		return result;
	}

}
