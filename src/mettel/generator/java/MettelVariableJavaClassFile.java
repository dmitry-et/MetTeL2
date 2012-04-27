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
public class MettelVariableJavaClassFile extends MettelJavaClassFile {

	private String prefix = "Mettel";
	private String type = null;

	public MettelVariableJavaClassFile(String prefix, String type, MettelJavaPackage pack) {
		super(prefix + MettelJavaNames.firstCharToUpperCase(type) + "Variable", pack, "public",
				prefix + "AbstractVariable", new String[]{prefix + MettelJavaNames.firstCharToUpperCase(type)});
		this.prefix = prefix;
		this.type = type;
		body();
	}

	protected void imports(){
		headings.appendLine("import java.util.Comparator;");
		headings.appendEOL();
		
		headings.appendLine("import mettel.core.MettelExpression;");
		headings.appendEOL();
	}

	private void body(){
		final String TYPE = prefix + MettelJavaNames.firstCharToUpperCase(type);

		appendLine("public " + TYPE + "Variable(String name, " + prefix + "ObjectFactory factory) {");
		incrementIndentLevel();
	        appendLine("super(name, factory);");
	    decrementIndentLevel();
	    appendLine('}');

	    appendEOL();

	    appendLine("int sortId(){ return SORTID; }");

		appendEOL();

		appendLine("public "+prefix+"Substitution match("+prefix+"Expression e){");
		incrementIndentLevel();
			appendLine(prefix+"Substitution s = new "+prefix+"TreeSubstitution();");
			appendLine("if(match(e,s)){");
				incrementIndentLevel();
//appendLine("System.out.println(\"match\");");
					appendLine("return factory.getSubstitution(s);");
				decrementIndentLevel();
			appendLine("}else{ return null; }");
		decrementIndentLevel();
		appendLine('}');

		appendEOL();

		appendLine("public boolean match("+prefix+"Expression e, "+prefix+"Substitution s){");
		incrementIndentLevel();
		    appendLine("if(!(e instanceof "+prefix+MettelJavaNames.firstCharToUpperCase(type)+")){ return false; }");
		    appendLine("return s.append(this,("+prefix+MettelJavaNames.firstCharToUpperCase(type)+")e);");
		decrementIndentLevel();
		appendLine('}');

		appendEOL();

	    appendLine("public " + prefix + "Expression rewrite(" + prefix + "Replacement r) {");
	    incrementIndentLevel();
	    	appendLine(TYPE + " e = r.get" + MettelJavaNames.firstCharToUpperCase(type) +"(this);");
	    	appendLine("return (e == null) ? this : e;");
	   	decrementIndentLevel();
	   	appendLine('}');

	   	appendEOL();

	   	appendLine("public " + prefix + "Expression substitute(" + prefix + "Substitution s) {");
	   	incrementIndentLevel();
	   		appendLine(TYPE + " e = s.get" + MettelJavaNames.firstCharToUpperCase(type) +"(this);");
	   		appendLine("return (e == null) ? this : e;");
    	decrementIndentLevel();
	   	appendLine('}');

	   	appendEOL();


/*	   	appendLine("public " + prefix + "Substitution match(" + prefix + "Expression e) {");
	    incrementIndentLevel();
	    	appendLine("if(e instanceof " + TYPE +") {");
	    	incrementIndentLevel();
	    		appendLine(prefix + "Substitution s = new " + prefix + "TreeSubstitution();");
	    		appendLine("s.add(this, (" + TYPE + ")e);");
	    		appendLine("return s;");
	    	decrementIndentLevel();
	    	appendLine('}');
	    	appendLine("return null;");
	   	decrementIndentLevel();
	   	appendLine('}');

	   	appendEOL();
*/
		appendLine("private int hashCode = 0;");
		appendLine("public int hashCode(){");
		incrementIndentLevel();
		appendLine("if(hashCode == 0){");
		incrementIndentLevel();
			appendLine("hashCode = 23*SORTID + name.hashCode();");
		decrementIndentLevel();
		appendLine('}');
		appendLine("return hashCode;");
		decrementIndentLevel();
		appendLine('}');

		appendEOL();

		appendLine("public int compareTo(MettelExpression e){");
		incrementIndentLevel();
			appendLine("if(e instanceof " +TYPE +"Variable) return name.compareTo((("+TYPE+"Variable)e).name());");
			appendLine("if(e instanceof " +TYPE +") return -1;");
			appendLine("return SORTID - (("+prefix+"AbstractExpression)e).sortId();");
		decrementIndentLevel();
		appendLine('}');

		appendEOL();
		
		appendLine("int compareArgumentsTo("+prefix+"AbstractExpression e, Comparator<"+prefix+"AbstractExpression> c){return 0;}");
		appendLine("int compareArguments("+prefix+"AbstractExpression e, Comparator<"+prefix+"AbstractExpression> c){return 0;}");
		
		appendEOL();

		appendLine("public boolean equals(Object o){");
		incrementIndentLevel();
			appendLine("if(o == this){ return true; }");
			appendLine("if(!(o instanceof "+TYPE+"Variable)){ return false; }");
			appendLine("return name.equals((("+TYPE+"Variable)o).name());");
		decrementIndentLevel();
		appendLine('}');
		
		appendEOL();
		
		appendLine("public boolean isEquality(){");
		incrementIndentLevel();
			appendLine("return false;");
		decrementIndentLevel();
		appendLine('}');

		appendLine("public int length(){");
		incrementIndentLevel();
			appendLine("return 1;");
		decrementIndentLevel();
		appendLine('}');
	}

}
