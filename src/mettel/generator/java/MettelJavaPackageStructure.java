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
package mettel.generator.java;

import static mettel.util.MettelStrings.GRAMMAR_STRING;
import static mettel.util.MettelStrings.LEXER_STRING;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import mettel.generator.antlr.MettelANTLRGrammar;
import mettel.util.MettelIndentedStringBuilder;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelJavaPackageStructure {

	@SuppressWarnings("unused")
	private MettelJavaPackageStructure(){}

	private MettelJavaPackage grammarPackage = null, langPackage = null;

	public MettelJavaPackageStructure(String base){
		super();

		grammarPackage = new MettelJavaPackage(base +'.'+GRAMMAR_STRING);
		langPackage = new MettelJavaPackage(base);

	}

	public void appendParser(MettelANTLRGrammar g){
		grammarPackage.createFile(g.name() + ".g").append(g.toStringBuilder());
	}

	public void appendTrivialLexer(){
		MettelIndentedStringBuilder b = new MettelIndentedStringBuilder(new StringBuilder());
		b.append(GRAMMAR_STRING);
		b.append(' ');
		b.append(LEXER_STRING);
		b.append(' ');
		b.append("Trivial");
		b.append(';');
		b.appendEOL();
			//TODO header

		BufferedReader r = new BufferedReader(
			new InputStreamReader(
				this.getClass().getResourceAsStream("resources/lexer")));
		String s;
		try {
		   while((s = r.readLine()) != null){
		        b.append(s);
		        b.appendEOL();
		   }
		} catch (IOException e) {
		   e.printStackTrace();
		}
	}



	public void flush(String outputPath) throws IOException {
		langPackage.flush(outputPath);
		grammarPackage.flush(outputPath);
	}

}
