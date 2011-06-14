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
public class MettelSimpleTableauAnnotation implements MettelTableauAnnotation {

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauAnnotation#annotate(mettel.core.MettelExpression)
	 */
	@Override
	public MettelAnnotatedExpression annotate(MettelExpression e) {
		// TODO Auto-generated method stub
		return new MettelSimpleAnnotatedExpression(e,this);
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauAnnotation#annotate(mettel.core.MettelSubstitution)
	 */
	@Override
	public MettelAnnotatedSubstitution annotate(MettelSubstitution s) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauAnnotation#merge(mettel.core.MettelTableauAnnotation)
	 */
	@Override
	public MettelTableauAnnotation merge(MettelTableauAnnotation a) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauAnnotation#mergeArray(mettel.core.MettelTableauAnnotation[])
	 */
	@Override
	public MettelTableauAnnotation mergeArray(MettelTableauAnnotation[] anns) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauAnnotation#merge(mettel.core.MettelTableauAnnotation[])
	 */
	@Override
	public MettelTableauAnnotation merge(MettelTableauAnnotation[] anns) {
		// TODO Auto-generated method stub
		return null;
	}

}
