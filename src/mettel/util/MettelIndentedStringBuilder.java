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

import static mettel.util.MettelStrings.LINE_SEPARATOR;
import static mettel.util.MettelStrings.TAB_STRING;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelIndentedStringBuilder implements Appendable{

	protected StringBuilder b = null;

//	public StringBuilder builder(){
//		return b;
//	}

	@SuppressWarnings("unused")
	private MettelIndentedStringBuilder(){};

	/**
	 *
	 */
	public MettelIndentedStringBuilder(StringBuilder b) {
		this.b = b;
	}

	public MettelIndentedStringBuilder(MettelIndentedStringBuilder b) {
		this(b.b,b.indentString,b.indentLevel+1);
	}

	public MettelIndentedStringBuilder(StringBuilder b, String indent) {
		this(b);
		setIndent(indent);
	}

	public MettelIndentedStringBuilder(StringBuilder b, String indent, int indentLevel) {
		this(b,indent);
		setIndentLevel(indentLevel);
	}

	public MettelIndentedStringBuilder(StringBuilder b, int indentLevel) {
		this(b);
		setIndentLevel(indentLevel);
	}

	public StringBuilder appendEOL(){
		return b.append(LINE_SEPARATOR);
	}

	private String indentString = TAB_STRING;

//	public String indentString(){
//		return indentString;
//	}

	public void setIndent(String indent){
		this.indentString = indent;
	}

	private int indentLevel = -1;

//	public int indentLevel(){
//		return indentLevel;
//	}

	public void setIndentLevel(int l){
		this.indentLevel = l;
	}

	public StringBuilder indent(){
		for(int i = 0;i<indentLevel;i++){
			b.append(indentString);
		}
		return b;
	}

	/* (non-Javadoc)
	 * @see java.lang.Appendable#append(java.lang.CharSequence)
	 */
	public StringBuilder append(CharSequence csq) {
		return b.append(csq);
	}

	/* (non-Javadoc)
	 * @see java.lang.Appendable#append(java.lang.CharSequence, int, int)
	 */
	public StringBuilder append(CharSequence csq, int start, int end) {
		return b.append(csq,start,end);
	}

	/* (non-Javadoc)
	 * @see java.lang.Appendable#append(char)
	 */
	public StringBuilder append(char c) {
		return b.append(c);
	}

	public StringBuilder appendLine(CharSequence csq) {
		indent();
		append(csq);
		return appendEOL();
	}

	public StringBuilder appendLine(char c) {
			indent();
			append(c);
			return appendEOL();
	}

	public StringBuilder builder(){
		return b;
	}

	public String toString(){
		return b.toString();
	}
}