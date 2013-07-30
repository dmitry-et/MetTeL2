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
package mettel.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

//import S4.language.S4ProblemAnalyzer;

import java.util.Map;
import java.util.Properties;

import mettel.core.MettelCoreRuntimeException;

import static mettel.util.MettelStrings.LINE_SEPARATOR;

public class MettelProblemFile{
	
	public final static String GENERATOR_HEADER = "/** Generator Properties **";
	public final static String STATISTICS_HEADER = "/** Problem Statistics **";
	public final static String STATUS_HEADER = "/** Status **";
	public final static String GENERATOR_FOOTER = "** End of Generator Properties **/";
	public final static String STATISTICS_FOOTER = "** End of Problem Statistics **/";
	public final static String STATUS_FOOTER = "** End of Status **/";
		
	private File file = null;
	private String path = null;
	
	private MettelProperties generatorProperties = new MettelProperties();
	private MettelProperties statisticsProperties = new MettelProperties();
	private MettelProperties statusProperties = new MettelProperties();
	private StringBuilder expressions = new StringBuilder();
	
	
	
	public MettelProblemFile(String fileName) throws IOException{
		this(new File(fileName));
	}
		
	public MettelProblemFile(File file) throws IOException{
		this.file = file;
		this.path = file.toString();
		if (file.exists()){
			BufferedReader inReader = new BufferedReader(new FileReader(file));
			try{
				
				String line = null;
				while((line = inReader.readLine()) != null){
					if (line.trim().equals(GENERATOR_HEADER)){
						extractGeneratorProperties(inReader);
						skipEmptyLine(inReader);
					}else if(line.trim().equals(STATISTICS_HEADER)){
						extractStatisticsProperties(inReader);
						skipEmptyLine(inReader);
					}else if(line.trim().equals(STATUS_HEADER)){
						extractStatusProperties(inReader);
						skipEmptyLine(inReader);
					}else{
						expressions.append(line);
						expressions.append(LINE_SEPARATOR);
					}
				}
			}finally{
				inReader.close();
			}
		}
	}
	// TODO won't work if one properties block finishes and another one starts instantly
	private void skipEmptyLine(BufferedReader inReader) throws IOException{
		String line = null;
		if ((line = inReader.readLine()) != null && !line.isEmpty()){
			expressions.append(line);
			expressions.append(LINE_SEPARATOR);
		}
	}
	
	private void extractGeneratorProperties(BufferedReader inReader) throws IOException, MettelCoreRuntimeException{
		String line = null;
		StringBuilder propertiesString = new StringBuilder();
		while ((line = inReader.readLine()) != null && !line.trim().equals(GENERATOR_FOOTER)){
			propertiesString.append(line);
			propertiesString.append(LINE_SEPARATOR);
		}
		if (line == null){
			throw new MettelCoreRuntimeException("Invalid file format, there was no ending of generator block.");
		}
		StringReader propertiesReader = new StringReader(propertiesString.toString());
		generatorProperties.load(propertiesReader);
	}
	
	private void extractStatisticsProperties(BufferedReader inReader) throws IOException, MettelCoreRuntimeException{
		String line = null;
		StringBuilder propertiesString = new StringBuilder();
		while ((line = inReader.readLine()) != null && !line.trim().equals(STATISTICS_FOOTER)){
			propertiesString.append(line);
			propertiesString.append(LINE_SEPARATOR);
		}
		if (line == null){
			throw new MettelCoreRuntimeException("Invalid file format, there was no ending of statistics block.");
		}
		StringReader propertiesReader = new StringReader(propertiesString.toString());
		statisticsProperties.load(propertiesReader);
	}
	
	private void extractStatusProperties(BufferedReader inReader) throws IOException, MettelCoreRuntimeException{
		String line = null;
		StringBuilder propertiesString = new StringBuilder();
		while ((line = inReader.readLine()) != null && !line.trim().equals(STATUS_FOOTER)){
			propertiesString.append(line);
			propertiesString.append(LINE_SEPARATOR);
		}
		if (line == null){
			throw new MettelCoreRuntimeException("Invalid file format, there was no ending of status block.");
		}
		StringReader propertiesReader = new StringReader(propertiesString.toString());
		statusProperties.load(propertiesReader);
	}	

