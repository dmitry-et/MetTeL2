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

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelGeneralTableauRule {

	/**
	 *
	 */
	public MettelGeneralTableauRule(Set<? extends MettelExpression> premises, Set<? extends Set<? extends MettelExpression>> branches) {
		super();
//		if(premises == null || premises.size() == 0) throw new MettelCoreRuntimeException("Empty premises in tableau rule");
		this.premises = new MettelExpression[premises.size()];
		premises.toArray(this.premises);

		Iterator<? extends Set<? extends MettelExpression>> i = branches.iterator();
		while(i.hasNext()){
			if(i.next().isEmpty()) i.remove();
		}
//		if(conclusions != null && conclusions.size() > 0){
			this.branches = branches;
//		}
	}

	MettelExpression[] premises = null;
	Set<? extends Set<? extends MettelExpression>> branches = null;

//	public int arity(){
//		return premises.length;
//	}

/*	HashSet<HashSet<MettelExpression>> result(MettelSubstitution s){
		if(branches == null) return null;
		HashSet<HashSet<MettelExpression>> r = new HashSet<HashSet<MettelExpression>>(branches.size());
		for(Set<MettelExpression> b:branches){
			//TODO normalise the rule to avoid empty branches
			HashSet<MettelExpression> rb = new HashSet<MettelExpression>(b.size());
			for(MettelExpression t:b){
				rb.add(t.substitute(s));
			}
			r.add(rb);
		}
		return r;
	}*/

	/*MettelExpression[] premises(){
		return premises;
	}

	Set<? extends Set<MettelExpression>> branches(){
		return branches;
	}*/

	private volatile String str = null;
    public String toString(){
    	if(str == null) str = toString(premises,branches);
    	return str;
    }

    public static String toString(MettelExpression[] premises, Collection<? extends Set<? extends MettelExpression>> branches){
	     StringBuilder buf = new StringBuilder();
	     addToStringBuffer(buf,premises);
	     buf.append('/');
	     Iterator<? extends Set<? extends MettelExpression>> ib = branches.iterator();
	     if(ib.hasNext()){
	        //buf.append('{');
	        addToStringBuffer(buf,ib.next());
	        //buf.append('}');
	        while(ib.hasNext()){
		    buf.append(" $| ");
		    //buf.append('{');
		    addToStringBuffer(buf,ib.next());
		    //buf.append('}');
	        }
	     }
	    return buf.toString();
    }

    private static void addToStringBuffer(StringBuilder buf, MettelExpression[] ff){
    	final int SIZE = ff.length;
    	if(SIZE > 0){
    	    buf.append(ff[0]);
                for(int i = 1; i < SIZE; i++) {
    		buf.append(' ');
    		buf.append(ff[i]);
    	    }
    	}
    }

    private static void addToStringBuffer(StringBuilder buf,Collection<? extends MettelExpression> c){
		final int SIZE = c.size();
		if(SIZE > 0){
		    MettelExpression[] ff = c.toArray(new MettelExpression[SIZE]);
		    buf.append(ff[0]);
	            for(int i = 1; i < SIZE; i++) {
			buf.append(' ');
			buf.append(ff[i]);
		    }
		}
    }

}
