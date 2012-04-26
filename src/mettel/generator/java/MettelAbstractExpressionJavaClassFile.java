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
package mettel.generator.java;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelAbstractExpressionJavaClassFile extends MettelJavaClassFile {

	private String prefix = "Mettel";
	/**
	 * @param fileName
	 * @param pack
	 * @param superClass
	 * @param interfaces
	 */
	public MettelAbstractExpressionJavaClassFile(String prefix,	MettelJavaPackage pack) {
		super(prefix + "AbstractExpression", pack, "abstract", "MettelAbstractExpression", new String[]{prefix + "Expression"});
		this.prefix = prefix;
		body();
	}

	protected void imports(){
	    headings.appendLine("import mettel.core.MettelExpression;");
	    headings.appendLine("import mettel.core.MettelSubstitution;");
		headings.appendLine("import mettel.core.MettelAbstractExpression;");
	}

	private void body(){
		 appendLine("private static volatile int expressionCounter = 0;");
		 appendLine("private int id = -1;");
		 appendLine("protected "+prefix+"ObjectFactory factory = null;");

		 appendEOL();

		 appendLine(prefix + "AbstractExpression(" + prefix + "ObjectFactory factory){");
		 incrementIndentLevel();
		 	appendLine("super();");
		 	appendLine("this.factory = factory;");
		    appendLine("id = expressionCounter++;");
		 decrementIndentLevel();
		 appendLine('}');

		 appendEOL();

		 appendLine(prefix + "AbstractExpression(){");
		 incrementIndentLevel();
		 	appendLine("this(" + prefix +"ObjectFactory.DEFAULT);");
		 decrementIndentLevel();
		 appendLine('}');

		 appendEOL();

		 appendLine("public int id(){ return id; }");

		 appendEOL();

		 //appendLine(prefix + "ObjectFactory factory = null;");
		 appendLine("public " + prefix + "ObjectFactory factory(){ return factory; }");

		 appendEOL();

		 appendLine("abstract int sortId();");

		 appendEOL();

		 appendLine("abstract int priority();");

/*		 appendEOL();

		 appendLine("public int hashCode() {return id;}");

		 appendEOL();

		 appendLine("public int compareTo(" + prefix + "Expression e) {return (id - e.id());}");
*/
		 appendEOL();

		 appendLine("public MettelSubstitution match(MettelExpression e){");
		 incrementIndentLevel();
		 appendLine("if(e instanceof "+prefix+"Expression) return match(("+prefix+"Expression)e);");
		 appendLine("return null;");
		 decrementIndentLevel();
		 appendLine('}');

		 appendEOL();

		 appendLine("public MettelExpression substitute(MettelSubstitution s){");
		 incrementIndentLevel();
		 appendLine("if(s instanceof "+prefix+"Substitution) return substitute(("+prefix+"Substitution)s);");
		 appendLine("return null;");
		 decrementIndentLevel();
		 appendLine('}');
		 
		 appendEOL();
		 
		 //appendLine("public int compareTo(MettelExpression e){ return id() - (("+prefix+"AbstractExpression)e).id(); }");
		 
		 //appendLine("int preCompareTo(MettelExpression e){return 0;}");

/*		 appendEOL();

		 appendLine("public MettelExpression rewrite(MettelReplacement r){");
		 incrementIndentLevel();
		 appendLine("if(s instanceof "+prefix+"Replacement) return rewrite(("+prefix+"Replacement)s);");
		 appendLine("return null;");
		 decrementIndentLevel();
		 appendLine('}');
*/
	}

}
