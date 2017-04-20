/**
 * 
 */
package mettel.core.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author dmitry
 *
 */
public class MettelDoubleTreeSet<T> implements SortedSet<T> {
	
	private final TreeSet<T> s1;
	private final TreeSet<T> s2;
	
	private final Comparator<? super T> c1;
	private final Comparator<? super T> c2;
	
	public MettelDoubleTreeSet(Comparator<? super T> c1, Comparator<? super T> c2) {
		super();
		this.c1 = c1;
		this.c2 = c2;
		s1 = new TreeSet<T>(c1);
		s2 = new TreeSet<T>(c2);
	}

	/* (non-Javadoc)
	 * @see java.util.Set#size()
	 */
	@Override
	public int size() {
		return s1.size();
	}

	/* (non-Javadoc)
	 * @see java.util.Set#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return s1.isEmpty();
	}

	/* (non-Javadoc)
	 * @see java.util.Set#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o) {
		return s1.contains(o);
	}

	/* (non-Javadoc)
	 * @see java.util.Set#iterator()
	 */
	@Override
	public Iterator<T> iterator() {
		return s1.iterator();
	}

	/* (non-Javadoc)
	 * @see java.util.Set#toArray()
	 */
	@Override
	public Object[] toArray() {
		return s1.toArray();
	}

	/* (non-Javadoc)
	 * @see java.util.Set#toArray(java.lang.Object[])
	 */
	@Override
	public <U> U[] toArray(U[] a) {
		return s1.toArray(a);
	}

	/* (non-Javadoc)
	 * @see java.util.Set#add(java.lang.Object)
	 */
	@Override
	public boolean add(T e) {
		if(s1.contains(e)) return false;
		
		final T e0 = s2.ceiling(e);
		if(e0 != null) {
			final int cmp = c2.compare(e, e0);
			if(cmp == 0) {
				if(c1.compare(e, e0) < 0) {
					s1.remove(e0);
					s2.remove(e0);
					s1.add(e);
					s2.add(e);
					return true;
				}
				return false;
			}
		}
		s1.add(e);
		s2.add(e);
		return true;
	}

	/* (non-Javadoc)
	 * @see java.util.Set#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {	
		if(s1.remove(o)) {
			s2.remove(o);
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.Set#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		return s1.containsAll(c);
	}

	/* (non-Javadoc)
	 * @see java.util.Set#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends T> c) {
		boolean result = false;
		for(T e: c) {
			result = result || add(e);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.util.Set#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		boolean result = false;
		for(T e: s1) {
			if(!c.contains(e)) {
				result = result || remove(e);
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.util.Set#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		boolean result = false;
		for(Object o: c) {
			result = result || remove(o);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.util.Set#clear()
	 */
	@Override
	public void clear() {
		s1.clear();
		s2.clear();
	}

	/* (non-Javadoc)
	 * @see java.util.SortedSet#comparator()
	 */
	@Override
	public Comparator<? super T> comparator() {
		return s1.comparator();
	}

	/* (non-Javadoc)
	 * @see java.util.SortedSet#subSet(java.lang.Object, java.lang.Object)
	 */
	@Override
	public SortedSet<T> subSet(T fromElement, T toElement) {
		return s1.subSet(fromElement, toElement);
	}

	/* (non-Javadoc)
	 * @see java.util.SortedSet#headSet(java.lang.Object)
	 */
	@Override
	public SortedSet<T> headSet(T toElement) {
		return s1.headSet(toElement);
	}

	/* (non-Javadoc)
	 * @see java.util.SortedSet#tailSet(java.lang.Object)
	 */
	@Override
	public SortedSet<T> tailSet(T fromElement) {
		return s1.tailSet(fromElement);
	}

	/* (non-Javadoc)
	 * @see java.util.SortedSet#first()
	 */
	@Override
	public T first() {
		return s1.first();
	}

	/* (non-Javadoc)
	 * @see java.util.SortedSet#last()
	 */
	@Override
	public T last() {
		return s1.last();
	}

}
