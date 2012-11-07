package mettel.generator;

public interface MettelANTLRGrammarDefaultOptions {

	public static final String TABLEAU_RULE_DELIMITER = "$;";
	public static final String TABLEAU_RULE_BRANCH_DELIMITER = "$|";
	public static final String TABLEAU_RULE_PREMISE_DELIMITER = "/";

	public static final String LIST_LEFT_DELIMITER = "";
	public static final String LIST_RIGHT_DELIMITER = "";

	public static final String EXPRESSION_LEFT_DELIMITER = "(";
	public static final String EXPRESSION_RIGHT_DELIMITER = ")";

	public static final String BRANCH_BOUND = null;

	public static final int ANTLR_K = 1;
	public static final String ANTLR_SUPERCLASS = "mettel.core.language.MettelAbstractLogicParser";
	public static final boolean ANTLR_BACKTRACK = false;
	public static final boolean ANTLR_MEMOIZE = false;
}