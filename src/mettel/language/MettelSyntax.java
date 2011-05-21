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
import java.util.HashMap;
import java.util.List;

import static mettel.util.MettelStrings.LINE_SEPARATOR;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelSyntax implements MettelBlock {

	private String name = null;

	private List<String> parents = null;

	public String name(){
		return name;
	}

	/*TODO implement hierarchical extension mechanism
	 *
    private MettelSyntax parent = null;
	*/

	private ArrayList<MettelSort> sorts = new ArrayList<MettelSort>();

    private HashMap<String,MettelSort> sortMap = new HashMap<String,MettelSort>();

    private HashMap<MettelSort,ArrayList<MettelBNFStatement>> bnfs = new HashMap<MettelSort,ArrayList<MettelBNFStatement>>();
	/**
	 *
	 */
    @SuppressWarnings("unused")
	private MettelSyntax(){};

	MettelSyntax(String name) {
		this(name, null);
	}

	MettelSyntax(String name, List<String> parents) {
		super();
		this.name = name;
		this.parents = parents;
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
		MettelSort sort = sortMap.get(sortName);
		if(sort != null) return false;
		final MettelSort SORT = new MettelSort(sortName);
		sorts.add(SORT);
		sortMap.put(sortName, SORT);
		final ArrayList<MettelBNFStatement> BNFS = new ArrayList<MettelBNFStatement>();
		bnfs.put(SORT,BNFS);
		return true;
	}

	/**
	 * @param sortName A sort name to check for existence
	 * @returns true if the sort exists
	 */
	boolean sortExists(String sortName) {
		return (sortMap.get(sortName) != null);
	}

	/**
	 * @param sortName A sort name to get
	 * @returns sort
	 */
	MettelSort getSort(String sortName) {
		return sortMap.get(sortName);
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
//		if(statements == null){
//			statements = new ArrayList<MettelBNFStatement>();
			bnfs.put(sort,statements);
//		}
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
		if(parents != null){
			final int SIZE = parents.size();
			if(SIZE >0){
				buf.append(" extends ");
				buf.append(parents.get(0));
				for(int i = 1; i < SIZE; i++){
					buf.append(", ");
					buf.append(parents.get(i));
				}
			}
		}
		buf.append('{');
		buf.append(LINE_SEPARATOR);
		int i = 0;
		buf.append(LINE_SEPARATOR);
		buf.append('\t');
		for(MettelSort sort:sorts){
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

		for(MettelSort sort:sorts){
			ArrayList<MettelBNFStatement> statements = bnfs.get(sort);
			final int SIZE = statements.size();
			if(SIZE > 0){
				buf.append(LINE_SEPARATOR);
				buf.append('\t');
				sort.toBuffer(buf);
				buf.append(' ');
				for(int j = 0; j < SIZE; j++){
					if(j > 0){
						buf.append(LINE_SEPARATOR);
						buf.append("\t| ");
					}
					statements.get(j).toBuffer(buf);
				}
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
	public List<MettelSort> sorts() {
		return sorts;
	}

	/**
	 * @param sort
	 * @return
	 */
	public List<MettelBNFStatement> getBNFs(MettelSort sort) {
		return bnfs.get(sort);
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
