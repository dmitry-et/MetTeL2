package mettel.generator;

import junit.framework.*;

public class AllTests {

	private static final String[] classNames = new String[]{
		"V.language.V.test.VParserTest",
		"V.tableau.V.test.VTableauTest",
		"Int.language.Int.test.IntParserTest",
		"Int.tableau.IPC.test.IPCTableauTest",
		"lists.language.lists.test.ListsParserTest",
		"lists.tableau.lists.test.ListsTableauTest",
		"bool.language.Boolean.test.BooleanParserTest",
		"bool.tableau.Boolean.test.BooleanTableauTest",
		"ALCO.language.ALCO.test.ALCOParserTest",
		"ALCO.tableau.ALCO.test.ALCOTableauTest",
		"S4.language.S4.test.S4ParserTest",
		"S4.tableau.S4.test.S4TableauTest",
		"ALBOid.language.ALBOid.test.ALBOidParserTest",
		"ALBOid.tableau.ALBOid.test.ALBOidTableauTest",
		"KE3.language.KE3.test.KE3ParserTest",
		"KE3.tableau.KE3.test.KE3TableauTest",
//		"fotheory.language.test.SomeFOTheoryParserTest",
//		"fotheory.tableau.test.SomeFOTheoryTableauTest",
		"IEL.language.IEL.test.IELParserTest",
		"IEL.tableau.IEL.test.IELTableauTest",
		"LTL.language.LTL.test.LTLParserTest",
		"LTL.tableau.LTL.test.LTLTableauTest",
		"LTLC.language.LTLC.test.LTLCParserTest",
		"LTLC.tableau.LTLC.test.LTLCTableauTest",
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
