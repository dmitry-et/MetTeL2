/**
 * 
 */
package mettel.generator.java;

/**
 * @author alijevt1
 *
 */
public interface MettelRandomExpressionDefaultPropertiesValues {
	public static final String SORT_CONNECTIVE_FREQUENCY = "1";
	public static final String SORT_VARIABLE_FREQUENCY = "1";
	public static final String SORT_VARIABLE_DEPTH = "1";
	// default {} because we remove 1st and last character all the 
	// time so it would result in empty string ""	
	public static final String SORT_VARIABLES = "{}";
	public static final String SORT_VARIABLES_SIZE = "3";
	public static final String SORT_GENERATE = "5";
}
