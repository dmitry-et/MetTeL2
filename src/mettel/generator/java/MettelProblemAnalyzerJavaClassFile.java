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

import static mettel.generator.MettelANTLRGrammarGeneratorDefaultOptions.NAME_SEPARATOR;

import java.util.ArrayList;
import java.util.Hashtable;

import mettel.util.MettelJavaNames;

public class MettelProblemAnalyzerJavaClassFile extends MettelJavaClassFile{

	private String prefix = "Mettel";
	private String nameSeparator = NAME_SEPARATOR;

	//TODO repeating code everywhere with signature class
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
	
	public MettelProblemAnalyzerJavaClassFile(String prefix, MettelJavaPackage pack, String nameSeparator) {
		super(prefix + "ProblemAnalyzer", pack, "public", null, null);
			this.prefix = prefix;
			this.nameSeparator = nameSeparator;
	}

	//TODO perhaps rename also repeating code everywhere with signature class
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
	
	
	protected void imports(){
		headings.appendEOL();
		headings.appendLine("import java.io.InputStream;");
		headings.appendLine("import java.io.IOException;");
		headings.appendLine("import java.io.FileInputStream;");		
		headings.appendEOL();
		headings.appendLine("import java.util.ArrayList;");
		headings.appendLine("import java.util.Collections;");
		headings.appendLine("import java.util.HashMap;");
		headings.appendLine("import java.util.Map;");
		headings.appendLine("import java.util.Map.Entry;");
		headings.appendLine("import java.util.Set;");
		headings.appendLine("import java.util.Properties;");
		headings.appendEOL();
		headings.appendLine("import org.antlr.runtime.ANTLRFileStream;");
		headings.appendLine("import org.antlr.runtime.ANTLRInputStream;");
		headings.appendLine("import org.antlr.runtime.CharStream;");
		headings.appendLine("import org.antlr.runtime.CommonTokenStream;");
		headings.appendLine("import org.antlr.runtime.RecognitionException;");
	}
	
