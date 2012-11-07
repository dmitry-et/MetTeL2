package mettel;

import junit.framework.*;

public class AllTests {

	public static void main(String[] args)
	   throws java.io.FileNotFoundException{

//		System.setErr(new java.io.PrintStream(
//	            new java.io.FileOutputStream("test.err")));

		new junit.textui.TestRunner(
		  new java.io.PrintStream(
            new java.io.FileOutputStream("test.log"){
                public void write(byte b[], int off, int len)
                    throws java.io.IOException{
                    super.write(b,off,len);
                    System.out.write(b,off,len);
                }
            })).doRun(suite());
	}

	public static Test suite() {
		TestSuite suite= new TestSuite("MetTeL Tests");
		suite.addTest(mettel.core.util.AllTests.suite());
		suite.addTest(mettel.generator.AllTests.suite());
		suite.addTest(mettel.language.AllTests.suite());
		suite.addTest(mettel.fo.AllTests.suite());
		return suite;
	}
}
