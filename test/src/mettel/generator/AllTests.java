package mettel.generator;

import junit.framework.*;

public class AllTests {

	private static final String[] classNames = new String[]{
		"lists.language.test.ListsParserTest",
		"lists.tableau.test.ListsTableauTest",
		"bool.language.test.BooleanParserTest",
		"bool.tableau.test.BooleanTableauTest",
		"ALCO.language.test.ALCOParserTest",
		"ALCO.tableau.test.ALCOTableauTest",
		"S4.language.test.S4ParserTest",
		"S4.tableau.test.S4TableauTest",
		"ALBOid.language.test.ALBOidParserTest",
		"ALBOid.tableau.test.ALBOidTableauTest",
		"KE3.language.test.KE3ParserTest",
		"KE3.tableau.test.KE3TableauTest",
//		"fotheory.language.test.SomeFOTheoryParserTest",
//		"fotheory.tableau.test.SomeFOTheoryTableauTest",
		"IEL.language.test.IELParserTest",
		"IEL.tableau.test.IELTableauTest",
		"LTL.language.test.LTLParserTest",
		"LTL.tableau.test.LTLTableauTest",
		"LTLC.language.test.LTLCParserTest",
		"LTLC.tableau.test.LTLCTableauTest",
	};

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		TestSuite suite= new TestSuite("mettel.generator Tests");

		for(String s:classNames){
				try {
					suite.addTestSuite(Class.forName(s));
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return suite;
	}
}
