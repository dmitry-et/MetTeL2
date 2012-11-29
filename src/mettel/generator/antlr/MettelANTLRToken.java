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
	public static final MettelANTLRToken ID = new MettelANTLRToken(ID_STRING,true);
	public static final MettelANTLRToken LBRACE = new MettelANTLRToken(LBRACE_STRING);
	public static final MettelANTLRToken RBRACE = new MettelANTLRToken(RBRACE_STRING);

	private String token = null;

	private boolean returns = false;

	/**
	 *
	 */
	public MettelANTLRToken(String token){
		this(token,false);
	}

	public MettelANTLRToken(String token, boolean returns) {
		this(token, returns, null);
	}
	
	public MettelANTLRToken(String token, boolean returns, MettelANTLRSyntacticPredicate syntacticPredicate) {
		super(syntacticPredicate);
		this.token  = token;
		this.returns = returns;
		//appendJavaToPostfix("r0 = t.getText();");
	}

	/* (non-Javadoc)
	 * @see mettel.generator.antlr.MettelANTLRExpression#toStringBuilder(mettel.util.MettelIndentedStringBuilder)
	 */
	@Override
	void toStringBuilder0(MettelIndentedStringBuilder b, boolean omitJavaBlocks) {
		//eMettelIndentedStringBuilder ib = new MettelIndentedStringBuilder(b);
		//prefixOutput(ib);
		if(returns && !omitJavaBlocks) b.append("t = ");
		b.append(token);
		//postfixOutput(ib);

	}

}
