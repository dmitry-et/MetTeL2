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
package mettel.language;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static mettel.util.MettelStrings.LINE_SEPARATOR;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelSyntax implements MettelBlock {

	private String name = null;

	String name(){
		return name;
	}

	/*TODO implement hierarchical extension mechanism
	 *
    private MettelSyntax parent = null;
	*/

    private HashMap<String,MettelSort> sorts = new HashMap<String,MettelSort>();

    private HashMap<MettelSort,ArrayList<MettelBNFStatement>> bnfs = new HashMap<MettelSort,ArrayList<MettelBNFStatement>>();
	/**
	 *
	 */
    @SuppressWarnings("unused")
	private MettelSyntax(){};

	MettelSyntax(String name) {
		super();
		this.name = name;
	}

/*TODO implement hierarchical extension mechanism
 *
  	MettelSyntax(String identifier, MettelSyntax parent){
		this(identifier);
		this.parent = parent;
	}
*/
	/**
	 * @returns the parent
     *TODO implement hierarchical extension mechanism
     *
	protected MettelSyntax parent() {
		return parent;
	}
	*/

	/**
	 * @param sort A sort to add
	 */
/*	void append(MettelSort sort) {
		if(sort == null) return;
		sorts.put(sort.name(),sort);
	}
*/

	/**
	 * @param sortName A sort name to add
	 */
	boolean addSort(String sortName) {
		MettelSort sort = sorts.get(sortName);
		if(sort != null) return false;
		sorts.put(sortName, new MettelSort(sortName));
		return true;
	}

	/**
	 * @param sortName A sort name to check for existence
	 * @returns true if the sort exists
	 */
	boolean sortExists(String sortName) {
		return (sorts.get(sortName) != null);
	}

	/**
	 * @param sortName A sort name to get
	 * @returns sort
	 */
	MettelSort getSort(String sortName) {
		return sorts.get(sortName);
	}

	/**
	 * @param sorts A collection of sorts to add
	 */
/*	void append(Collection<MettelSort> sorts) {
		if(sorts == null) return;
		for(MettelSort sort: sorts){
			this.sorts.put(sort.name(),sort);
		}
	}
*/

//	/**
//	 * @return the sorts
//	 */
//	HashMap<String,MettelSort> sortTable() {
//		return sorts;
//	}

//	/**
//	 * @return the sorts
//	 */
//	Collection<MettelSort> sorts() {
//		return sorts.values();
//	}

	/**
	 * @param statement A BNF statement to add
	 */
	void addBNF(MettelSort sort,MettelBNFStatement statement) {
		if(statement == null) return;
		ArrayList<MettelBNFStatement> statements = bnfs.get(sort);
		if(statements == null){
			statements = new ArrayList<MettelBNFStatement>();
			bnfs.put(sort,statements);
		}
		statements.add(statement);
	}

	/**
	 * @param statements A list of BNF statements to add
	 */
	/*void append(List<MettelBNFStatement> statements) {
		if(statements == null) return;
		this.statements.addAll(statements);
	}*/

	/**
	 * @param buf
	 */
	public void toBuffer(StringBuilder buf) {
		buf.append("syntax ");
		buf.append(name);
		buf.append('{');
		buf.append(LINE_SEPARATOR);
		int i = 0;
		buf.append(LINE_SEPARATOR);
		buf.append('\t');
		for(MettelSort sort:sorts.values()){
			if(i == 0){
				buf.append("sort ");
			}else{
				buf.append(',');
			}
			sort.toBuffer(buf);
			i++;
		}
		if(i > 0){
			buf.append(';');
			buf.append(LINE_SEPARATOR);
		}

		for(MettelSort sort:bnfs.keySet()){
			buf.append(LINE_SEPARATOR);
			buf.append('\t');
			sort.toBuffer(buf);
			buf.append(' ');
			ArrayList<MettelBNFStatement> statements = bnfs.get(sort);
			i = 0;
			for(MettelBNFStatement s:statements){
				if(i > 0){
					buf.append(LINE_SEPARATOR);
					buf.append("\t| ");
				}
				s.toBuffer(buf);
				i++;
			}
			if(i > 0){
				buf.append(';');
				buf.append(LINE_SEPARATOR);
			}
		}

		buf.append(LINE_SEPARATOR);
		buf.append('}');
	}

	/**
	 * @return
	 */
	public Collection<MettelSort> sorts() {
		return sorts.values();
	}


//	/**
//	 * @return the statements
//	 */
//	ArrayList<MettelBNFStatement> statements() {
//		return statements;
//	}

	/*TODO implement hierarchical extension mechanism
	 *
	void unravel(){
		while(parent!= null){
			sorts.addAll(0,parent.sorts);
			statements.addAll(0,parent.statements);
			parent = parent.parent();
		}
	}
	*/

	/**
	 *
	 */
//	void generateParser(PrintWriter out){
//		//TODO header
//		for(MettelSort sort:bnfs.keySet()){
//			ArrayList<MettelBNFStatement> statements = bnfs.get(sort);
//			String nodeName = sort.name()+"Primitive";
//			out.println(nodeName);
//			out.println(':');
//				out.println("IDENTIFIER");
//			out.println(';');
//			int i = 0;
//			for(MettelBNFStatement s:statements){
//				if(i == 0){
//					//identifier
//					so = s;
//				}else{
//					buf.append(LINE_SEPARATOR);
//					buf.append("\t| ");
//				}
//				s.toBuffer(buf);
//				i++;
//			}
//			if(i > 0){
//				buf.append(';');
//				buf.append(LINE_SEPARATOR);
//			}
//		}
//		//TODO footer
//	}

}
