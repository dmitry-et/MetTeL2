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
public class MettelObjectFactoryJavaInterfaceFile extends MettelJavaInterfaceFile {

	private String prefix = "Mettel";
	/**
	 * @param name
	 * @param pack
	 */
	public MettelObjectFactoryJavaInterfaceFile(String prefix, MettelJavaPackage pack) {
		super(prefix+"ObjectFactory", pack, null);
		this.prefix = prefix;
		fields();
		body();
	}


/*	public void addCreateMethod(String connective, String sort, String[] children, String[] cSorts){
		final String TYPE = MettelJavaNames.firstCharToUpperCase(connective) +
							MettelJavaNames.firstCharToUpperCase(sort);
		final int SIZE = children.length;
		String[] types = new String[SIZE];
		for(int i = 0; i < SIZE; i++){
			types[i] = MettelJavaNames.firstCharToUpperCase(children[i]) +
					   MettelJavaNames.firstCharToUpperCase(cSorts[i]);
		}
		addCreateMethod(TYPE,types);
	}
*/

	private void fields(){
		appendLine("final " + prefix + "ObjectFactory DEFAULT = new " + prefix +"DefaultObjectFactory();");
	}

	public void addCreateMethod(String type, String name, String[] types){
		final String ltype = name + MettelJavaNames.firstCharToUpperCase(type);
		final String TYPE = prefix + MettelJavaNames.firstCharToUpperCase(ltype);
		final int SIZE = types.length;

		indent();
		if(SIZE > 0){
			append(TYPE + " create" + MettelJavaNames.firstCharToUpperCase(ltype) + '(');
			append(prefix + MettelJavaNames.firstCharToUpperCase(types[0]) +" e0");
			for(int i = 1; i < SIZE; i++){
				append(", " + prefix + MettelJavaNames.firstCharToUpperCase(types[i]) + 'e' + i);
			}
			append(");");
		}else if(SIZE == 0){
			append(TYPE + ' ' + ltype + "Constant();");
		}
		appendEOL();
	}

	public void addVariableMethod(String type){
		final String TYPE = type + "Variable";
		appendLine(prefix + TYPE + " create" + TYPE + "(String name);");
	}

	private void body(){
		appendLine(prefix+"Replacement getReplacement("+prefix+"Replacement r);");
		appendLine(prefix+"Substitution getSubstitution("+prefix+"Substitution s);");
	}
}
