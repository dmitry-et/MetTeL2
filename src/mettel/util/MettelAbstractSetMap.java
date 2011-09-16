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

import java.util.AbstractSet;
import java.util.Collection;
//import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Map;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public abstract class MettelAbstractSetMap<Key, E extends MettelAnnotatedObject<Key>>
		extends AbstractSet<E> implements MettelSetMap<Key, E> {

	protected Map<Key,Set<E>> map = null;

	/**
     * The number of structural modifications.
     */
//    private transient int modCount = 0;

	private transient int size = 0;
	/* (non-Javadoc)
	 * @see java.util.Set#size()
	 */
	@Override
	public int size() {
		return size;
	}

	/* (non-Javadoc)
	 * @see java.util.Set#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return (size == 0);
	}

	/* (non-Javadoc)
	 * @see java.util.Set#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o) {
		if(o == null) return false;

		@SuppressWarnings("unchecked")
		final MettelAnnotatedObject<Key> e = (MettelAnnotatedObject<Key>)o;

		final Iterator<Key> ki = keySet().iterator();
		while(ki.hasNext()){
			final Key key = ki.next();
//System.out.println("Contains: key="+key);
			if(contains(key,e.create(key,e.object()))){
				return true;
			}
		}

		return false;

//		@SuppressWarnings("unchecked")
//		final Object obj = (o instanceof MettelAnnotatedObject<?>)? ((MettelAnnotatedObject<Key>)o).object(): o;
//		for(Key key:keySet()){
//			if(contains(key,obj)) return true;
//		}
//		return false;

//			return contains(((MettelAnnotatedObject<?>)o).key(),o);
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#iterator()
	 */
	public Iterator<E> iterator() {
		return new MettelSetMapIterator();
	}

	private class MettelSetMapIterator implements Iterator<E> {
		Iterator<Key> ki = keySet().iterator();
		Iterator<E> i = null;
		Key key = null;

		private MettelSetMapIterator(){
			super();
			if(ki.hasNext()){
				key = ki.next();
				i = iterator(key);
			}
		}

        public boolean hasNext() {
        	if(i == null) return false;
        	if(i.hasNext()) return true;
//        	if(map.get(key).isEmpty()){
//				ki.remove();
//			}
        	while(ki.hasNext()){
        		key = ki.next();
        		i = iterator(key);
        		if(i.hasNext()) return true;
        		ki.remove();//In fact we should never come here
        	}
        	return false;
        }

        public void remove() {
        	if(i == null)
        		throw new IllegalStateException();
        	i.remove();
        	size--;
        	if(map.get(key).isEmpty()){
				ki.remove();
			}
        }

        public E next() {
        	if(i == null)
        		throw new NoSuchElementException();
        	if(i.hasNext()) return i.next();
//        	if(map.get(key).isEmpty()){
//				ki.remove();
//			}
        	while(ki.hasNext()){
        		key = ki.next();
        		i = iterator(key);
        		if(i.hasNext()) return i.next();
        		ki.remove();//In fact we should never come here
        	}
        	throw new NoSuchElementException();
        }
    }


	/* (non-Javadoc)
	 * @see java.util.Set#iterator()
	 *
	@Override
	public Iterator<E> iterator(); {
		return null;
	}

	/* (non-Javadoc)
	 * @see java.util.Set#toArray()
	 *
	@Override
	public Object[] toArray() {
		return toArray(new Object[0]);
	}

	/* (non-Javadoc)
	 * @see java.util.Set#toArray(T[])
	 *
	@Override
	public <T> T[] toArray(T[] a) {
		T[] tmp = a;
		T[] res = new T[0];
		for(Key key:map.keySet()){
			tmp = toArray(key,a);
			System.arraycopy(tmp, 0, res, res.length, length);
		}
		return res;
	}*/

	/* (non-Javadoc)
	 * @see java.util.Set#add(java.lang.Object)
	 */
	@Override
	public boolean add(E e) {
		if(e == null) return false;
		//final Key key = e.key();
		if(contains(e)){
//System.out.println("add: "+e+" is already in the set ");
			return false;
		}
//System.out.println("add: Adding "+e);
		return add(e.key(),e);
	}

	/* (non-Javadoc)
	 * @see java.util.Set#remove(java.lang.Object)
	 *
	@Override
	public boolean remove(Object o) {
		if(!(o instanceof MettelAnnotatedObject)) return false;
		try{
			@SuppressWarnings("unchecked")
			Key key = ((MettelAnnotatedObject<Key>)o).key();
			return remove(key,o);
		}catch(ClassCastException e){
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see java.util.Set#containsAll(java.util.Collection)
	 *
	@Override
	public boolean containsAll(Collection<?> c) {
		Iterator<?> e = c.iterator();
        while (e.hasNext())
            if (!contains(e.next()))
                return false;
        return true;
	}

	/* (non-Javadoc)
	 * @see java.util.Set#addAll(java.util.Collection)
	 *
	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean modified = false;
        Iterator<? extends E> e = c.iterator();
        while (e.hasNext()) {
            if (add(e.next()))
                modified = true;
        }
        return modified;
	}

	/* (non-Javadoc)
	 * @see java.util.Set#retainAll(java.util.Collection)
	 *
	@Override
	public boolean retainAll(Collection<?> c) {
		boolean modified = false;
        Iterator<E> e = iterator();
        while (e.hasNext()) {
            if (!c.contains(e.next())) {
                e.remove();
                modified = true;
            }
        }
        return modified;
	}

	/* (non-Javadoc)
	 * @see java.util.Set#removeAll(java.util.Collection)
	 *
	@Override
	public boolean removeAll(Collection<?> c) {
		boolean modified = false;
        Iterator<?> e = iterator();
        while (e.hasNext()) {
            if (c.contains(e.next())) {
                e.remove();
                modified = true;
            }
        }
        return modified;
	}
	*/

	/* (non-Javadoc)
	 * @see java.util.Set#clear()
	 */
	@Override
	public void clear() {
		for(Key key:map.keySet()){
			clear(key);
		}
	}

	/* (non-Javadoc)
	 * @see mettel.util.SetMap#size(java.lang.Object)
	 */
	@Override
	public int size(Object key) {
		final Set<E> set = map.get(key);
		return set == null ? -1: set.size();
	}

	/* (non-Javadoc)
	 * @see mettel.util.SetMap#isEmpty(java.lang.Object)
	 */
	@Override
	public boolean isEmpty(Object key) {
		final Set<E> set = map.get(key);
		return set == null ? true: set.isEmpty();
	}

	/* (non-Javadoc)
	 * @see mettel.util.SetMap#contains(java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean contains(Object key, Object o) {
		final Set<E> set = map.get(key);
		if(set == null) return false;
		return set.contains(o);
	}

	/* (non-Javadoc)
	 * @see mettel.util.SetMap#iterator(java.lang.Object)
	 */
	@Override
	public Iterator<E> iterator(Object key) {
		final Set<E> set = map.get(key);
		if(set == null) return null;
		return set.iterator();
	}

	/* (non-Javadoc)
	 * @see mettel.util.SetMap#toArray(java.lang.Object)
	 */
	@Override
	public Object[] toArray(Object key) {
		final Set<E> set = map.get(key);
		if(set == null) return null;
		return set.toArray();
	}

	/* (non-Javadoc)
	 * @see mettel.util.SetMap#toArray(java.lang.Object, T[])
	 */
	@Override
	public <T> T[] toArray(Object key, T[] a) {
		final Set<E> set = map.get(key);
		if(set == null) return null;
		return set.toArray(a);
	}

	protected abstract Set<E> newsubset();

	/* (non-Javadoc)
	 * @see mettel.util.SetMap#add(java.lang.Object, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean add(Object key, E e) {
		Set<E> set = map.get(key);
		if(set == null){
			set = newsubset();
			map.put((Key)key,set);
		}
		if(set.add(e)){
			size++;
//			modCount++;
			return true;
		}else return false;
	}

	/* (non-Javadoc)
	 * @see mettel.util.SetMap#remove(java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean remove(Object key, Object o) {
		final Set<E> set = map.get(key);
		if(set == null) return false;
		if(set.remove(o)){
			size--;
			if(set.isEmpty()){
				map.remove(key);
			}
//			modCount++;
			return true;
		}else return false;
	}

	/* (non-Javadoc)
	 * @see mettel.util.SetMap#containsAll(java.lang.Object, java.util.Collection)
	 */
	@Override
	public boolean containsAll(Object key, Collection<?> c) {
		final Set<E> set = map.get(key);
		if(set == null) return false;
		return set.containsAll(c);
	}

	/* (non-Javadoc)
	 * @see mettel.util.SetMap#addAll(java.lang.Object, java.util.Collection)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean addAll(Object key, Collection<? extends E> c) {
		Set<E> set = map.get(key);
		if(set == null){
			set = newsubset();
			map.put((Key)key,set);
		}
		final int SIZE0 = set.size();
		if(set.addAll(c)){
			size += (set.size()-SIZE0);
//			modCount++;
			return true;
		}else return false;
	}

	/* (non-Javadoc)
	 * @see mettel.util.SetMap#retainAll(java.lang.Object, java.util.Collection)
	 */
	@Override
	public boolean retainAll(Object key, Collection<?> c) {
		final Set<E> set = map.get(key);
		if(set == null) return false;
		final int SIZE0 = set.size();
		if(set.retainAll(c)){
			size -= (SIZE0 - set.size());
			if(set.isEmpty()){
				map.remove(key);
			}
//			modCount++;
			return true;
		}else return false;
	}

	/* (non-Javadoc)
	 * @see mettel.util.SetMap#removeAll(java.lang.Object, java.util.Collection)
	 */
	@Override
	public boolean removeAll(Object key, Collection<?> c) {
		final Set<E> set = map.get(key);
		if(set == null) return false;
		final int SIZE0 = set.size();
		if(set.removeAll(c)){
			size -= (SIZE0 - set.size());
			if(set.isEmpty()){
				map.remove(key);
			}
//			modCount++;
			return true;
		}else return false;
	}

	/* (non-Javadoc)
	 * @see mettel.util.SetMap#clear(java.lang.Object)
	 */
	@Override
	public void clear(Object key) {
		final Set<E> set = map.get(key);
		if(set != null){
			final int SIZE0 = set.size();
			set.clear();
			size -= (SIZE0 - set.size());
			map.remove(key);
//			modCount++;
		}
	}

	/* (non-Javadoc)
	 * @see mettel.util.MettelSetMap#contains(mettel.util.MettelAnnotatedObject)
	 */
	@Override
	public boolean contains(E e) {
		if(e == null) return false;
//		return contains(e.key(),e);

		final Iterator<Key> ki = keySet().iterator();
		while(ki.hasNext()){
			final Key key = ki.next();
//System.out.println("Contains#2: key="+key);
			if(contains(key,e.create(key,e.object()))){
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see mettel.util.MettelSetMap#keySet()
	 */
	@Override
	public Set<Key> keySet() {
		return map.keySet();
	}

	/* (non-Javadoc)
	 * @see mettel.util.MettelSetMap#remove(mettel.util.MettelAnnotatedObject)
	 */
	@Override
	public boolean remove(E e) {
		if(e == null) return false;
		return remove(e.key(),e);
	}

    public boolean removeAll(MettelSetMap<?, ?> c) {
    	boolean modified = false;

    	Set<?> keySet = c.keySet();
    	for(Object k:keySet){
    		modified |= removeAll(k,c.subset(k));
//   		if(map.get(k).isEmpty()){
//				map.remove(k);
//			}
    	}

    	return modified;
    }

    public boolean removeAll(Collection<?> c) {
        boolean modified = false;

        if (size > c.size()) {
            for (Iterator<?> i = c.iterator(); i.hasNext(); )
                modified |= remove(i.next());
        } else {
        	final Set<Key> keySet = map.keySet();
        	for(Key k:keySet){
        		for (Iterator<?> i = iterator(k); i.hasNext(); ) {
        			if (c.contains(i.next())) {
        				i.remove();
        				modified = true;
        			}
        		}
   				if(map.get(k).isEmpty()){
					map.remove(k);
				}
        	}
        }
        return modified;
    }

	/* (non-Javadoc)
	 * @see mettel.util.MettelSetMap#subset(java.lang.Object)
	 */
	@Override
	public Set<E> subset(Object key) {
		return map.get(key);//TODO: Unsafe for modifications
	}

   public int hashCode() {
        int h = 0;
        final Set<Key> keySet = map.keySet();
    	for(Key k:keySet){
	        Iterator<E> i = iterator(k);
	        while (i.hasNext()) {
	            E obj = i.next();
	            if (obj != null)
	                h += obj.hashCode();
	        }
    	}
        return h;
    }

   public String toString(){
	   StringBuilder b = new StringBuilder();
	   b.append('[');
	   final Set<Key> keySet = map.keySet();
	   int i = 0;
   	   for(Key k:keySet){
   		   if(i > 0) b.append(',');
   		   b.append(k);
   		   b.append(':');
//   		   b.append('[');
   		   b.append(subset(k));
//   		   b.append(']');
//   		   b.append(MettelStrings.LINE_SEPARATOR);
   		   i++;
   	   }
   	   b.append(']');
   	   return b.toString();
   }
}