	//TODO change to string?
	public void setGeneratorProperties(Map<? extends Object,? extends Object> properties){
		generatorProperties.putAll(properties);
	}
	
	public void setStatisticsProperties(Map<? extends Object, ? extends Object> properties){
		statisticsProperties.putAll(properties);
	}
	
	public void setStatusProperties(Map<? extends Object, ? extends Object> properties){
		statusProperties.putAll(properties);
	}

	public void setExpressions(CharSequence expressions){
		this.expressions.append(expressions);
	}
	
	public Properties getGeneratorProperties(){
		return generatorProperties;
	}
	
	public Properties getStatisticsProperties(){
		return statisticsProperties;
	}
	
	public Properties getStatusProperties(){
		return statusProperties;
	}

	public CharSequence getExpressions(){
		return expressions;
	}
	
	
	public void addGeneratorProperty(String key, String value){
		generatorProperties.setProperty(key, value);
	}
	
	public void addStatisticsProperty(String key, String value){
		statisticsProperties.setProperty(key, value);
	}
	
	public void addStatusProperty(String key, String value){
		statusProperties.setProperty(key, value);
	}
		
	public boolean existsGeneratorProperties(){
		return !generatorProperties.isEmpty();
	}
	
	public boolean existsStatisticsProperties(){
		return !statisticsProperties.isEmpty();
	}
	
	public boolean existsStatusProperties(){
		return !statusProperties.isEmpty();
	}
	
	public boolean existsExpressions(){
		return expressions.length() > 0;
	}
	
	public void writeToFile() throws IOException{
		if (file.exists()){
			makeBackupOldFile();
		}
		BufferedWriter outWriter = null;
		outWriter = new BufferedWriter(new FileWriter(file));
		try{
			if (existsGeneratorProperties()){
				outWriter.write(GENERATOR_HEADER);
				outWriter.newLine();
				generatorProperties.store(outWriter, "");
				outWriter.write(GENERATOR_FOOTER);
				outWriter.newLine();
				outWriter.newLine();
			}
			if (existsStatisticsProperties()){
				outWriter.write(STATISTICS_HEADER);
				outWriter.newLine();
				statisticsProperties.store(outWriter, "");
				outWriter.write(STATISTICS_FOOTER);
				outWriter.newLine();
				outWriter.newLine();
			}
			if (existsStatusProperties()){
				outWriter.write(STATUS_HEADER);
				outWriter.newLine();
				statusProperties.store(outWriter, "");
				outWriter.write(STATUS_FOOTER);
				outWriter.newLine();
				outWriter.newLine();
			}
			if (existsExpressions()){
				outWriter.write(expressions.toString());
			}
		}finally{
			outWriter.close();
		}
	}
	
	private void makeBackupOldFile(){
		File backupFile = new File(path.replace(".mtl", ".mtl~"));
		if (backupFile.exists() && !backupFile.delete())
			throw new MettelCoreRuntimeException("Couldn't delete old backup file");
		
		if (!file.renameTo(backupFile))
			throw new MettelCoreRuntimeException("Couldn't make backup of old file");
	}

	/*
	public static void main(String [] args) throws IOException{
		try{
			S4ProblemAnalyzer pr = new S4ProblemAnalyzer(args[0]);
			
			MettelProblemFile f = new MettelProblemFile(args[0]);			
			//f.setStatisticsProperties(pr.printAllInfo());
			System.out.println(f.expressions);
			f.writeToFile();

		}catch(Exception e){
	        System.out.println("Sorry! I detected an exceptional situation and terminate now.");
	        System.out.println("If you can help me to avoid this situation in future, please look at my error output.");
	        System.err.println("==Exception==========================");
	        e.printStackTrace(System.err);
	        System.err.println("=====================================");
	        System.exit(-1);
		}
	}
	*/
}
