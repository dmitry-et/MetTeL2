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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import mettel.generator.antlr.MettelANTLRGrammar;
import mettel.language.MettelToken;

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

	private MettelJavaPackage basePackage = null;
	private MettelJavaPackage etcPackage = null;
	
	private MettelRandomExpressionGeneratorPropertiesFile randomExpressionPropertiesFile = null;
	private MettelBenchmarkJavaClassFile benchmarkGenerator = null;
	
	private String nameSeparator = NAME_SEPARATOR;

	public String nameSeparator(){
		return nameSeparator;
	}

	private class LanguagePackages{
		
		private LanguagePackages(String name){
			String path = basePackage.path()+".language."+name;
			
			grammarPackage = new MettelJavaPackage(path);
			langPackage = grammarPackage;

			testLangPackage = new MettelJavaPackage(path+".test");
			utilLangPackage = new MettelJavaPackage(path+".util");
		}
		
		private MettelJavaPackage grammarPackage = null, langPackage = null, testLangPackage = null, utilLangPackage = null;
		
		private MettelObjectFactoryJavaInterfaceFile iFactory = null;
		private MettelObjectFactoryJavaClassFile factory = null;
		private MettelExpressionGeneratorJavaInterfaceFile iExpressionGenerator = null;
		private MettelRandomExpressionGeneratorJavaClassFile expressionGenerator = null;
		private MettelProblemAnalyzerJavaClassFile problemAnalyzer = null;
	}
	
	private HashMap<String,LanguagePackages> langPacks = new HashMap<String,LanguagePackages>();

	public LanguagePackages initLanguage(String name){
		LanguagePackages packs = langPacks.get(name);
		if(packs == null){
			packs = new LanguagePackages(name);
			langPacks.put(name, packs);
		}
		return packs;
	}
	
    private class TableauPackages{
		
		private TableauPackages(String name){
			String path = basePackage.path()+".tableau."+name;
					
			tableauPackage = new MettelJavaPackage(path);
			testTableauPackage = new MettelJavaPackage(path+".test");
		}
		
		private MettelJavaPackage testTableauPackage = null, tableauPackage = null;
		MettelBenchmarkJavaClassFile benchmark = null;
		
	}
	
	private HashMap<String,TableauPackages> tabPacks = new HashMap<String,TableauPackages>();

	public TableauPackages initTableau(String name){
		TableauPackages packs = tabPacks.get(name);
		if(packs == null){
			packs = new TableauPackages(name);
			tabPacks.put(name, packs);
		}
		return packs;
	}
	
	public MettelJavaPackageStructure(String base){
		super();
		basePackage =  new MettelJavaPackage(base);
		etcPackage = new MettelJavaPackage("etc");
	}

	public void appendParser(String name, MettelANTLRGrammar g){
		LanguagePackages packs = initLanguage(name);
		packs.grammarPackage.createFile(g.name(),"g").append(g.toStringBuilder());
	}

	public void appendStandardClasses(String prefix){
		randomExpressionPropertiesFile = new MettelRandomExpressionGeneratorPropertiesFile(prefix,etcPackage);
		etcPackage.add(randomExpressionPropertiesFile);
	}
	
	public void appendStandardLanguageClasses(String name, String prefix, String nameSeparator){
		this.nameSeparator = nameSeparator;
		
		LanguagePackages packs = initLanguage(name);		
		MettelJavaPackage langPackage = packs.langPackage;
		MettelJavaPackage utilLangPackage = packs.utilLangPackage;

		langPackage.add(new MettelExpressionInterfaceFile(prefix,langPackage));
		langPackage.add(new MettelVariableJavaInterfaceFile(prefix,langPackage));

		langPackage.add(new MettelAbstractExpressionJavaClassFile(prefix,langPackage));
		langPackage.add(new MettelAbstractVariableJavaClassFile(prefix,langPackage));

		langPackage.add(new MettelIDComparatorJavaClassFile(prefix,langPackage));
		langPackage.add(new MettelLPOComparatorJavaClassFile(prefix,langPackage));

		MettelObjectFactoryJavaInterfaceFile iFactory = new MettelObjectFactoryJavaInterfaceFile(prefix,langPackage,nameSeparator);
		langPackage.add(iFactory);
		packs.iFactory = iFactory;
		
		MettelExpressionGeneratorJavaInterfaceFile iExpressionGenerator = new MettelExpressionGeneratorJavaInterfaceFile(prefix,utilLangPackage,langPackage,nameSeparator);
		utilLangPackage.add(iExpressionGenerator);
		packs.iExpressionGenerator = iExpressionGenerator;

		MettelObjectFactoryJavaClassFile factory = new MettelObjectFactoryJavaClassFile(prefix,langPackage,nameSeparator);
		langPackage.add(factory);
		packs.factory = factory;
		
		MettelRandomExpressionGeneratorJavaClassFile expressionGenerator = new MettelRandomExpressionGeneratorJavaClassFile(prefix,utilLangPackage,langPackage,nameSeparator);
		utilLangPackage.add(expressionGenerator);
		packs.expressionGenerator = expressionGenerator;
		
		MettelProblemAnalyzerJavaClassFile problemAnalyzer = new MettelProblemAnalyzerJavaClassFile(prefix, langPackage, nameSeparator);
		langPackage.add(problemAnalyzer);
		packs.problemAnalyzer = problemAnalyzer;

		langPackage.add(new MettelTableauObjectFactoryJavaClassFile(prefix,langPackage));
	}
	
	public void appendStandardTableauClasses(String tabName, String synName, String prefix, String nameSeparator){
		this.nameSeparator = nameSeparator;
		
		TableauPackages tabPacks = initTableau(tabName);		
		MettelJavaPackage tableauPackage = tabPacks.tableauPackage;
		
		LanguagePackages synPacks = initLanguage(synName);		
		MettelJavaPackage langPackage = synPacks.langPackage;
		
		MettelBenchmarkJavaClassFile benchmark = new MettelBenchmarkJavaClassFile(prefix, tableauPackage, langPackage, nameSeparator);
		tableauPackage.add(benchmark);
		tabPacks.benchmark = benchmark; 
	}
	

	public void appendStandardClasses(String prefix, String[] sorts, String branchBound){
		langPackage.add(new MettelReplacementJavaInterfaceFile(prefix,langPackage,sorts, nameSeparator));
		langPackage.add(new MettelSubstitutionJavaInterfaceFile(prefix,langPackage,sorts, nameSeparator));
		langPackage.add(new MettelReplacementJavaClassFile(prefix,langPackage,sorts, nameSeparator));
		langPackage.add(new MettelSubstitutionJavaClassFile(prefix,langPackage,sorts, nameSeparator));

		if(sorts.length > 0){
			basePackage.add(new MettelTableauProverFile(prefix,sorts[0], branchBound, this));

			testLangPackage.add(new MettelParserTestJavaClassFile(prefix,sorts[0],this));
			testTableauPackage.add(new MettelTableauTestJavaClassFile(prefix,sorts[0],branchBound,this));
		}

		for(String sort:sorts){
			langPackage.add(new MettelComplexExpressionJavaInterfaceFile(prefix,sort,langPackage,nameSeparator));
			langPackage.add(new MettelVariableJavaClassFile(prefix,sort,langPackage,nameSeparator));
			iFactory.addVariableMethod(sort);
			factory.addVariableMethod(sort);
			factory.addMap(sort);
			iExpressionGenerator.addMethod(sort);
			expressionGenerator.appendSignature(sort);
			
			//added by tomas
			randomExpressionPropertiesFile.appendSignature(sort);
			problemAnalyzerGenerator.appendSignature(sort);
			benchmarkGenerator.appendSignature(sort);
		}
		expressionGenerator.generateBody();
		
		//added by tomas
		randomExpressionPropertiesFile.generateBody();
		problemAnalyzerGenerator.generateBody();
		benchmarkGenerator.generateBody();
	}

	public void appendConnectiveClass(String prefix, String sort, String name, String[] sorts, List<MettelToken> tokens, boolean equality){
		MettelComplexExpressionJavaClassFile f = new MettelComplexExpressionJavaClassFile(prefix,sort,name,sorts,langPackage,equality, this.nameSeparator);
		f.addToStringMethod(tokens);
		langPackage.add(f);

		factory.addCreateMethod(sort, name, sorts);
		iFactory.addCreateMethod(sort, name, sorts);
		expressionGenerator.appendSignature(sort, name, sorts);
		
		//added by tomas
		randomExpressionPropertiesFile.appendSignature(sort, name);
		problemAnalyzerGenerator.appendSignature(sort, name, sorts);
		//probably not needed and do like randomexpresionpropertiesfile?
		benchmarkGenerator.appendSignature(sort, name, sorts);
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
