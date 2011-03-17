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

import java.util.Collection;
import java.util.HashMap;

import static mettel.util.MettelStrings.LINE_SEPARATOR;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelSpecification {

	private String path = null;

	private HashMap<String,MettelSyntax> syntaxTable = new HashMap<String,MettelSyntax>();

	public MettelSyntax getSyntax(String name){
		return syntaxTable.get(name);
	}

	@SuppressWarnings("unused")
	private MettelSpecification(){}

	MettelSpecification(String path){
		super();
		this.path = path;
	}

	/**
	 * @return the path
	 */
	public String path() {
		return path;
	}


	/**
	 *
	 */
	public void toBuffer(StringBuilder buf){
		buf.append("specification ");
		buf.append(path);
		buf.append(';');
		buf.append(LINE_SEPARATOR);
		for(MettelSyntax syn:syntaxTable.values()){
			buf.append(LINE_SEPARATOR);
			syn.toBuffer(buf);
		}

	}

	/**
	 * @param syn
	 */
	void addSyntax(MettelSyntax syn) {
		syntaxTable.put(syn.name(), syn);

	}

	/**
	 * @return
	 */
	public Collection<MettelSyntax> syntaxes() {
		return syntaxTable.values();
	}

}
