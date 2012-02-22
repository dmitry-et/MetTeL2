package mettel.fo;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.IOException;

import java.util.ArrayList;

import junit.framework.*;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

public class MettelFOParserTest extends TestCase{

	private static final String inFile = "test/examples/fo-input";
	private static final String outFile = "test/examples/fo-output";
	
    public void testParser() throws IOException, RecognitionException{

    		CommonTokenStream tokens = new CommonTokenStream();
	        CharStream in = new ANTLRFileStream(inFile);
	        MettelFOLexer lexer = new MettelFOLexer(in);
	        tokens.setTokenSource(lexer);
	        MettelFOParser parser = new MettelFOParser(tokens);
	        final ArrayList<MettelFOFormula> formulae = new ArrayList<MettelFOFormula>(); 
//	        parser.formulae(formulae);

	        StringBuilder b = new StringBuilder();
	        for(MettelFOFormula f:formulae){
	        	b.append(f);
	        	b.append(' ');
	        }

	        in = new ANTLRReaderStream(new StringReader(b.toString()));
	        lexer = new MettelFOLexer(in);
	        tokens.setTokenSource(lexer);
	        final ArrayList<MettelFOFormula> formulae0 = new ArrayList<MettelFOFormula>(); 
//	        parser.formulae(formulae0);

	        PrintWriter w = new PrintWriter(new FileWriter(outFile));
	        for(MettelFOFormula f:formulae0) w.println(f);
	        w.close();
	        
	        assertEquals(formulae,formulae0);
	       
//	        StringBuilder b0 = new StringBuilder();
//	        spec.toBuffer(b0);


//	        assertEquals(b.toString(),b0.toString());
    }
}
