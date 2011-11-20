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

import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelSimpleTableauExplanation implements MettelTableauExplanation {

	private MettelTableauState state = null;

	/**
	 *
	 */
	@SuppressWarnings("unchecked")
	public MettelSimpleTableauExplanation(MettelTableauState state, int n) {
		super();
		this.state = state;
		sublemmas = new Set[n];
		SIZE = n;
	}

	private final int SIZE;
	private Set<MettelAnnotatedExpression>[] sublemmas = null;
	private int counter = 0;

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauExplanation#isComplete()
	 */
	@Override
	public boolean isComplete() {
		return counter == SIZE;
	}

	private transient SortedSet<MettelAnnotatedExpression> lemma = null;
	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauExplanation#lemma()
	 */
	@Override
	public SortedSet<MettelAnnotatedExpression> lemma() {
		if(lemma != null) return lemma;
		if(counter < SIZE) return null;
		lemma = new TreeSet<MettelAnnotatedExpression>(MettelAnnotatedExpressionComparator.COMPARATOR);
		final int ID = state.id();
		for(int i = 0; i < SIZE; i++){
			final Iterator<MettelAnnotatedExpression> j = sublemmas[i].iterator();
			while(j.hasNext()){
				final MettelAnnotatedExpression ae = j.next();
				if(ae.key().id() <= ID){
					lemma.add(ae);
				}
			}
		}
		return lemma;
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauExplanation#incompleteLemma()
	 */
	@Override
	public SortedSet<MettelAnnotatedExpression> incompleteLemma() {
		if(lemma != null) return lemma;
		final TreeSet<MettelAnnotatedExpression> result = new TreeSet<MettelAnnotatedExpression>(MettelAnnotatedExpressionComparator.COMPARATOR);
		final int ID = state.id();
		for(int i = 0; i < counter; i++){
			final Iterator<MettelAnnotatedExpression> j = sublemmas[i].iterator();
			while(j.hasNext()){
				final MettelAnnotatedExpression ae = j.next();
				if(ae.key().id() <= ID){
					result.add(ae);
				}
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauExplanation#append(java.util.Set)
	 */
	@Override
	public void append(Set<MettelAnnotatedExpression> sublemma) {
		sublemmas[counter] = sublemma;
		counter++;
	}

	private transient MettelTableauState ancestor = null;

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauExplanation#state()
	 */
	@Override
	public MettelTableauState state() {
		if(ancestor != null) return ancestor;
		if(counter < SIZE) return null;
		SortedSet<MettelAnnotatedExpression> lemma0 = lemma();
		final MettelTableauState child = lemma0.last().key();
//System.out.println("Child = "+child);
		final MettelTableauState parent = state.parent();
//System.out.println("Parent = "+parent);
		if(parent == null){
			ancestor = child;
			return ancestor;
		}
		final MettelTableauExplanation explanation = parent.explanation();
		explanation.append(lemma0);
		ancestor = explanation.state();
//System.out.println("Ancestor = "+ancestor);
		if(ancestor == null){
			ancestor = child;
		}
		return ancestor;
	}


	public String toString(){
		return lemma().toString();
	}
}
