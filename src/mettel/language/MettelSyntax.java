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
import java.util.List;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision: $ $Date: $
 *
 */
class MettelSyntax implements MettelBlock {

	private String identifier = null;

    private MettelSyntax parent = null;

    private List<MettelSort> sorts = null;

    private List<MettelBNFStatement> statements = null;
	/**
	 *
	 */
    @SuppressWarnings("unused")
	private MettelSyntax(){};

	MettelSyntax(String identifier) {
		super();
		this.identifier = identifier;
	}

	MettelSyntax(String identifier, MettelSyntax parent){
		this(identifier);
		this.parent = parent;
	}

	/**
	 * @returns the parent
	 */
	protected MettelSyntax parent() {
		return parent;
	}


	/**
	 * @param sort A sort to add
	 */
	void addSort(MettelSort sort) {
		if(sort == null) return;
		if(sorts == null) sorts = new ArrayList<MettelSort>();
		sorts.add(sort);
	}

	/**
	 * @return the sorts
	 */
	List<MettelSort> sorts() {
		return sorts;
	}

	/**
	 * @param statements the statements to set
	 */
	void addStatement(MettelBNFStatement statement) {
		if(statement == null) return;
		if(statements == null) statements = new ArrayList<MettelBNFStatement>();
		statements.add(statement);
	}

	/**
	 * @return the statements
	 */
	List<MettelBNFStatement> statements() {
		return statements;
	}

	/**
	 *
	 */
	void unravel(){
		while(parent!= null){
			sorts.addAll(0,parent.sorts);
			statements.addAll(0,parent.statements);
			parent = parent.parent();
		}
	}

}
