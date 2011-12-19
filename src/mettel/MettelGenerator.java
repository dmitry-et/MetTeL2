/**
 * This file is part of MetTeL.
 *
 * MetTeL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MetTeL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MetTeL.  If not, see <http://www.gnu.org/licenses/>.
 */
package mettel;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
//import org.antlr.tool.ErrorManager;

//import org.antlr.Tool;

import mettel.generator.MettelANTLRGrammarGenerator;
import mettel.generator.MettelANTLRGrammarGeneratorProperties;
import mettel.generator.java.MettelJavaPackageStructure;
import mettel.language.MettelLexer;
import mettel.language.MettelParser;
import mettel.language.MettelSpecification;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelGenerator {

	private static PrintWriter out = null;
	private static PrintWriter err = null;
	private static CharStream in = null;
	private static String outFileName = null;
	private static String outputPath = "output";
	private static FileReader prop = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("-----------------------------------------------------------------");
		System.out.println("MetTeL  Copyright (C) 2009-2011 Dmitry Tishkovsky");
	    System.out.println("This program comes with ABSOLUTELY NO WARRANTY.");//for details type `show w'.
	    System.out.println("This is free software, and you are welcome to redistribute it");
	    System.out.println("under certain conditions; use the option `--license' for details.");
	    System.out.println("-----------------------------------------------------------------");
    	try{
    		final int SIZE = args.length;
    		for(int i = 0; i < SIZE; i++){
        	  	if("-i".equals(args[i])||"--input".equals(args[i])){
        	        if(i < SIZE-1){
            		    in = new ANTLRFileStream(args[++i]);
            		    System.out.println("Input file: "+args[i]);
                    }else{
                        System.out.println("Input file name required");
                        System.exit(-1);
                    }

        		}else if("-o".equals(args[i])||"--output".equals(args[i])){

                    if(i < SIZE-1){
                      outFileName = args[++i];
            		  out = new PrintWriter(new FileWriter(outFileName),true);
            		  System.out.println("Output file: "+outFileName);
                    }else{
                        System.out.println("Output file name required");
                        System.exit(-1);
                    }

        		}else if("-e".equals(args[i])||"--error".equals(args[i])){

                    if(i < SIZE-1){
            		  err = new PrintWriter(new FileWriter(args[++i]),true);
            		  System.out.println("Using error file: "+args[i]);
                    }else{
                        System.out.println("Error file name required");
                        System.exit(-1);
                    }
        		}else if("-d".equals(args[i])||"--output-directory".equals(args[i])){

                    if(i < SIZE-1){
            		  outputPath = args[++i];
            		  System.out.println("Output path: "+outputPath);
                    }else{
                        System.out.println("Output directory name required");
                        System.exit(-1);
                    }
        		}else if("-p".equals(args[i])||"--properties".equals(args[i])){

        			if(i < SIZE-1){
       					prop = new FileReader(args[++i]);
        				System.out.println("Properties file: "+args[i]);
                    }else{
                        System.out.println("Properties file name required");
                        System.exit(-1);
                    }
        		}
        	}



        	CommonTokenStream tokens = new CommonTokenStream();

    		if(in == null) in = new ANTLRInputStream(System.in);

        	MettelLexer lexer = new MettelLexer(in);

        	tokens.setTokenSource(lexer);
        	MettelParser parser = new MettelParser(tokens);

        	if(out == null) out = new PrintWriter(
    				new OutputStreamWriter(System.out),true);
         	if(err == null) err = new PrintWriter(
    				new OutputStreamWriter(System.err),true);

        	MettelSpecification spec = parser.specification();

        	//StringBuilder buf = new StringBuilder();
        	//spec.toBuffer(buf);
        	//System.out.print(buf);
        	MettelANTLRGrammarGeneratorProperties p = (prop == null)? null: new MettelANTLRGrammarGeneratorProperties(prop);
        	MettelANTLRGrammarGenerator gen = new MettelANTLRGrammarGenerator(spec,p);
//        	StringBuilder buf = new StringBuilder();
        	for(MettelJavaPackageStructure pStructure:gen.processSyntaxes()){
        		//System.out.println('#');
        		pStructure.flush(outputPath);
        	}
/*        	out.print(buf);
        	out.flush();
        	out.close();
*/
/*        	String[] antlrArgs = {"-o", "/var/tmp/", "-print", outFileName};
        	if(outFileName == null){
        		System.err.print("ANTLR file name required");
        		System.exit(0);
        	}

        	Tool antlr = new Tool(antlrArgs);
            antlr.process();
            if(ErrorManager.getNumErrors() > 0){
            	System.exit(1);
            }
*/

        	System.exit(0);
        } catch(Exception e) {

    	    	err.println("==Exception==========================");
           	e.printStackTrace(err);
            	err.println("=====================================");
            	System.exit(-1);

        }

	}

}
