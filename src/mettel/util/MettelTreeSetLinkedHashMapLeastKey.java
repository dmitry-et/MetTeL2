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
package mettel.util;

import java.util.Comparator;
import java.util.Iterator;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision: $ $Date: $
 *
 */
public class MettelTreeSetLinkedHashMapLeastKey<Key extends Comparable<? super Key>, E extends MettelAnnotatedObject<Key>> extends
		MettelTreeSetLinkedHashMap<Key, E> {
	
	public MettelTreeSetLinkedHashMapLeastKey(){
		super();
	}

	public MettelTreeSetLinkedHashMapLeastKey(Comparator<? super E> comparator){
		super(comparator);
	}
	
	/* (non-Javadoc)
	 * @see java.util.Set#add(java.lang.Object)
	 */
	@Override
	public boolean add(E e) {
		if(e == null) return false;
		
		final Iterator<Key> ki = keySet().iterator();
		while(ki.hasNext()){
			final Key key = ki.next();
//System.out.println("Contains: key="+key);
			final Object o = e.create(key,e.object());
			
			if(contains(key,o)){
				//final Comparable<? super Key> c = (Comparable<? super Key>)key;
				final int COMP = key.compareTo(e.key());
				if(COMP <= 0) return false;
				remove(key,o);
				return add(e.key(),e);
			}
		}
		return add(e.key(),e);
	}	
}
