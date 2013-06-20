/**
 * 
 */
package mettel.generator.java;

/**
 * @author alijevt1
 *
 */
public class MettelRandomExpressionDefaultPropertiesNames {

	public static String sortConnectiveFrequencyProperty(String sort, String connective) {
		return sort + "." + connective + ".frequency";
	}
	
	public static String sortVariableFrequencyProperty(String sort) {
		return sort + ".variable.frequency";
	}
	
	public static String sortVariableDepthProperty(String sort) {
		return sort + ".depth";
	}
	
	public static String sortVariablesProperty(String sort) {
		return sort + ".variables";
	}
	
	public static String sortVariablesSizeProperty(String sort) {
		return sort + ".variables.size";
	}
	
	public static String sortGenerateProperty(String sort) {
		return sort + ".generate";
	}
	
	/*
	// or perhaps create an object 
	private String sort, connective;
	
	public MettelRandomExpressionDefaultPropertiesNames(String sort, String connective) {
		this.sort = sort;
		this.connective = connective;
	}
	
	public String sortConnectiveFrequencyProperty(String sort, String connective) {
		return sort + "." + connective + ".frequency";
	}
	
	public String sortVariableFrequencyProperty(String sort) {
		return sort + ".variable.frequency";
	}
	
	public String sortVariableDepthProperty(String sort) {
		return sort + ".depth";
	}
	
	public String sortVariablesProperty(String sort) {
		return sort + ".variables";
	}
	
	public String sortVariablesSizeProperty(String sort) {
		return sort + ".variables.size";
	}
	
	public String sortGenerateProperty(String sort) {
		return sort + ".generate";
	}

	*/
	
}
