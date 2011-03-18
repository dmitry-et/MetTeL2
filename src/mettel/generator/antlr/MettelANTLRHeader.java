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

import java.util.ArrayList;

import mettel.util.MettelIndentedStringBuilder;

import static mettel.util.MettelStrings.HEADER_STRING;
import static mettel.util.MettelStrings.LEXER_STRING;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
class MettelANTLRHeader {

    /**
     *
     */
    MettelANTLRHeader() {
	this(PARSER);
    }

    private int kind = PARSER;
    final static int PARSER = 0;
    final static int LEXER = 1;

    MettelANTLRHeader(int kind){
	super();
	this.kind = kind;
    }

    ArrayList<String> statements = new ArrayList<String>();

    void addStatement(String s){
	statements.add(s);
    }

    /**
     * @param b
     */
    public void toStringBuilder(MettelIndentedStringBuilder b) {
	MettelIndentedStringBuilder ib = new MettelIndentedStringBuilder(b);
	ib.append('@');
	if(kind == LEXER){
	    ib.append(LEXER_STRING);
	    ib.append(':');
	    ib.append(':');
	}
	ib.append(HEADER_STRING);
	ib.append('{');
	ib.appendEOL();

	for(String s:statements){
	    ib.appendLine(s);
	}

	ib.append('}');
	ib.appendEOL();
	}

}
