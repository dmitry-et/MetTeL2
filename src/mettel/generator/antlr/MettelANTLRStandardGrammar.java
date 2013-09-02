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

import static mettel.util.MettelStrings.PACKAGE_STRING;
import mettel.util.MettelJavaNames;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 */
public class MettelANTLRStandardGrammar extends MettelANTLRGrammar {

	/**
	 * @param name  Name of the grammar
	 * @param path	Package name
	 * @param options Grammar options
	 */
	public MettelANTLRStandardGrammar(String name, String path,
			MettelANTLRGrammarOptions options) {
		super(name, options);

		final String NAME = MettelJavaNames.firstCharToUpperCase(name);
		final String s = PACKAGE_STRING+' '+path+';';

		addToHeader(s);
		addToHeader("");
		addToHeader("import java.util.Collection;");
		addToHeader("import java.util.ArrayList;");
		addToHeader("import java.util.LinkedHashSet;");
		addToHeader("");
		addToHeader("import mettel.core.tableau.MettelGeneralTableauRule;");

		addToHeader("import "+options.superClass()+';');

		addToLexerHeader(s);

		addToMembers("public Object recoverFromMismatchedToken(IntStream input, int ttype, BitSet follow)");
		addToMembers("       throws RecognitionException{");
		addToMembers("    MismatchedTokenException e = new MismatchedTokenException(ttype, input);");
		addToMembers("    reportError(e);");
		addToMembers("    throw e;");
		addToMembers("}");

		addToMembers("private "+NAME+"ObjectFactory factory = "+NAME+"ObjectFactory.DEFAULT;");

		addToMembers("public "+NAME+"Parser(TokenStream input, "+NAME+"ObjectFactory factory){");
        addToMembers("    this(input);");
        addToMembers("    this.factory = factory;");
        addToMembers("}");

		addToMembers("public "+NAME+"Parser(TokenStream input, RecognizerSharedState state, "+NAME+"ObjectFactory factory){");
        addToMembers("    this(input,state);");
        addToMembers("    this.factory = factory;");
        addToMembers("}");
	}

}
