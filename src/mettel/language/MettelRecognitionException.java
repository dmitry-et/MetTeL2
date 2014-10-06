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

import org.antlr.v4.runtime.IntStream;
import org.antlr.v4.runtime.RecognitionException;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelRecognitionException extends RecognitionException {

	/**
	 *
	 */
	private static final long serialVersionUID = -6523022868000940084L;

	protected int line = -1;
	protected int charPositionInLine = -1;
	
	
	/**
	 *
	 */
	public MettelRecognitionException() {
		super(null,null,null);
	}

	/**
	 * @param input
	 */
	public MettelRecognitionException(IntStream input) {
		super(null,input,null);
		// TODO Auto-generated constructor stub
	}
	
	public MettelRecognitionException(int line, int pos) {
		this();
		this.line = line;
		this.charPositionInLine = pos;
	}

	public String getMessage(){
		return "Something is not right.";//at line "+line+" at position "+charPositionInLine;
	}

}
