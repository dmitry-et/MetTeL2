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
 * @version $Revision$ $Date$
 *
 */
public class MettelBNFStatement{

	/**
	 *
	 */
	private static int id = 0;

	/**
	 *
	 */
	private static final String className = "MettelBNFStatement";

	/**
	 *
	 */
	private String identifier = null;

	/**
	 *
	 */
	private ArrayList<MettelToken> tokens = new ArrayList<MettelToken>();

	public List<MettelToken> tokens(){
		return tokens;
	}

	/**
	 *
	 */
	MettelBNFStatement() {
		this(null);
	}

	MettelBNFStatement(String identifier) {
		super();
		if(identifier == null) {
			this.identifier = className + ++id;
		} else {
			this.identifier = identifier;
		}
	}

	public String identifier(){
		return identifier;
	}

//	public void setIdentifier(String s){
//		identifier = s;
//	}

	/**
	 *
	 */
	void addToken(MettelToken token) {
		tokens.add(token);
	}

	/**
	 * @param buf
	 */
	public void toBuffer(StringBuilder buf) {
		buf.append(identifier);
		buf.append(" =");
		for(MettelToken t:tokens){
			buf.append(' ');
			t.toBuffer(buf);
		}

	}
}
