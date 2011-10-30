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
package mettel.fo;

import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.TokenStream;
/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public abstract class MettelAbstractLogicParser extends Parser implements MettelLogicParser {

	/**
	 * @param input
	 */
	public MettelAbstractLogicParser(TokenStream input) {
		super(input);
	}

	public MettelAbstractLogicParser(TokenStream input, RecognizerSharedState state) {
		super(input,state);
	}

}
