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
package mettel.generator.java.test;

import mettel.util.MettelJavaNames;
import mettel.generator.java.*;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelParserTestJavaClassFile extends MettelJavaClassFile {

	private String prefix = "Mettel";
//	private String packName = null;
//	private String packNameFull = null;
	
	private MettelJavaPackageStructure pStructure = null;
	
	public MettelParserTestJavaClassFile(String prefix, String sort, MettelJavaPackageStructure pStructure) {
		super(prefix+"ParserTest", pStructure.testLanguagePackage(), "public", "TestCase", null);
		this.pStructure = pStructure;
		
		this.prefix = prefix;
		
//		packName = pack.path();
//		packNameFull = packName.substring(0,packName.lastIndexOf('.')+1);
//		packName = packName.substring(0,packName.indexOf('.')+1);
		
		body(sort);
	}

	protected void imports(){
		headings.appendLine("import java.util.ArrayList;");
		headings.appendLine("import java.io.FileWriter;");
		headings.appendLine("import java.io.PrintWriter;");
		headings.appendLine("import java.io.StringReader;");
		headings.appendLine("import java.io.IOException;");
		headings.appendEOL();
		headings.appendLine("import junit.framework.*;");
		headings.appendEOL();
		headings.appendLine("import org.antlr.runtime.ANTLRFileStream;");
		headings.appendLine("import org.antlr.runtime.ANTLRReaderStream;");
		headings.appendLine("import org.antlr.runtime.CharStream;");
		headings.appendLine("import org.antlr.runtime.CommonTokenStream;");
		headings.appendLine("import org.antlr.runtime.RecognitionException;");
		headings.appendEOL();
		headings.appendLine("import "+pStructure.languagePackage().path()+".*;");
		headings.appendEOL();
	}

	private void body(String sort){

		appendLine("final static String inFile = \""+MettelJavaNames.quote(MettelJavaNames.systemPath("test.examples."+pStructure.basePackage().path()+".input"))+"\";");
		appendLine("final static String outFile = \""+MettelJavaNames.quote(MettelJavaNames.systemPath("test.examples."+pStructure.basePackage().path()+".output"))+"\";");

		appendLine("public void testParser() throws IOException, RecognitionException{");
		incrementIndentLevel();

		appendLine("CommonTokenStream tokens = new CommonTokenStream();");
		appendLine("CharStream in = new ANTLRFileStream(inFile);");
		appendLine(prefix+"Lexer lexer = new "+prefix+"Lexer(in);");
		appendLine("tokens.setTokenSource(lexer);");
    	appendLine(prefix+"Parser parser = new "+prefix+"Parser(tokens);");

        appendLine("ArrayList<"+prefix+MettelJavaNames.firstCharToUpperCase(sort)+
        		"> list = new ArrayList<"+prefix+MettelJavaNames.firstCharToUpperCase(sort)+">();");
        appendLine("parser."+sort+"s(list);");

        appendLine("StringBuilder b = new StringBuilder();");
        appendLine("for("+prefix+MettelJavaNames.firstCharToUpperCase(sort)+" e:list){");
        	incrementIndentLevel();
        	appendLine("b.append(e);");
        	//appendLine("System.out.println(e);");
        	appendLine("b.append(System.getProperty(\"line.separator\"));");
        	decrementIndentLevel();
        appendLine('}');

        appendLine("PrintWriter w = new PrintWriter(new FileWriter(outFile));");
        appendLine("w.println(b);");

        appendLine("in = new ANTLRReaderStream(new StringReader(b.toString()));");
        appendLine("lexer = new "+prefix+"Lexer(in);");
        appendLine("tokens.setTokenSource(lexer);");

        appendLine("ArrayList<"+prefix+MettelJavaNames.firstCharToUpperCase(sort)+
        		"> list0 = new ArrayList<"+prefix+MettelJavaNames.firstCharToUpperCase(sort)+">();");
        appendLine("parser."+sort+"s(list0);");

        appendLine("assertEquals(list,list0);");

        appendLine("final int SIZE = list.size();");
        appendLine("for(int i = 0; i < SIZE; i++){");
        	incrementIndentLevel();
        		appendLine("for(int j = i + 1; j < SIZE; j++){");
        			incrementIndentLevel();
        				appendLine("assertEquals(list.get(i).compareTo(list.get(j)), -list.get(j).compareTo(list.get(i)));");
        				appendLine("if(list.get(i).compareTo(list.get(j)) == 0){ assertEquals(list.get(i),list.get(j)); }");
appendLine("w.println(\"Matching \"+list.get(i)+\" with \"+list.get(j));");
        				appendLine(prefix+"Substitution s = list.get(i).match(list.get(j));");
appendLine("w.println(\"Substitution: \"+s);");
        				appendLine("if(s != null){");
        					incrementIndentLevel();
        						//appendLine("System.out.println(\"Match \"+list.get(i)+' '+list.get(j));");
        						appendLine("assertEquals(list.get(j),list.get(i).substitute(s));");
        					decrementIndentLevel();
        				appendLine('}');
        				appendLine("s = list.get(j).match(list.get(i));");
        				appendLine("if(s != null){");
        					incrementIndentLevel();
        						//appendLine("System.out.println(\"Match \"+list.get(i)+' '+list.get(j));");
        						appendLine("assertEquals(list.get(i),list.get(j).substitute(s));");
        					decrementIndentLevel();
        				appendLine('}');
        			decrementIndentLevel();
        		appendLine('}');
        	decrementIndentLevel();
        appendLine('}');

        appendLine("w.close();");


        decrementIndentLevel();
    	appendLine('}');
	}

}
