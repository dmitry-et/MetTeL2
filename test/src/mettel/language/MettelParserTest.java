package mettel.language;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.IOException;

import junit.framework.*;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

public class MettelParserTest extends TestCase{

	private static final String[] inFiles = new String[]{"test/examples/ALCO/ALCO","test/examples/bool/Boolean" };

    public void testParser() throws IOException, RecognitionException{
    	final int SIZE = inFiles.length;
    	for(int i = 0; i< SIZE; i++){
    		String inFile = inFiles[i]+".s";
    		String outFile = inFiles[i]+"-test-output.s";

	        CommonTokenStream tokens = new CommonTokenStream();
	        CharStream in = new ANTLRFileStream(inFile);
	        MettelLexer lexer = new MettelLexer(in);
	        tokens.setTokenSource(lexer);
	        MettelParser parser = new MettelParser(tokens);
	        MettelSpecification spec = parser.specification();

	        StringBuilder b = new StringBuilder();
	        spec.toBuffer(b);

	        in = new ANTLRReaderStream(new StringReader(b.toString()));
	        lexer = new MettelLexer(in);
	        tokens.setTokenSource(lexer);
	        spec = parser.specification();

	        StringBuilder b0 = new StringBuilder();
	        spec.toBuffer(b0);

	        PrintWriter w = new PrintWriter(new FileWriter(outFile));
	        w.println(b0);
	        w.close();

	        assertEquals(b.toString(),b0.toString());
    	}
    }
}
