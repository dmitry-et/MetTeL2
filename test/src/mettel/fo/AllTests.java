package mettel.fo;

import junit.framework.*;

public class AllTests {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		TestSuite suite= new TestSuite("mettel.fo Tests");
		suite.addTestSuite(MettelFOParserTest.class);
		return suite;
	}
}
