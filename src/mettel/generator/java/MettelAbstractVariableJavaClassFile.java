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
public class MettelAbstractVariableJavaClassFile extends MettelJavaClassFile {


	private String prefix = "Mettel";

	/**
	 * @param fileName
	 * @param pack
	 * @param superClass
	 * @param interfaces
	 */
	public MettelAbstractVariableJavaClassFile(String prefix, MettelJavaPackage pack) {
		super(prefix + "AbstractVariable", pack, "abstract", prefix + "AbstractExpression", new String[]{prefix + "Variable"});
		this.prefix = prefix;
		body();
	}

	private void body(){
		appendLine("private String name = null;");

		appendEOL();

		appendLine("public String name(){");
		incrementIndentLevel();
		appendLine("return name;");
		decrementIndentLevel();
		appendLine('}');

		appendEOL();

		appendLine("public " + prefix + "AbstractVariable(String name, " + prefix +"ObjectFactory factory){");
		incrementIndentLevel();
		appendLine("super(factory);");
		appendLine("this.name = name;");
		decrementIndentLevel();
		appendLine('}');

		appendEOL();

		appendLine("public String toString(){");
		incrementIndentLevel();
		appendLine("return name;");
		decrementIndentLevel();
		appendLine('}');
	}

}
