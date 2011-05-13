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
public class MettelVariableJavaClassFile extends MettelJavaClassFile {

	private String prefix = "Mettel";
	private String type = null;

	public MettelVariableJavaClassFile(String prefix, String type, MettelJavaPackage pack) {
		super(prefix + type + "Variable", pack, "public", prefix + "AbstractVariable", new String[]{prefix + type});
		this.prefix = prefix;
		this.type = type;
		body();
	}

	private void body(){
		final String TYPE = prefix + type;

		appendLine("public " + TYPE + "Variable(String name, " + prefix + "ObjectFactory factory) {");
		incrementIndentLevel();
	        appendLine("super(name, factory);");
	    decrementIndentLevel();
	    appendLine('}');

	    appendEOL();

	    appendLine("public " + TYPE + "replace(" + prefix + "Replacement r) {");
	    incrementIndentLevel();
	    	appendLine(TYPE + "e = r.get" + type +"(this);");
	    	appendLine("return (e == null) ? this : e;");
	   	decrementIndentLevel();
	   	appendLine('}');

	   	appendEOL();

	   	appendLine("public " + TYPE + "substitute(" + prefix + "Substitution s) {");
	    incrementIndentLevel();
	    	appendLine("return replace(s);");
	   	decrementIndentLevel();
	   	appendLine('}');

	   	appendEOL();


	   	appendLine("public " + prefix + "Substitution match(" + prefix + "Expression e) {");
	    incrementIndentLevel();
	    	appendLine("if(e instanceof " + TYPE +") {");
	    	incrementIndentLevel();
	    		appendLine(prefix + "Substitution s = new " + prefix + "HashSubstitution();");
	    		appendLine("s.add(this, (" + TYPE + ") e;");
	    		appendLine("return s;");
	    	decrementIndentLevel();
	    	appendLine('}');
	    	appendLine("return null;");
	   	decrementIndentLevel();
	   	appendLine('}');

	   	appendEOL();

		appendLine("private int hashCode = 0;");
		appendLine("public int hashCode(){");
		incrementIndentLevel();
		appendLine("if(hashCode == 0){");
		incrementIndentLevel();
			appendLine("hashCode = 31*PRIORITY + name.hashCode();");
		decrementIndentLevel();
		appendLine('}');
		appendLine("return hashCode;");
		decrementIndentLevel();
		appendLine('}');

		appendEOL();

		appendLine("public int compareTo(" + prefix + "Expression e){");
		incrementIndentLevel();
			appendLine("if(e instanceof " + prefix + type +"Variable) return name.compareTo(("+prefix+type+"Variable)e.name());");
			appendLine("return PRIORITY - ("+prefix+"AbstractExpression)e.priority());");
		decrementIndentLevel();
		appendLine('}');

		appendEOL();

		appendLine("public boolean equals(Object o){");
		incrementIndentLevel();
			appendLine("if(o == this){ return true; }");
			appendLine("if(!(o instanceof "+prefix+type+"Variable)){ return false; }");
			appendLine("return name.equals(("+prefix+type+"Variable)o.name());");
		decrementIndentLevel();
		appendLine('}');
	}

}
