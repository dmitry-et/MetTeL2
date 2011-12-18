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
public class MettelExpressionInterfaceFile extends MettelJavaInterfaceFile {

	private String prefix = "Mettel";
	/**
	 * @param prefix
	 * @param pack
	 */
	public MettelExpressionInterfaceFile(String prefix, MettelJavaPackage pack) {
		super(prefix+"Expression", pack, new String[]{"MettelExpression"});
		this.prefix = prefix;
		body();
	}

	protected void imports(){
	    headings.appendLine("import mettel.core.MettelExpression;");
	}

	void body(){
		indent();append("int id();");appendEOL();
		indent();append(prefix+"ObjectFactory factory();");appendEOL();
		indent();append(prefix+"Expression substitute("+prefix+"Substitution s);");appendEOL();
		indent();append(prefix+"Expression rewrite("+prefix+"Replacement r);");appendEOL();
		indent();append(prefix+"Substitution match("+prefix+"Expression e);");appendEOL();

		appendEOL();

		appendLine("boolean match("+prefix+"Expression e, "+prefix+"Substitution s);");
	}
}
