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

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

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

	private TreeSet<MettelAnnotatedExpression> dependencies = new TreeSet<MettelAnnotatedExpression>();

	/**
	 * @param annotation
	 */
	public MettelSimpleTableauAnnotation(MettelTableauAnnotation annotation) {
		super();
		this.state = annotation.state();
		this.dependencies.addAll(annotation.dependencies());
	}

	/**
	 * @param mettelSimpleTableauAnnotation
	 * @param dependencies2
	 */
	protected MettelSimpleTableauAnnotation(
			MettelTableauAnnotation a,
			Set<MettelAnnotatedExpression> deps) {
		this(a);
		this.dependencies.addAll(deps);
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauAnnotation#annotate(mettel.core.tableau.MettelExpression)
	 */
	@Override
	public MettelAnnotatedExpression annotate(MettelExpression e) {
		return new MettelSimpleAnnotatedExpression(e,this);
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauAnnotation#annotate(mettel.core.tableau.MettelSubstitution)
	 */
	@Override
	public MettelAnnotatedSubstitution annotate(MettelSubstitution s) {
		return new MettelSimpleAnnotatedSubstitution(s,this);
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauAnnotation#merge(mettel.core.tableau.MettelTableauAnnotation)
	 */
	@Override
	public MettelTableauAnnotation merge(MettelTableauAnnotation a) {
		final MettelTableauState astate = a.state();
		if(this.state.id() > astate.id()){
			return new MettelSimpleTableauAnnotation(this, a.dependencies());
//			result.dependencies().addAll(a.dependencies());
//			return result;
		}else{
			return new MettelSimpleTableauAnnotation(a,dependencies);
		}
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauAnnotation#mergeArray(mettel.core.tableau.MettelTableauAnnotation[])
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
	 * @see mettel.core.tableau.MettelTableauAnnotation#merge(mettel.core.tableau.MettelTableauAnnotation[])
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
	 * @see mettel.core.tableau.MettelTableauAnnotation#state()
	 */
	@Override
	public MettelTableauState state() {
		return state;
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauAnnotation#annotate(java.util.Collection, java.util.Collection)
	 */
	@Override
	public void annotate(Collection<MettelAnnotatedExpression> out,
			Collection<? extends MettelExpression> in) {
		for(MettelExpression e:in){
			out.add(annotate(e));
		}
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauAnnotation#newInstance(mettel.core.tableau.MettelTableauState)
	 *
	@Override
	public MettelTableauAnnotation newInstance(MettelTableauState state) {
		return new MettelSimpleTableauAnnotation(state);
	}*/

	public String toString(){
		return "Ann("+state+')';
	}

	/**
	 * @param expression
	 */
	public void appendDependency(MettelAnnotatedExpression expression) {
		this.dependencies.add(expression);

	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauAnnotation#dependencies()
	 */
	@Override
	public Set<MettelAnnotatedExpression> dependencies() {
		return dependencies;
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauAnnotation#rewrite(mettel.core.tableau.MettelTableauState, mettel.core.tableau.MettelReplacement)
	 *
	@Override
	public void rewrite(MettelAnnotator annotator, MettelTableauState state, MettelReplacement replacement) {
		final TreeSet<MettelAnnotatedExpression> deps = new TreeSet<MettelAnnotatedExpression>();
		for(MettelAnnotatedExpression ae:dependencies){
			final MettelExpression e0 = ae.expression();
			final MettelExpression e1 = replacement.rewrite(e0);
			if(e0 == e1){//Only single instance of expression exists!
				deps.add(ae);
			}else{
				deps.add(annotator.annotate(e1,state));
			}
		}
		dependencies = deps;
	}*/

}
