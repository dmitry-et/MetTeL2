/**
 * 
 */
package mettel.generator.java;

import static mettel.generator.MettelANTLRGrammarGeneratorDefaultOptions.NAME_SEPARATOR;

import java.util.ArrayList;
import java.util.Hashtable;

import mettel.util.MettelJavaNames;

/**
 * @author alijevt1
 *
 */
public class MettelRandomExpressionConfigurator extends MettelJavaClassFile{
	
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
	
	public MettelRandomExpressionConfigurator(String prefix, MettelJavaPackage pack, MettelJavaPackage langPack, String nameSeparator) {
		super(prefix + "RandomExpressionConfigurator", pack, "public", null, null);
		this.prefix = prefix;
		this.langPack = langPack;
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
				
				for(Signature s:signatures.get(type)){
					final String Name = MettelJavaNames.firstCharToUpperCase(s.name);
					appendLine("generator.set" + Name + Type + "Frequency(Integer.parseInt(configuration.getProperty(\"" + type + "." + s.name + ".frequency\")));");
				}
				appendEOL();
				
				appendLine("generator.set" + Type + "VariableFrequency(Integer.parseInt(configuration.getProperty(\"" + type + ".variable.frequency\")));");
				appendLine("generator.setDepth" + Type + "(Integer.parseInt(configuration.getProperty(\"" + type + ".depth\")));");
				appendEOL();
				
				appendLine("String " + type + "VariablesLine = configuration.getProperty(\"" + type + ".variables\");");
				appendLine(type + "VariablesLine = " + type + "VariablesLine.substring(1, " + type + "VariablesLine.length() - 1);");
				appendLine("String [] " + type + "Variables = " + type + "VariablesLine.split(\"\\\\s*+\");");
				appendLine("generator.set" + Type + "Variables(" + type + "Variables);");
				appendEOL();
				
				appendLine("generator.set" + Type + "VariablesSize(Integer.parseInt(configuration.getProperty(\"" + type + ".variables.size\")));");
				appendEOL();
				
				appendLine("int " + type + "TimesToRun = Integer.parseInt(configuration.getProperty(\"" + type + ".generate\"));");
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
