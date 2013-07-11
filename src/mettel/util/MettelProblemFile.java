package mettel.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

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
	
	//private final static String[] propertiesHeaders = {GENERATOR_HEADER, STATISTICS_HEADER, STATUS_HEADER};
	
	private File file = null;
	private String path = null;
	
	private Properties generatorProperties = null;
	private Properties statisticsProperties = null;
	private Properties statusProperties = null;
	private CharSequence expressions = null;
	
	public MettelProblemFile(String fileName) throws IOException{
		this(new File(fileName));
	}
	
	public MettelProblemFile(File file) throws IOException{
		//try{
		this.file = file;
		this.path = file.toString();
		
		BufferedReader inReader = new BufferedReader(new FileReader(file));
		String line = null;
		String stringExpressions = "";
		while((line = inReader.readLine()) != null){
			if (line.trim().equals(GENERATOR_HEADER)){
				extractGeneratorProperties(inReader);
			}else if(line.trim().equals(STATISTICS_HEADER)){
				extractStatisticsProperties(inReader);
			}else if(line.trim().equals(STATUS_HEADER)){
				extractStatusProperties(inReader);
			}else if (!line.isEmpty()){
				stringExpressions = stringExpressions.concat(line + LINE_SEPARATOR);
			}
		}
		/* TODO is it needed??
		if (stringExpressions.equals("")){
			throws new MettelRunTimeException("File can not exist without expressions");
		}
		*/
		this.expressions = stringExpressions;
		inReader.close();
			
		// TODO should I deal with exceptions her? or the caller should deal with them?
		// probably not here
		/*
		}catch(Exception e){
            System.out.println("Sorry! I detected an exceptional situation and terminate now.");
            System.out.println("If you can help me to avoid this situation in future, please look at my error output.");
            System.err.println("==Exception==========================");
            e.printStackTrace(System.err);
            System.err.println("=====================================");
            System.exit(-1);
        }finally{
        	try{ 
        		if (inReader != null) inReader.close(); 
        	}catch (Exception e){
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
	
	private void extractGeneratorProperties(BufferedReader inReader) throws IOException, MettelCoreRuntimeException{
		String line = null;
		String propertiesString = "";
		while ((line = inReader.readLine()) != null && !line.trim().equals(GENERATOR_FOOTER)){
			propertiesString = propertiesString.concat(line + LINE_SEPARATOR);
		}
		//TODO what exception to throw here?
		if (line == null){
			throw new MettelCoreRuntimeException("Invalid file format, there was no ending of generator block.");
		}
		StringReader propertiesReader = new StringReader(propertiesString);
		generatorProperties = new Properties();
		generatorProperties.load(propertiesReader);
	}
	
	private void extractStatisticsProperties(BufferedReader inReader) throws IOException, MettelCoreRuntimeException{
		String line = null;
		String propertiesString = "";
		while ((line = inReader.readLine()) != null && !line.trim().equals(STATISTICS_FOOTER)){
			propertiesString = propertiesString.concat(line + LINE_SEPARATOR);
		}
		if (line == null){
			throw new MettelCoreRuntimeException("Invalid file format, there was no ending of statistics block.");
		}
		StringReader propertiesReader = new StringReader(propertiesString);
		statisticsProperties = new Properties();
		statisticsProperties.load(propertiesReader);
	}
	
	private void extractStatusProperties(BufferedReader inReader) throws IOException, MettelCoreRuntimeException{
		String line = null;
		String propertiesString = "";
		while ((line = inReader.readLine()) != null && !line.trim().equals(STATUS_FOOTER)){
			propertiesString = propertiesString.concat(line + LINE_SEPARATOR);
		}
		if (line == null){
			throw new MettelCoreRuntimeException("Invalid file format, there was no ending of status block.");
		}
		StringReader propertiesReader = new StringReader(propertiesString);
		statusProperties = new Properties();
		statusProperties.load(propertiesReader);
	}	

	//TODO is cloning ok?
	//TODO overwrite or just add new ones??? or both!? i.e. extract all properties
	// from given properties object
	public void setGeneratorProperties(Properties properties){
		generatorProperties = (Properties) properties.clone();
	}
	
	public void setStatisticsProperties(Properties properties){
		statisticsProperties = (Properties) properties.clone();
	}
	
	public void setStatusProperties(Properties properties){
		statusProperties = (Properties) properties.clone();
	}

	//TODO don't need to clone?
	public void setExpressions(CharSequence expressions){
		this.expressions = expressions;
	}
	
	public void addGeneratorProperty(String key, String value){
		if (generatorProperties == null){
			generatorProperties = new Properties();
		}
		generatorProperties.setProperty(key, value);
	}
	
	public void addStatisticsProperty(String key, String value){
		if (statisticsProperties == null){
			statisticsProperties = new Properties();
		}
		statisticsProperties.setProperty(key, value);
	}
	
	public void addStatusProperty(String key, String value){
		if (statusProperties == null){
			statusProperties = new Properties();
		}
		statusProperties.setProperty(key, value);
	}
	
	public void addExpression(CharSequence expression){
		expressions = expressions + LINE_SEPARATOR + expression;
	}
	
	public boolean existsGeneratorProperties(){
		return generatorProperties != null;
	}
	
	public boolean existsStatisticsProperties(){
		return statisticsProperties != null;
	}
	
	public boolean existsStatusProperties(){
		return statusProperties != null;
	}
	
	public boolean existsExpressions(){
		return expressions != null;
	}
	
	public void writeToFile() throws IOException{
		makeBackupOldFile();
		BufferedWriter outWriter = null;
		outWriter = new BufferedWriter(new FileWriter(file));
		if (existsGeneratorProperties()){
			outWriter.write(GENERATOR_HEADER);
			outWriter.newLine();
			generatorProperties.store(outWriter, "");
			outWriter.newLine();
			outWriter.write(GENERATOR_FOOTER);
			outWriter.newLine();
			outWriter.newLine();
		}
		if (existsStatisticsProperties()){
			outWriter.write(STATISTICS_HEADER);
			outWriter.newLine();
			statisticsProperties.store(outWriter, "");
			outWriter.newLine();
			outWriter.write(STATISTICS_FOOTER);
			outWriter.newLine();
			outWriter.newLine();
		}
		if (existsStatusProperties()){
			outWriter.write(STATUS_HEADER);
			outWriter.newLine();
			statusProperties.store(outWriter, "");
			outWriter.newLine();
			outWriter.write(STATUS_FOOTER);
			outWriter.newLine();
			outWriter.newLine();
		}
		if (existsExpressions()){
			outWriter.write(expressions.toString());
		}
	}
	
	private void makeBackupOldFile(){
		File backupFile = new File(path.replace(".mtl", "~.mtl"));
		file.renameTo(backupFile);
		/*TODO is it needed? or just ignore?
		if (!file.renameTo(backupFile))
			throw new IOException("Couldn't make backup of old file");
		*/
	}

/*
	public void setGeneratorProperties(Hashtable<Object,Object> property){
		generatorProperties = (Properties) property;
	}
*/
	
	public static void main(String [] args) throws IOException{
		try{
			MettelProblemFile f = new MettelProblemFile(args[0]);			
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
}
