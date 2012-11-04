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
import mettel.util.MettelJavaNames;

import static mettel.util.MettelStrings.OPTIONS_STRING;
/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelANTLRGrammarOptions {

	/* A copy from org.antlr.tool.Grammar, ANTLR v3.1.3:
		282		//Is there a global fixed lookahead set for this grammar? If 0, nothing specified. -1 implies we have not looked at the options table yet to set k.
		283		protected int global_k = -1;

	 */
    private int k = 1;
    private String superClass = "mettel.generator.MettelAbstractLogicParser";
    private boolean backtrack = false;
    private boolean memoize = false;

    String superClass(){
    	return superClass;
    }

    /**
     *
     */
    public MettelANTLRGrammarOptions() {
    	this(1);
    }

    public MettelANTLRGrammarOptions(int k) {
    	super();
    	if(k < 0) throw new MettelANTLRGrammarOptionsException("Wrong lookahead parameter: "+k);
    	this.k = k;
    }

    public MettelANTLRGrammarOptions(int k, String superClass) {
    	this(k);
    	this.superClass = superClass;
    }

    public MettelANTLRGrammarOptions(int k, String superClass, boolean backtrack, boolean memoize) {
    	this(k, superClass);
    	this.backtrack = backtrack;
    	this.memoize = memoize;
    }

    void toStringBuilder(MettelIndentedStringBuilder b) {
	MettelIndentedStringBuilder ib = new MettelIndentedStringBuilder(b);
	ib.indent();
	ib.append(OPTIONS_STRING);
	ib.append('{');
	ib.appendEOL();

	MettelIndentedStringBuilder ibb = new MettelIndentedStringBuilder(ib);
	ibb.indent();
	if(k > 0){
		ibb.append('k');
		ibb.append('=');
		ibb.append(String.valueOf(k));
		ibb.append(';');
		ibb.appendEOL();
		ibb.indent();
	}
	ibb.append("superClass=");
	ibb.append(MettelJavaNames.getClassName(superClass));
	ibb.append(';');
	ibb.appendEOL();
	ibb.indent();
	ibb.append("backtrack=");
	ibb.append(String.valueOf(backtrack));
	ibb.append(';');
	ibb.appendEOL();
	ibb.indent();
	ibb.append("memoize=");
	ibb.append(String.valueOf(memoize));
	ibb.append(';');

	ibb.appendEOL();

	ib.append('}');
	ib.appendEOL();
    }


}
