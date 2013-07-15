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
public class MettelReplacementJavaClassFile extends MettelJavaClassFile {

	private String prefix = "Mettel";
	private String nameSeparator = "";

	public MettelReplacementJavaClassFile(String prefix,
			MettelJavaPackage pack, String[] sorts, String nameSeparator) {
		super(prefix+"TreeReplacement", pack, "public", null,
				new String[]{prefix+"Replacement"});
		this.prefix = prefix;
		this.nameSeparator = nameSeparator;
		body(sorts);
	}

	protected void imports(){
		headings.appendLine("import java.util.Comparator;");
		headings.appendLine("import java.util.Map;");
		headings.appendLine("import java.util.TreeMap;");
		headings.appendLine("import java.util.Set;");
		headings.appendLine("import java.util.TreeSet;");
		headings.appendLine("import java.util.Map.Entry;");
		headings.appendLine("import mettel.core.tableau.MettelReplacement;");
		headings.appendLine("import mettel.core.tableau.MettelSubstitution;");
		headings.appendLine("import mettel.core.tableau.MettelExpression;");
		headings.appendEOL();
	}

	private void body(String[] sorts){

		appendLine("private class Pointer<T extends "+prefix+"Expression>{");
		incrementIndentLevel();
			appendLine("T expression = null;");
			appendLine("Pointer(T expression){");
			incrementIndentLevel();
				appendLine("super();");
				appendLine("this.expression = expression;");
			decrementIndentLevel();
			appendLine('}');

			appendLine("public int hashCode(){");
			incrementIndentLevel();
	        	appendLine("return expression.hashCode();");
	        decrementIndentLevel();
	        appendLine('}');
	    decrementIndentLevel();
		appendLine('}');
		appendEOL();

		appendLine("private Comparator<"+prefix+"AbstractExpression> comparator = new "+prefix+"IDComparator();");
		appendEOL();

		for(String sort:sorts){
			final String TYPE = prefix+MettelJavaNames.firstCharToUpperCase(sort, nameSeparator);
			appendLine("protected final Map<"+TYPE+", Pointer<"+TYPE+">> "+sort+"Map = new TreeMap<"+TYPE+", Pointer<"+TYPE+">>();");

			appendLine("public " + TYPE+" get"+MettelJavaNames.firstCharToUpperCase(sort, nameSeparator)+'('+TYPE+" e){");
			incrementIndentLevel();
				appendLine("final Pointer<"+TYPE+"> p = "+sort+"Map.get(e);");
				appendLine("return p == null? null: p.expression;");
			decrementIndentLevel();
			appendLine('}');
			appendEOL();

			appendLine("public Set<"+TYPE+"> "+sort+"Keys(){");
			incrementIndentLevel();
				appendLine("return "+sort+"Map.keySet();");
			decrementIndentLevel();
			appendLine('}');
			appendEOL();

/*			appendLine("public boolean append("+TYPE+" e0, "+TYPE+" e1){");
			incrementIndentLevel();
				appendLine("if(e0 == null || e1 == null){ return false; }");

				appendLine("final int ID0 = e0.id();");
				appendLine("int ID1 = e1.id();");
			    //appendLine("final int res = ID0-ID1;");
			    appendLine("if(ID0 == ID1) return false;");
			    incrementIndentLevel();
			    	appendLine(TYPE+" left = null;");
			        appendLine(TYPE+" right = null;");
			        appendLine("if(ID0 > ID1){");
			        incrementIndentLevel();
			        	appendLine("left = e0;");
			        	appendLine("right = e1;");
			        decrementIndentLevel();
			        appendLine("}else{");
			        incrementIndentLevel();
			        	appendLine("left = e1;");
			        	appendLine("right = e0;");
			        	appendLine("ID1 = ID0;");
			        decrementIndentLevel();
					appendLine('}');
					//appendEOL();
//appendLine("System.out.println(\"Left=\"+left+\", right=\"+right);");

			        appendLine("final Pointer<"+TYPE+"> entry = "+sort+"Map.get(left);");
			        appendLine("if(entry == null){");
			        incrementIndentLevel();
			        	appendLine(sort+"Map.put(left, new Pointer<"+TYPE+">(right));");
			        	appendLine("return true;");
			        decrementIndentLevel();
			        appendLine("}else{");
			        incrementIndentLevel();
			        	appendLine("final "+TYPE+" third = entry.expression;");
			        	appendLine("final int ID2 = third.id();");
			        	appendLine("if(ID1 == ID2) return false;");
			        	appendLine("if(ID1 > ID2){");
			        	incrementIndentLevel();
			        		appendLine(sort+"Map.put(right, entry);");
			        		appendLine("return true;");
			        	decrementIndentLevel();
			        	appendLine("}else{");
			        	incrementIndentLevel();
			        		appendLine("entry.expression = right;");
			        		appendLine(sort+"Map.put(third, entry);");
			        		appendLine("return true;");
			        	decrementIndentLevel();
			        	appendLine('}');
			        decrementIndentLevel();
			        appendLine('}');

/*				appendLine("final "+TYPE+" e = "+sort+"Map.get(e0);");
				appendLine("if(e == null){");
				incrementIndentLevel();
//					appendLine("if(!e0.equals(e1)){ "+sort+"Map.put(e0,e1); };");
					appendLine(sort+"Map.put(e0,e1);");
//appendLine("System.out.println(\"Good replacement\");");
					appendLine("return true;");
				decrementIndentLevel();
				appendLine("}else{");
				incrementIndentLevel();
					appendLine("return (e.equals(e1));");
				decrementIndentLevel();
				appendLine('}');
/
			decrementIndentLevel();
			appendLine('}');
			appendEOL();
*/
			appendLine("public boolean append("+TYPE+" e0, "+TYPE+" e1){");
			incrementIndentLevel();
				appendLine("if(e0 == null || e1 == null){ return false; }");

				//appendLine("final int ID0 = e0.id();");
				//appendLine("int ID1 = e1.id();");
			    //appendLine("final int res = ID0-ID1;");

				appendLine("final int CMP = comparator.compare(("+prefix+"AbstractExpression)e0,("+prefix+"AbstractExpression)e1);");
				appendLine("if(CMP == 0) return false;");
			    incrementIndentLevel();
			    	appendLine(TYPE+" left = null;");
			        appendLine(TYPE+" right = null;");
			        appendLine("if(CMP > 0){");
			        incrementIndentLevel();
			        	appendLine("left = e0;");
			        	appendLine("right = e1;");
			        decrementIndentLevel();
			        appendLine("}else{");
			        incrementIndentLevel();
			        	appendLine("left = e1;");
			        	appendLine("right = e0;");
			        	//appendLine("ID1 = ID0;");
			        decrementIndentLevel();
					appendLine('}');
					//appendEOL();
//appendLine("System.out.println(\"Left=\"+left+\", right=\"+right);");

//appendLine("System.out.println("+sort+"Map.keySet());");
			        appendLine("final Pointer<"+TYPE+"> entry = "+sort+"Map.get(left);");

			        appendLine("if(entry == null){");
			        incrementIndentLevel();
			        	appendLine("final Pointer<"+TYPE+"> rEntry = "+sort+"Map.get(right);");
			        	appendLine("if(rEntry == null) {");
			        	incrementIndentLevel();
			        		appendLine(sort+"Map.put(left, new Pointer<"+TYPE+">(right));");
//appendLine("System.out.println(\"* \"+left+\"->\"+right);");
//appendLine("System.out.println("+sort+"Map.keySet());");
//appendLine("System.out.println(this);");
			        	decrementIndentLevel();
			        	appendLine("}else{");
			        	incrementIndentLevel();
		        			appendLine(sort+"Map.put(left, rEntry);");
//appendLine("System.out.println(\"# \"+left+\"->\"+rEntry.expression);");
		        		decrementIndentLevel();
		        		appendLine('}');
			        	appendLine("return true;");
			        decrementIndentLevel();
			        appendLine("}else{");
			        incrementIndentLevel();
			        	appendLine("final "+TYPE+" third = entry.expression;");
			        	//appendLine("final int ID2 = third.id();");
			        	appendLine("final int CMP0 = comparator.compare(("+prefix+"AbstractExpression)right, ("+prefix+"AbstractExpression)third);");
			        	appendLine("if(CMP0 == 0) return false;");
			        	appendLine("if(CMP0 > 0){");
			        	incrementIndentLevel();
			        		appendLine(sort+"Map.put(right, entry);");
//appendLine("System.out.println(\"** \"+right+\"->\"+third);");
			        		appendLine("return true;");
			        	decrementIndentLevel();
			        	appendLine("}else{");
			        	incrementIndentLevel();
			        		appendLine("entry.expression = right;");
			        		appendLine(sort+"Map.put(third, entry);");
//appendLine("System.out.println(\"*** \"+third+\"->\"+right);");
			        		appendLine("return true;");
			        	decrementIndentLevel();
			        	appendLine('}');
			        decrementIndentLevel();
			        appendLine('}');
			decrementIndentLevel();
			appendLine('}');
			appendEOL();
		}

		appendLine("public boolean isEmpty(){");
		incrementIndentLevel();
			indent(); append("return ");
				boolean notFirst = false;
				for(String sort:sorts){
					if(notFirst){
						append(" && ");
					}else{
						notFirst = true;
					}
					append(sort+"Map.isEmpty()");
				}
			append(';');appendEOL();
		decrementIndentLevel();
		appendLine('}');
		appendEOL();


		appendLine("public boolean append(MettelExpression e0, MettelExpression e1){");
		incrementIndentLevel();
			for(String sort:sorts){
				final String TYPE = prefix+MettelJavaNames.firstCharToUpperCase(sort, nameSeparator);
				appendLine("if(e0 instanceof "+TYPE+"){ return append(("+TYPE+")e0, ("+TYPE+")e1); }");
			}
			appendLine("return false;");
		decrementIndentLevel();
		appendLine('}');
		appendEOL();

		appendLine("public boolean append(MettelReplacement r){");
		incrementIndentLevel();
			appendLine("if(r == null){ return false; }");
			appendLine("return append(("+prefix+"Replacement)r);");
		decrementIndentLevel();
		appendLine('}');
		appendEOL();

		appendLine("public MettelExpression rewrite(MettelExpression e) {");
		incrementIndentLevel();
			for(String sort:sorts){
				final String uSort = MettelJavaNames.firstCharToUpperCase(sort, nameSeparator);
				final String TYPE = prefix+uSort;

				appendLine("if(e instanceof "+TYPE+"){");
				incrementIndentLevel();
					appendLine("return (("+TYPE+")e).rewrite(this);");
					/*appendLine(TYPE+" f1 = ("+TYPE+")e;");
					appendLine(TYPE+" f0 = null;");
					appendLine("do{");
					incrementIndentLevel();
						appendLine("f0 = f1;");
						appendLine("f1 = ("+TYPE+")f0.replace(this);");
					decrementIndentLevel();
					appendLine("}while(f0 != f1);");//We can use equality here since only one instance is allowed
					appendLine("return f1;");*/
				decrementIndentLevel();
				appendLine('}');
			}
			appendLine("return null;");
		decrementIndentLevel();
		appendLine('}');
		appendEOL();

	    appendLine("public MettelSubstitution rewrite(MettelSubstitution s) {");
	    incrementIndentLevel();
	    	appendLine("final "+prefix+"Substitution s0 = ("+prefix+"Substitution)s;");
        	appendLine(prefix+"Substitution res = new "+prefix+"TreeSubstitution();");

	    	for(String sort:sorts){
				final String uSort = MettelJavaNames.firstCharToUpperCase(sort, nameSeparator);
				final String TYPE = prefix+uSort;

	    		appendLine("final Set<Entry<"+TYPE+"Variable, "+TYPE+">> "+sort+"EntrySet = s0."+sort+"Map().entrySet();");
	        	appendLine("for(Entry<"+TYPE+"Variable, "+TYPE+"> entry:"+sort+"EntrySet){");
	        	incrementIndentLevel();
	        		appendLine("res.append(entry.getKey(),("+TYPE+")rewrite(entry.getValue()));");
	        	decrementIndentLevel();
	        	appendLine('}');
	    	}
	        appendLine("return res;");
	    decrementIndentLevel();
	    appendLine('}');
	    appendEOL();

		appendLine("public boolean append("+prefix+"Replacement r){");
		incrementIndentLevel();
			appendLine("boolean result = false;");
			for(String sort:sorts){
				final String uSort = MettelJavaNames.firstCharToUpperCase(sort, nameSeparator);
				final String TYPE = prefix+uSort;

		        appendLine("final Set<"+TYPE+"> "+sort+"Keys = r."+sort+"Keys();");
		        appendLine("for("+TYPE+" key:"+sort+"Keys){");
		        incrementIndentLevel();
		        	appendLine("result |= append(key, r.get"+uSort+"(key));");
		        decrementIndentLevel();
		        appendLine('}');

/*				appendLine("final Map<"+TYPE+", "+TYPE+"> m"+uSort+" = r."+sort+"Map();");
				appendLine("for("+TYPE+" key:m"+uSort+".keySet()){");
				incrementIndentLevel();
					appendLine("if(!append(key,r.get"+uSort+"(key))){ return false; }");
				decrementIndentLevel();
				appendLine('}');
*/
			}
			appendLine("return result;");
		decrementIndentLevel();
		appendLine('}');
		appendEOL();
		    //MettelReplacement compose(MettelReplacement r);

		appendLine("private int hashCode = 0;");
		appendLine("public int hashCode(){");
		incrementIndentLevel();
			appendLine("hashCode = 1;");
			for(String sort:sorts){
				appendLine("hashCode = 31*hashCode + " +sort +"Map.hashCode();");
			}
			appendLine("return hashCode;");
		decrementIndentLevel();
		appendLine('}');
		appendEOL();

		appendLine("public int compareTo(MettelReplacement r){");
		incrementIndentLevel();
			appendLine("if(r == this){ return 0; }");
			for(String sort:sorts){
				final String uSort = MettelJavaNames.firstCharToUpperCase(sort, nameSeparator);
				final String TYPE = prefix+uSort;
				appendLine("final Set<"+TYPE+"> keys"+uSort+"0 = "+sort+"Map.keySet();");
				appendLine("final Set<"+TYPE+"> keys"+uSort+"1 = (("+prefix+"Replacement)r)."+sort+"Keys();");
				appendLine("final TreeSet<"+TYPE+"> keys"+uSort+" = new TreeSet<"+TYPE+">(keys"+uSort+"0);");//Note: this is a sorted set!
				appendLine("keys"+uSort+".addAll(keys"+uSort+"1);");
				appendLine("for("+TYPE +" key:keys"+uSort+"){");
				incrementIndentLevel();
					appendLine("if(!keys"+uSort+"0.contains(key)){");
						incrementIndentLevel();
							appendLine("return 1;");
						decrementIndentLevel();
					appendLine("}else{");
						incrementIndentLevel();
						appendLine("if(!keys"+uSort+"1.contains(key)){");
							incrementIndentLevel();
								appendLine("return -1;");
							decrementIndentLevel();
						appendLine("}else{");
							incrementIndentLevel();
								appendLine("final "+TYPE + " v0 = "+sort+"Map.get(key).expression;");
								appendLine("final "+TYPE + " v1 = (("+prefix+"Replacement)r).get"+uSort+"(key);");
								appendLine("final int result = v0.compareTo(v1);");
								appendLine("if(result != 0){ return result; }");
							decrementIndentLevel();
						appendLine('}');
						decrementIndentLevel();
					appendLine('}');
				decrementIndentLevel();
				appendLine("}");
			}
			appendLine("return 0;");
		decrementIndentLevel();
		appendLine('}');
		appendEOL();

		appendLine("public boolean equals(Object o){");
		incrementIndentLevel();
			appendLine("if(o == this){ return true; }");
			appendLine("if(!(o instanceof "+prefix+"Replacement)){ return false; }");
			appendLine("final "+prefix+"Replacement r = ("+prefix+"Replacement)o;");

//			boolean notFirst = false;
//			indent();append("return ");
			for(String sort:sorts){
				final String uSort = MettelJavaNames.firstCharToUpperCase(sort, nameSeparator);
				final String TYPE = prefix+uSort;
				appendLine("final Set<"+TYPE+"> "+sort+"Keys = "+sort+"Map.keySet();");
		        appendLine("if(!"+sort+"Keys.equals(r."+sort+"Keys())) return false;");
		        appendLine("for("+TYPE+" key:"+sort+"Keys){");
		        incrementIndentLevel();
		        	appendLine("final "+TYPE+" v = "+sort+"Map.get(key).expression;");
		        	appendLine("if(!v.equals(r.get"+uSort+"(key))) return false;");
		        decrementIndentLevel();
		        appendLine('}');
//				if(notFirst){
//					append(" && ");
//				}else{
//					notFirst = true;
//				}
//				append(sort+"Map.equals(r."+sort+"Map())");
			}
//			append(';');
//			appendEOL();
	        appendLine("return true;");
			decrementIndentLevel();
		appendLine('}');
		appendEOL();

	    appendLine("public String toString(){");
	    incrementIndentLevel();
	    	appendLine("StringBuilder b = new StringBuilder();");
	    	appendLine("b.append(\"$[\");");

	    	appendLine("boolean notFirst = false;");
	    	for(String sort:sorts){
	    		final String NAME = prefix+MettelJavaNames.firstCharToUpperCase(sort, nameSeparator);

	    		appendLine("final Set<Entry<"+NAME+", Pointer<"+NAME+">>> "+sort+"EntrySet = "+sort+"Map.entrySet();");
		    	appendLine("for(Entry<"+NAME+", Pointer<"+NAME+">> entry:"+sort+"EntrySet){");
		    	incrementIndentLevel();
		    		appendLine("if(notFirst){");
		    		incrementIndentLevel();
		    			appendLine("b.append(\", \");");
		    		decrementIndentLevel();
		    		appendLine("}else{");
		    		incrementIndentLevel();
		    			appendLine("notFirst = true;");
		    		decrementIndentLevel();
		    		appendLine('}');
		    		appendLine("b.append(entry.getKey());");
		    		appendLine("b.append('/');");
		    		appendLine("b.append(entry.getValue().expression);");
		    	decrementIndentLevel();
		    	appendLine('}');
	    	}
	    	appendLine("b.append(']');");
	    	appendLine("return b.toString();");
	    decrementIndentLevel();
	    appendLine('}');
	    appendEOL();
	}

}
