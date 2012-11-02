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
package mettel.core.annotations;

import mettel.core.MettelAnnotatedExpression;
import mettel.core.MettelAnnotatedSubstitution;
import mettel.core.MettelExpression;
import mettel.core.MettelSimpleTableauAnnotation;
import mettel.core.MettelSubstitution;
import mettel.core.MettelTableauAnnotation;
//import mettel.core.MettelTableauRule;
import mettel.core.MettelTableauState;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelHistoryAnnotation extends MettelSimpleTableauAnnotation {

	//private MettelTableauRule rule = null;

	//private MettelAnnotatedExpression[] cause = null;

	//private MettelAnnotatedSubstitution[] substitutions = null;

	/**
	 * @param state
	 */
	public MettelHistoryAnnotation(MettelTableauState state) {
		super(state);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauAnnotation#annotate(mettel.core.MettelExpression)
	 */
	@Override
	public MettelAnnotatedExpression annotate(MettelExpression e) {
		return new MettelHistoryAnnotatedExpression(e,this);
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauAnnotation#annotate(mettel.core.MettelSubstitution)
	 */
	@Override
	public MettelAnnotatedSubstitution annotate(MettelSubstitution s) {
		return new MettelHistoryAnnotatedSubstitution(s,this);
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauAnnotation#newInstance(mettel.core.MettelTableauState)
	 *
	@Override
	public MettelTableauAnnotation newInstance(MettelTableauState state) {
		return new MettelHistoryAnnotation(state);
	}*/

//TODO Overwrite the method
/*	public String toString(){
		return "Ann("+state+')';
	}
*/
	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauAnnotation#merge(mettel.core.MettelTableauAnnotation)
	 */
	@Override
	public MettelTableauAnnotation merge(MettelTableauAnnotation a) {
		MettelHistoryAnnotation a0 = (MettelHistoryAnnotation)super.merge(a);

		return a0;
	}

}
