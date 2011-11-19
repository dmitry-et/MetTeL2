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

	private boolean equality = false;
	/**
	 * @param prefix
	 * @param sort
	 * @param pack
	 */
	public MettelComplexExpressionJavaClassFile(String prefix, String sort, String name, String[] sorts, MettelJavaPackage pack, boolean equality){
		super(prefix+MettelJavaNames.firstCharToUpperCase(name)+MettelJavaNames.firstCharToUpperCase(sort), pack, "public",
				prefix+"AbstractExpression",
				equality? new String[]{"MettelEqualityExpression", prefix+MettelJavaNames.firstCharToUpperCase(sort)} :
					      new String[]{prefix+MettelJavaNames.firstCharToUpperCase(sort)});
		priority = ++counter;

		this.prefix = prefix;
		this.sort = sort;
		this.name = name;
		this.equality = equality;
		body(sorts);
	}

	/**
	 * @param prefix
	 * @param sort
	 * @param pack
	 */
	public MettelComplexExpressionJavaClassFile(String prefix, String sort, String name, String[] sorts, MettelJavaPackage pack){
		this(prefix, sort, name, sorts, pack, false);
	}


	protected void imports(){
		headings.appendLine("import mettel.core.MettelExpression;");
		if(equality){
			headings.appendLine("import mettel.core.MettelEqualityExpression;");
		}
		headings.appendEOL();
	}

	private void body(String[] sorts){
		final String TYPE = prefix+MettelJavaNames.firstCharToUpperCase(name)+MettelJavaNames.firstCharToUpperCase(sort);
		final int SIZE = sorts.length;

		appendLine("static final int PRIORITY = "+priority+';');

		appendLine("private final int LENGTH;");

		for(int i = 0; i < SIZE; i++){
			appendLine("protected "+prefix+MettelJavaNames.firstCharToUpperCase(sorts[i])+" e"+i+" = null;");
		}
		appendEOL();

		indent();
		append("public "+TYPE+'(');
		if(SIZE>0){
			append(prefix+MettelJavaNames.firstCharToUpperCase(sorts[0])+" e"+0);
			for(int i = 1; i<SIZE; i++){
				append(", "+prefix+MettelJavaNames.firstCharToUpperCase(sorts[i])+" e"+i);
			}
			append(", ");
		}
		append(prefix+"ObjectFactory f){");
		appendEOL();
			incrementIndentLevel();
				appendLine("super(f);");
				appendLine("int l = 1;");
				for(int i = 0; i < SIZE;i++){
					appendLine("this.e"+i+" = e"+i+';');
					appendLine("l += e"+i+".length();");
				}
				appendLine("LENGTH = l;");
			decrementIndentLevel();
		appendLine('}');
		appendEOL();

		appendLine("int sortId(){ return SORTID; }");

		appendEOL();

		appendLine("int priority(){ return PRIORITY; }");

		appendEOL();

		appendLine("public int length(){ return LENGTH; }");

		appendEOL();

		appendLine("public "+prefix+"Substitution match("+prefix+"Expression e){");
		incrementIndentLevel();
			appendLine(prefix+"Substitution s = new "+prefix+"TreeSubstitution();");
			appendLine("if(match(e,s)){");
				incrementIndentLevel();
					appendLine("return factory.getSubstitution(s);");
				decrementIndentLevel();
			appendLine("}else{ return null; }");
		decrementIndentLevel();
		appendLine('}');

		appendEOL();

		appendLine("public boolean match("+prefix+"Expression e, "+prefix+"Substitution s){");
		incrementIndentLevel();
		    appendLine("if(!(e instanceof "+TYPE+")){ return false;}");
		    if(SIZE > 0){
			    appendLine("final "+TYPE+" ee = ("+TYPE+")e;");
				indent();
				append("return e"+0+".match(ee.e"+0+", s)");
				for(int i = 1; i < SIZE; i++){
					append(" && e"+i+".match(ee.e"+i+", s)");
				}
				append(';');
				appendEOL();
		    }else{
		    	appendLine("return true;");
		    }
		decrementIndentLevel();
		appendLine('}');

		appendEOL();

		appendLine("public "+prefix+"Expression substitute("+prefix+"Substitution s){");
			incrementIndentLevel();
				if(SIZE > 0){
					indent();
					append("return factory.create"+MettelJavaNames.firstCharToUpperCase(name)+MettelJavaNames.firstCharToUpperCase(sort)+'(');
					append('(');
					append(prefix+MettelJavaNames.firstCharToUpperCase(sorts[0]));
					append(')');
					append("e0.substitute(s)");
					for(int i = 1; i < SIZE; i++){
						append(", ");
						append('(');
						append(prefix+MettelJavaNames.firstCharToUpperCase(sorts[i]));
						append(')');
						append("e"+i+".substitute(s)");
					}
					append(");");
				}else{
					appendLine("return this;");
				}
				appendEOL();
			decrementIndentLevel();
		appendLine('}');

		appendEOL();

		appendLine("public "+prefix+"Expression replace("+prefix+"Replacement s){");
		incrementIndentLevel();
			appendLine(prefix+"Expression e = s.get"+MettelJavaNames.firstCharToUpperCase(sort)+"(this);");
			appendLine("if(e != null){ return e; }");
			if(SIZE > 0){
				indent();
				append("return factory.create"+MettelJavaNames.firstCharToUpperCase(name)+MettelJavaNames.firstCharToUpperCase(sort)+'(');
				append('(');
				append(prefix+MettelJavaNames.firstCharToUpperCase(sorts[0]));
				append(')');
				append("e0.replace(s)");
				for(int i = 1; i < SIZE; i++){
					append(", ");
					append('(');
					append(prefix+MettelJavaNames.firstCharToUpperCase(sorts[i]));
					append(')');
					append("e"+i+".replace(s)");
				}
				append(");");
			}else{
				appendLine("return this;");
			}
			appendEOL();
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
			if(SIZE > 0){
				appendLine(TYPE+" e = ("+TYPE+") o;");
				indent();
				append("return (e0.equals(e.e0))");
				for(int i = 1; i < SIZE; i++){
					append(" && (e"+i+".equals(e.e"+i+"))");
				}
				append(';');
				appendEOL();
			}else{
				appendLine("return true;");
			}
		decrementIndentLevel();
		appendLine('}');

		appendEOL();

		appendLine("public int compareTo(MettelExpression e){");
		incrementIndentLevel();
			appendLine("if(e == this){ return 0; }");
			appendLine("if(!(e instanceof "+prefix+MettelJavaNames.firstCharToUpperCase(sort)+")){ return SORTID - (("+
					prefix+"AbstractExpression)e).sortId(); }");
			appendLine("if(e instanceof "+prefix+MettelJavaNames.firstCharToUpperCase(sort)+"Variable){ return 1; }");
			appendLine("if(!(e instanceof "+TYPE+")){ return PRIORITY - (("+
					prefix+"AbstractExpression)e).priority(); }");
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

		if(equality){
			appendLine("public MettelExpression left(){");
			incrementIndentLevel();
				appendLine("return e0;");
			decrementIndentLevel();
			appendLine('}');

			appendLine("public MettelExpression right(){");
			incrementIndentLevel();
				appendLine("return e1;");
			decrementIndentLevel();
			appendLine('}');
		}

	}

	public void addToStringMethod(List<MettelToken> tokens){
		appendEOL();
		appendLine("private String str = null;");
		appendLine("public String toString(){");
		incrementIndentLevel();
			appendLine("if(str == null){");
			incrementIndentLevel();
				appendLine("StringBuilder b = new StringBuilder();");
				appendLine("b.append('(');");
				int i = 0;
				for(MettelToken t:tokens){
					appendLine("b.append(' ');");
					if(t instanceof MettelStringLiteral){
						String s = t.toString();
						if(s.length() > 3){
							s = s.replace('\'', '"');
						}
						appendLine("b.append("+s+");");
					}else{
						appendLine("b.append(e"+i+");");
						i++;
					}
				}
				appendLine("b.append(' ');");
				appendLine("b.append(')');");
				appendLine("str = b.toString();");
			decrementIndentLevel();
			appendLine('}');
			appendLine("return str;");
		decrementIndentLevel();
		appendLine('}');
	}
}
