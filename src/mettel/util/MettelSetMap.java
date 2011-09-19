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

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public interface MettelSetMap<Key, E extends MettelAnnotatedObject<Key>> extends Set<E> {

	public int size(Object key);
	public boolean isEmpty(Object key);
	public boolean contains(Object key, Object o);
	public boolean contains(E e);
	public Iterator<E> iterator(Object key);
	public Set<Key> keySet();
	public Object[] toArray(Object key);
	public <T> T[] toArray(Object key,T[] a);
	public boolean add(Object key,E e);
	public boolean remove(Object key,Object o);
	public boolean remove(E e);
	public boolean containsAll(Object key,Collection<?> c);
	public boolean addAll(Object key,Collection<? extends E> c);
	public boolean retainAll(Object key,Collection<?> c);
	public boolean removeAll(Object key,Collection<?> c);
	public void clear(Object key);
	public Set<E> subset(Object key);
	public void init(Object key);

}
