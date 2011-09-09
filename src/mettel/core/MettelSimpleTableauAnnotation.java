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
 * @version $Revision$ $Date$
 *
 */
public class MettelSimpleTableauAnnotation implements MettelTableauAnnotation {

	private MettelTableauState state = null;

//	private Set<MettelTableauState> dependencySet = null;

	@SuppressWarnings("unused")
	private MettelSimpleTableauAnnotation(){}

	public MettelSimpleTableauAnnotation(MettelTableauState state){
		super();
		this.state = state;
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauAnnotation#annotate(mettel.core.MettelExpression)
	 */
	@Override
	public MettelAnnotatedExpression annotate(MettelExpression e) {
		return new MettelSimpleAnnotatedExpression(e,this);
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauAnnotation#annotate(mettel.core.MettelSubstitution)
	 */
	@Override
	public MettelAnnotatedSubstitution annotate(MettelSubstitution s) {
		return new MettelSimpleAnnotatedSubstitution(s,this);
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauAnnotation#merge(mettel.core.MettelTableauAnnotation)
	 */
	@Override
	public MettelTableauAnnotation merge(MettelTableauAnnotation a) {
		final MettelTableauState astate = a.state();
		if(this.state.id() < astate.id()){
			return new MettelSimpleTableauAnnotation(this.state);
		}else{
			return new MettelSimpleTableauAnnotation(astate);
		}
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauAnnotation#mergeArray(mettel.core.MettelTableauAnnotation[])
	 */
	@Override
	public MettelTableauAnnotation mergeArray(MettelTableauAnnotation[] anns) {
		final int SIZE = anns.length;
		if(SIZE == 0) return null;
		MettelTableauAnnotation a = anns[0];
		for(int i = 1; i < SIZE; i++){
			a = a.merge(anns[i]);
		}
		return a;
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauAnnotation#merge(mettel.core.MettelTableauAnnotation[])
	 */
	@Override
	public MettelTableauAnnotation merge(MettelTableauAnnotation[] anns) {
		final int SIZE = anns.length;
		if(SIZE == 0) return null;
		MettelTableauAnnotation a = this;
		for(int i = 0; i < SIZE; i++){
			a = a.merge(anns[i]);
		}
		return a;
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauAnnotation#state()
	 */
	@Override
	public MettelTableauState state() {
		return state;
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauAnnotation#annotate(java.util.Collection, java.util.Collection)
	 */
	@Override
	public void annotate(Collection<MettelAnnotatedExpression> out,
			Collection<? extends MettelExpression> in) {
		for(MettelExpression e:in){
			out.add(annotate(e));
		}
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauAnnotation#newInstance(mettel.core.MettelTableauState)
	 */
	@Override
	public MettelTableauAnnotation newInstance(MettelTableauState state) {
		return new MettelSimpleTableauAnnotation(state);
	}

	public String toString(){
		return "Ann("+state+')';
	}

}
