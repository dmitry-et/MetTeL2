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

import static mettel.util.MettelStrings.LINE_SEPARATOR;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelObjectFactoryClass extends MettelJavaFile {

	private String name = null;
	/**
	 * @param name
	 * @param pack
	 */
	public MettelObjectFactoryClass(String name, MettelJavaPackage pack) {
		//if(name == null) throw MettelGeneratorRuntimeException("Name is null");
		super(MettelJavaNames.firstCharToUpperCase(name) + "DefaultObjectFactory.java", pack);
		this.name = name;
	}

	public void addCreateMethod(String connective, String sort, String[] children){
		append(LINE_SEPARATOR);

		append("public");
		append(' ');
		append(MettelJavaNames.firstCharToUpperCase(name));
		append(MettelJavaNames.firstCharToUpperCase(connective));
		append(MettelJavaNames.firstCharToUpperCase(sort));
		append(' ');
		append("create");
		append(MettelJavaNames.firstCharToUpperCase(connective));
		append(MettelJavaNames.firstCharToUpperCase(sort));
		append('(');
		final int SIZE = children.length;
		if(SIZE > 0){
			append(MettelJavaNames.firstCharToUpperCase(name));
			append(MettelJavaNames.firstCharToUpperCase(children[0]));
			append(' ');
			append("param" +0);
			for(int i = 1; i < SIZE; i++){
				append(',');
				append(' ');
				append(MettelJavaNames.firstCharToUpperCase(name));
				append(MettelJavaNames.firstCharToUpperCase(children[i]));
				append("param"+i);
			}
		}
		append(')');
		append('{');
		append(LINE_SEPARATOR);

		append(LINE_SEPARATOR);
		append('}');
	}

}
