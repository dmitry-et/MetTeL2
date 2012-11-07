package mettel.core.util;

import java.util.Iterator;
import java.util.LinkedHashSet;

//import mettel.core.util.MettelAnnotatedObject;
//import mettel.core.util.MettelTreeSetLinkedHashMap;

import junit.framework.*;

public class MettelSetMapTest extends TestCase{

	private static int counter = -1;

	private class Entry implements MettelAnnotatedObject<String>, Comparable<Entry>{

		private final int e;// = ++counter;

		private String key = null;

		/**
		 * @param e
		 */
		public Entry(int e) {
			super();
			this.e = e;
		}

		public Entry(int e, String key) {
			super();
			this.e = e;
			this.key = key;
		}

		/**
		 *
		 */
		public Entry() {
			super();
			e = ++counter;
		}

		/* (non-Javadoc)
		 * @see mettel.util.MettelAnnotatedObject#key()
		 */
		@Override
		public String key() {
			if(key == null) key = "Annotation#"+e%3;
			return key;
		}

		public String toString(){
			return "("+e+", "+key()+')';
		}

		/* (non-Javadoc)
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(Entry o) {
//System.out.println("Comparing: "+this+ " vs "+o);
			return e - o.e;
		}

		/* (non-Javadoc)
		 * @see mettel.util.MettelAnnotatedObject#object()
		 */
		@Override
		public Object object() {
			return this;
		}

		/* (non-Javadoc)
		 * @see mettel.util.MettelAnnotatedObject#create(java.lang.Object, java.lang.Object)
		 */
		@Override
		public Object create(String key, Object o) {
			final Entry e = (Entry)o;
			return new Entry(e.e);
		}

		public boolean equals(Object o){
//System.out.println("Equals: "+this+ " vs "+o);
			if(o == null) return false;
			return (e == ((Entry)o).e);
		}

	}

	private MettelTreeSetLinkedHashMap<String,Entry> set = new MettelTreeSetLinkedHashMap<String,Entry>();
	private LinkedHashSet<Entry> set0 = new LinkedHashSet<Entry>();

    public void testSetMap(){
		for(int i = 0; i < 9; i++){
			Entry e = new Entry();
			set.add(e);
			set0.add(e);
		}

		assertEquals(set.size(),set0.size());
		assertEquals(set,set0);

		set.add(new Entry(2,"Annotation#0"));

		assertEquals(set.size(),set0.size());

		Iterator<Entry> i;

/*		i = set.iterator();
		while(i.hasNext()){
			System.out.println(i.next());
		}
*/
		assertEquals(set,set0);

		i = set.iterator();
		set0.remove(i.next());i.remove();
		set0.remove(i.next());i.remove();
		set0.remove(i.next());i.remove();

		assertEquals(set.size(),set0.size());

/*		i = set.iterator();
		while(i.hasNext()){
			System.out.println(i.next());
		}
*/
		assertEquals(set,set0);

		i = set0.iterator();
		set.remove(i.next());i.remove();
		set.remove(i.next());i.remove();
		set.remove(i.next());i.remove();

		assertEquals(set.size(),set0.size());

		/*		i = set.iterator();
				while(i.hasNext()){
					System.out.println(i.next());
				}
		*/
		assertEquals(set,set0);
    }
}
