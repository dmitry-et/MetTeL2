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
public class MettelRandomExpressionPropertiesFile extends MettelJavaFile {
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
				appendLine(type + "." + name + ".frequency = 1");
			}
			appendEOL();
			
			appendLine(type + ".variable.frequency = 1");
			appendLine(type + ".depth = 3");
			// default {} because we remove 1st and last character all the 
			// time so it would result in empty string ""
			appendLine(type + ".variables = {}");
			appendLine(type + ".variables.size = 3");
			appendEOL();
			
			appendLine(type + ".generate = 5");
			appendEOL();
		}
	}

}
