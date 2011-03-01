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
import java.util.List;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
class MettelSyntax implements MettelBlock {

	private String identifier = null;

	/*TODO implement hierarchical extension mechanism
	 *
    private MettelSyntax parent = null;
	*/

    private HashMap<String,MettelSort> sorts = new HashMap<String,MettelSort>();

    private ArrayList<MettelBNFStatement> statements = new ArrayList<MettelBNFStatement>();
	/**
	 *
	 */
    @SuppressWarnings("unused")
	private MettelSyntax(){};

	MettelSyntax(String identifier) {
		super();
		this.identifier = identifier;
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
	void append(MettelSort sort) {
		if(sort == null) return;
		sorts.put(sort.name(),sort);
	}

	/**
	 * @param sorts A collection of sorts to add
	 */
	void append(Collection<MettelSort> sorts) {
		if(sorts == null) return;
		for(MettelSort sort: sorts){
			this.sorts.put(sort.name(),sort);
		}
	}

	/**
	 * @return the sorts
	 */
	HashMap<String,MettelSort> sortTable() {
		return sorts;
	}

	/**
	 * @return the sorts
	 */
	Collection<MettelSort> sorts() {
		return sorts.values();
	}

	/**
	 * @param statement A BNF statement to add
	 */
	void append(MettelBNFStatement statement) {
		if(statement == null) return;
		statements.add(statement);
	}

	/**
	 * @param statements A list of BNF statements to add
	 */
	void append(List<MettelBNFStatement> statements) {
		if(statements == null) return;
		this.statements.addAll(statements);
	}


	/**
	 * @return the statements
	 */
	ArrayList<MettelBNFStatement> statements() {
		return statements;
	}

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

}
