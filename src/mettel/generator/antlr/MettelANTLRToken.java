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

import static mettel.util.MettelStrings.EOF_STRING;
import static mettel.util.MettelStrings.ID_STRING;
import static mettel.util.MettelStrings.LBRACE_STRING;
import static mettel.util.MettelStrings.RBRACE_STRING;
import mettel.util.MettelIndentedStringBuilder;
/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelANTLRToken extends MettelANTLRExpression{


	public static final MettelANTLRToken EOF = new MettelANTLRToken(EOF_STRING);
	public static final MettelANTLRToken ID = new MettelANTLRToken(ID_STRING);
	public static final MettelANTLRToken LBRACE = new MettelANTLRToken(LBRACE_STRING);
	public static final MettelANTLRToken RBRACE = new MettelANTLRToken(RBRACE_STRING);

	private String token = null;

	/**
	 *
	 */
	public MettelANTLRToken(String token) {
		super();
		this.token  = token;
	}

	/* (non-Javadoc)
	 * @see mettel.generator.antlr.MettelANTLRExpression#toStringBuilder(mettel.util.MettelIndentedStringBuilder)
	 */
	@Override
	void toStringBuilder(MettelIndentedStringBuilder b) {
		MettelIndentedStringBuilder ib = new MettelIndentedStringBuilder(b);
		prefixOutput(ib);
		ib.appendLine(token);
		postfixOutput(ib);

	}

}
