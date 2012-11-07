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
public class MettelTableauObjectFactoryJavaClassFile extends MettelJavaClassFile {

	private String prefix = "Mettel";
	/**
	 * @param name
	 * @param pack
	 */
	public MettelTableauObjectFactoryJavaClassFile(String prefix, MettelJavaPackage pack) {
		//if(name == null) throw MettelGeneratorRuntimeException("Name is null");
		super(prefix + "TableauObjectFactory", pack, "public", null,
			  new String[]{"MettelTableauObjectFactory"});
		this.prefix = prefix;
		body();
	}

	protected void imports(){
		headings.appendLine("import mettel.core.tableau.MettelTableauObjectFactory;");
		headings.appendLine("import mettel.core.tableau.MettelReplacement;");
		headings.appendEOL();
	}

	private void body(){
		appendLine("public MettelReplacement createReplacement(){");
		incrementIndentLevel();
			appendLine("return new "+prefix+"TreeReplacement();");
		decrementIndentLevel();
		appendLine('}');

		appendEOL();
	}

}
