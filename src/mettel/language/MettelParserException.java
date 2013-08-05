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

import org.antlr.runtime.Token;

import mettel.MettelRuntimeException;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelParserException extends MettelRuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private  Token token = null;
	private  int line = 0, pos = 0;

	/**
	 *
	 */
	@SuppressWarnings("unused")
	private MettelParserException() {}

	public MettelParserException(String description, Token token){
		super(description);
		this.token = token;
		this.line = token.getLine();
		this.pos = token.getCharPositionInLine();
	}

	public MettelParserException(String description, int line, int pos){
		super(description);
		this.line = line;
		this.pos = pos;
	}


	public String getLocalizedMessage(){
		StringBuilder result = new StringBuilder();
	    String message = super.getLocalizedMessage();
	    if(message == null) message = getClass().getName();
	    result.append(message);
	    if(token != null){
	    	result.append(" on token ");
	    	result.append(token.getText());
	    }
	    result.append(" at line ");
	    result.append(line);
	    result.append(" and position ");
	    result.append(pos);

	    return result.toString();
	}
}
