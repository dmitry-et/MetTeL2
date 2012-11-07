package mettel.core.util;

import junit.framework.*;

public class AllTests {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		TestSuite suite= new TestSuite("mettel.util Tests");
		suite.addTestSuite(MettelSetMapTest.class);
		return suite;
	}
}
