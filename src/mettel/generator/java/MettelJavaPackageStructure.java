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

//import static mettel.util.MettelStrings.GRAMMAR_STRING;
//import static mettel.util.MettelStrings.LEXER_STRING;


//import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
//import java.io.InputStream;
//import java.io.InputStreamReader;

import mettel.generator.antlr.MettelANTLRGrammar;
//import mettel.generator.antlr.MettelANTLRHeader;
//import mettel.util.MettelIndentedStringBuilder;
import mettel.language.MettelToken;

import mettel.generator.java.test.MettelParserTestJavaClassFile;
import mettel.generator.java.test.MettelTableauTestJavaClassFile;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelJavaPackageStructure {

	@SuppressWarnings("unused")
	private MettelJavaPackageStructure(){}

	private MettelJavaPackage grammarPackage = null, langPackage = null, testPackage = null;

	private MettelObjectFactoryJavaInterfaceFile iFactory = null;
	private MettelObjectFactoryJavaClassFile factory = null;

	//private MettelParserTestJavaClassFile testFile = null;

	public MettelJavaPackageStructure(String base){
		super();

		grammarPackage = new MettelJavaPackage(base);// +'.'+GRAMMAR_STRING);
		langPackage = new MettelJavaPackage(base);
		testPackage = new MettelJavaPackage(base+".test");

	}

	public void appendParser(MettelANTLRGrammar g){
		grammarPackage.createFile(g.name(),"g").append(g.toStringBuilder());
	}

	public void appendStandardClasses(String prefix){
		langPackage.add(new MettelExpressionInterfaceFile(prefix,langPackage));
		langPackage.add(new MettelVariableJavaInterfaceFile(prefix,langPackage));

		langPackage.add(new MettelAbstractExpressionJavaClassFile(prefix,langPackage));
		langPackage.add(new MettelAbstractVariableJavaClassFile(prefix,langPackage));

		iFactory = new MettelObjectFactoryJavaInterfaceFile(prefix,langPackage);
		langPackage.add(iFactory);

		factory = new MettelObjectFactoryJavaClassFile(prefix,langPackage);
		langPackage.add(factory);
	}

	public void appendStandardClasses(String prefix, String[] sorts){
		langPackage.add(new MettelReplacementJavaInterfaceFile(prefix,langPackage,sorts));
		langPackage.add(new MettelSubstitutionJavaInterfaceFile(prefix,langPackage,sorts));
		langPackage.add(new MettelReplacementJavaClassFile(prefix,langPackage,sorts));
		langPackage.add(new MettelSubstitutionJavaClassFile(prefix,langPackage,sorts));

		if(sorts.length > 0){
			testPackage.add(new MettelParserTestJavaClassFile(prefix,sorts[0],testPackage));
			testPackage.add(new MettelTableauTestJavaClassFile(prefix,sorts[0],testPackage));
		}

		for(String sort:sorts){
			langPackage.add(new MettelComplexExpressionJavaInterfaceFile(prefix,sort,langPackage));
			langPackage.add(new MettelVariableJavaClassFile(prefix,sort,langPackage));
			iFactory.addVariableMethod(sort);
			factory.addVariableMethod(sort);
		}
	}

	public void appendConnectiveClass(String prefix, String sort, String name, String[] sorts, List<MettelToken> tokens){
		MettelComplexExpressionJavaClassFile f = new MettelComplexExpressionJavaClassFile(prefix,sort,name,sorts,langPackage);
		f.addToStringMethod(tokens);
		langPackage.add(f);
		factory.addCreateMethod(sort, name, sorts);
		iFactory.addCreateMethod(sort, name, sorts);
	}

	/*public void appendLexer(String name, MettelANTLRHeader h, InputStream stream){
		MettelIndentedStringBuilder b = new MettelIndentedStringBuilder(new StringBuilder());
		b.append(LEXER_STRING);
		b.append(' ');
		b.append(GRAMMAR_STRING);
		b.append(' ');
		b.append("Trivial");
		b.append(';');
		b.appendEOL();

		h.toStringBuilder(b);

		BufferedReader r = new BufferedReader(
			new InputStreamReader(stream));
		String s;
		try {
		   while((s = r.readLine()) != null){
		        b.append(s);
		        b.appendEOL();
		   }
		} catch (IOException e) {
		   e.printStackTrace();
		}
		grammarPackage.createFile(name + ".g").append(b.toString());
	}*/



	public void flush(String outputPath) throws IOException {
		langPackage.flush(outputPath);
		grammarPackage.flush(outputPath);
		testPackage.flush(outputPath);
	}

}
