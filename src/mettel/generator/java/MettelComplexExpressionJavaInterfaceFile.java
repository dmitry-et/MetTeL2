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
public class MettelComplexExpressionJavaInterfaceFile extends MettelJavaInterfaceFile {

//	private String prefix = "Mettel";
//	private String sort = null;

	private static int counter = 0;
	private int sortid = -1;
	/**
	 * @param prefix
	 * @param sort
	 * @param pack
	 */
	public MettelComplexExpressionJavaInterfaceFile(String prefix, String sort, MettelJavaPackage pack){
		super(prefix+MettelJavaNames.firstCharToUpperCase(sort), pack, new String[]{prefix+"Expression"});
		sortid = ++counter;
//		this.prefix = prefix;
//		this.sort = sort;
		body();
	}

	private void body(){
		appendLine("final int SORTID = "+sortid+';');

//		appendEOL();

//		appendLine("boolean match("+prefix+MettelJavaNames.firstCharToUpperCase(sort)+" e, "+prefix+"Substitution s);");
	}

}
