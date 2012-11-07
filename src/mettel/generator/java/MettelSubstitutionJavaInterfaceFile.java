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

import mettel.util.MettelJavaNames;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelSubstitutionJavaInterfaceFile extends MettelJavaInterfaceFile {

	private String prefix = "Mettel";

	public MettelSubstitutionJavaInterfaceFile(String prefix,
			MettelJavaPackage pack, String[] sorts) {
		super(prefix+"Substitution", pack,
				new String[]{"MettelSubstitution"});//, prefix+"Replacement"});
		this.prefix = prefix;
		body(sorts);
	}

	protected void imports(){
		headings.appendLine("import java.util.Map;");
		headings.appendLine("import mettel.core.tableau.MettelSubstitution;");
	}

	private void body(String[] sorts){
		for(String sort:sorts){
			final String TYPE = prefix+MettelJavaNames.firstCharToUpperCase(sort);
			appendLine(TYPE+" get"+MettelJavaNames.firstCharToUpperCase(sort)+'('+TYPE+"Variable e);");appendEOL();
//			appendLine("Iterator<"+TYPE+"> "+sort+"Iterator();");appendEOL();
			appendLine("Map<"+TYPE+"Variable, "+TYPE+"> "+sort+"Map();");appendEOL();
			appendLine("boolean append("+TYPE+"Variable e0, "+TYPE+" e1);");appendEOL();
		}
	}
}
