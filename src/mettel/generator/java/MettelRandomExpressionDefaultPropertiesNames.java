/**
 * 
 */
package mettel.generator.java;

/**
 * @author alijevt1
 *
 */
// put into same class? MettelRandomExpressionDefaultPropertiesNames and MettelRandomExpressionDefaultPropertiesValues?
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
		return sort + ".variables.number";
	}
	
	public static String sortGenerateProperty(String sort) {
		return sort + ".generate";
	}
	
	public static String sortTopConnectives(String sort) {
		return sort + ".top.connectives";
	}
	
	public static String METTEL_VERSION = "MetTeL.version";
	
	public static String SYNTAX = "syntax.file";
}
