/**
 * 
 */
package mettel.generator.java;

import static mettel.generator.MettelANTLRGrammarGeneratorDefaultOptions.NAME_SEPARATOR;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * @author alijevt1
 *
 */
public class MettelRandomExpressionPropertiesFile extends MettelJavaFile {

	//TODO do I really need those private variables? probably not
	private String prefix = "Mettel";
	private MettelJavaPackage langPack = null;
	
	private String nameSeparator = NAME_SEPARATOR;
	
	//TODO probably types aren't needed
	private class Signature{

		//private String type;
		private String name;
		private String[] types;

		Signature(String name, String[] types){
			super();
			//this.type = type;
			this.name = name;
			this.types = types;
		}
	}
	private Hashtable<String,ArrayList<Signature>> signatures = new Hashtable<String,ArrayList<Signature>>();	
	
	//TODO make sure it creates properties directory to do that probably needs new class
	// for creating ordinary files (not extending MettelJavaFile)
	public MettelRandomExpressionPropertiesFile(String prefix, MettelJavaPackage pack, MettelJavaPackage langPack, String nameSeparator) {
		super(prefix + "RandomExpressionPropertiesFile", null, pack);
			this.prefix = prefix;
			this.langPack = langPack;
			this.nameSeparator = nameSeparator;
	}
	
	public void appendSignature(String type){
		ArrayList<Signature> ss = signatures.get(type);
		if(ss == null){
			ss = new ArrayList<Signature>();
			signatures.put(type, ss);
		}
	}

	public void appendSignature(String type, String name, String[] types){
		ArrayList<Signature> ss = signatures.get(type);
		if(ss == null){
			ss = new ArrayList<Signature>();
			signatures.put(type, ss);
		}
		ss.add(new Signature(name, types));
	}
	
	public void generateBody() {
		for(String type:signatures.keySet()){
			for(Signature s:signatures.get(type)){
				appendLine(type + "." + s.name + ".frequency = 1");
			}
			appendEOL();
			
			appendLine(type + ".variable.frequency = 1");
			appendLine(type + ".depth = 3");
			appendLine(type + ".variables = {}");
			appendLine(type + ".variables.size = 3");
			appendEOL();
			
			appendLine(type + ".generate = 5");
			appendEOL();
		}
	}
}
