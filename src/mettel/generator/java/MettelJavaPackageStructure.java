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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.antlr.v4.Tool;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.TokenSource;
import org.antlr.runtime.TokenStream;

import mettel.core.language.MettelAbstractLogicParser;
import mettel.core.tableau.MettelGeneralTableauRule;
import mettel.generator.antlr.MettelANTLRGrammar;
import mettel.language.MettelToken;
import mettel.util.MettelJavaNames;
import mettel.util.MettelReport;

import mettel.generator.java.test.MettelParserTestJavaClassFile;
import mettel.generator.java.test.MettelTableauTestJavaClassFile;
import mettel.generator.util.MettelSignature;

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

	private HashMap<String, HashMap<String, LinkedHashSet<MettelSignature>>> signatures = new HashMap<String, HashMap<String, LinkedHashSet<MettelSignature>>>();

	private void addSignature(String synName, String sort){
		HashMap<String, LinkedHashSet<MettelSignature>> map = signatures.get(synName);
		if(map == null){
			map = new HashMap<String, LinkedHashSet<MettelSignature>>();
			signatures.put(synName, map);
		}
		LinkedHashSet<MettelSignature> set = map.get(sort);
		if(set == null){
			set = new LinkedHashSet<MettelSignature>();
			map.put(sort, set);
		}
	}

	private void addSignature(String synName, String sort, String name, String[] sorts){
		HashMap<String, LinkedHashSet<MettelSignature>> map = signatures.get(synName);
		if(map == null){
			map = new HashMap<String, LinkedHashSet<MettelSignature>>();
			signatures.put(synName, map);
		}
		LinkedHashSet<MettelSignature> set = map.get(sort);
		if(set == null){
			set = new LinkedHashSet<MettelSignature>();
			map.put(sort, set);
		}
		set.add(new MettelSignature(sort, name, sorts));
	}

	/*private HashMap<String, LinkedHashSet<MettelSignature>> map(LinkedHashSet<MettelSignature> signatures){
		HashMap<String, LinkedHashSet<MettelSignature>> result = new HashMap<String, LinkedHashSet<MettelSignature>>();
		for(MettelSignature s:signatures){
			LinkedHashSet<MettelSignature> set = result.get(s.sort());
			if(set == null){
				set = new LinkedHashSet<MettelSignature>();
				result.put(s.sort(), set);
			}
			set.add(s);
		}
		return result;
	}*/

	/*private HashMap<String, LinkedHashSet<MettelSignature>> signatures(String synName){
		return signatures.get(synName);
	}*/

	/*private String mainSort(String synName){
		return signatures.get(synName).iterator().next().sort();
	}*/

	private class LanguagePackages{

		private String name  = null;

		private LanguagePackages(String name){
			this.name = name;

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
		
		private MettelAbstractLogicParser parser = null;

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
				testLangPackage.add(new MettelParserTestJavaClassFile(prefix, sorts[0], MettelJavaPackageStructure.this, name));
			}

			for(String sort:sorts){
				langPackage.add(new MettelComplexExpressionJavaInterfaceFile(prefix,sort,langPackage,nameSeparator));
				langPackage.add(new MettelVariableJavaClassFile(prefix,sort,langPackage,nameSeparator));
				iFactory.addVariableMethod(sort);
				factory.addVariableMethod(sort);
				factory.addMap(sort);
				iExpressionGenerator.addMethod(sort);
				//expressionGenerator.appendSignature(sort);

				//randomExpressionPropertiesFile.appendSignature(sort);
				//problemAnalyzer.appendSignature(sort);

				addSignature(this.name, sort);
			}

			//System.out.println(signatures.get(this.name));

			expressionGenerator.setSignatures(signatures.get(this.name));
			expressionGenerator.generateBody();

			randomExpressionPropertiesFile.setSignatures(signatures.get(this.name));
			randomExpressionPropertiesFile.generateBody();

			problemAnalyzer.setSignatures(signatures.get(this.name));
			problemAnalyzer.generateBody();
		}

		private void appendConnectiveClass(String prefix, String sort, String name, String[] sorts, List<MettelToken> tokens, boolean equality){
			MettelComplexExpressionJavaClassFile f = new MettelComplexExpressionJavaClassFile(prefix,sort,name,sorts,langPackage, equality, nameSeparator);
			f.addToStringMethod(tokens);
			langPackage.add(f);

			factory.addCreateMethod(sort, name, sorts);
			iFactory.addCreateMethod(sort, name, sorts);

			//expressionGenerator.appendSignature(sort, name, sorts);
			//randomExpressionPropertiesFile.appendSignature(sort, name);
			//problemAnalyzer.appendSignature(sort, name, sorts);

			addSignature(this.name, sort, name, sorts);
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

    	private String name = null;
    	private String synName = null;

		private TableauPackages(String name, String synName){
			this.name = name;
			this.synName = synName;

			String path = basePackage.path()+".tableau."+name;

			tableauPackage = new MettelJavaPackage(path);
			testTableauPackage = new MettelJavaPackage(path+".test");
		}

		private MettelJavaPackage testTableauPackage = null, tableauPackage = null;
		private MettelBenchmarkJavaClassFile benchmark = null;

		private void appendStandardClasses(MettelJavaPackage langPackage, String prefix, String nameSeparator, String searchStrategy){
			benchmark = new MettelBenchmarkJavaClassFile(prefix, tableauPackage, langPackage, nameSeparator, synName, searchStrategy);
			tableauPackage.add(benchmark);
		}

		private void appendStandardClasses(String prefix, String[] sorts, String branchBound, String searchStrategy){
			if(sorts.length > 0){
				//basePackage.add(new MettelTableauProverFile(prefix, sorts[0], branchBound, MettelJavaPackageStructure.this, name, synName));
				tableauPackage.add(new MettelTableauProverFile(prefix, sorts[0], branchBound, MettelJavaPackageStructure.this, name, synName, searchStrategy));
			    testTableauPackage.add(new MettelTableauTestJavaClassFile(prefix,sorts[0],branchBound,MettelJavaPackageStructure.this, name, synName, searchStrategy));
			    benchmark.setMainSort(sorts[0]);
			    benchmark.setBranchBound(branchBound);
			}
			//for(String sort:sorts){
			//	benchmark.appendSignature(sort);
			//}
			benchmark.setSignatures(signatures.get(this.synName));
			benchmark.generateBody();
		}

		//private void appendConnectiveClass(String sort, String name, String[] sorts){
		//	benchmark.appendSignature(sort, name, sorts);
		//}

		private void flush(String outputPath) throws IOException {
			tableauPackage.flush(outputPath);
			testTableauPackage.flush(outputPath);
		}
	}

	private HashMap<String, TableauPackages> tabPacks = new HashMap<String, TableauPackages>();
	private HashMap<String, LinkedHashSet<TableauPackages>> synToTabPacks = new HashMap<String, LinkedHashSet<TableauPackages>>();

	public TableauPackages tableau(String name, String synName){
		TableauPackages packs = tableau(name);
		if(packs == null){
			packs = new TableauPackages(name, synName);
			tabPacks.put(name, packs);
			LinkedHashSet<TableauPackages> set = tableaux(synName);
			if(set == null){
				set = new LinkedHashSet<TableauPackages>();
				synToTabPacks.put(synName, set);
			}
			set.add(packs);
		}
		return packs;
	}

	private LinkedHashSet<TableauPackages> tableaux(String synName){
		return synToTabPacks.get(synName);
	}

	public TableauPackages tableau(String name){
		return tabPacks.get(name);
	}

	public MettelJavaPackageStructure(String base){
		super();
		basePackage =  new MettelJavaPackage(base);
		etcPackage = new MettelJavaPackage(base+".etc");
	}

	public void appendParser(String name, MettelANTLRGrammar g){
		grammarPackage(name).createFile(g.name(),"g").append(g.toStringBuilder());
	}

	public void appendStandardClasses(String prefix){
		randomExpressionPropertiesFile = new MettelRandomExpressionGeneratorPropertiesFile(prefix,etcPackage);
		etcPackage.add(randomExpressionPropertiesFile);
	}

	public void appendStandardLanguageClasses(String name, String prefix, String nameSeparator){
		this.nameSeparator = nameSeparator;
		language(name).appendStandardClasses(prefix, nameSeparator);
	}

	public void appendStandardTableauClasses(String tabName, String synName, String prefix, String nameSeparator, String searchStrategy){
		this.nameSeparator = nameSeparator;
		tableau(tabName,synName).appendStandardClasses(language(synName).langPackage, prefix, nameSeparator, searchStrategy);
	}

	public void appendStandardLanguageClasses(String synName, String prefix, String[] sorts, String branchBound){
	    language(synName).appendStandardClasses(prefix, sorts, branchBound);
	}

	public void appendStandardTableauClasses(String tabName, String prefix, String[] sorts, String branchBound, String searchStrategy){
		tableau(tabName).appendStandardClasses(prefix, sorts, branchBound, searchStrategy);
	}

	public void appendConnectiveLanguageClass(String synName, String prefix, String sort, String name, String[] sorts, List<MettelToken> tokens, boolean equality){
		language(synName).appendConnectiveClass(prefix, sort, name, sorts, tokens, equality);
	}

