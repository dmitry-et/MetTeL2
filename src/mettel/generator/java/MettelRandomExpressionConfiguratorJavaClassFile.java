/**
 * 
 */
package mettel.generator.java;

import java.util.ArrayList;
import java.util.Hashtable;

import mettel.util.MettelJavaNames;

import static mettel.generator.MettelANTLRGrammarGeneratorDefaultOptions.NAME_SEPARATOR;

/**
 * @author alijevt1
 *
 */
public class MettelRandomExpressionConfiguratorJavaClassFile extends MettelJavaClassFile implements MettelRandomExpressionDefaultPropertiesValues{
	
	private String prefix = "Mettel";
	private String nameSeparator = NAME_SEPARATOR;
	
	// Can't think of how I can use just ArrayList or HashSet? unless make one array of strings for types and then 2-d array of strings row index being type name and columns
	// perhaps rename
	private Hashtable<String,ArrayList<String>> signatures = new Hashtable<String,ArrayList<String>>();
	
	public MettelRandomExpressionConfiguratorJavaClassFile(String prefix, MettelJavaPackage pack, String nameSeparator) {
		super(prefix + "RandomExpressionConfigurator", pack, "public", null, null);
		this.prefix = prefix;
		this.nameSeparator = nameSeparator;
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
	
	//TODO perhaps rename also repeating code
	void appendSignature(String type) {
		ArrayList<String> ss = signatures.get(type);
		if(ss == null){
			ss = new ArrayList<String>();
			signatures.put(type, ss);
		}
	}
	
	void appendSignature(String type, String name) {
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
			
			
			for(String type:signatures.keySet()){
				
				final String Type = MettelJavaNames.firstCharToUpperCase(type);
				
				for(String name:signatures.get(type)){
					final String ltype = name + Type;
					appendLine("generator.set" + MettelJavaNames.firstCharToUpperCase(ltype, nameSeparator) + "Frequency(Integer.parseInt(configuration.getProperty(\"" + MettelRandomExpressionDefaultPropertiesNames.sortConnectiveFrequencyProperty(type, name) + "\", \"" + SORT_CONNECTIVE_FREQUENCY + "\")));");
				}
				appendEOL();
				
				final String vtype = type + "Variable";
				appendLine("generator.set" + MettelJavaNames.firstCharToUpperCase(vtype) + "Frequency(Integer.parseInt(configuration.getProperty(\"" + MettelRandomExpressionDefaultPropertiesNames.sortVariableFrequencyProperty(type) + "\", \"" + SORT_VARIABLE_FREQUENCY +"\")));");
				appendLine("generator.setDepth" + Type + "(Integer.parseInt(configuration.getProperty(\"" + MettelRandomExpressionDefaultPropertiesNames.sortVariableDepthProperty(type) + "\", \"" + SORT_VARIABLE_DEPTH + "\")));");
				appendEOL();
				
				appendLine("String " + type + "VariablesLine = configuration.getProperty(\"" + MettelRandomExpressionDefaultPropertiesNames.sortVariablesProperty(type) + "\", \"" + SORT_VARIABLES + "\");");
				appendLine(type + "VariablesLine = " + type + "VariablesLine.substring(1, " + type + "VariablesLine.length() - 1);");
				appendLine("String [] " + type + "Variables = " + type + "VariablesLine.split(\",\\\\s*+\");");
				appendLine("generator.set" + Type + "Variables(" + type + "Variables);");
				appendEOL();
				
				appendLine("generator.set" + Type + "VariablesSize(Integer.parseInt(configuration.getProperty(\"" + MettelRandomExpressionDefaultPropertiesNames.sortVariablesSizeProperty(type) + "\", \"" + SORT_VARIABLES_NUMBER + "\")));");
				appendEOL();
				
			}

			// Function call for actually generating some sorts (types) must be the last one because
			// i.e. we have nominal and function sort; if you first generate nominals before 
			// setting formula variables you would get default formula variables instead the set ones 
			for(String type:signatures.keySet()){
				appendLine("int " + type + "TimesToRun = Integer.parseInt(configuration.getProperty(\"" + MettelRandomExpressionDefaultPropertiesNames.sortGenerateProperty(type) + "\", \"" + SORT_GENERATE + "\"));");
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
