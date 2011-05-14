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
public class MettelObjectFactoryJavaClassFile extends MettelJavaClassFile {

	private String prefix = "Mettel";
	/**
	 * @param name
	 * @param pack
	 */
	public MettelObjectFactoryJavaClassFile(String prefix, MettelJavaPackage pack) {
		//if(name == null) throw MettelGeneratorRuntimeException("Name is null");
		super(prefix + "DefaultObjectFactory", pack, "public", null,
			  new String[]{prefix + "ObjectFactory"});
		this.prefix = prefix;
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

	public void addCreateMethod(String type, String name, String[] types){
		final String ltype = name + MettelJavaNames.firstCharToUpperCase(type);
		final String TYPE = prefix + MettelJavaNames.firstCharToUpperCase(name) + MettelJavaNames.firstCharToUpperCase(type);

		appendLine("private Map<"+TYPE+", "+TYPE+"> "+ ltype + "s = new TreeMap<"+TYPE+", "+TYPE+">();");
		appendEOL();

		indent();
		final int SIZE = types.length;
		if(SIZE > 0){
			append("public "+TYPE+" create"+MettelJavaNames.firstCharToUpperCase(ltype) + '(');
			append(prefix+MettelJavaNames.firstCharToUpperCase(types[0])+" e"+0);
			for(int i = 1; i < SIZE; i++){
				append(", "+prefix+MettelJavaNames.firstCharToUpperCase(types[i])+"e"+i);
			}
		}else{
			append("public "+TYPE+' '+ltype+"Constant(e");
		}
		append("){");
		appendEOL();
			incrementIndentLevel();
				indent();
				append("final "+TYPE+" e = new "+TYPE+'(');
				if(SIZE > 0){
					append("e0");
					for(int i = 1; i < SIZE; i++){
						append(", e"+i);
					}
				}
				append(", this);");
				appendEOL();

				appendLine("final "+TYPE+" old = "+ltype+"s.get(e);");
				appendLine("if(old == null){ ");
				incrementIndentLevel();
					appendLine(type+"s.put(e,e);");
					appendLine("return e;");
				decrementIndentLevel();
				appendLine("}else{ return old; }");
			decrementIndentLevel();
		appendLine('}');
		appendEOL();
	}

	public void addVariableMethod(String type){
		final String TYPE = type + "Variable";
		final String PTYPE = prefix + MettelJavaNames.firstCharToUpperCase(TYPE);

		appendLine("private Map<String, "+PTYPE+"> "+type+"Variables = new TreeMap<String, "+PTYPE+">();");
		appendEOL();

		appendLine("public "+PTYPE + " create" + MettelJavaNames.firstCharToUpperCase(TYPE) + "(String name){");

		incrementIndentLevel();
			appendLine(PTYPE + " v = " + TYPE + "Variables(name);");
			appendLine("if(v == null){");
			incrementIndentLevel();
				appendLine("v = new " + PTYPE + "(name, this);");
				appendLine(TYPE + "Variables.put(name, v);");
			decrementIndentLevel();
			appendLine('}');
			appendLine("return v;");
		decrementIndentLevel();
		appendLine('}');
		appendEOL();
	}

	private void body(){
		appendLine("private Map<"+prefix+"Substitution, "+prefix+"Substitution> subs = new TreeMap<"+prefix+"Substitution, "+prefix+"Substitution>();");
		appendEOL();

		appendLine("public "+prefix+"Substitution getSubstitution("+prefix+"Substitution s){");
		incrementIndentLevel();
			appendLine("final "+prefix+"Substitution old = subs.get(s);");
			appendLine("if(old == null){ ");
			incrementIndentLevel();
				appendLine("subs.put(s,s);");
				appendLine("return s;");
			decrementIndentLevel();
			appendLine("}else{ return old; }");
		decrementIndentLevel();
		appendLine('}');

		appendEOL();

		appendLine("private Map<"+prefix+"Replacement, "+prefix+"Replacement> reps = new TreeMap<"+prefix+"Replacement, "+prefix+"Replacement>();");
		appendEOL();

		appendLine("public "+prefix+"Replacement getReplacement("+prefix+"Replacement r){");
		incrementIndentLevel();
			appendLine("final "+prefix+"Replacement old = reps.get(r);");
			appendLine("if(old == null){ ");
			incrementIndentLevel();
				appendLine("reps.put(r,r);");
				appendLine("return r;");
			decrementIndentLevel();
			appendLine("}else{ return old; }");
		decrementIndentLevel();
		appendLine('}');

		appendEOL();
	}

}
