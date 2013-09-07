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

import java.util.HashMap;
import java.util.LinkedHashSet;

import mettel.generator.util.MettelSignature;
import mettel.util.MettelJavaNames;


public class MettelBenchmarkJavaClassFile extends MettelJavaClassFile{
	private String prefix = "Mettel";
	private MettelJavaPackage langPack = null;

	private String nameSeparator = null;

	private String mainConnective = null;
	private String MainConnective = null;

	private String SynName = null;

	private HashMap<String, LinkedHashSet<MettelSignature>> signatures = null;

	public void setMainSort(String sort){
		mainConnective = sort;
		MainConnective = MettelJavaNames.firstCharToUpperCase(sort, nameSeparator);
	}

	public void setSignatures(HashMap<String, LinkedHashSet<MettelSignature>> signatures){
		this.signatures = signatures;
	}

	public MettelBenchmarkJavaClassFile(String prefix, MettelJavaPackage pack, MettelJavaPackage langPack, String nameSeparator, String synName) {
		super(prefix + "Benchmark", pack, "public", null, null);
			this.prefix = prefix;
			this.langPack = langPack;
			this.nameSeparator = nameSeparator;
			SynName = MettelJavaNames.firstCharToUpperCase(synName);
	}

	protected void imports(){
		headings.appendEOL();
		headings.appendLine("import java.io.BufferedWriter;");
		headings.appendLine("import java.io.File;");
		headings.appendLine("import java.io.FilenameFilter;");
		headings.appendLine("import java.io.FileWriter;");
		headings.appendLine("import java.io.IOException;");
		headings.appendEOL();
		headings.appendLine("import java.lang.management.ManagementFactory;");
		headings.appendLine("import java.lang.management.ThreadMXBean;");
		headings.appendEOL();
		headings.appendLine("import java.text.SimpleDateFormat;");
		headings.appendEOL();
		headings.appendLine("import java.util.ArrayList;");
		headings.appendLine("import java.util.Date;");
		headings.appendLine("import java.util.HashSet;");
		headings.appendLine("import java.util.Iterator;");
		headings.appendLine("import java.util.LinkedHashSet;");
		headings.appendLine("import java.util.Properties;");
		headings.appendLine("import java.util.Set;");
		headings.appendEOL();
		headings.appendLine("import mettel.core.tableau.MettelGeneralTableauRule;");
		headings.appendLine("import mettel.core.tableau.MettelProverThread;");
		headings.appendLine("import mettel.core.MettelCoreRuntimeException;");
		headings.appendLine("import mettel.util.MettelProblemFile;");
		headings.appendLine("import mettel.util.MettelProperties;");
		headings.appendEOL();
		headings.appendLine("import org.antlr.runtime.ANTLRFileStream;");
		headings.appendLine("import org.antlr.runtime.ANTLRInputStream;");
		headings.appendLine("import org.antlr.runtime.CharStream;");
		headings.appendLine("import org.antlr.runtime.CommonTokenStream;");
		headings.appendLine("import org.antlr.runtime.RecognitionException;");
		headings.appendEOL();
		headings.appendLine("import au.com.bytecode.opencsv.CSVWriter;");
		headings.appendEOL();
		headings.appendLine("import " + langPack.path() + "." + SynName + MainConnective + ';');
		headings.appendLine("import " + langPack.path() + "." + SynName + "Lexer;");
		headings.appendLine("import " + langPack.path() + "." + SynName + "Parser;");
		headings.appendLine("import " + langPack.path() + "." + SynName + "ProblemAnalyzer;");
		headings.appendLine("import " + langPack.path() + "." + SynName + "TableauObjectFactory;");
		headings.appendEOL();
		headings.appendLine("import static mettel.util.MettelStrings.FILE_SEPARATOR;");
	}

