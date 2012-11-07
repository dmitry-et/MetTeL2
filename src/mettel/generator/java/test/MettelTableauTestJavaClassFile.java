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
public class MettelTableauTestJavaClassFile extends MettelJavaClassFile {

	private String prefix = "Mettel";
//	private String packName = null;
//	private String packNameFull = null;

	private MettelJavaPackageStructure pStructure = null;

	public MettelTableauTestJavaClassFile(String prefix, String sort, String branchBound, MettelJavaPackageStructure pStructure) {
		super(prefix+"TableauTest", pStructure.testTableauPackage(), "public", "TestCase", null);
		this.pStructure = pStructure;
		this.prefix = prefix;

//		packName = pack.path();
//		packNameFull = packName.substring(0,packName.lastIndexOf('.')+1);
//		packName = packName.substring(0,packName.indexOf('.')+1);

		body(sort, branchBound);

		if(branchBound != null){
			headings.appendLine("import mettel.core.tableau.acceptor.MettelSmallTableauStateAcceptor;");
		}
	}

	protected void imports(){
		headings.appendLine("import java.util.ArrayList;");
//		headings.appendLine("import java.util.Set;");
//		headings.appendLine("import java.util.TreeSet;");
		headings.appendLine("import java.util.LinkedHashSet;");
		headings.appendLine("import java.io.File;");
//		headings.appendLine("import java.io.FileWriter;");
//		headings.appendLine("import java.io.PrintWriter;");
//		headings.appendLine("import java.io.StringReader;");
		headings.appendLine("import java.io.IOException;");

		headings.appendLine("import junit.framework.*;");

		headings.appendLine("import org.antlr.runtime.ANTLRFileStream;");
//		headings.appendLine("import org.antlr.runtime.ANTLRReaderStream;");
		headings.appendLine("import org.antlr.runtime.CharStream;");
		headings.appendLine("import org.antlr.runtime.CommonTokenStream;");
		headings.appendLine("import org.antlr.runtime.RecognitionException;");

		headings.appendLine("import mettel.core.tableau.MettelSimpleTableauManager;");
		headings.appendLine("import mettel.core.tableau.MettelGeneralTableauRule;");
		headings.appendLine("import mettel.core.tableau.MettelTableauObjectFactory;");
		//headings.appendLine("import mettel.core.tableau.acceptor.MettelSmallTableauStateAcceptor;");

		headings.appendLine("import "+pStructure.languagePackage().path()+".*;");
		headings.appendEOL();
	}

