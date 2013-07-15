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
public class MettelObjectFactoryJavaClassFile extends MettelJavaClassFile {

	private String prefix = "Mettel";

	private String nameSeparator = NAME_SEPARATOR;
	/**
	 * @param name
	 * @param pack
	 */
	public MettelObjectFactoryJavaClassFile(String prefix, MettelJavaPackage pack, String nameSeparator) {
		//if(name == null) throw MettelGeneratorRuntimeException("Name is null");
		super(prefix + "DefaultObjectFactory", pack, "public", null,
			  new String[]{prefix + "ObjectFactory"});
		this.prefix = prefix;
		this.nameSeparator = nameSeparator;
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

	protected void imports(){
		headings.appendLine("import java.util.Map;");
		headings.appendLine("import java.util.TreeMap;");
		headings.appendEOL();
	}


//	private LinkedHashMap<String, StringBuffer> createMethodMap = new LinkedHashMap<String, StringBuffer>();

	public void addCreateMethod(String type, String name, String[] types){
		final String ltype = name + MettelJavaNames.firstCharToUpperCase(type, nameSeparator);
		final String TYPE = prefix + MettelJavaNames.firstCharToUpperCase(name, nameSeparator) + MettelJavaNames.firstCharToUpperCase(type, nameSeparator);
		final String TYPE0 = prefix + MettelJavaNames.firstCharToUpperCase(type, nameSeparator);

		final int SIZE = types.length;
		if(SIZE > 0){
//			appendLine("private Map<"+TYPE+", "+TYPE+"> "+ ltype + "s = new TreeMap<"+TYPE+", "+TYPE+">();");
//			appendEOL();

			indent();

			append("public "+TYPE0+" create"+MettelJavaNames.firstCharToUpperCase(ltype, nameSeparator) + '(');
			append(prefix+MettelJavaNames.firstCharToUpperCase(types[0], nameSeparator)+" e"+0);
			for(int i = 1; i < SIZE; i++){
				append(", "+prefix+MettelJavaNames.firstCharToUpperCase(types[i], nameSeparator)+" e"+i);
			}
			append("){");
			appendEOL();

			incrementIndentLevel();
				indent();
				append("final "+TYPE+" e = new "+TYPE+'(');
					append("e0");
					for(int i = 1; i < SIZE; i++){
						append(", e"+i);
					}
					append(", ");
				append("this);");
				appendEOL();

				appendLine("final "+TYPE0+" old = "+type+"s.get(e);");
				appendLine("if(old == null){ ");
				incrementIndentLevel();
					appendLine(type+"s.put(e,e);");
					appendLine("return e;");
				decrementIndentLevel();
				appendLine("}else{ return old; }");
			decrementIndentLevel();
			appendLine('}');
			appendEOL();

		}else{
			appendLine("private "+TYPE+' '+ nameSeparator + ltype+"Constant = new "+TYPE+"(this);");
			appendEOL();

			appendLine("public "+TYPE+" create"+MettelJavaNames.firstCharToUpperCase(ltype, nameSeparator) + "(){");
			incrementIndentLevel();
				appendLine("return "+ nameSeparator + ltype+"Constant;");
			decrementIndentLevel();
			appendLine('}');
		}
	}

	public void addVariableMethod(String type){
		final String TYPE = type + "Variable";
		final String PTYPE = prefix + MettelJavaNames.firstCharToUpperCase(TYPE, nameSeparator);

		appendLine("private Map<String, "+PTYPE+"> "+TYPE+"s = new TreeMap<String, "+PTYPE+">();");
		appendEOL();

		appendLine("public "+PTYPE + " create" + MettelJavaNames.firstCharToUpperCase(TYPE, nameSeparator) + "(String name){");

		incrementIndentLevel();
			appendLine(PTYPE + " v = " + TYPE + "s.get(name);");
			appendLine("if(v == null){");
			incrementIndentLevel();
				appendLine("v = new " + PTYPE + "(name, this);");
				appendLine(TYPE + "s.put(name, v);");
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
//appendLine("System.out.println(\"Old = \"+old+\", s = \"+s);");
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


	/**
	 * @param sort
	 */
	public void addMap(String sort) {
		final String TYPE = prefix + MettelJavaNames.firstCharToUpperCase(sort, nameSeparator);
		appendLine("private Map<"+TYPE+", "+TYPE+"> "+ sort + "s = new TreeMap<"+TYPE+", "+TYPE+">();");
		appendEOL();
	}

}
