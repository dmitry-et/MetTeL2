package mettel.generator.java;

public class MettelProblemAnalyzerDefaultPropertiesNames {
	public static String sortVariablesNames(String sort){
		return sort + ".variable.names";
	}
	
	public static String totalNumberOfSortVariables(String sort){
		return sort + ".total.variable.number";
	}
	
	public static String sortVariablesOccurences(String sort){
		return sort + ".variable.occurences";
	}
	
	public static String sortVariablesMaxDepth(String sort){
		return sort + ".variable.max-depth";
	}
	
	public static String totalSortVariablesOccurences(String sort){
		return sort + ".total.variable.occurences";
	}
	
	public static String totalSortVariablesMaxDepth(String sort){
		return sort + ".total.variable.max-depth";
	}
	
	public static String totalSortConnectivesOccurences (String sort){
		return sort + ".total.connective.occurences";
	}

	public static String totalSortConnectivesMaxDepth (String sort){
		return sort + ".total.connective.max-depth";
	}
	
	public static String connectiveSortOccurences (String sort, String connective){
		return sort + "." + connective + ".occurences";
	}
	
	public static String connectiveSortMaxDepth (String sort, String connective){
		return sort + "." + connective + ".max-depth";
	}
	
	public final static String TOTAL_VARIABLES_OCCURENCES = "total.variable.occurences";
	
	public final static String TOTAL_VARIABLES_MAX_DEPTH = "total.variable.max-depth";
	
	public final static String TOTAL_CONNECTIVES_OCCURENCES = "total.connective.occurences";
	
	public final static String TOTAL_CONNECTIVES_MAX_DEPTH = "total.connective.max-depth";
	
	public final static String TOTAL_SYMBOLS_OCCURENCES = "total.symbol.occurences";
	
	public final static String TOTAL_SYMBOLS_MAX_DEPTH = "total.symbol.max-depth";
	
	public final static String TOTAL_SYMBOLS_MAX_LENGTH = "total.symbol.length";
	
	//add total connectives number (if >0 ++)
	//add sort connectives number (if >0 ++)
	//add total number of variables (different variable names)
}