	private void body(String sort, String branchBound){

    	appendLine("final static String tableauFile = \""+MettelJavaNames.quote(MettelJavaNames.systemPath("test.examples."+pStructure.basePackage().path()+".tableau"))+"\";");
    	appendLine("final static String inDir = \""+MettelJavaNames.quote(MettelJavaNames.systemPath("test.examples."+pStructure.basePackage().path()+".problems"))+"\";");

    	appendEOL();

    	appendLine("public void testTableau() throws IOException, RecognitionException{");
    	incrementIndentLevel();
    		appendLine("CommonTokenStream tokens = new CommonTokenStream();");
    	    appendLine("CharStream tin = new ANTLRFileStream(tableauFile);");
    	    appendLine(prefix+"Lexer lexer = new "+prefix+"Lexer(tin);");

    	    appendEOL();

    	    appendLine("tokens.setTokenSource(lexer);");
    	    appendLine(prefix+"Parser parser = new "+prefix+"Parser(tokens);");

    	    appendEOL();

appendLine("System.out.println();");
appendLine(  "System.out.println(\"----------------------------------------------------\");");
appendLine("System.out.print(\"Reading tableau calculus for "+prefix+"...\");");

			appendLine("LinkedHashSet<MettelGeneralTableauRule> calculus = new LinkedHashSet<MettelGeneralTableauRule>();");
    	    appendLine("parser.tableauCalculus(calculus);");

//appendLine("System.out.println(calculus);");
appendLine("System.out.println(\"OK\");");
appendLine(  "System.out.println(\"----------------------------------------------------\");");
appendLine("System.out.println(\"Testing tableau algorithm for "+prefix+"\");");
appendLine(  "System.out.print(\"----------------------------------------------------\");");

    	    appendEOL();

    	    appendLine("File problemDir = new File(inDir);");
    	    appendLine("String[] fileNames = problemDir.list();");

    	    appendLine("int counter = 0;");
    	    appendLine("for(String fileName:fileNames){");
    	    incrementIndentLevel();
    	        appendLine("int i = fileName.lastIndexOf('.');");
    	        appendLine("if(i != -1){");
    	        incrementIndentLevel();
    	        	appendLine("String ext = fileName.substring(i);");
    	        	appendLine("if(ext.equals(\".mtl\")){");
    	        	incrementIndentLevel();
    	        		appendLine("String name = fileName.substring(0, i);");
    	        		appendLine("int j = name.lastIndexOf('-');");
    	        		appendLine("if(j != -1){");
    	        		incrementIndentLevel();
    	        		appendLine("counter++;");
appendLine("System.out.println();");
appendLine("System.out.print(\"\"+counter+\". \"+fileName+\"...\");");

    	        			appendLine("boolean expected = name.substring(j+1).equals(\"sat\");");

    	        			appendLine("CharStream in = new ANTLRFileStream(inDir+System.getProperty(\"file.separator\")+fileName);");
    	        		    appendLine("lexer = new "+prefix+"Lexer(in);");
    	        		    appendLine("tokens.setTokenSource(lexer);");

    	        		    appendLine("ArrayList<"+prefix+MettelJavaNames.firstCharToUpperCase(sort)+
    	        		    		"> list = new ArrayList<"+prefix+MettelJavaNames.firstCharToUpperCase(sort)+">();");
    	        		    appendLine("parser."+sort+"s(list);");

//    	        		    appendLine("TreeSet<MettelAnnotatedExpression> annotated = new TreeSet<MettelAnnotatedExpression>();");

    	        		    appendLine("MettelTableauObjectFactory tfactory = new "+prefix+"TableauObjectFactory();");
    	        		    if(branchBound == null){

    	        		    	appendLine("MettelSimpleTableauManager m = new MettelSimpleTableauManager(tfactory, calculus);");
//    	        		    appendLine("MettelSimpleTableauAnnotation a = new MettelSimpleTableauAnnotation(state);");
//    	        		    appendLine("a.annotate(annotated,list);");
//    	        		    appendLine("state.addAll(annotated);");
//
    	        		    }else{
    	        		    	appendLine("int branchBound = 0;");
    	        		    	appendLine("for("+prefix+MettelJavaNames.firstCharToUpperCase(sort)+" e:list){");
    	        		    	incrementIndentLevel();
    	        		    		appendLine("branchBound += "+branchBound.replaceAll("%l", "e.length()")+';');
    	        		    	decrementIndentLevel();
    	        		    	appendLine('}');
    	        		    	appendLine("MettelSmallTableauStateAcceptor acceptor = new MettelSmallTableauStateAcceptor(branchBound);");
    	        		    	appendLine("MettelSimpleTableauManager m = new MettelSimpleTableauManager(tfactory, calculus,acceptor);");
    	        		    }

       	        		    appendLine("final long start = System.currentTimeMillis();");
       	        		    appendLine("final boolean result = m.isSatisfiable(list);");
    	        		    appendLine("final long finish = System.currentTimeMillis();");
appendLine("System.out.print(\"\"+(finish-start)+\" msec...\");");
							appendLine("if(result){");
							incrementIndentLevel();
								appendLine("System.out.println();");
								appendLine("System.out.println(\"...Model: \"+m.model());");
								appendLine("System.out.print(\"...\");");
							decrementIndentLevel();
							appendLine("}else if(m.contradiction() != null){");
							incrementIndentLevel();
								appendLine("System.out.println();");
								appendLine("System.out.println(\"...Contradiction: \"+m.contradiction());");
								appendLine("System.out.print(\"...\");");
							decrementIndentLevel();
							appendLine('}');
    	        		    appendLine("assertEquals(expected,result);");
appendLine("System.out.print(\"OK\");");
						decrementIndentLevel();
    	        		appendLine('}');
					decrementIndentLevel();
    	        	appendLine('}');
				decrementIndentLevel();
    	        appendLine('}');
			decrementIndentLevel();
    	    appendLine('}');
appendLine("System.out.println();");
appendLine(  "System.out.println(\"----------------------------------------------------\");");
		decrementIndentLevel();
    	appendLine('}');
	}
}
