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

public class MettelProblemAnalyzerDefaultPropertiesNames {
	public final static String sortVariablesNames(String sort){
		return sort + ".variable.names";
	}
	
	public final static String totalNumberOfSortVariables(String sort){
		return sort + ".total.variable.number";
	}
	
	//this
	public final static String totalNumberOfSortConnectives(String sort){
		return sort + ".total.connective.number";
	}
	
	public final static String sortVariablesOccurences(String sort){
		return sort + ".variable.occurences";
	}
	
	public final static String sortVariablesMaxDepth(String sort){
		return sort + ".variable.max-depth";
	}
	
	public final static String totalSortVariablesOccurences(String sort){
		return sort + ".total.variable.occurences";
	}
	
	public final static String totalSortVariablesMaxDepth(String sort){
		return sort + ".total.variable.max-depth";
	}
	
	public final static String totalSortConnectivesOccurences (String sort){
		return sort + ".total.connective.occurences";
	}

	public final static String totalSortConnectivesMaxDepth (String sort){
		return sort + ".total.connective.max-depth";
	}
	
	public final static String connectiveSortOccurences (String sort, String connective){
		return sort + "." + connective + ".occurences";
	}
	
	public final static String connectiveSortMaxDepth (String sort, String connective){
		return sort + "." + connective + ".max-depth";
	}
	
	public final static String TOTAL_VARIABLES_OCCURENCES = "total.variable.occurences";
	
	public final static String TOTAL_VARIABLES_MAX_DEPTH = "total.variable.max-depth";
	
	public final static String TOTAL_NUMBER_OF_VARIABLES = "total.variable.number";
	
	public final static String TOTAL_CONNECTIVES_OCCURENCES = "total.connective.occurences";
	
	public final static String TOTAL_CONNECTIVES_MAX_DEPTH = "total.connective.max-depth";
	
	public final static String TOTAL_NUMBER_OF_CONNECTIVES= "total.connective.number";
	
	public final static String TOTAL_SYMBOLS_OCCURENCES = "total.symbol.occurences";
	
	public final static String TOTAL_SYMBOLS_MAX_DEPTH = "total.symbol.max-depth";
	
	public final static String TOTAL_SYMBOLS_MAX_LENGTH = "total.symbol.length";
}
