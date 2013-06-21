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
import mettel.language.MettelToken;
//import mettel.util.MettelJavaNames;

import mettel.generator.java.test.MettelParserTestJavaClassFile;
import mettel.generator.java.test.MettelTableauTestJavaClassFile;

import static mettel.generator.MettelANTLRGrammarGeneratorDefaultOptions.NAME_SEPARATOR;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelJavaPackageStructure {

	@SuppressWarnings("unused")
	private MettelJavaPackageStructure(){}

	private MettelJavaPackage basePackage = null, grammarPackage = null, langPackage = null, testLangPackage = null, testTableauPackage = null, tableauPackage = null, utilLangPackage = null;
	// added by tomas
	private MettelJavaPackage utilLangPropertiesPackage = null;
	
	
	private MettelObjectFactoryJavaInterfaceFile iFactory = null;
	private MettelObjectFactoryJavaClassFile factory = null;
	private MettelExpressionGeneratorJavaInterfaceFile iExpressionGenerator = null;
	private MettelRandomExpressionGeneratorJavaClassFile expressionGenerator = null;
	
	// added by tomas
	private MettelRandomExpressionPropertiesFile randomExpressionPropertiesFile = null;
	private MettelRandomExpressionConfiguratorJavaClassFile randomExpressionConfigurator = null;
	
	private String nameSeparator = NAME_SEPARATOR;
//
//	private MettelTableauObjectFactoryJavaClassFile tfactory = null;

	//private MettelParserTestJavaClassFile testFile = null;

	public MettelJavaPackageStructure(String base){
		super();

		basePackage =  new MettelJavaPackage(base);

		grammarPackage = new MettelJavaPackage(base+".language");// +'.'+GRAMMAR_STRING);
		langPackage = new MettelJavaPackage(base+".language");

		tableauPackage = new MettelJavaPackage(base+".tableau");

		testLangPackage = new MettelJavaPackage(base+".language.test");
		utilLangPackage = new MettelJavaPackage(base+".language.util");
		
		//added by tomas
		utilLangPropertiesPackage = new MettelJavaPackage(base+".language.util.properties");
		
		testTableauPackage = new MettelJavaPackage(base+".tableau.test");
	}

	public void appendParser(MettelANTLRGrammar g){
		grammarPackage.createFile(g.name(),"g").append(g.toStringBuilder());
	}

	public void appendStandardClasses(String prefix, String nameSeparator){
		this.nameSeparator = nameSeparator;
		
		langPackage.add(new MettelExpressionInterfaceFile(prefix,langPackage));
		langPackage.add(new MettelVariableJavaInterfaceFile(prefix,langPackage));

		langPackage.add(new MettelAbstractExpressionJavaClassFile(prefix,langPackage));
		langPackage.add(new MettelAbstractVariableJavaClassFile(prefix,langPackage));

		langPackage.add(new MettelIDComparatorJavaClassFile(prefix,langPackage));
		langPackage.add(new MettelLPOComparatorJavaClassFile(prefix,langPackage));

		iFactory = new MettelObjectFactoryJavaInterfaceFile(prefix,langPackage,nameSeparator);
		langPackage.add(iFactory);

		iExpressionGenerator = new MettelExpressionGeneratorJavaInterfaceFile(prefix,utilLangPackage,langPackage);
		utilLangPackage.add(iExpressionGenerator);

		factory = new MettelObjectFactoryJavaClassFile(prefix,langPackage,nameSeparator);
		langPackage.add(factory);

		expressionGenerator = new MettelRandomExpressionGeneratorJavaClassFile(prefix,utilLangPackage,langPackage,nameSeparator);
		utilLangPackage.add(expressionGenerator);

		// added by tomas
		randomExpressionPropertiesFile = new MettelRandomExpressionPropertiesFile(prefix,utilLangPropertiesPackage);
		utilLangPropertiesPackage.add(randomExpressionPropertiesFile);
		randomExpressionConfigurator = new MettelRandomExpressionConfiguratorJavaClassFile(prefix,utilLangPackage,nameSeparator);
		utilLangPackage.add(randomExpressionConfigurator);
		
		langPackage.add(new MettelTableauObjectFactoryJavaClassFile(prefix,langPackage));
	}

	public void appendStandardClasses(String prefix, String[] sorts, String branchBound){
		langPackage.add(new MettelReplacementJavaInterfaceFile(prefix,langPackage,sorts));
		langPackage.add(new MettelSubstitutionJavaInterfaceFile(prefix,langPackage,sorts));
		langPackage.add(new MettelReplacementJavaClassFile(prefix,langPackage,sorts));
		langPackage.add(new MettelSubstitutionJavaClassFile(prefix,langPackage,sorts));

		if(sorts.length > 0){
			basePackage.add(new MettelTableauProverFile(prefix,sorts[0], branchBound, this));

			testLangPackage.add(new MettelParserTestJavaClassFile(prefix,sorts[0],this));
			testTableauPackage.add(new MettelTableauTestJavaClassFile(prefix,sorts[0],branchBound,this));
		}

		for(String sort:sorts){
			langPackage.add(new MettelComplexExpressionJavaInterfaceFile(prefix,sort,langPackage));
			langPackage.add(new MettelVariableJavaClassFile(prefix,sort,langPackage));
			iFactory.addVariableMethod(sort);
			factory.addVariableMethod(sort);
			factory.addMap(sort);
			iExpressionGenerator.addMethod(sort);
			expressionGenerator.appendSignature(sort);
			
			//added by tomas
			randomExpressionPropertiesFile.appendSignature(sort);
			randomExpressionConfigurator.appendSignature(sort);
		}
		expressionGenerator.generateBody();
		
		//added by tomas
		randomExpressionPropertiesFile.generateBody();
		randomExpressionConfigurator.generateBody();
	}

	public void appendConnectiveClass(String prefix, String sort, String name, String[] sorts, List<MettelToken> tokens, boolean equality){
		MettelComplexExpressionJavaClassFile f = new MettelComplexExpressionJavaClassFile(prefix,sort,name,sorts,langPackage,equality, this.nameSeparator);
		f.addToStringMethod(tokens);
		langPackage.add(f);

		factory.addCreateMethod(sort, name, sorts);
		iFactory.addCreateMethod(sort, name, sorts);
		expressionGenerator.appendSignature(sort, name, sorts);
		
		//added by tomas
		//randomExpressionPropertiesFile.appendSignature(sort, name, sorts);
		randomExpressionPropertiesFile.appendSignature(sort, name);
		randomExpressionConfigurator.appendSignature(sort, name);
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
		//final String srcPath = MettelJavaNames.addSeparator(outputPath)+"src";

		basePackage.flush(outputPath);

		langPackage.flush(outputPath);
		grammarPackage.flush(outputPath);

		tableauPackage.flush(outputPath);

		testLangPackage.flush(outputPath);
		testTableauPackage.flush(outputPath);

		utilLangPackage.flush(outputPath);
		
		// added by tomas
		utilLangPropertiesPackage.flush(outputPath);
	}

	/**
	 * @return
	 */
	public MettelJavaPackage grammarPackage() {
		return grammarPackage;
	}

	/**
	 * @return
	 */
	public MettelJavaPackage testTableauPackage() {
		return testTableauPackage;
	}

	/**
	 * @return
	 */
	public MettelJavaPackage languagePackage() {
		return langPackage;
	}

	/**
	 * @return
	 */
	public MettelJavaPackage basePackage() {
		return basePackage;
	}

	/**
	 * @return
	 */
	public MettelJavaPackage testLanguagePackage() {
		return testLangPackage;
	}

	/**
	 * @return
	 */
	public MettelJavaPackage tableauPackage() {
		return tableauPackage;
	}

}
