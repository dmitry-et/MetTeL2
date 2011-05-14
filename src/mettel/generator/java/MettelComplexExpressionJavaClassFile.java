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

import java.util.List;

import mettel.language.MettelStringLiteral;
import mettel.language.MettelToken;
import mettel.util.MettelJavaNames;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelComplexExpressionJavaClassFile extends MettelJavaClassFile {

	private static int counter = 0;
	private int priority = -1;

	private String prefix = "Mettel";
	private String sort = null;
	private String name = null;
	/**
	 * @param prefix
	 * @param sort
	 * @param pack
	 */
	public MettelComplexExpressionJavaClassFile(String prefix, String sort, String name, String[] sorts, MettelJavaPackage pack){
		super(prefix+MettelJavaNames.firstCharToUpperCase(name)+MettelJavaNames.firstCharToUpperCase(sort), pack, "public", prefix+"AbstractExpression", new String[]{prefix+sort});
		priority = ++counter;

		this.prefix = prefix;
		this.sort = sort;
		this.name = name;
		body(sorts);
	}

	private void body(String[] sorts){
		final String TYPE = prefix+MettelJavaNames.firstCharToUpperCase(name)+MettelJavaNames.firstCharToUpperCase(sort);
		final int SIZE = sorts.length;

		appendLine("static final int PRIORITY = "+priority+';');

		for(int i = 0; i < SIZE; i++){
			appendLine("protected "+prefix+sorts[i]+" e"+i+" = null;");
		}
		appendEOL();

		indent();
		append("public "+TYPE+'(');
		if(SIZE>0){
			append(prefix+sorts[0]+" e"+0);
			for(int i = 1; i<SIZE; i++){
				append(", "+prefix+sorts[0]+" e"+i);
			}
		}
		append(", "+prefix+"ObjectFactory f){");
		appendEOL();
			incrementIndentLevel();
				appendLine("super(f);");
				for(int i = 0; i < SIZE;i++){
					appendLine("this.e"+i+" = e"+i+';');
				}
			decrementIndentLevel();
		appendLine('}');
		appendEOL();

		appendLine("static int sortId(){ return SORTID; }");

		appendEOL();

		appendLine("public "+prefix+"Substitution match("+prefix+"Expression e){");
			incrementIndentLevel();
				appendLine("if(!(e instanceof "+TYPE+")){ return null;}");
				if(SIZE > 0){
					appendLine(prefix+"Substitution s = new "+prefix+"Substitution(factory);");
					appendLine("if(match(("+TYPE+")e,s)){");
						incrementIndentLevel();
							appendLine("return factory.getSubstitution(s);");
						decrementIndentLevel();
					appendLine('}');
				}else{
					appendLine("return factory.getSubstitution(new "+prefix+"Substitution(factory));");
				}
			decrementIndentLevel();
		appendLine('}');

		appendEOL();

		if(SIZE > 0){
			appendLine("public boolean match("+TYPE+"e, "+prefix+"Substitution s){");
			incrementIndentLevel();
				indent();
				append("return e"+0+".match(e.e"+0+", s) ");
				for(int i = 1; i < SIZE; i++){
					append("&& e"+i+".match(e.e"+i+", s) ");
				}
				append(';');
			decrementIndentLevel();
			appendLine('}');

			appendEOL();
		}

		appendLine("public "+prefix+"Expression substitute("+prefix+"Substitution s){");
			incrementIndentLevel();
				if(SIZE > 0){
					indent();
					append("return factory.create"+name+sort+'(');
					append("e0.substitute(s)");
					for(int i = 1; i < SIZE; i++){
						append(", e"+i+".substitute(s)");
					}
					append(");");
				}else{
					appendLine("return this;");
				}
			decrementIndentLevel();
		appendLine('}');

		appendEOL();

		appendLine("public "+prefix+"Expression replace("+prefix+"Replacement s){");
		incrementIndentLevel();
			appendLine(prefix+"Expression e = s.get"+sort+"(this);");
			appendLine("if(e != null){ return e; }");
			if(SIZE > 0){
				indent();
				append("return factory.create"+name+sort+'(');
				append("e0.replace(s)");
				for(int i = 1; i < SIZE; i++){
					append(", e"+i+".replace(s)");
				}
				append(");");
			}else{
				appendLine("return this;");
			}
		decrementIndentLevel();
		appendLine('}');

		appendEOL();

		appendLine("private int hashCode = 0;");
		appendLine("public int hashCode(){");
		incrementIndentLevel();
			appendLine("if(hashCode == 0){");
			incrementIndentLevel();
				appendLine("hashCode = 31*SORTID + PRIORITY;");
				for(int i = 0; i < SIZE; i++){
					appendLine("hashCode = 31*hashCode + e"+i+".hashCode();");
				}
			decrementIndentLevel();
			appendLine('}');
			appendLine("return hashCode;");
		decrementIndentLevel();
		appendLine('}');

		appendEOL();

		appendLine("public boolean equals(Object o){");
		incrementIndentLevel();
			appendLine("if(o == this){ return true; }");
			appendLine("if(!(o instanceof "+TYPE+")){ return false; }");
			appendLine(TYPE+" e = ("+TYPE+") o;");
			for(int i = 0; i < SIZE; i++){
				appendLine("if(!(e"+i+".equals(e.e"+i+"))){ return false; }");
			}
			appendLine("return true;");
		decrementIndentLevel();
		appendLine('}');

		appendEOL();

		appendLine("public int compareTo("+prefix+"Expression e){");
		incrementIndentLevel();
			appendLine("if(e == this){ return 0; }");
			appendLine("if(!(e instanceof "+prefix+sort+"){ return SORTID - e.SORTID; }");
			appendLine("if(e instanceof "+prefix+sort+"Variable){ return 1; }");
			appendLine("if(!(e instanceof "+TYPE+")){ return PRIORITY - e.PRIORITY; }");
			if(SIZE >0 ){
				appendLine(TYPE+" ee = ("+TYPE+") e;");
				appendLine("int result = 0;");
				for(int i = 0; i < SIZE; i++){
					appendLine("result = e"+i+".compareTo(ee.e"+i+");");
					appendLine("if(result != 0){ return result; }");
				}
			}
			appendLine("return 0;");
		decrementIndentLevel();
		appendLine('}');

	}

	public void addToStringMethod(List<MettelToken> tokens){
		appendLine("private String str = null;");
		appendLine("public String toString(){");
		incrementIndentLevel();
			appendLine("if(str == null){");
			incrementIndentLevel();
				appendLine("StringBuilder b = new StringBuilder();");
				appendLine("b.append('(');");
				int i = 0;
				for(MettelToken t:tokens){
					if(t instanceof MettelStringLiteral){
						appendLine("b.append("+t+");");
					}else{
						appendLine("b.append(e"+i+");");
						i++;
					}
				}
				appendLine("b.append(')');");
				appendLine("str = b.toString();");
			decrementIndentLevel();
			appendLine('}');
			appendLine("return str;");
		decrementIndentLevel();
		appendLine('}');
	}
}
