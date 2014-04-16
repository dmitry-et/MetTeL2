/**
 * This file is part of MetTeL.
 *
 * MetTeL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MetTeL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MetTeL.  If not, see <http://www.gnu.org/licenses/>.
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
	
	public static final String METTEL_VERSION_VALUE = "TODO";
	public static final String SYNTAX_PATH = "TODO";
	public static final String SAT_TO_UNSAT_RATIO_VALUE = "-1";
}
