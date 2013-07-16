package mettel.generator.java;

public class MettelProblemAnalyzerDefaultPropertiesNames {
	public static String sortVariablesNames(String sort){
		return sort + ".variables.names";
	}
	
	public static String totalNumberOfSortVariables(String sort){
		return sort + ".variables.total.number";
	}
	
	public static String sortVariablesOccurences(String sort){
		return sort + ".variables.occurences";
	}
	
	public static String sortVariablesMaxDepth(String sort){
		return sort + ".variables.max-depth";
	}
	
	public static String totalSortVariablesOccurences(String sort){
		return sort + ".variables.total.occurences";
	}
	
	public static String totalSortVariablesMaxDepth(String sort){
		return sort + ".variables.total.max-depth";
	}
	
	public static String totalSortConnectivesOccurences (String sort){
		return sort + ".connectives.total.occurences";
	}

	public static String totalSortConnectivesMaxDepth (String sort){
		return sort + ".conectives.total.max-depth";
	}
	
	public static String connectiveSortOccurences (String sort, String connective){
		return sort + "." + connective + ".occurences";
	}
	
	public static String connectiveSortMaxDepth (String sort, String connective){
		return sort + "." + connective + ".max-depth";
	}
	
	public final static String TOTAL_VARIABLES_OCCURENCES = "variables.total.occurences";
	
	public final static String TOTAL_VARIABLES_MAX_DEPTH = "variables.total.max-depth";
	
	public final static String TOTAL_CONNECTIVES_OCCURENCES = "connectives.total.occurences";
	
	public final static String TOTAL_CONNECTIVES_MAX_DEPTH = "connectives.total.max-depth";
	
	public final static String TOTAL_SYMBOLS_OCCURENCES = "symbols.total.occurences";
	
	public final static String TOTAL_SYMBOLS_MAX_DEPTH = "symbols.total.max-depth";
	
	public final static String TOTAL_SYMBOLS_MAX_LENGTH = "symbols.total.max-length";
}