	public void generateBody(){
		appendLine("private final static Runtime runtime = Runtime.getRuntime();");
		appendLine("private static int memoryExcess = 0;");
		appendLine("private final static int MAX_THREAD_TRIES = 10000;");
		//appendLine("private final static int SLEEP_FACTOR = 1000;");
		appendLine("private static BufferedWriter out = null;");
		appendLine("private static File inDir = null;");
		appendLine("private static CharStream in = null;");
		appendLine("private static long timeOutMilliSeconds = 1000;");
		appendLine("private static CSVWriter csvOut = null;");
		appendLine("private static File systemInfoFile = null;");
		appendLine("private static LinkedHashSet<MettelGeneralTableauRule> calculus;");
		appendLine("private static int numberOfThreads = Runtime.getRuntime().availableProcessors() - 2 > 0 ? Runtime.getRuntime().availableProcessors() - 2 : 1;");
		appendLine("final private static CommonTokenStream tokens = new CommonTokenStream();");
		appendLine("final private static " + SynName + "Parser parser = new " + SynName + "Parser(tokens);");
		appendLine("final private static ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();");
		appendLine("private static HashSet<MettelProverThread> threads = new HashSet<MettelProverThread>(numberOfThreads);");
		appendLine("private static Properties pStat = null;");
		appendEOL();

		// TODO can be static somewhere else
		appendLine("private final static String CSV_NOT_RELEVANT_FIELD = \"N/A\";");
		appendLine("final private static String PROBLEM_FILE_EXT = \".mtl\";");
		appendLine("final private static String SYSTEM_INFO_FILE_EXT = \".info\";");
		appendLine("final private static String MODEL_FILE_EXT = \".model\";");
		appendLine("final private static String CONTRADICTION_FILE_EXT = \".contradiction\";");
		appendLine("private static double NANO_TO_MILLI_SECONDS = 1000000.0;");
		appendEOL();

		appendLine("final private static class MtlFilter implements FilenameFilter{");
			incrementIndentLevel();
			appendLine("public boolean accept(File dir, String name){");
				incrementIndentLevel();
				appendLine("return name.endsWith(PROBLEM_FILE_EXT);");
				decrementIndentLevel();
			appendLine('}');
			decrementIndentLevel();
		appendLine('}');
		appendLine("final private static MtlFilter MTL_FILTER = new MtlFilter();");
		//TODO do somehow different because output be huge
		appendLine("final private static String[] csvHeader = {" + generateCSVHeader() + "};");
		appendLine("");

		//TODO close properly streams in finally
		appendLine("@SuppressWarnings(\"deprecation\")");
		appendLine("public static void main(String[] args) throws IOException{");
			incrementIndentLevel();
			appendLine("try{");
				incrementIndentLevel();
				appendLine("parseCommandLineArguments(args);");
				appendLine("File csvFile = new File(inDir + FILE_SEPARATOR + \"" + prefix + ".csv\");");
				appendLine("boolean fileExisted = false;");
				appendLine("if (csvFile.exists()){");
					incrementIndentLevel();
					appendLine("fileExisted = true;");
					decrementIndentLevel();
				appendLine('}');
				appendLine("csvOut = new CSVWriter(new FileWriter(csvFile, true));");
				appendLine("try{");
					incrementIndentLevel();
					appendLine("if (!fileExisted){");
						incrementIndentLevel();
						appendLine("csvOut.writeNext(csvHeader);");
						appendLine("csvOut.flush();");
						decrementIndentLevel();
					appendLine('}');
					appendEOL();

					appendLine("calculus = new LinkedHashSet<MettelGeneralTableauRule>();");
					appendLine("parseCalculus(calculus);");
					appendEOL();

					appendLine("String systemInfoFilePath = inDir + FILE_SEPARATOR + \"system\";");
					appendLine("systemInfoFile = getUniqueResultFile(systemInfoFilePath, SYSTEM_INFO_FILE_EXT);");
					appendLine("out = new BufferedWriter(new FileWriter(systemInfoFile));");
					appendLine("try{");
						incrementIndentLevel();
						appendLine("MettelProperties prop = new MettelProperties();");
						appendLine("prop.putAll(System.getProperties());");
						appendLine("prop.store(out, \"\");");
						decrementIndentLevel();
					appendLine("}finally{out.close();}");
					appendEOL();

					appendLine("String[] problemFiles = inDir.list(MTL_FILTER);");
					appendLine("int indexProblemFile = 0;");
					appendEOL();

					appendLine("while (!threads.isEmpty() || indexProblemFile < problemFiles.length){");
						incrementIndentLevel();
						appendLine("for (int i = threads.size(); i < numberOfThreads && indexProblemFile < problemFiles.length && (runtime.freeMemory() % ((threads.size() + 1) * 1024 *1024) >= memoryExcess); i++){");
							incrementIndentLevel();
							appendLine("threads.add(createAndStartNewThread(problemFiles[indexProblemFile]));");
							appendLine("indexProblemFile++;");
							decrementIndentLevel();
						appendLine('}');
						appendLine("Iterator<MettelProverThread> it = threads.iterator();");
						appendLine("while (it.hasNext()){");
							incrementIndentLevel();
							appendLine("MettelProverThread thread = it.next();");
							appendLine("boolean threadFinished = false;");
							appendLine("if (!thread.isAlive()){");
								incrementIndentLevel();
								appendLine("final Boolean threadResult = thread.result();");
								appendLine("System.out.println(thread.getName() + \" completed, \" + thread.time() + \" ms, \" + (threadResult == null ? (thread.exception() == null? \"Unknown error\": thread.exception()) : (threadResult ? \"Satisfiable\" : \"Unsatisfiable\")));");
								appendLine("threadFinished = true;");
								decrementIndentLevel();
							appendLine("}else{");
								incrementIndentLevel();
								appendLine("long nanos = mxBean.getThreadUserTime(thread.getId());");
								appendLine("if (nanos >= timeOutMilliSeconds * NANO_TO_MILLI_SECONDS){");
									incrementIndentLevel();
									appendLine("thread.stop();");
									appendLine("System.out.println(thread.getName()+\" time-out\");");
									appendLine("threadFinished = true;");
									decrementIndentLevel();
								appendLine('}');
								decrementIndentLevel();
							appendLine('}');
							appendLine("System.runFinalization();");
							appendLine("System.gc();");
							appendLine("if (threadFinished){");
								incrementIndentLevel();
								appendLine("updateProblemFile(thread);");
								appendLine("printToCSV(thread);");
								appendLine("it.remove();");
								decrementIndentLevel();
							appendLine('}');
							decrementIndentLevel();
						appendLine('}');
						decrementIndentLevel();
					appendLine('}');
					decrementIndentLevel();
				appendLine("}finally{csvOut.close();}");
				decrementIndentLevel();
			appendLine("}catch(Exception e){");
				incrementIndentLevel();
				//TODO same used in few places
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

		appendLine("private static MettelProverThread createAndStartNewThread(String problemFile) throws IOException, RecognitionException, InterruptedException{");
			incrementIndentLevel();
			appendLine("for(int i = 0; i < MAX_THREAD_TRIES; i++){");
				incrementIndentLevel();
				appendLine("try{");
					incrementIndentLevel();
					appendLine("in = new ANTLRFileStream(inDir + FILE_SEPARATOR + problemFile);");
					appendLine("tokens.setTokenSource(new " + SynName + "Lexer(in));");
					appendLine("ArrayList<" + SynName + MainConnective + "> list = new ArrayList<" + SynName + MainConnective + ">();");
					//TODO better way to add s
					appendLine("parser." + mainConnective + "s(list);");
					appendLine(SynName + "TableauObjectFactory tfactory = new " + SynName + "TableauObjectFactory();");
					appendLine("MettelProverThread pt = new MettelProverThread(calculus, list, tfactory, mxBean);");
					appendLine("pt.setName(problemFile);");
					appendLine("pt.start();");
					appendLine("if(i > 0) System.out.println();");
					appendLine("System.out.println(problemFile + \" started\");");
					appendLine("Thread.sleep(0);");
					appendLine("return pt;");
					decrementIndentLevel();
				appendLine("}catch(OutOfMemoryError e){");
					incrementIndentLevel();
					appendLine("System.gc();");
					appendLine("if(i == 0) System.out.print(problemFile + \" start failure #\");");
					appendLine("for(int j = i; j > 0; j = j%10) System.out.print('\\b');");
					appendLine("System.out.print(i+1);");
					appendLine("if(i == MAX_THREAD_TRIES - 1){ throw e; }");
					appendLine("System.runFinalization();");
					appendLine("System.gc();");
					decrementIndentLevel();
				appendLine('}');
				decrementIndentLevel();
			appendLine('}');
			appendLine("throw new MettelCoreRuntimeException(\"I never should be here!\");");
			decrementIndentLevel();
		appendLine('}');

		//TODO static method same for anything
		appendLine("private static File getUniqueResultFile(String fileName, String ext){");
			incrementIndentLevel();
			appendLine("File uniqueFile = null;");
			appendLine("int index = 0;");
			appendLine("if ((uniqueFile = new File(fileName + ext)).exists()){");
				incrementIndentLevel();
				appendLine("while ((uniqueFile = new File(fileName + \"_\" + index + ext)).exists()){");
					incrementIndentLevel();
					appendLine("index++;");
					decrementIndentLevel();
				appendLine('}');
				appendLine("return uniqueFile;");
				decrementIndentLevel();
			appendLine("}else{");
				incrementIndentLevel();
				appendLine("return uniqueFile;");
				decrementIndentLevel();
			appendLine('}');
			decrementIndentLevel();
		appendLine('}');
		appendEOL();

		appendLine("private static void updateProblemFile(MettelProverThread thread) throws IOException, RecognitionException{");
			incrementIndentLevel();
			appendLine("String problemPath = new String(inDir + FILE_SEPARATOR + thread.getName());");
			appendLine("MettelProblemFile pFile = new MettelProblemFile(problemPath);");
			appendLine("if(!pFile.existsStatisticsProperties()){");
				incrementIndentLevel();
				appendLine(SynName + "ProblemAnalyzer pAnalyzer = new " + SynName + "ProblemAnalyzer(problemPath);");
				appendLine("pStat = pAnalyzer.getStatistics();");
				appendLine("pFile.setStatisticsProperties(pStat);");
				decrementIndentLevel();
			appendLine("}else{");
				incrementIndentLevel();
				appendLine("pStat = pFile.getStatisticsProperties();");
				decrementIndentLevel();
			appendLine('}');
			appendLine("pFile.addStatusProperty(\"" + prefix + "Prover\", thread.result() == null? CSV_NOT_RELEVANT_FIELD: (thread.result()? \"Satisfiable\":\"Unsatisfiable\"));");
			appendLine("pFile.writeToFile();");
			decrementIndentLevel();
		appendLine('}');
		appendEOL();

		appendLine("private static void printToCSV(MettelProverThread thread) throws IOException{");
			incrementIndentLevel();
			appendLine("String[] problemInfo = new String[csvHeader.length];");
			appendLine("problemInfo[0] = inDir + FILE_SEPARATOR + thread.getName();");
			appendEOL();

			appendLine("SimpleDateFormat sdf = new SimpleDateFormat(\"dd-MM-yyyy HH:mm:ss\");");
			appendLine("String formattedDate = sdf.format(new Date());");
			appendLine("problemInfo[5] = formattedDate;");
	        appendEOL();

	        appendLine("problemInfo[6] = String.valueOf(timeOutMilliSeconds);");
    		appendLine("problemInfo[7] = String.valueOf(numberOfThreads);");
			appendLine("problemInfo[8] = systemInfoFile.toString();");
			appendLine("problemInfo[9] = thread.exception() == null ? CSV_NOT_RELEVANT_FIELD: thread.exception();");
			appendEOL();

			appendLine("if (thread.finished()){");
				incrementIndentLevel();
				appendLine("String resultFilePath = inDir + FILE_SEPARATOR + thread.getName().substring(0, thread.getName().length() - PROBLEM_FILE_EXT.length());");
				appendLine("File resultFile = null;");
				appendLine("String resultOutput = null;");
				appendLine("if (thread.model() != null){");
					incrementIndentLevel();
					appendLine("resultFile = getUniqueResultFile(resultFilePath, MODEL_FILE_EXT);");
					appendLine("resultOutput = thread.model().toString();");
					decrementIndentLevel();
				appendLine("}else{");
					incrementIndentLevel();
					appendLine("if(thread.contradiction() != null){");
    		    		                incrementIndentLevel();
    		    		                appendLine("resultFile = getUniqueResultFile(resultFilePath, CONTRADICTION_FILE_EXT);");
					        appendLine("resultOutput = thread.contradiction().toString();");
						decrementIndentLevel();
    		    	                appendLine('}');
					decrementIndentLevel();
				appendLine('}');
				appendLine("if(resultFile != null){");
				        incrementIndentLevel();
				        appendLine("BufferedWriter resultOut = new BufferedWriter(new FileWriter(resultFile));");
				        appendLine("try{");
				                incrementIndentLevel();
				                appendLine("resultOut.write(resultOutput);");
				                decrementIndentLevel();
				        appendLine("}finally{");
				                incrementIndentLevel();
                				appendLine("resultOut.close();");
                			        decrementIndentLevel();
                			appendLine('}');
                			decrementIndentLevel();
                		appendLine('}');
				appendEOL();
				appendLine("problemInfo[1] = thread.result() == null? CSV_NOT_RELEVANT_FIELD: String.valueOf(thread.result());");
				appendLine("problemInfo[2] = resultFile == null ? CSV_NOT_RELEVANT_FIELD:resultFile.toString();");
				appendLine("problemInfo[3] = String.valueOf(thread.time());");
				appendLine("problemInfo[4] = String.valueOf(false);");
				decrementIndentLevel();
			appendLine("}else{");
				incrementIndentLevel();
				appendLine("problemInfo[1] = CSV_NOT_RELEVANT_FIELD;");
				appendLine("problemInfo[2] = CSV_NOT_RELEVANT_FIELD;");
				appendLine("problemInfo[3] = CSV_NOT_RELEVANT_FIELD;");
				appendLine("problemInfo[4] = String.valueOf(true);");
				decrementIndentLevel();
			appendLine('}');
			appendLine("fillCsvStatistics(problemInfo);");
			appendLine("csvOut.writeNext(problemInfo);");
			appendLine("csvOut.flush();");
			decrementIndentLevel();
		appendLine('}');

		appendLine("private static void fillCsvStatistics(String[] problemInfo){");
			//TODO do not hardcode index
			int i = 10;
			incrementIndentLevel();
			for(String sort : signatures.keySet()){
				appendLine("problemInfo[" + i + "] = pStat.getProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.totalNumberOfSortVariables(sort) + "\");");
				i++;
				appendLine("problemInfo[" + i + "] = pStat.getProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.totalNumberOfSortConnectives(sort) + "\");");
				i++;
				appendLine("problemInfo[" + i + "] = pStat.getProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.totalSortVariablesOccurences(sort) + "\");");
				i++;
				appendLine("problemInfo[" + i + "] = pStat.getProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.totalSortVariablesMaxDepth(sort) + "\");");
				i++;
				appendLine("problemInfo[" + i + "] = pStat.getProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.totalSortConnectivesOccurences(sort) + "\");");
				i++;
				appendLine("problemInfo[" + i + "] = pStat.getProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.totalSortConnectivesMaxDepth(sort) + "\");");
				i++;
				for (MettelSignature s : signatures.get(sort)){
					final String connective = s.name();
					appendLine("problemInfo[" + i + "] = pStat.getProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.connectiveSortOccurences(sort, connective) + "\");");
					i++;
					appendLine("problemInfo[" + i + "] = pStat.getProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.connectiveSortMaxDepth(sort, connective) + "\");");
					i++;
				}
			}
			appendLine("problemInfo[" + i + "] = pStat.getProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.TOTAL_VARIABLES_OCCURENCES + "\");");
			i++;
			appendLine("problemInfo[" + i + "] = pStat.getProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.TOTAL_VARIABLES_MAX_DEPTH + "\");");
			i++;
			appendLine("problemInfo[" + i + "] = pStat.getProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.TOTAL_NUMBER_OF_VARIABLES + "\");");
			i++;
			appendLine("problemInfo[" + i + "] = pStat.getProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.TOTAL_CONNECTIVES_OCCURENCES + "\");");
			i++;
			appendLine("problemInfo[" + i + "] = pStat.getProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.TOTAL_CONNECTIVES_MAX_DEPTH + "\");");
			i++;
			appendLine("problemInfo[" + i + "] = pStat.getProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.TOTAL_NUMBER_OF_CONNECTIVES + "\");");
			i++;
			appendLine("problemInfo[" + i + "] = pStat.getProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.TOTAL_SYMBOLS_MAX_DEPTH + "\");");
			i++;
			appendLine("problemInfo[" + i + "] = pStat.getProperty(\"" + MettelProblemAnalyzerDefaultPropertiesNames.TOTAL_SYMBOLS_MAX_LENGTH + "\");");
			i++;
			decrementIndentLevel();
		appendLine('}');
		appendEOL();

		appendLine("private static void parseCommandLineArguments(String[] args) throws IOException{");
			incrementIndentLevel();
			appendLine("final int SIZE = args.length;");
			appendLine("for(int i = 0; i < SIZE; i++){");
				incrementIndentLevel();
				appendLine("if(\"-d\".equals(args[i])||\"--directory\".equals(args[i])){");
					incrementIndentLevel();
					appendLine("if(i < SIZE-1){");
						incrementIndentLevel();
						appendLine("inDir = new File(args[++i]);");
						appendLine("System.out.println(\"Input directory: \" + args[i]);");
						decrementIndentLevel();
					appendLine("}else{");
						incrementIndentLevel();
						appendLine("System.out.println(\"I need a name of directory with problem files.\");");
						appendLine("System.exit(-1);");
						decrementIndentLevel();
					appendLine('}');
					decrementIndentLevel();
				appendLine("}else if(\"-t\".equals(args[i])||\"--timeout\".equals(args[i])){");
					incrementIndentLevel();
					appendLine("if(i < SIZE-1){");
						incrementIndentLevel();
						appendLine("timeOutMilliSeconds = Long.parseLong(args[++i]);");
						appendLine("if(timeOutMilliSeconds < 0)  timeOutMilliSeconds *= -1;");
						appendLine("System.out.println(\"Timeout is: \" + timeOutMilliSeconds + \" ms\");");
						decrementIndentLevel();
					appendLine("}else{");
						incrementIndentLevel();
						appendLine("System.out.println(\"I need a number which specifies timeout.\");");
						appendLine("System.exit(-1);");
						decrementIndentLevel();
					appendLine('}');
					decrementIndentLevel();
				appendLine("}else if(\"-n\".equals(args[i])||\"--number-of-threads\".equals(args[i])){");
					incrementIndentLevel();
					appendLine("if(i < SIZE-1){");
						incrementIndentLevel();
						appendLine("numberOfThreads = Integer.parseInt(args[++i]);");
						appendLine("if(numberOfThreads < 0) numberOfThreads *= -1;");
						appendLine("System.out.println(\"Number of threads is: \" + numberOfThreads);");
						decrementIndentLevel();
					appendLine("}else{");
						incrementIndentLevel();
						appendLine("System.out.println(\"I need a number which specifies number of threads.\");");
						appendLine("System.exit(-1);");
						decrementIndentLevel();
					appendLine('}');
					decrementIndentLevel();
				appendLine("}else if(\"-me\".equals(args[i])||\"--memory-excess\".equals(args[i])){");
					incrementIndentLevel();
					appendLine("if(i < SIZE-1){");
						incrementIndentLevel();
						appendLine("memoryExcess = Integer.parseInt(args[++i]);");
						appendLine("if(memoryExcess < 0) memoryExcess *= -1;");
						appendLine("System.out.println(\"Memory excess per thread is: \" + memoryExcess + 'M');");
						decrementIndentLevel();
					appendLine("}else{");
						incrementIndentLevel();
						appendLine("System.out.println(\"I need a number which specifies the memory excess.\");");
						appendLine("System.exit(-1);");
						decrementIndentLevel();
					appendLine('}');
					decrementIndentLevel();
				appendLine('}');
				decrementIndentLevel();
			appendLine('}');
			// TODO allow to enter single files as well so check both
			appendLine("if (inDir == null){");
				incrementIndentLevel();
				appendLine("System.out.println(\"You must provide input directory.\");");
				appendLine("System.exit(-1);");
				decrementIndentLevel();
			appendLine('}');
			appendLine("if (!inDir.isDirectory()){");
				incrementIndentLevel();
				appendLine("System.out.println(\"Provided input is not a directory.\");");
				appendLine("System.exit(-1);");
				decrementIndentLevel();
			appendLine('}');
			decrementIndentLevel();
		appendLine('}');
		appendEOL();

		// TODO repeating from prover, probably dont even need a method?
		appendLine("public static void parseCalculus(Set<MettelGeneralTableauRule> calculus) throws IOException, RecognitionException{");
			incrementIndentLevel();
			// is it ok?
			appendLine("CharStream tin = new ANTLRInputStream(" +
					prefix + "Benchmark.class.getResourceAsStream(\"calculus\"));");
							//"/"	+ prefix + "/tableau/calculus\"));");
			appendLine("tokens.setTokenSource(new " + SynName + "Lexer(tin));");
			appendLine("parser.tableauCalculus(calculus);");
			decrementIndentLevel();
		appendLine('}');
	}

