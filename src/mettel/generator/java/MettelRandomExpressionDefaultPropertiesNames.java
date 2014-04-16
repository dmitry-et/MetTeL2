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
// put into same class? MettelRandomExpressionDefaultPropertiesNames and MettelRandomExpressionDefaultPropertiesValues?
public class MettelRandomExpressionDefaultPropertiesNames {

	public static final String SAT_TO_UNSAT_RATIO = "sat.to.unsat.ratio";

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
