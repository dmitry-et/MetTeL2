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
package mettel.core.util;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeSet;


/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelTreeSetLinkedHashMap<Key, E extends MettelAnnotatedObject<Key>> extends MettelAbstractSetMap<Key, E> {

	public MettelTreeSetLinkedHashMap(){
		this(null);
	}

	private final Comparator<? super E> comparator;

	public MettelTreeSetLinkedHashMap(Comparator<? super E> comparator){
		super();
		this.comparator = comparator;
		map = new LinkedHashMap<Key, Set<E>>(32);
	}


	protected Set<E> newsubset(){
		if(comparator == null) return new TreeSet<E>();
		return new TreeSet<E>(comparator);
	}

	@SuppressWarnings("unchecked")
	public void embed(MettelTreeSetLinkedHashMap<? extends Key, ? extends E> set){
		Set<Key> keySet = (Set<Key>)set.keySet();
		for(Key k:keySet){
			map.put(k,(Set<E>)set.subset(k));
		}
	}
}
