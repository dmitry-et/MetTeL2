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
public class MettelAbstractAnnotatedSubstitution implements
		MettelAnnotatedSubstitution {


	@SuppressWarnings("unused")
	private MettelAbstractAnnotatedSubstitution() {}

	public MettelAbstractAnnotatedSubstitution(MettelSubstitution s, MettelTableauAnnotation a) {
		super();
		this.s = s;
		this.a = a;
	}

	private MettelSubstitution s = null;

	/* (non-Javadoc)
	 * @see mettel.core.MettelAnnotatedSubstitution#substitution()
	 */
	@Override
	public MettelSubstitution substitution() {
		return s;
	}


	private MettelTableauAnnotation a = null;
	/* (non-Javadoc)
	 * @see mettel.core.MettelAnnotatedSubstitution#annotation()
	 */
	@Override
	public MettelTableauAnnotation annotation() {
		return a;
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelAnnotatedSubstitution#merge(mettel.core.MettelAnnotatedSubstitution)
	 */
	@Override
	public MettelAnnotatedSubstitution merge(MettelAnnotatedSubstitution s0) {
		MettelSubstitution s1 = s.merge(s0.substitution());
		if(s1 == null) return null;
		return a.merge(s0.annotation()).annotate(s1);
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelAnnotatedSubstitution#merge(mettel.core.MettelAnnotatedSubstitution[])
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
	 * @see mettel.core.MettelAnnotatedSubstitution#merge(mettel.core.MettelAnnotatedSubstitution[])
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
		return s.toString();
	}
}
