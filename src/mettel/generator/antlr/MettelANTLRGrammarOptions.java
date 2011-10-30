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

import mettel.util.MettelIndentedStringBuilder;

import static mettel.util.MettelStrings.OPTIONS_STRING;
/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
class MettelANTLRGrammarOptions {

    private int k = 1;
    private String superClass = "MettelAbstractLogicParser";

    /**
     *
     */
    MettelANTLRGrammarOptions() {
	super();
	this.k = 1;
    }

    MettelANTLRGrammarOptions(int k) {
	super();
	this.k = k;
    }

    void toStringBuilder(MettelIndentedStringBuilder b) {
	MettelIndentedStringBuilder ib = new MettelIndentedStringBuilder(b);
	ib.indent();
	ib.append(OPTIONS_STRING);
	ib.append('{');
	ib.appendEOL();

	MettelIndentedStringBuilder ibb = new MettelIndentedStringBuilder(ib);
	ibb.indent();
	ibb.append('k');
	ibb.append('=');
	ibb.append(String.valueOf(k));
	ibb.append(';');
	ibb.append("superClass=");
	ibb.append(superClass);
	ibb.append(';');
	ibb.appendEOL();

	ib.append('}');
	ib.appendEOL();
    }


}