	// TODO somehow different because output huge and some fields are static same everywhere
	private String generateCSVHeader() {
		StringBuilder header = new StringBuilder("\"File name\", \"Satisfiable\", \"Model's/Contradiction's file name\", \"User time(ms)\", \"Time-out\", \"Timestamp\", \"Allowed execution-time(ms)\", \"Number of threads\", \"System Information\", \"Error\",");
		for(String sort : signatures.keySet()){
			header.append("\"" + MettelProblemAnalyzerDefaultPropertiesNames.totalNumberOfSortVariables(sort) + "\", ");
			header.append("\"" + MettelProblemAnalyzerDefaultPropertiesNames.totalNumberOfSortConnectives(sort) + "\", ");
			header.append("\"" + MettelProblemAnalyzerDefaultPropertiesNames.totalSortVariablesOccurences(sort) + "\", ");
			header.append("\"" + MettelProblemAnalyzerDefaultPropertiesNames.totalSortVariablesMaxDepth(sort) + "\", ");
			header.append("\"" + MettelProblemAnalyzerDefaultPropertiesNames.totalSortConnectivesOccurences(sort) + "\", ");
			header.append("\"" + MettelProblemAnalyzerDefaultPropertiesNames.totalSortConnectivesMaxDepth(sort) + "\", ");
			for (MettelSignature s : signatures.get(sort)){
				final String connective = s.name();
				header.append("\"" + MettelProblemAnalyzerDefaultPropertiesNames.connectiveSortOccurences(sort, connective) + "\", ");
				header.append("\"" + MettelProblemAnalyzerDefaultPropertiesNames.connectiveSortMaxDepth(sort, connective) + "\", ");
			}
		}
		header.append("\"" + MettelProblemAnalyzerDefaultPropertiesNames.TOTAL_VARIABLES_OCCURENCES + "\", ");
		header.append("\"" + MettelProblemAnalyzerDefaultPropertiesNames.TOTAL_VARIABLES_MAX_DEPTH + "\", ");
		header.append("\"" + MettelProblemAnalyzerDefaultPropertiesNames.TOTAL_NUMBER_OF_VARIABLES + "\", ");
		header.append("\"" + MettelProblemAnalyzerDefaultPropertiesNames.TOTAL_CONNECTIVES_OCCURENCES + "\", ");
		header.append("\"" + MettelProblemAnalyzerDefaultPropertiesNames.TOTAL_CONNECTIVES_MAX_DEPTH + "\", ");
		header.append("\"" + MettelProblemAnalyzerDefaultPropertiesNames.TOTAL_NUMBER_OF_CONNECTIVES + "\", ");
		header.append("\"" + MettelProblemAnalyzerDefaultPropertiesNames.TOTAL_SYMBOLS_MAX_DEPTH + "\", ");
		header.append("\"" + MettelProblemAnalyzerDefaultPropertiesNames.TOTAL_SYMBOLS_MAX_LENGTH + "\"");
		return header.toString();
	}
}
