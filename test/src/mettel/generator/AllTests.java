package mettel.generator;

import junit.framework.*;

public class AllTests {

	private static final String[] classNames = new String[]{
		"bool.test.BooleanParserTest",
		"bool.test.BooleanTableauTest",
		"ALCO.test.ALCOParserTest",
		"ALCO.test.ALCOTableauTest",
		"S4.test.S4ParserTest",
		"S4.test.S4TableauTest",
		"ALBOid.test.ALBOidParserTest",
		"ALBOid.test.ALBOidTableauTest",
		"LTL.test.LTLParserTest",
		"LTL.test.LTLTableauTest"
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