//	public void appendConnectiveTableauClass(String tabName, String sort, String name, String[] sorts){
//		tableau(tabName).appendConnectiveClass(sort, name, sorts);
//	}

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

	public void appendTableauFile(String name, String content) {
		tableauPackage(name).createFile("calculus", null).append(content);
	}

	public boolean antlr(String outputPath, MettelReport report){

		for(String lang:langPacks.keySet()){

			LanguagePackages packs = language(lang);
			final String path = MettelJavaNames.addSeparator(outputPath) +
					MettelJavaNames.addSeparator(MettelJavaNames.systemPath(packs.grammarPackage.path())) + MettelJavaNames.firstCharToUpperCase(lang)+".g";
			final String[] antlrArguments = {
					path
			};

			report.report("  ANTLR is generating a parser for the syntax "+lang+'.');
			Tool antlr = new Tool(antlrArguments);
	        antlr.processGrammarsOnCommandLine();
	        final int errorNumber = antlr.errMgr.getNumErrors();
	        if ( errorNumber > 0) {
	        		report.report("\t ANTLR reported "+errorNumber+" errors for the syntax "+lang+'.');
	                return false;
	        }
	        report.report("  The Java code of the parser for the syntax "+lang+" is generated.");
		}
        return true;
	}

	public boolean verifyTableaux(String outputPath, File dir, MettelReport report) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException{
		for(String name:tabPacks.keySet()){
			report.report("  I am verifying the tableau calculus specification "+name+'.');

			TableauPackages packs = tableau(name);
			final String tableau = MettelJavaNames.addSeparator(outputPath) +
					MettelJavaNames.addSeparator(MettelJavaNames.systemPath(packs.tableauPackage.path())) + "calculus";
			final String mainClass = //basePackage.path() +
									 packs.tableauPackage.path() +
					'.' + MettelJavaNames.firstCharToUpperCase(name) + "TableauProver";

			ClassLoader currentThreadClassLoader = Thread.currentThread().getContextClassLoader();
			URLClassLoader classLoader = new URLClassLoader(new URL[]{dir.toURI().toURL()}, currentThreadClassLoader);

			Class<?> cls = Class.forName(mainClass, true, classLoader);
			LinkedHashSet<MettelGeneralTableauRule> calculus = new LinkedHashSet<MettelGeneralTableauRule>();
			try{
				cls.getMethod("parseCalculus", Set.class, String.class).invoke(null, calculus, tableau);
			}catch(InvocationTargetException e){
				report.report("  There are errors in the tableau calculus specification "+ name+'.');
				return false;
			}
		}
		return true;
	}
	
	public boolean instantiateParsers(String outputPath, File dir, MettelReport report) throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		for(String name:langPacks.keySet()){
			ClassLoader currentThreadClassLoader = Thread.currentThread().getContextClassLoader();
			URLClassLoader classLoader = new URLClassLoader(new URL[]{dir.toURI().toURL()}, currentThreadClassLoader);
			
			final LanguagePackages pack = langPacks.get(name); 
			final String parserClass = pack.langPackage.path() + '.' + MettelJavaNames.firstCharToUpperCase(name) + "Parser";
			final String lexerClass = pack.langPackage.path() + '.' + MettelJavaNames.firstCharToUpperCase(name) + "Lexer";
			
			Class<?> cls = Class.forName(parserClass, true, classLoader);
			Constructor<?> constructor = cls.getConstructor(TokenStream.class);
			
			CommonTokenStream tokens = new CommonTokenStream();
			MettelAbstractLogicParser parser = (MettelAbstractLogicParser) constructor.newInstance(tokens);
			
			cls = Class.forName(lexerClass, true, classLoader);
			constructor = cls.getConstructor();
			
			tokens.setTokenSource((TokenSource)constructor.newInstance());
			
			//System.out.println(name + " parser = " + parser);
			pack.parser = parser;
		}
		return true;
	}
	
	public MettelAbstractLogicParser parser(String name){
		return langPacks.get(name).parser;
	}

}
