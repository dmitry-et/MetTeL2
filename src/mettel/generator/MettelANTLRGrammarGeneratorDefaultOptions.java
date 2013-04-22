package mettel.generator;

import mettel.generator.antlr.MettelANTLRGrammarDefaultOptions;

public interface MettelANTLRGrammarGeneratorDefaultOptions extends MettelANTLRGrammarDefaultOptions, MettelEqualityDefaultKeywords{

	public static final String TABLEAU_RULE_DELIMITER = "$;";
	public static final String TABLEAU_RULE_BRANCH_DELIMITER = "$|";
	public static final String TABLEAU_RULE_PREMISE_DELIMITER = "/";

	public static final String LIST_LEFT_DELIMITER = "";
	public static final String LIST_RIGHT_DELIMITER = "";

	public static final String EXPRESSION_LEFT_DELIMITER = "(";
	public static final String EXPRESSION_RIGHT_DELIMITER = ")";

	public static final String BRANCH_BOUND = null;
	
	public static final String NAME_SEPARATOR = "_";

}