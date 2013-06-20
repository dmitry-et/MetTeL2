/**
 * 
 */
package mettel.generator.java;

import java.util.ArrayList;
import java.util.Hashtable;

import mettel.util.MettelJavaNames;

/**
 * @author alijevt1
 *
 */
public class MettelRandomExpressionConfiguratorJavaClassFile extends MettelJavaClassFile{
	
	private String prefix = "Mettel";

	// Can't think of how I can use just ArrayList or HashSet? unless make one array of strings for types and then 2-d array of strings row index being type name and columns
	// perhaps rename
	private Hashtable<String,ArrayList<String>> signatures = new Hashtable<String,ArrayList<String>>();
	
	public MettelRandomExpressionConfiguratorJavaClassFile(String prefix, MettelJavaPackage pack) {
		super(prefix + "RandomExpressionConfigurator", pack, "public", null, null);
		this.prefix = prefix;
	}

	protected void imports(){
		headings.appendEOL();
		headings.appendLine("import mettel.util.MettelJavaNames;");
		headings.appendEOL();
		headings.appendLine("import java.io.FileReader;");
		headings.appendLine("import java.util.Properties;");
		headings.appendLine("import java.io.IOException;");
		headings.appendEOL();		
	}	
	
	// perhaps rename also repeating code
	public void appendSignature(String type) {
		ArrayList<String> ss = signatures.get(type);
		if(ss == null){
			ss = new ArrayList<String>();
			signatures.put(type, ss);
		}
	}
	
	public void appendSignature(String type, String name) {
		ArrayList<String> ss = signatures.get(type);
		if(ss == null){
			ss = new ArrayList<String>();
			signatures.put(type, ss);
		}
		ss.add(name);
	}

	//TODO exceptions
	public void generateBody() {
		appendLine("public static void main (String[] args) throws IOException {");
			incrementIndentLevel();
			appendLine(prefix + "RandomExpressionGenerator generator = new " + prefix + "RandomExpressionGenerator();");
			appendEOL();
			
			appendLine("Properties configuration = new Properties();");
			appendEOL();
			
			appendLine("String path = MettelJavaNames.systemPath(" + prefix + "RandomExpressionConfigurator.class.getPackage().getName() + \".properties." + prefix + "RandomExpressionPropertiesFile\");");
			appendLine("configuration.load(new FileReader(path));");
			appendEOL();
			
			// properties are repeating from MettelRandomExpressionPropertiesFile
			
			for(String type:signatures.keySet()){
				
				final String Type = MettelJavaNames.firstCharToUpperCase(type);
				
				for(String name:signatures.get(type)){
					final String Name = MettelJavaNames.firstCharToUpperCase(name);
					appendLine("generator.set" + Name + Type + "Frequency(Integer.parseInt(configuration.getProperty(\"" + type + "." + name + ".frequency\", \"1\")));");
				}
				appendEOL();
				
				appendLine("generator.set" + Type + "VariableFrequency(Integer.parseInt(configuration.getProperty(\"" + type + ".variable.frequency\", \"1\")));");
				appendLine("generator.setDepth" + Type + "(Integer.parseInt(configuration.getProperty(\"" + type + ".depth\", \"3\")));");
				appendEOL();
				
				// default is {} because we always delete first and last chars so it would be empty string
				appendLine("String " + type + "VariablesLine = configuration.getProperty(\"" + type + ".variables\", \"{}\");");
				appendLine(type + "VariablesLine = " + type + "VariablesLine.substring(1, " + type + "VariablesLine.length() - 1);");
				appendLine("String [] " + type + "Variables = " + type + "VariablesLine.split(\"\\\\s*+\");");
				appendLine("generator.set" + Type + "Variables(" + type + "Variables);");
				appendEOL();
				
				appendLine("generator.set" + Type + "VariablesSize(Integer.parseInt(configuration.getProperty(\"" + type + ".variables.size\", \"3\")));");
				appendEOL();
				
			}

			// Function call for actually generating some sorts (types) must be the last one because
			// i.e. we have nominal and function sort; if you first generate nominals before 
			// setting formula variables you would get default formula variables instead the set ones 
			for(String type:signatures.keySet()){
				appendLine("int " + type + "TimesToRun = Integer.parseInt(configuration.getProperty(\"" + type + ".generate\", \"5\"));");
				appendLine("for (int i = 0; i < " + type + "TimesToRun; i++)");
					incrementIndentLevel();
					appendLine("System.out.println(generator." + type + "());");
					decrementIndentLevel();
				appendEOL();
			}
			
			decrementIndentLevel();
		appendLine("}");
	}	
}
