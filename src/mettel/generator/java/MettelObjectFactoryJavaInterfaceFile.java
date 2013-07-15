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

import static mettel.generator.MettelANTLRGrammarGeneratorDefaultOptions.NAME_SEPARATOR;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelObjectFactoryJavaInterfaceFile extends MettelJavaInterfaceFile {

	private String prefix = "Mettel";

	private String nameSeparator = NAME_SEPARATOR;
	/**
	 * @param name
	 * @param pack
	 */
	public MettelObjectFactoryJavaInterfaceFile(String prefix, MettelJavaPackage pack, String nameSeparator) {
		super(prefix+"ObjectFactory", pack, null);
		this.prefix = prefix;
		this.nameSeparator = nameSeparator;
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
		appendEOL();
	}

	public String methodSignature(String type, String name, String[] types){
		final String ltype = name + MettelJavaNames.firstCharToUpperCase(type, nameSeparator);
		final String TYPE = prefix + MettelJavaNames.firstCharToUpperCase(ltype, nameSeparator);
		final int SIZE = types.length;

		StringBuilder b  = new StringBuilder();
		if(SIZE > 0){
			append(prefix+MettelJavaNames.firstCharToUpperCase(type, nameSeparator) + " create" + MettelJavaNames.firstCharToUpperCase(ltype, nameSeparator) + '(');
			append(prefix + MettelJavaNames.firstCharToUpperCase(types[0], nameSeparator) +" e0");
			for(int i = 1; i < SIZE; i++){
				append(", " + prefix + MettelJavaNames.firstCharToUpperCase(types[i], nameSeparator) + " e" + i);
			}
			append(");");
		}else if(SIZE == 0){
			append(TYPE + " create" + MettelJavaNames.firstCharToUpperCase(ltype, nameSeparator) +"();");
		}

		return b.toString();
	}

	public void addCreateMethod(String type, String name, String[] types){
		indent();
		appendLine(methodSignature(type,name,types));
		appendEOL();
	}

	public void addVariableMethod(String type){
		final String TYPE = type + "Variable";
		appendLine(prefix + MettelJavaNames.firstCharToUpperCase(TYPE, nameSeparator) + " create" + MettelJavaNames.firstCharToUpperCase(TYPE, nameSeparator) + "(String name);");
		appendEOL();
	}

	private void body(){
		appendLine(prefix+"Replacement getReplacement("+prefix+"Replacement r);");
		appendEOL();
		appendLine(prefix+"Substitution getSubstitution("+prefix+"Substitution s);");
		appendEOL();
	}
}
