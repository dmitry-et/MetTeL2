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

		private void appendStandardClasses(String prefix, String nameSeparator){
			langPackage.add(new MettelExpressionInterfaceFile(prefix,langPackage));
			langPackage.add(new MettelVariableJavaInterfaceFile(prefix,langPackage));

			langPackage.add(new MettelAbstractExpressionJavaClassFile(prefix,langPackage));
			langPackage.add(new MettelAbstractVariableJavaClassFile(prefix,langPackage));

			langPackage.add(new MettelIDComparatorJavaClassFile(prefix,langPackage));
			langPackage.add(new MettelLPOComparatorJavaClassFile(prefix,langPackage));

			iFactory = new MettelObjectFactoryJavaInterfaceFile(prefix,langPackage,nameSeparator);
			langPackage.add(iFactory);

			iExpressionGenerator = new MettelExpressionGeneratorJavaInterfaceFile(prefix,utilLangPackage,langPackage,nameSeparator);
			utilLangPackage.add(iExpressionGenerator);

			factory = new MettelObjectFactoryJavaClassFile(prefix,langPackage,nameSeparator);
			langPackage.add(factory);

			expressionGenerator = new MettelRandomExpressionGeneratorJavaClassFile(prefix,utilLangPackage,langPackage,nameSeparator);
			utilLangPackage.add(expressionGenerator);

			problemAnalyzer = new MettelProblemAnalyzerJavaClassFile(prefix, langPackage, nameSeparator);
			langPackage.add(problemAnalyzer);

			langPackage.add(new MettelTableauObjectFactoryJavaClassFile(prefix,langPackage));
		}

		private void appendStandardClasses(String prefix, String[] sorts, String branchBound){
			langPackage.add(new MettelReplacementJavaInterfaceFile(prefix,langPackage,sorts, nameSeparator));
			langPackage.add(new MettelSubstitutionJavaInterfaceFile(prefix,langPackage,sorts, nameSeparator));
			langPackage.add(new MettelReplacementJavaClassFile(prefix,langPackage,sorts, nameSeparator));
			langPackage.add(new MettelSubstitutionJavaClassFile(prefix,langPackage,sorts, nameSeparator));

			if(sorts.length > 0){
				testLangPackage.add(new MettelParserTestJavaClassFile(prefix,sorts[0], MettelJavaPackageStructure.this));
			}

			for(String sort:sorts){
				langPackage.add(new MettelComplexExpressionJavaInterfaceFile(prefix,sort,langPackage,nameSeparator));
				langPackage.add(new MettelVariableJavaClassFile(prefix,sort,langPackage,nameSeparator));
				iFactory.addVariableMethod(sort);
				factory.addVariableMethod(sort);
				factory.addMap(sort);
				iExpressionGenerator.addMethod(sort);
				expressionGenerator.appendSignature(sort);

				randomExpressionPropertiesFile.appendSignature(sort);
				problemAnalyzer.appendSignature(sort);
			}
			expressionGenerator.generateBody();

			randomExpressionPropertiesFile.generateBody();
			problemAnalyzer.generateBody();
		}

		private void appendConnectiveClass(String prefix, String sort, String name, String[] sorts, List<MettelToken> tokens, boolean equality){
			MettelComplexExpressionJavaClassFile f = new MettelComplexExpressionJavaClassFile(prefix,sort,name,sorts,langPackage, equality, nameSeparator);
			f.addToStringMethod(tokens);
			langPackage.add(f);

			factory.addCreateMethod(sort, name, sorts);
			iFactory.addCreateMethod(sort, name, sorts);
			expressionGenerator.appendSignature(sort, name, sorts);

			randomExpressionPropertiesFile.appendSignature(sort, name);
			problemAnalyzer.appendSignature(sort, name, sorts);
		}

		private void flush(String outputPath) throws IOException {
			langPackage.flush(outputPath);
			//grammarPackage.flush(outputPath);//langPackage=grammarPackage

			testLangPackage.flush(outputPath);

			utilLangPackage.flush(outputPath);
		}

	}

	private HashMap<String,LanguagePackages> langPacks = new HashMap<String,LanguagePackages>();

	public LanguagePackages language(String name){
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
		private MettelBenchmarkJavaClassFile benchmark = null;

		private void appendStandardClasses(MettelJavaPackage langPackage, String prefix, String nameSeparator){
			benchmark = new MettelBenchmarkJavaClassFile(prefix, tableauPackage, langPackage, nameSeparator);
			tableauPackage.add(benchmark);
		}

		private void appendStandardClasses(String prefix, String[] sorts, String branchBound){
			if(sorts.length > 0){
				testTableauPackage.add(new MettelTableauTestJavaClassFile(prefix,sorts[0],branchBound,MettelJavaPackageStructure.this));
			}
			for(String sort:sorts){
				benchmark.appendSignature(sort);
			}
			benchmark.generateBody();
		}

		private void appendConnectiveClass(String sort, String name, String[] sorts){
			benchmark.appendSignature(sort, name, sorts);
		}

		private void flush(String outputPath) throws IOException {
			tableauPackage.flush(outputPath);
			testTableauPackage.flush(outputPath);
		}
	}

	private HashMap<String,TableauPackages> tabPacks = new HashMap<String,TableauPackages>();

	public TableauPackages tableau(String name){
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
		LanguagePackages packs = language(name);
		packs.grammarPackage.createFile(g.name(),"g").append(g.toStringBuilder());
	}

	public void appendStandardClasses(String prefix){
		randomExpressionPropertiesFile = new MettelRandomExpressionGeneratorPropertiesFile(prefix,etcPackage);
		etcPackage.add(randomExpressionPropertiesFile);
	}

	public void appendStandardLanguageClasses(String name, String prefix, String nameSeparator){
		this.nameSeparator = nameSeparator;
		language(name).appendStandardClasses(prefix, nameSeparator);
	}

	public void appendStandardTableauClasses(String tabName, String synName, String prefix, String nameSeparator){
		this.nameSeparator = nameSeparator;
		tableau(tabName).appendStandardClasses(language(synName).langPackage, prefix, nameSeparator);
	}

	public void appendStandardClasses(String prefix, String sort, String branchBound){
		basePackage.add(new MettelTableauProverFile(prefix, sort, branchBound, this));
	}

	public void appendStandardLanguageClasses(String synName, String prefix, String[] sorts, String branchBound){
	    language(synName).appendStandardClasses(prefix, sorts, branchBound);
	}

	public void appendStandardTableauClasses(String tabName, String prefix, String[] sorts, String branchBound){
	    tableau(tabName).appendStandardClasses(prefix, sorts, branchBound);
	}

	public void appendConnectiveLanguageClass(String synName, String prefix, String sort, String name, String[] sorts, List<MettelToken> tokens, boolean equality){
		language(synName).appendConnectiveClass(prefix, sort, name, sorts, tokens, equality);
	}

	public void appendConnectiveTableauClass(String tabName, String sort, String name, String[] sorts){
		tableau(tabName).appendConnectiveClass(sort, name, sorts);
	}

	public void flush(String outputPath) throws IOException {
		basePackage.flush(outputPath);
		etcPackage.flush(outputPath);

		for(LanguagePackages packs:langPacks.values()){
			packs.flush(outputPath);
		}
		for(TableauPackages packs:tabPacks.values()){
			packs.flush(outputPath);
		}
	}

	/**
	 * @return
	 */
	public MettelJavaPackage grammarPackage(String synName) {
		return language(synName).grammarPackage;
	}

	/**
	 * @return
	 */
	public MettelJavaPackage testTableauPackage(String tabName) {
		return tableau(tabName).testTableauPackage;
	}

	/**
	 * @return
	 */
	public MettelJavaPackage languagePackage(String synName) {
		return language(synName).langPackage;
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
	public MettelJavaPackage testLanguagePackage(String synName) {
		return language(synName).testLangPackage;
	}

	/**
	 * @return
	 */
	public MettelJavaPackage tableauPackage(String tabName) {
		return tableau(tabName).tableauPackage;
	}

}
