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

import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;

import mettel.language.MettelLexer;
import mettel.language.MettelParser;
import mettel.language.MettelSpecification;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

    	PrintWriter out =
    			new PrintWriter(
    				new OutputStreamWriter(System.out),true);
    	PrintWriter err =
    			new PrintWriter(
    				new OutputStreamWriter(System.err),true);
    	try{
    		CharStream in = new ANTLRInputStream(System.in);
        	final int SIZE = args.length;
        	for(int i = 0; i < SIZE; i++){
        	  	if("-i".equals(args[i])||"--input".equals(args[i])){

                    if(i < SIZE-1){
            		  in = new ANTLRFileStream(args[++i]);
                    }else{
                        System.out.println("Input file name required");
                    }

        		}else if("-o".equals(args[i])||"--output".equals(args[i])){

                    if(i < SIZE-1){
            		  out = new PrintWriter(new FileWriter(args[++i]),true);
                    }else{
                        System.out.println("Output file name required");
                    }

        		}else if("-e".equals(args[i])||"--error".equals(args[i])){

                    if(i < SIZE-1){
            		  err = new PrintWriter(new FileWriter(args[++i]),true);
                    }else{
                        System.out.println("Error file name required");
                    }
        		}
        	}
        	MettelLexer lexer = new MettelLexer(in);
            CommonTokenStream tokens = new CommonTokenStream();
        	tokens.setTokenSource(lexer);
        	MettelParser parser = new MettelParser(tokens);

        	MettelSpecification spec = parser.specification();

        	System.exit(0);
        } catch(Exception e) {

    	    	err.println("==Exception==========================");
           		e.printStackTrace(err);
            	err.println("=====================================");
            	System.exit(-1);

        }

	}

}
