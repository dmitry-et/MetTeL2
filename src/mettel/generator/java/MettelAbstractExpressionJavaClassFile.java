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
		super(prefix + "AbstractExpression", pack, "abstract", null, new String[]{prefix + "Expression"});
		this.prefix = prefix;
		body();
	}

	private void body(){
		 appendLine("private static volatile int expressionCounter = 0;");
		 appendLine("private int id = -1;");

		 appendEOL();

		 appendLine(prefix + "AbstractExpression(" + prefix + "ObjectFactory factory) {");
		 incrementIndentLevel();
		 	appendLine("super();");
		 	appendLine("this.factory = factory;");
		    appendLine("id = expressionCounter++;");
		 decrementIndentLevel();
		 appendLine('}');

		 appendEOL();

		 appendLine(prefix + "AbstractExpression() {");
		 incrementIndentLevel();
		 	appendLine("this(" + prefix +"MettelObjectFactory.DEFAULT);");
		 decrementIndentLevel();
		 appendLine('}');

		 appendEOL();

		 appendLine("public int id(){return id;}");

		 appendEOL();

		 appendLine(prefix + "ObjectFactory factory = null;");
		 appendLine("public " + prefix + "ObjectFactory factory() {return factory;}");

		 appendEOL();

		 appendLine("public int hashCode() {return id;}");

		 appendEOL();

		 appendLine("public int compareTo(" + prefix + "Expression e) {return (id - e.id());}");
	}

}
