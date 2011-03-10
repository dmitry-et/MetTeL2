/**
 * 
 */
package mettel.generator.antlr;

import mettel.util.MettelIndentedStringBuilder;

import static mettel.util.MettelStrings.OPTIONS;
/**
 * @author dmitry
 *
 */
class MettelANTLRGrammarOptions {

    private int k = 1;
    
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

    void toStringBuilder(StringBuilder b) {
	MettelIndentedStringBuilder ib = new MettelIndentedStringBuilder(b,1);
	ib.indent();
	ib.append(OPTIONS);
	ib.append('{');
	ib.appendEOL();
	
	MettelIndentedStringBuilder ibb = new MettelIndentedStringBuilder(ib);
	ibb.indent();
	ibb.append('k');
	ibb.append('=');
	ibb.append(String.valueOf(k));
	ibb.append(';');
	ibb.appendEOL();
	
	ib.append('}');
	ib.appendEOL();
    }
    
    
}
