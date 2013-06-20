/**
 * 
 */
package mettel.generator.java;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * @author alijevt1
 *
 */
public class MettelRandomExpressionPropertiesFile extends MettelJavaFile implements MettelRandomExpressionDefaultPropertiesValues {
	// Can't think of how I can use just ArrayList or HashSet? unless make one array of strings for types and then 2-d array of strings row index being type name and columns
	// perhaps rename
	private Hashtable<String,ArrayList<String>> signatures = new Hashtable<String,ArrayList<String>>();
	
	//TODO make sure it creates properties directory to do that probably needs new class
	// for creating ordinary files (not extending MettelJavaFile)
	public MettelRandomExpressionPropertiesFile(String prefix, MettelJavaPackage pack) {
		super(prefix + "RandomExpressionPropertiesFile", null, pack);
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
	
	public void generateBody() {
		for(String type:signatures.keySet()){
			for(String name:signatures.get(type)){
				appendLine(MettelRandomExpressionDefaultPropertiesNames.sortConnectiveFrequencyProperty(type, name) + " = " + SORT_CONNECTIVE_FREQUENCY);
			}
			appendEOL();
			
			appendLine(MettelRandomExpressionDefaultPropertiesNames.sortVariableFrequencyProperty(type) +" = " + SORT_VARIABLE_FREQUENCY);
			appendLine(MettelRandomExpressionDefaultPropertiesNames.sortVariableDepthProperty(type) + " = " + SORT_VARIABLE_DEPTH);
			appendLine(MettelRandomExpressionDefaultPropertiesNames.sortVariablesProperty(type) + " = " + SORT_VARIABLES);
			appendLine(MettelRandomExpressionDefaultPropertiesNames.sortVariablesSizeProperty(type) + " = " + SORT_VARIABLES_SIZE);
			appendEOL();
			
			appendLine(MettelRandomExpressionDefaultPropertiesNames.sortGenerateProperty(type) + " = " + SORT_GENERATE);
			appendEOL();
		}
	}

}