	public void generateBody(){
		appendLine("// UNLISTED_DEPTH must be lower than STARTING_DEPTH");
		appendLine("private final static int STARTING_DEPTH = 0;");
		appendLine("private final static int UNLISTED_DEPTH = -1;");
		appendEOL();
		
		appendLine("private static int currentDepth = STARTING_DEPTH - 1;");
		appendEOL();
		
		for(String sort : signatures.keySet()){
			appendLine("private HashMap<String, Integer> " + sort + "ConnectivesOccurences = new HashMap<String, Integer>();");
			appendLine("private HashMap<String, Integer> " + sort + "VariablesOccurences = new HashMap<String, Integer>();");
			appendLine("private HashMap<String, Integer> " + sort + "ConnectivesMaxDepth = new HashMap<String, Integer>();");
			appendLine("private HashMap<String, Integer> " + sort + "VariablesMaxDepth = new HashMap<String, Integer>();");
			appendEOL();
		}
		appendLine("private int totalSymbolsMaxLength = 0;");
		
		appendLine("public " + prefix + "ProblemAnalyzer(String problemFile) throws IOException, RecognitionException{");
			incrementIndentLevel();
			appendLine("this(new FileInputStream(problemFile));");
//			appendLine("CharStream in = new ANTLRFileStream(problemFile);");
//			appendLine("CommonTokenStream tokens = new CommonTokenStream();");
//			appendLine(prefix + "Parser parser = new " + prefix + "Parser(tokens);");
//			appendLine("tokens.setTokenSource(new " + prefix + "Lexer(in));");
//			appendLine("ArrayList<" + prefix + "Expression> list = parser.expressions();");
//			appendEOL();
//			
//			appendLine("for (" + prefix + "Expression expression : list){");
//				incrementIndentLevel();
//					appendLine("if (totalSymbolsMaxLength < expression.length()){");
//						incrementIndentLevel();
//							appendLine("totalSymbolsMaxLength = expression.length();");
//						decrementIndentLevel();
//					appendLine('}');
//					appendLine("determineSymbol(expression);");
//				decrementIndentLevel();
//			appendLine('}');
			decrementIndentLevel();
		appendLine('}');
		appendEOL();
		
		//TODO same code merge with other constructor
		appendLine("public " + prefix + "ProblemAnalyzer(InputStream problemReader) throws IOException, RecognitionException{");
			incrementIndentLevel();
			appendLine("CharStream in = new ANTLRInputStream(problemReader);");
			appendLine("CommonTokenStream tokens = new CommonTokenStream();");
			appendLine(prefix + "Parser parser = new " + prefix + "Parser(tokens);");
			appendLine("tokens.setTokenSource(new " + prefix + "Lexer(in));");
			appendLine("ArrayList<" + prefix + "Expression> list = parser.expressions();");
			appendEOL();
			
			appendLine("for (" + prefix + "Expression expression : list){");
				incrementIndentLevel();
					appendLine("if (totalSymbolsMaxLength < expression.length()){");
						incrementIndentLevel();
							appendLine("totalSymbolsMaxLength = expression.length();");
						decrementIndentLevel();
					appendLine('}');
					appendLine("determineSymbol(expression);");
				decrementIndentLevel();
			appendLine('}');
			decrementIndentLevel();
		appendLine('}');
	appendEOL();

		
		appendLine("private void determineSymbol(" + prefix + "Expression expression){");
			incrementIndentLevel();
			appendLine("currentDepth++;");
			boolean firstIteration = true;
			for(String sort : signatures.keySet()){
				final String Sort = MettelJavaNames.firstCharToUpperCase(sort, nameSeparator);
				final String prefixSort = prefix + Sort;
				if (!firstIteration){
					indent();
					append("}else ");
				}
				appendLine("if (expression instanceof " + prefixSort + "){");
					incrementIndentLevel();
					appendLine("if (expression instanceof " + prefixSort + "Variable){");
						incrementIndentLevel();
						appendLine("Integer value;");
						appendLine("String variableName = ((" + prefixSort + "Variable)expression).toString();");
						appendLine("if ((value = " + sort + "VariablesOccurences.get(variableName)) == null){");
							incrementIndentLevel();
							appendLine(sort + "VariablesOccurences.put(variableName, 1);");
							decrementIndentLevel();
						appendLine("}else{");
							incrementIndentLevel();
							appendLine(sort + "VariablesOccurences.put(variableName, value + 1);");
							decrementIndentLevel();
						appendLine('}');
						appendLine("if (((value = " + sort + "VariablesMaxDepth.get(variableName)) == null) || value < currentDepth){");
							incrementIndentLevel();
							appendLine(sort + "VariablesMaxDepth.put(variableName, currentDepth);");
							decrementIndentLevel();
						appendLine('}');
						decrementIndentLevel();
					for (Signature s : signatures.get(sort)){
						final String connective = s.name;
						final String prefixConnectiveSort = prefix + MettelJavaNames.firstCharToUpperCase(connective, nameSeparator) + Sort;
						appendLine("}else if (expression instanceof " + prefixConnectiveSort + "){");
							incrementIndentLevel();
							appendLine("update" + Sort + "ConnectiveOccurences(\"" + connective + "\");");
							appendLine("update" + Sort + "ConnectiveMaxDepth(\"" + connective + "\");");
							for (int i = 0; i < s.types.length; i++){
								appendLine("determineSymbol(((" + prefixConnectiveSort + ")expression).e" + i +");");
							}
							decrementIndentLevel();
					}
					appendLine('}');
					firstIteration = false;
					decrementIndentLevel();
				}
				appendLine('}');
				appendLine("currentDepth--;");
				decrementIndentLevel();
			appendLine('}');
			appendEOL();
		
		for(String sort : signatures.keySet()){
			final String Sort = MettelJavaNames.firstCharToUpperCase(sort, nameSeparator); 
			appendLine("private void update" + Sort + "ConnectiveOccurences(String connective){");
				incrementIndentLevel();
				appendLine("Integer value;");
				appendLine("if ((value = " + sort + "ConnectivesOccurences.get(connective)) == null){");
					incrementIndentLevel();
					appendLine(sort + "ConnectivesOccurences.put(connective, 1);");
					decrementIndentLevel();
				appendLine("}else{");
					incrementIndentLevel();
					appendLine(sort + "ConnectivesOccurences.put(connective, value + 1);");
					decrementIndentLevel();
				appendLine('}');
				decrementIndentLevel();
			appendLine('}');
			appendEOL();
			
			appendLine("private void update" + Sort + "ConnectiveMaxDepth(String connective){");
				incrementIndentLevel();
				appendLine("Integer value;");
				appendLine("if (((value = " + sort + "ConnectivesMaxDepth.get(connective)) == null) || (value < currentDepth)){");
					incrementIndentLevel();
					appendLine(sort + "ConnectivesMaxDepth.put(connective, currentDepth);");
					decrementIndentLevel();
				appendLine('}');
				decrementIndentLevel();
			appendLine('}');
			appendEOL();
		}

		for(String sort : signatures.keySet()){
			final String Sort = MettelJavaNames.firstCharToUpperCase(sort, nameSeparator); 
			
			appendLine("public Set<String> " + sort + "VariablesNames(){");
				incrementIndentLevel();
				appendLine("return " + sort + "VariablesOccurences.keySet();");
				decrementIndentLevel();
			appendLine('}');
			appendEOL();
						
			appendLine("public int totalNumberOf" + Sort + "Variables(){");
				incrementIndentLevel();
				appendLine("return " + sort + "VariablesOccurences.size();");
				decrementIndentLevel();
			appendLine('}');
			appendEOL();
			
			appendLine("public int totalNumberOf" + Sort + "Connectives(){");
				incrementIndentLevel();
				appendLine("return " + sort + "ConnectivesOccurences.size();");
				decrementIndentLevel();
			appendLine('}');
			appendEOL();
			
			appendLine("public HashMap<String, Integer> " + sort + "VariablesOccurences(){");
				incrementIndentLevel();
				appendLine("return " + sort + "VariablesOccurences;");
				decrementIndentLevel();
			appendLine('}');
			appendEOL();
			
			appendLine("public HashMap<String, Integer> " + sort + "VariablesMaxDepth(){");
				incrementIndentLevel();
				appendLine("return " + sort + "VariablesMaxDepth;");
				decrementIndentLevel();
			appendLine('}');
			appendEOL();
			
			appendLine("private Integer total" + Sort + "VariablesOccurences = null;");
			appendEOL();
			appendLine("public int total" + Sort + "VariablesOccurences(){");
				incrementIndentLevel();
				appendLine("if (total" + Sort + "VariablesOccurences != null){");
					incrementIndentLevel();
					appendLine("return total" + Sort + "VariablesOccurences;");
					decrementIndentLevel();
				appendLine("}else{");
					incrementIndentLevel();
					appendLine("int sum = 0;");
					appendLine("for (int occurence : " + sort + "VariablesOccurences.values()){");
						incrementIndentLevel();
						appendLine("sum += occurence;");
						decrementIndentLevel();
					appendLine('}');
					appendLine("total" + Sort + "VariablesOccurences = new Integer(sum);");
					appendLine("return sum;");
					decrementIndentLevel();
				appendLine('}');
				decrementIndentLevel();
			appendLine('}');
			appendEOL();
			
			appendLine("private Integer total" + Sort + "VariablesMaxDepth = null;");
			appendEOL();
			appendLine("public int total" + Sort + "VariablesMaxDepth(){");
				incrementIndentLevel();
				appendLine("if (total" + Sort + "VariablesMaxDepth != null){");
					incrementIndentLevel();
					appendLine("return total" + Sort + "VariablesMaxDepth;");
					decrementIndentLevel();
				appendLine("}else{");
					incrementIndentLevel();
					appendLine("if (" + sort + "VariablesMaxDepth.size() > 0){");
						incrementIndentLevel();
						appendLine("int total = Collections.max(" + sort + "VariablesMaxDepth.values());");
						appendLine("total" + Sort + "VariablesMaxDepth = new Integer(total);");
						appendLine("return total;");
						decrementIndentLevel();
					appendLine("}else{");
						incrementIndentLevel();
						appendLine("total" + Sort + "VariablesMaxDepth = new Integer(UNLISTED_DEPTH);");
						appendLine("return UNLISTED_DEPTH;");
						decrementIndentLevel();
					appendLine('}');
					decrementIndentLevel();
				appendLine('}');
				decrementIndentLevel();
			appendLine('}');
			appendEOL();

			appendLine("private Integer total" + Sort + "ConnectivesOccurences = null;");
			appendEOL();
			appendLine("public int total" + Sort + "ConnectivesOccurences(){");
				incrementIndentLevel();
				appendLine("if (total" + Sort + "ConnectivesOccurences != null){");
					incrementIndentLevel();
					appendLine("return total" + Sort + "ConnectivesOccurences;");
					decrementIndentLevel();
				appendLine("}else{");
					incrementIndentLevel();
					appendLine("int sum = 0;");
					appendLine("for (int occurence : " + sort + "ConnectivesOccurences.values()){");
						incrementIndentLevel();
						appendLine("sum += occurence;");
						decrementIndentLevel();
					appendLine('}');
					appendLine("total" + Sort + "ConnectivesOccurences = new Integer(sum);");
					appendLine("return sum;");
					decrementIndentLevel();
				appendLine('}');
				decrementIndentLevel();
			appendLine('}');
			appendEOL();
			
			appendLine("private Integer total" + Sort + "ConnectivesMaxDepth = null;");
			appendEOL();
			appendLine("public int total" + Sort + "ConnectivesMaxDepth(){");
				incrementIndentLevel();
				appendLine("if (total" + Sort + "ConnectivesMaxDepth != null){");
					incrementIndentLevel();
					appendLine("return total" + Sort + "ConnectivesMaxDepth;");
					decrementIndentLevel();
				appendLine("}else{");
					incrementIndentLevel();
					appendLine("if (" + sort + "ConnectivesMaxDepth.size() > 0){");
						incrementIndentLevel();
						appendLine("int total = Collections.max(" + sort + "ConnectivesMaxDepth.values());");
						appendLine("total" + Sort + "ConnectivesMaxDepth = new Integer(total);");
						appendLine("return total;");
						decrementIndentLevel();
					appendLine("}else{");
						incrementIndentLevel();
						appendLine("total" + Sort + "ConnectivesMaxDepth = new Integer(UNLISTED_DEPTH);");
						appendLine("return UNLISTED_DEPTH;");
						decrementIndentLevel();
					appendLine('}');
					decrementIndentLevel();
				appendLine('}');
				decrementIndentLevel();
			appendLine('}');
			appendEOL();			
			
			for (Signature s : signatures.get(sort)){
				final String connective = s.name;
				final String connectiveSort = connective + Sort;
				
				appendLine("public int " + connectiveSort + "Occurences(){");
					incrementIndentLevel();
					appendLine("Integer result = " + sort + "ConnectivesOccurences.get(\"" + connective + "\");");
					appendLine("return result == null ? 0 : result;");
					decrementIndentLevel();
				appendLine('}');
				appendEOL();
				
				appendLine("public int " + connectiveSort + "MaxDepth(){");
					incrementIndentLevel();
					appendLine("Integer result = " + sort + "ConnectivesMaxDepth.get(\"" + connective + "\");");
					appendLine("return result == null ? UNLISTED_DEPTH : result;");
					decrementIndentLevel();
				appendLine('}');
				appendEOL();
			}
		}
		
		appendLine("private Integer totalVariablesOccurences = null;");
		appendEOL();
		appendLine("public int totalVariablesOccurences(){");
			incrementIndentLevel();
			appendLine("if (totalVariablesOccurences != null){");
				incrementIndentLevel();
				appendLine("return totalVariablesOccurences;");
				decrementIndentLevel();
			appendLine("}else{");
				incrementIndentLevel();
				appendLine("int sum = 0;");
				for(String sort : signatures.keySet()){
					final String Sort = MettelJavaNames.firstCharToUpperCase(sort, nameSeparator);
					appendLine("sum += total" + Sort + "VariablesOccurences();");
				}
				appendLine("return sum;");
				decrementIndentLevel();
			appendLine('}');
			decrementIndentLevel();
		appendLine('}');
		appendEOL();
		
		appendLine("private Integer totalVariablesMaxDepth = null;");
		appendEOL();		
		appendLine("public int totalVariablesMaxDepth(){");
			incrementIndentLevel();
			appendLine("if (totalVariablesMaxDepth != null){");
				incrementIndentLevel();
				appendLine("return totalVariablesMaxDepth;");
				decrementIndentLevel();
			appendLine("}else{");
				incrementIndentLevel();
				appendLine("int maxDepth = UNLISTED_DEPTH;");
				appendLine("int tempDepth = 0;");
				for(String sort : signatures.keySet()){
					final String Sort = MettelJavaNames.firstCharToUpperCase(sort, nameSeparator);
					appendLine("tempDepth = total" + Sort + "VariablesMaxDepth();");
					appendLine("maxDepth = maxDepth > tempDepth ? maxDepth : tempDepth;");
				}
				appendLine("return maxDepth;");
				
				decrementIndentLevel();
			appendLine('}');
			decrementIndentLevel();
		appendLine('}');
		appendEOL();
		
		appendLine("private Integer totalNumberOfVariables = null;");
		appendEOL();
		appendLine("public int totalNumberOfVariables(){");
			incrementIndentLevel();
			appendLine("if (totalNumberOfVariables != null){");
				incrementIndentLevel();
				appendLine("return totalNumberOfVariables;");
				decrementIndentLevel();
			appendLine("}else{");
				incrementIndentLevel();
				appendLine("int sum = 0;");
				for(String sort : signatures.keySet()){
					final String Sort = MettelJavaNames.firstCharToUpperCase(sort, nameSeparator);
					appendLine("sum += totalNumberOf" + Sort + "Variables();");
				}
				appendLine("return sum;");
				decrementIndentLevel();
			appendLine('}');
			decrementIndentLevel();
		appendLine('}');
		appendEOL();
		
		appendLine("private Integer totalConnectivesOccurences = null;");
		appendEOL();		
		appendLine("public int totalConnectivesOccurences(){");
			incrementIndentLevel();
			appendLine("if (totalConnectivesOccurences != null){");
				incrementIndentLevel();
				appendLine("return totalConnectivesOccurences;");
				decrementIndentLevel();
			appendLine("}else{");
				incrementIndentLevel();
				appendLine("int sum = 0;");
				for(String sort : signatures.keySet()){
					final String Sort = MettelJavaNames.firstCharToUpperCase(sort, nameSeparator);
					appendLine("sum += total" + Sort + "ConnectivesOccurences();");
				}
				appendLine("return sum;");
				decrementIndentLevel();
			appendLine('}');
			decrementIndentLevel();
		appendLine('}');
		appendEOL();
		
		appendLine("private Integer totalConnectivesMaxDepth = null;");
		appendEOL();				
		appendLine("public int totalConnectivesMaxDepth(){");
			incrementIndentLevel();
			appendLine("if (totalConnectivesMaxDepth != null){");
				incrementIndentLevel();
				appendLine("return totalConnectivesMaxDepth;");
				decrementIndentLevel();
			appendLine("}else{");
				incrementIndentLevel();
				appendLine("int maxDepth = UNLISTED_DEPTH;");
				appendLine("int tempDepth = 0;");
				for(String sort : signatures.keySet()){
					final String Sort = MettelJavaNames.firstCharToUpperCase(sort, nameSeparator);
					appendLine("tempDepth = total" + Sort + "ConnectivesMaxDepth();");
					appendLine("maxDepth = maxDepth > tempDepth ? maxDepth : tempDepth;");
				}
				appendLine("return maxDepth;");
				decrementIndentLevel();
			appendLine('}');
			decrementIndentLevel();
		appendLine('}');
		appendEOL();
				
		appendLine("private Integer totalNumberOfConnectives = null;");
		appendEOL();
		appendLine("public int totalNumberOfConnectives(){");
			incrementIndentLevel();
			appendLine("if (totalNumberOfConnectives != null){");
				incrementIndentLevel();
				appendLine("return totalNumberOfConnectives;");
				decrementIndentLevel();
			appendLine("}else{");
				incrementIndentLevel();
				appendLine("int sum = 0;");
				for(String sort : signatures.keySet()){
					final String Sort = MettelJavaNames.firstCharToUpperCase(sort, nameSeparator);
					appendLine("sum += totalNumberOf" + Sort + "Connectives();");
				}
				appendLine("return sum;");
				decrementIndentLevel();
			appendLine('}');
			decrementIndentLevel();
		appendLine('}');
		appendEOL();
		
		
		appendLine("public int totalSymbolsMaxDepth(){");
			incrementIndentLevel();
			appendLine("return totalVariablesMaxDepth() > totalConnectivesMaxDepth() ? totalVariablesMaxDepth() : totalConnectivesMaxDepth();");
			decrementIndentLevel();
		appendLine('}');
		appendEOL();
		
		appendLine("public int totalSymbolsMaxLength(){");
			incrementIndentLevel();
			appendLine("return totalSymbolsMaxLength;");
			decrementIndentLevel();
		appendLine('}');
		
		appendLine("public Properties getStatistics(){");
			incrementIndentLevel();
			appendLine("Properties st = new Properties();");
			appendEOL();
			
			for(String sort : signatures.keySet()){
				final String Sort = MettelJavaNames.firstCharToUpperCase(sort, nameSeparator);
				appendLine("st.setProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.sortVariablesNames(sort) + "\", variableNamesString(" + sort + "VariablesNames()));");
				appendLine("st.setProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.totalNumberOfSortVariables(sort) + "\", String.valueOf(totalNumberOf" + Sort + "Variables()));");
				appendLine("st.setProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.totalNumberOfSortConnectives(sort) + "\", String.valueOf(totalNumberOf" + Sort + "Connectives()));");
				appendEOL();
				
				appendLine("for (Map.Entry<String, Integer> entry : " + sort + "VariablesOccurences().entrySet()){");
					incrementIndentLevel();
					// TODO need to hard code property for a variable because its not know however can put all those methods 
					// into some other class and use them directly in generated file instead generating strings here
					appendLine("st.setProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.sortVariablesOccurences(sort) + ".\" + entry.getKey(), String.valueOf(entry.getValue()));");
					decrementIndentLevel();
				appendLine('}');
				appendLine("for (Map.Entry<String, Integer> entry : " + sort + "VariablesMaxDepth().entrySet()){");
					incrementIndentLevel();
					// TODO need to hard code property for a variable because its not know however can put all those methods 
					// into some other class and use them directly in generated file instead generating strings here
					appendLine("st.setProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.sortVariablesMaxDepth(sort) + ".\" + entry.getKey(), String.valueOf(entry.getValue()));");
					decrementIndentLevel();
				appendLine('}');
				appendEOL();
								
				appendLine("st.setProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.totalSortVariablesOccurences(sort) + "\", String.valueOf(total" + Sort + "VariablesOccurences()));");
				appendLine("st.setProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.totalSortVariablesMaxDepth(sort) + "\", String.valueOf(total" + Sort + "VariablesMaxDepth()));");
				appendLine("st.setProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.totalSortConnectivesOccurences(sort) + "\", String.valueOf(total" + Sort + "ConnectivesOccurences()));");
				appendLine("st.setProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.totalSortConnectivesMaxDepth(sort) + "\", String.valueOf(total" + Sort + "ConnectivesMaxDepth()));");
				for (Signature s : signatures.get(sort)){
					final String connective = s.name;
					final String connectiveSort = connective + Sort;

					appendLine("st.setProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.connectiveSortOccurences(sort, connective) + "\", String.valueOf(" + connectiveSort + "Occurences()));");
					appendLine("st.setProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.connectiveSortMaxDepth(sort, connective) + "\", String.valueOf(" + connectiveSort + "MaxDepth()));");
				}
			}
			appendEOL();
			
			appendLine("st.setProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.TOTAL_VARIABLES_OCCURENCES + "\", String.valueOf(totalVariablesOccurences()));");
			appendLine("st.setProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.TOTAL_VARIABLES_MAX_DEPTH + "\", String.valueOf(totalVariablesMaxDepth()));");
			appendLine("st.setProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.TOTAL_NUMBER_OF_VARIABLES + "\", String.valueOf(totalNumberOfVariables()));");
			appendEOL();
			
			appendLine("st.setProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.TOTAL_CONNECTIVES_OCCURENCES + "\", String.valueOf(totalConnectivesOccurences()));");
			appendLine("st.setProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.TOTAL_CONNECTIVES_MAX_DEPTH + "\", String.valueOf(totalConnectivesMaxDepth()));");
			appendLine("st.setProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.TOTAL_NUMBER_OF_CONNECTIVES + "\", String.valueOf(totalNumberOfConnectives()));");
			appendEOL();
			
			appendLine("st.setProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.TOTAL_SYMBOLS_MAX_DEPTH + "\", String.valueOf(totalSymbolsMaxDepth()));");
			appendLine("st.setProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.TOTAL_SYMBOLS_MAX_LENGTH + "\", String.valueOf(totalSymbolsMaxLength()));");
			appendEOL();
			
			appendLine("return st;");
			decrementIndentLevel();
		appendLine('}');
		appendEOL();
		
		//TODO same method for every class
		appendLine("private static String variableNamesString(Set<String> variableSet){");
			incrementIndentLevel();
			appendLine("StringBuilder variableNames = new StringBuilder();");
			appendLine("boolean notStart = false;");
			appendLine("for (String variable : variableSet){");
				incrementIndentLevel();
				appendLine("if(notStart){");
					incrementIndentLevel();
					appendLine("variableNames.append(\", \");");
					decrementIndentLevel();
				appendLine("}else{");
					incrementIndentLevel();
					appendLine("notStart = true;");
					decrementIndentLevel();
				appendLine('}');
				appendLine("variableNames.append(variable);");
				decrementIndentLevel();
			appendLine('}');
			appendLine("return variableNames.toString();");
			decrementIndentLevel();
		appendLine('}');
		appendEOL();
	}
}
