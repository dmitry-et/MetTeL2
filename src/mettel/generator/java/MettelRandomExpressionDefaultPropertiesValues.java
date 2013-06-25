/**
 * 
 */
package mettel.generator.java;

/**
 * @author alijevt1
 *
 */
//put into same class? MettelRandomExpressionDefaultPropertiesNames and MettelRandomExpressionDefaultPropertiesValues?
public interface MettelRandomExpressionDefaultPropertiesValues {
	public static final String SORT_CONNECTIVE_FREQUENCY = "1";
	
	public static final String SORT_VARIABLE_FREQUENCY = "1";
	
	public static final String SORT_VARIABLE_DEPTH = "3";
	
	//TODO to ask if it's ok?
	// default variables name which will be printed in default property file is
	// empty string but in code we must set it as null
	// NOTE: in most cases it doesn't make sense to make it differently 
	// (i.e. when we have more than 1 sort)
	// SORT_VARIABLES can't be anything else than null (because it is being set to String array) 
	public static final String SORT_VARIABLES = "null";
	public static final String SORT_VARIABLES_TEXT = "";
	
	public static final String SORT_VARIABLES_NUMBER = "3";
	
	public static final String SORT_GENERATE = "5";
	
	public static final String SORT_TOP_CONNECTIVES = "null";
	public static final String SORT_TOP_CONNECTIVES_TEXT = "";
}
