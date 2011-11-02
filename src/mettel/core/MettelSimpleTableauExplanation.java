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
import java.util.TreeSet;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelSimpleTableauExplanation implements MettelTableauExplanation {

	/**
	 *
	 */
	@SuppressWarnings("unchecked")
	public MettelSimpleTableauExplanation(int n) {
		sublemmas = new Set[n];
		SIZE = n;
	}

	private final int SIZE;
	private Set<MettelExpression>[] sublemmas = null;
	private int counter = 0;

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauExplanation#isComplete()
	 */
	@Override
	public boolean isComplete() {
		return counter == SIZE;
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauExplanation#lemma()
	 */
	@Override
	public Set<MettelExpression> lemma() {
		if(counter < SIZE) return null;
		TreeSet<MettelExpression> lemma = new TreeSet<MettelExpression>();
		for(int i = 0; i < SIZE; i++){
			lemma.addAll(sublemmas[i]);
		}
		return lemma;
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelTableauExplanation#append(java.util.Set)
	 */
	@Override
	public void append(Set<MettelExpression> sublemma) {
		sublemmas[counter] = sublemma;
		counter++;
	}

}
