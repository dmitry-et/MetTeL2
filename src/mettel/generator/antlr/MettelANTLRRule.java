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
package mettel.generator.antlr;

import mettel.util.MettelIndentedStringBuilder;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelANTLRRule {

	/**
	 *
	 */
	private String name = null;

	private MettelANTLRExpression e = null;

//	private boolean fragment = false;

	private String[] argumentTypes = null;

	private String[] returnTypes =null;

	public MettelANTLRRule(String name, MettelANTLRExpression e){
		this(name,e,/*false,*/null,null);
	}

/*	public MettelANTLRRule(String name, MettelANTLRExpression e, boolean fragment){
		this(name,e,fragment,null,null);
	}
*/

	public MettelANTLRRule(String name, MettelANTLRExpression e, String[] returnTypes){
		this(name,e,/*false,*/null,returnTypes);
	}

/*	public MettelANTLRRule(String name, MettelANTLRExpression e, boolean fragment, String[] returnTypes){
		this(name,e,fragment,null,returnTypes);
	}
*/

	public MettelANTLRRule(String name, MettelANTLRExpression e, /*boolean fragment,*/ String[] argumentTypes, String[] returnTypes){
		super();
		this.name = name;
		this.e = e;
//		this.fragment = fragment;
		this.argumentTypes = argumentTypes;
		this.returnTypes = returnTypes;
	}


	private MettelIndentedStringBuilder init = null;

	public void appendToInitBlock(CharSequence csq) {
		if(init == null){
			init = new MettelIndentedStringBuilder(new StringBuilder());
		}
		init.append(csq);
	}

	public void appendToInitrBlock(char c) {
		if(init == null){
			init = new MettelIndentedStringBuilder(new StringBuilder());
		}
		init.append(c);
	}

	public void appendLineToInitBlock(CharSequence csq) {
		if(init == null){
			init = new MettelIndentedStringBuilder(new StringBuilder());
		}
		init.appendLine(csq);
	}

	public void appendLineToInitBlock(char c) {
		if(init == null){
			init = new MettelIndentedStringBuilder(new StringBuilder());
		}
		init.appendLine(c);
	}


	private MettelIndentedStringBuilder after = null;

	public void appendToAfterBlock(CharSequence csq) {
		if(after == null){
			after = new MettelIndentedStringBuilder(new StringBuilder());
		}
		after.append(csq);
	}

	public void appendToAfterBlock(char c) {
		if(after == null){
			after = new MettelIndentedStringBuilder(new StringBuilder());
		}
		after.append(c);
	}

	public void appendLineToAfterBlock(CharSequence csq) {
		if(after == null){
			after = new MettelIndentedStringBuilder(new StringBuilder());
		}
		after.appendLine(csq);
	}

	public void appendLineToAfterBlock(char c) {
		if(after == null){
			after = new MettelIndentedStringBuilder(new StringBuilder());
		}
		after.appendLine(c);
	}

	/**
	 * @param b
	 */
	void toStringBuilder(MettelIndentedStringBuilder b){
/*		if(fragment){
			b.append("fragment");
			b.append(' ');
		}
*/
		b.appendLine(name);
		if(argumentTypes != null){
			final int SIZE = argumentTypes.length;
			if(SIZE > 0){
				b.append('[');
				b.append(argumentTypes[0]);
				b.append(" a0");
				for(int i = 1; i < SIZE; i++){
					b.append(", ");
					b.append(argumentTypes[i]);
					b.append("a"+i);
				}
				b.append(']');
				b.appendEOL();
			}
		}
		if(returnTypes != null){
			final int SIZE = returnTypes.length;
			if(SIZE > 0){
				b.append("returns [");
				b.append(returnTypes[0]);
				b.append(" r0");
				for(int i = 1; i < SIZE; i++){
					b.append(", ");
					b.append(returnTypes[i]);
					b.append("r"+i);
				}
				b.append(']');
				b.appendEOL();
			}
		}
		if(init != null){
			b.appendLine("@init{");
			b.append(init.builder());
			//b.appendEOL();
			b.appendLine('}');
		}
		if(after != null){
			b.appendLine("@after{");
			b.append(after.builder());
			//b.appendEOL();
			b.appendLine('}');
		}
		MettelIndentedStringBuilder ib = new MettelIndentedStringBuilder(b);
		ib.appendLine(':');
		e.toStringBuilder(ib);
		b.appendEOL();
		b.appendLine(';');
	}
}
