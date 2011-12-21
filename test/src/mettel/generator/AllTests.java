package mettel.generator;

import junit.framework.*;

public class AllTests {

	private static final String[] classNames = new String[]{
		"bool.language.test.BooleanParserTest",
		"bool.language.test.BooleanTableauTest",
		"ALCO.language.test.ALCOParserTest",
		"ALCO.language.test.ALCOTableauTest",
		"S4.language.test.S4ParserTest",
		"S4.language.test.S4TableauTest",
		"ALBOid.language.test.ALBOidParserTest",
		"ALBOid.language.test.ALBOidTableauTest",
		"LTL.language.test.LTLParserTest",
		"LTL.language.test.LTLTableauTest",
		"LTLC.language.test.LTLCParserTest",
		"LTLC.language.test.LTLCTableauTest"
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
