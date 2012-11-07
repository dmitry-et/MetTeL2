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

import mettel.core.util.MettelAnnotatedObject;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public abstract class MettelAbstractAnnotatedSubstitution implements
		MettelAnnotatedSubstitution, MettelAnnotatedObject<MettelTableauState> {


	@SuppressWarnings("unused")
	private MettelAbstractAnnotatedSubstitution() {}

	public MettelAbstractAnnotatedSubstitution(MettelSubstitution s, MettelTableauAnnotation a) {
		super();
		this.s = s;
		this.a = a;
	}

	private MettelSubstitution s = null;

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelAnnotatedSubstitution#substitution()
	 */
	@Override
	public MettelSubstitution substitution() {
		return s;
	}


	private MettelTableauAnnotation a = null;
	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelAnnotatedSubstitution#annotation()
	 */
	@Override
	public MettelTableauAnnotation annotation() {
		return a;
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelAnnotatedSubstitution#merge(mettel.core.tableau.MettelAnnotatedSubstitution)
	 */
	@Override
	public MettelAnnotatedSubstitution merge(MettelAnnotatedSubstitution s0) {
		MettelSubstitution s1 = s.merge(s0.substitution());
		if(s1 == null) return null;
		return a.merge(s0.annotation()).annotate(s1);
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelAnnotatedSubstitution#merge(mettel.core.tableau.MettelAnnotatedSubstitution[])
	 */
	@Override
	public MettelAnnotatedSubstitution merge(MettelAnnotatedSubstitution[] subs) {
		final int SIZE = subs.length;
		MettelSubstitution[] subs0 = new MettelSubstitution[SIZE];
		MettelTableauAnnotation[] anns0 = new MettelTableauAnnotation[SIZE];
		for(int i = 0; i < SIZE; i++){
			subs0[i] = subs[i].substitution();
			anns0[i] = subs[i].annotation();
		}
		return a.merge(anns0).annotate(s.merge(subs0));
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelAnnotatedSubstitution#merge(mettel.core.tableau.MettelAnnotatedSubstitution[])
	 */
	@Override
	public MettelAnnotatedSubstitution mergeArray(MettelAnnotatedSubstitution[] subs) {
		final int SIZE = subs.length;
		MettelSubstitution[] subs0 = new MettelSubstitution[SIZE];
		MettelTableauAnnotation[] anns0 = new MettelTableauAnnotation[SIZE];
		for(int i = 0; i < SIZE; i++){
			subs0[i] = subs[i].substitution();
			anns0[i] = subs[i].annotation();
		}
		return a.mergeArray(anns0).annotate(s.mergeArray(subs0));
	}

	public String toString(){
		return (s == null? "null" : s.toString())+'_' +(a == null? "null": a.toString());
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(MettelAnnotatedSubstitution s0) {
		return this.s.compareTo(s0.substitution());
	}
}
