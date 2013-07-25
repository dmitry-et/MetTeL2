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

import java.util.List;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelTableau implements MettelBlock {

	private String name = null;
	
	private MettelSyntax syntax = null;

	private List<MettelTableau> parents = null;

	public String name(){
		return name;
	}

	public MettelSyntax syntax(){
		return syntax;
	}
	
	public List<MettelTableau> parents(){
		return parents;
	}

	/*TODO implement hierarchical extension mechanism
     */

	private String content = null;

	public String content(){
		return content;
	}

	void setContent(String content){
		this.content = content;
	}

	/**
	 *
	 */
    @SuppressWarnings("unused")
	private MettelTableau(){};

	MettelTableau(String name, MettelSyntax syntax) {
		this(name, syntax, null);
	}

	MettelTableau(String name, MettelSyntax syntax, List<MettelTableau> parents) {
		super();
		this.name = name;
		this.syntax = syntax;
		this.parents = parents;
	}

}
