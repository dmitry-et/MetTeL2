/**
 * MetTeL is a tableau prover generator.
 * Copyright (C) 2009-2011 Dmitry Tishkovsky
 *
 * This file is part of MetTeL.
 *
 * MetTeL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version with the additional term
 * that all the references to the MetTeL original author,
 * Dmitry Tishkovsky, must be retained.
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



import java.util.ArrayList;
import java.util.Hashtable;

import mettel.util.MettelJavaNames;

import static mettel.generator.MettelANTLRGrammarGeneratorDefaultOptions.NAME_SEPARATOR;

import static mettel.util.MettelStrings.FILE_SEPARATOR;
//TODO also if you generate nominal sort do you need to print information about formula sort? also do you need to print information in standard output?"
//TODO can use specify same named variables?
//TODO exceptions?

public class MettelRandomExpressionGeneratorJavaClassFile extends
		MettelJavaClassFile implements MettelRandomExpressionDefaultPropertiesValues{

	private String prefix = "Mettel";
	private MettelJavaPackage langPack = null;
	
	private String nameSeparator = NAME_SEPARATOR;

	//TODO make new public class
	private class Signature{

	//	private String type;
		private String name;
		private String[] types;

		Signature(String name, String[] types){
			super();
	//		this.type = type;
			this.name = name;
			this.types = types;
		}

	}

	private Hashtable<String,ArrayList<Signature>> signatures = new Hashtable<String,ArrayList<Signature>>();

	public MettelRandomExpressionGeneratorJavaClassFile(String prefix, MettelJavaPackage pack, MettelJavaPackage langPack, String nameSeparator) {
		super(prefix + "RandomExpressionGenerator", pack, "public", null,
				  new String[]{prefix + "ExpressionGenerator"});
			this.prefix = prefix;
			this.langPack = langPack;
			this.nameSeparator = nameSeparator;
	}

	protected void imports(){
		headings.appendEOL();
		headings.appendLine("import " + langPack.path() + '.' + prefix + "ObjectFactory;");
		headings.appendEOL();
		headings.appendLine("import mettel.core.MettelCoreRuntimeException;");
		headings.appendEOL();
		headings.appendLine("import java.util.Collection;");
		headings.appendLine("import java.util.Random;");
		headings.appendEOL();
		headings.appendLine("import java.io.BufferedWriter;");
		headings.appendLine("import java.io.File;");
		headings.appendLine("import java.io.FileReader;");
		headings.appendLine("import java.io.FileWriter;");
		headings.appendLine("import java.io.IOException;");
		headings.appendLine("import java.util.Properties;");
		headings.appendLine("import java.util.StringTokenizer;");
	}

	void appendSignature(String type){
		ArrayList<Signature> ss = signatures.get(type);
		if(ss == null){
			ss = new ArrayList<Signature>();
			signatures.put(type, ss);
		}
	}

	void appendSignature(String type, String name, String[] types){
		ArrayList<Signature> ss = signatures.get(type);
		if(ss == null){
			ss = new ArrayList<Signature>();
			signatures.put(type, ss);
		}
		ss.add(new Signature(name, types));
	}

	public void generateBody(){
		appendLine("private static BufferedWriter out = null;");
		appendLine("private static FileReader prop = null;");
		appendLine("private static boolean standardOutput = false;");
		appendLine("private static String outputPath = \"random_problems\";");
		appendLine("private static int fileIndex = 0;");
		appendLine("private static " + prefix + "RandomExpressionGenerator g = new " + prefix + "RandomExpressionGenerator();");
		appendEOL();
		
		appendLine("public static void main(String[] args) {");
			incrementIndentLevel();
			appendLine("System.out.println(\"-------------------------------------------------------------------\");");
			appendLine("System.out.println(\"Hello there! I am a random expression generator for "+prefix+" logic.\");");
			appendLine("System.out.println(\"I am generated by MetTeL, an automated tableau prover generator,\");");
			appendLine("System.out.println(\"which is designed and implemented by Dmitry Tishkovsky.\");");
			appendLine("System.out.println(\"As a program, I have ABSOLUTELY NO WARRANTY.\");");
			appendLine("System.out.println(\"-------------------------------------------------------------------\");");
			appendLine("try{");
				incrementIndentLevel();
				appendLine("parseCommandLineArguments(args);");
				appendEOL();
				
				appendLine("if (prop != null)");
					incrementIndentLevel();
					appendLine("setProperties();");
					decrementIndentLevel();
				appendEOL();
				
				appendLine("outputGeneratedExpressions();");
				appendEOL();

				decrementIndentLevel();
			appendLine("} catch(Exception e) {");
				incrementIndentLevel();
				appendLine("System.out.println(\"Sorry! I detected an exceptional situation and terminate now.\");");
	        	appendLine("System.out.println(\"If you can help me to avoid this situation in future, please look at my error output.\");");
	        	appendLine("System.err.println(\"==Exception==========================\");");
	        	appendLine("e.printStackTrace(System.err);");
	        	appendLine("System.err.println(\"=====================================\");");
	        	appendLine("System.exit(-1);");
	        	decrementIndentLevel();
	        appendLine('}');
	        decrementIndentLevel();
        appendLine('}');
        
        appendEOL();

        appendLine("private static void parseCommandLineArguments(String[] args) throws IOException{");
        	incrementIndentLevel();
        	appendLine("final int SIZE = args.length;");
			appendLine("for(int i = 0; i < SIZE; i++){");
				incrementIndentLevel();
				appendLine("if(\"-\".equals(args[i])){");
					incrementIndentLevel();
					appendLine("standardOutput = true;");
					decrementIndentLevel();
				appendLine("}else if(\"-d\".equals(args[i])||\"--output-directory\".equals(args[i])){");
					incrementIndentLevel();
					appendLine("if(i < SIZE-1){");
						incrementIndentLevel();
						appendLine("outputPath = args[++i];");
						appendLine("System.out.println(\"Output path: \" + outputPath);");
						decrementIndentLevel();
					appendLine("}else{");
						incrementIndentLevel();
						appendLine("System.out.println(\"I need a name of directory where I will put generated random expressions.\");");
						appendLine("System.exit(-1);");
						decrementIndentLevel();
					appendLine('}');
					decrementIndentLevel();
				appendLine("}else if(\"-p\".equals(args[i])||\"--properties\".equals(args[i])){");
					incrementIndentLevel();
					appendLine("if(i < SIZE-1){");
						incrementIndentLevel();
						appendLine("prop = new FileReader(args[++i]);");
						appendLine("System.out.println(\"Properties file: \" + args[i]);");
						decrementIndentLevel();
					appendLine("}else{");
						incrementIndentLevel();
						appendLine("System.out.println(\"I need a name of file where you have specified properties of the random expression generator.\");");
						appendLine("System.exit(-1);");
						decrementIndentLevel();
					appendLine('}');
					decrementIndentLevel();
				appendLine("}else if(\"--file-index\".equals(args[i])){");
					incrementIndentLevel();
					appendLine("if(i < SIZE-1){");
						incrementIndentLevel();
						appendLine("fileIndex = Integer.parseInt(args[++i]);");
						appendLine("System.out.println(\"Starting file index is: \" + fileIndex);");
						decrementIndentLevel();
					appendLine("}else{");
						incrementIndentLevel();
						appendLine("System.out.println(\"I need a number which specified starting index of file names\");");
						appendLine("System.exit(-1);");
						decrementIndentLevel();
					appendLine('}');
					decrementIndentLevel();
				appendLine('}');
				decrementIndentLevel();
			appendLine('}');
			decrementIndentLevel();
		appendLine('}');

		appendEOL();
		
		appendLine("private static void setProperties() throws IOException{");
			incrementIndentLevel();
			appendLine("String variablesNamesLine;");
			appendLine("StringTokenizer variablesNamesTokenizer;");
			appendLine("String[] variablesNames;");
			appendEOL();
			
			appendLine("Properties configuration = new Properties();");
			appendLine("configuration.load(prop);");
			for(String type:signatures.keySet()){
				final String Type = MettelJavaNames.firstCharToUpperCase(type);
				
				// for each sorts connective create that connectives frequency
				for(Signature s:signatures.get(type)){
					final String ltype = s.name + Type;
					appendLine("g.set" + MettelJavaNames.firstCharToUpperCase(ltype, nameSeparator) + "Frequency(Integer.parseInt(configuration.getProperty(\"" + MettelRandomExpressionDefaultPropertiesNames.sortConnectiveFrequencyProperty(type, s.name) + "\", \"" + SORT_CONNECTIVE_FREQUENCY + "\")));");
				}
				appendEOL();
				
				// for each sort set variable frequency, depth
				final String vtype = type + "Variable";
				appendLine("g.set" + MettelJavaNames.firstCharToUpperCase(vtype) + "Frequency(Integer.parseInt(configuration.getProperty(\"" + MettelRandomExpressionDefaultPropertiesNames.sortVariableFrequencyProperty(type) + "\", \"" + SORT_VARIABLE_FREQUENCY +"\")));");
				appendLine("g.setDepth" + Type + "(Integer.parseInt(configuration.getProperty(\"" + MettelRandomExpressionDefaultPropertiesNames.sortVariableDepthProperty(type) + "\", \"" + SORT_VARIABLE_DEPTH + "\")));");
				appendEOL();
				
				// for each sort set variables names
				// tokenize variables names line by comma
				// if found 0 tokens it means it's empty and set string array to null
				// otherwise add each token to the array of strings
				appendLine("variablesNamesLine = configuration.getProperty(\"" + MettelRandomExpressionDefaultPropertiesNames.sortVariablesProperty(type) + "\", \"" + SORT_VARIABLES + "\");");
				appendLine("variablesNamesTokenizer = new StringTokenizer(variablesNamesLine, \",\");");
				appendLine("if (variablesNamesTokenizer.countTokens() == 0){");
					incrementIndentLevel();
					appendLine("variablesNames = null;");
					decrementIndentLevel();
				appendLine("}else{");
					incrementIndentLevel();
					appendLine("variablesNames = new String[variablesNamesTokenizer.countTokens()];");
					appendLine("for (int i = 0; i < variablesNames.length; i++)");
						incrementIndentLevel();
						appendLine("variablesNames[i] = variablesNamesTokenizer.nextToken().trim();");
						decrementIndentLevel();
					decrementIndentLevel();
				appendLine('}');
				appendLine("g.set" + Type + "Variables(variablesNames);");
				appendEOL();
				
				// for each sort set variables number used
				// must be set AFTER variables names
				appendLine("g.set" + Type + "VariablesSize(Integer.parseInt(configuration.getProperty(\"" + MettelRandomExpressionDefaultPropertiesNames.sortVariablesSizeProperty(type) + "\", \"" + SORT_VARIABLES_NUMBER + "\")));");
				appendEOL();
				
				// for each sort set how many times it will be generated
				appendLine("g.set" + Type + "TimesToRun(Integer.parseInt(configuration.getProperty(\"" + MettelRandomExpressionDefaultPropertiesNames.sortGenerateProperty(type) + "\", \"" + SORT_GENERATE + "\")));");
			}
			decrementIndentLevel();
		appendLine('}');
		
		appendEOL();
		
		appendLine("private static void outputGeneratedExpressions() throws IOException{");
			incrementIndentLevel();
			appendLine("new File(outputPath).mkdir();");
			appendLine("File outputFile;");
			appendLine("String generatedExpression;");
			appendLine("String variablesNamesLine;");
			appendEOL();
			
			for(String type:signatures.keySet()){
				appendLine("for (int i = 0; i < g." + type + "TimesToRun; i++){");
					incrementIndentLevel();
					appendLine("while ((outputFile = new File(outputPath + \"" + FILE_SEPARATOR + prefix + "_\" + fileIndex + \".mtl\")).exists())");
						incrementIndentLevel();
						appendLine("fileIndex++;");
						decrementIndentLevel();
					appendLine("out = new BufferedWriter(new FileWriter(outputFile));");
					appendEOL();
					
					appendLine("out.write(\"#MetTeL.version = TODO\");");
		        	appendLine("out.newLine();");
		        	appendLine("out.write(\"#syntax = <pathTODO>\");");
		        	appendLine("out.newLine();");
		        	appendLine("out.newLine();");
		        	appendEOL();
					
		        	// print configuration of the generated expression
					for(String type1:signatures.keySet()){
						final String Type1 = MettelJavaNames.firstCharToUpperCase(type1);
						for(Signature s:signatures.get(type1)){
							final String ltype = s.name + Type1;
							appendLine("out.write(\"#" + MettelRandomExpressionDefaultPropertiesNames.sortConnectiveFrequencyProperty(type1, s.name) + " = \" + g." + ltype + "Frequency);");
							appendLine("out.newLine();");
						}
						appendLine("out.newLine();");
						appendEOL();

						appendLine("out.write(\"#" + MettelRandomExpressionDefaultPropertiesNames.sortVariableFrequencyProperty(type1) +" = \" + g." + type1 + "VariableFrequency);");
						appendLine("out.newLine();");
						appendLine("out.write(\"#" + MettelRandomExpressionDefaultPropertiesNames.sortVariableDepthProperty(type1) + " = \" + g.depth" + Type1 + ");");
						appendLine("out.newLine();");
						appendEOL();
						
						// if variables array is null then write empty string
						// otherwise for each string in array write in and add comma with space
						// at the end remove 2 last characters (", " comma and space)
						appendLine("variablesNamesLine = \"\";");
						appendLine("if (g." + type1 + "Variables != null){");
							incrementIndentLevel();
							appendLine("for (String variableName : g." + type1 + "Variables)");
								incrementIndentLevel();
								appendLine("variablesNamesLine += variableName + \", \";");
								decrementIndentLevel();
							appendLine("variablesNamesLine = variablesNamesLine.substring(0, variablesNamesLine.length() - 2);");
							decrementIndentLevel();
						appendLine('}');
						appendEOL();
						
						appendLine("out.write(\"#" + MettelRandomExpressionDefaultPropertiesNames.sortVariablesProperty(type1) + " = \" + " + "variablesNamesLine);");
						appendLine("out.newLine();");
						appendLine("out.write(\"#" + MettelRandomExpressionDefaultPropertiesNames.sortVariablesSizeProperty(type1) + " = \" + g." + type1 + "VariablesSize);");
						appendLine("out.newLine();");
						appendLine("out.newLine();");
						appendEOL();
					}
					
					appendLine("generatedExpression = g." + type + "().toString();");
					appendEOL();
					
					appendLine("out.write(generatedExpression);");
					appendLine("out.close();");
					appendLine("if (standardOutput)");
						incrementIndentLevel();
						appendLine("System.out.println(generatedExpression);");
						decrementIndentLevel();
					decrementIndentLevel();
				appendLine('}');
			}
			decrementIndentLevel();
		appendLine('}');			
		
		appendEOL();
		
		appendLine("private " + prefix + "ObjectFactory factory = " + prefix + "ObjectFactory.DEFAULT;");

		appendEOL();

		appendLine("public " + this.fileName + "() {");
		    incrementIndentLevel();
			appendLine("super();");
			decrementIndentLevel();
		appendLine('}');

		appendEOL();

		appendLine("public " + this.fileName + '(' + prefix + "ObjectFactory factory) {");
		    incrementIndentLevel();
			appendLine("super();");
			appendLine("this.factory = factory;");
			decrementIndentLevel();
		appendLine('}');

		appendEOL();

		appendLine("private Random random = new Random();");

		appendEOL();

		for(String type:signatures.keySet()){

			final String Type = MettelJavaNames.firstCharToUpperCase(type);

			int total = 0;
			for(Signature s:signatures.get(type)){
				final String ltype = s.name + Type;

				appendLine("private int " + ltype + "Frequency = " + SORT_CONNECTIVE_FREQUENCY + ";");
				total++;

				appendEOL();

				appendLine("public void set" + MettelJavaNames.firstCharToUpperCase(ltype, nameSeparator) + "Frequency(int f){");
					incrementIndentLevel();
					appendLine("if(f < 0) throw new MettelCoreRuntimeException(\"Frequency parameter is negative\");");
					appendLine("total" + Type + "Frequency += (f - " + ltype + "Frequency);");
					appendLine(ltype+"Frequency = f;");
					decrementIndentLevel();
				appendLine('}');

				appendEOL();
			}

			final String vtype = type + "Variable";

			appendLine("private int " + vtype + "Frequency = " + SORT_VARIABLE_FREQUENCY + ";");
			appendLine("public void set" + MettelJavaNames.firstCharToUpperCase(vtype) + "Frequency(int f){");
				incrementIndentLevel();
				appendLine("if(f < 0) throw new MettelCoreRuntimeException(\"Frequency parameter is negative\");");
				appendLine("total" + Type + "Frequency += (f - " + vtype + "Frequency);");
				appendLine(vtype+"Frequency = f;");
				decrementIndentLevel();
			appendLine('}');

			appendEOL();

			appendLine("private int total" + Type + "Frequency = " + (total+1) + ';');

			appendEOL();

			appendLine("private int depth" + Type + " = " + SORT_VARIABLE_DEPTH + ";");

			appendEOL();

			appendLine("public void setDepth" + Type + "(int d){");
				incrementIndentLevel();
				appendLine("depth" + Type + " = d;");
				decrementIndentLevel();
			appendLine('}');

			appendEOL();

			headings.appendLine("import " + langPack.path() + '.' + prefix + Type +';');

			appendLine("public " + prefix + Type + ' ' + type + "(){");
				incrementIndentLevel();
				appendLine("return " + type + "(depth" + Type + ");");
				decrementIndentLevel();
			appendLine('}');

			appendEOL();

			appendLine("private " + prefix + Type + ' ' + type + "(int d){");
				incrementIndentLevel();
				appendLine("if(depth" + Type + " > 0 && d <= 0){");
					incrementIndentLevel();
					appendLine("return " + type + "Variable();");
					decrementIndentLevel();
				appendLine('}');

				appendEOL();

				appendLine("int r = random.nextInt(total" + Type + "Frequency);");

				appendEOL();

				for(Signature s:signatures.get(type)){
					final String ltype = s.name + Type;

					appendLine("if(r < " + ltype + "Frequency){");
						incrementIndentLevel();
						indent();
						append("return factory.create" + MettelJavaNames.firstCharToUpperCase(ltype,nameSeparator) + '(');
							int i = 0;
							for(String t:s.types){
								if(i > 0){
									append(", ");
								}
								append(t+"(d-1)");
								i++;
							}
						append(");");appendEOL();
						decrementIndentLevel();
					appendLine("}else{");
						incrementIndentLevel();
						appendLine("r -= " + ltype + "Frequency;");
				}

				appendLine("return " + type + "Variable();");


				final ArrayList<Signature> list = signatures.get(type);
				final int SIZE = list.size();
				for(int i = 0; i < SIZE; i++) {
					decrementIndentLevel();
					appendLine('}');
				}

				decrementIndentLevel();
			appendLine('}');

			appendEOL();

			appendLine("private String[] " + type + "Variables = " + SORT_VARIABLES + ";");
			appendLine("private int " + type + "VariablesSize = " + SORT_VARIABLES_NUMBER + ";");

			appendEOL();

			appendLine("public void set" + Type + "Variables(String[] names){");
				incrementIndentLevel();
				appendLine("if(names == null) return;");
				appendLine(type + "VariablesSize = names.length;");
				appendLine(type + "Variables = new String[" + type + "VariablesSize];");
				appendLine("System.arraycopy(names, 0, " + type +"Variables, 0, " + type + "VariablesSize);");
				decrementIndentLevel();
			appendLine('}');

			appendEOL();

			appendLine("public void set" + Type + "Variables(Collection<" + prefix + Type + "Variable> vars){");
				incrementIndentLevel();
				appendLine("if(vars == null) return;");
				appendLine(type + "VariablesSize = vars.size();");
				appendLine(type + "Variables = new String[" + type + "VariablesSize];");

				headings.appendLine("import " + langPack.path() + '.' + prefix + Type + "Variable;");

				appendLine(prefix + Type + "Variable[] vv = (" + prefix + Type + "Variable[]) vars.toArray();");
				appendLine("for(int i = 0; i < " + type + "VariablesSize; i++){");
					incrementIndentLevel();
					appendLine(type + "Variables[i] = vv[i].name();");
					decrementIndentLevel();
				appendLine('}');
				decrementIndentLevel();
			appendLine('}');

			appendEOL();

			appendLine("public void set" + Type + "VariablesSize(int size){");
				incrementIndentLevel();
				appendLine(type + "VariablesSize = size;");
				decrementIndentLevel();
			appendLine('}');
			
			appendEOL();
			
			appendLine("protected final " + prefix + Type + "Variable " + type + "Variable(){");
				incrementIndentLevel();
				appendLine("int r = random.nextInt(" + type + "VariablesSize);");
				appendLine("return factory.create" + Type + "Variable((" + type + "Variables != null && r < " + type + "Variables.length)? " +
						type + "Variables[r]: \"$" + type + "Variable\"+r );");
				decrementIndentLevel();
			appendLine('}');
			
			appendEOL();
			
			// added by tomas
			appendLine("private int " + type + "TimesToRun = " + SORT_GENERATE + ";");
			appendEOL();
			
			appendLine("public void set" + Type + "TimesToRun(int times) {");
				incrementIndentLevel();
				appendLine(type + "TimesToRun = times;");
				decrementIndentLevel();
			appendLine('}');
		}
	}
}
