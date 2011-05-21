package mettel.language;

import junit.framework.*;

public class AllTests {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		TestSuite suite= new TestSuite("mettel.language Tests");
		suite.addTestSuite(MettelParserTest.class);
		return suite;
	}
}
