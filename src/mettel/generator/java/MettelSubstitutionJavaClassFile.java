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
public class MettelSubstitutionJavaClassFile extends MettelJavaClassFile {

	private String prefix = "Mettel";

	public MettelSubstitutionJavaClassFile(String prefix,
			MettelJavaPackage pack, String[] sorts) {
		super(prefix+"TreeSubstitution", pack, "public", null, //prefix+"TreeReplacement",
				new String[]{prefix+"Substitution"});
		this.prefix = prefix;
		body(sorts);
	}

	protected void imports(){
		headings.appendLine("import java.util.Map;");
		headings.appendLine("import java.util.TreeMap;");
		headings.appendLine("import java.util.Map.Entry;");
		headings.appendLine("import java.util.Set;");
		headings.appendLine("import java.util.TreeSet;");
		headings.appendEOL();
		headings.appendLine("import mettel.core.MettelSubstitution;");
	}

	private void body(String[] sorts){
		for(String sort:sorts){
			final String TYPE = prefix+MettelJavaNames.firstCharToUpperCase(sort);
			appendLine("protected final Map<"+TYPE+"Variable, "+TYPE+"> "+sort+"Map = new TreeMap<"+TYPE+"Variable, "+TYPE+">();");

			appendLine("public " + TYPE+" get"+MettelJavaNames.firstCharToUpperCase(sort)+'('+TYPE+"Variable v){");
			incrementIndentLevel();
				appendLine("return "+sort+"Map.get(v);");
			decrementIndentLevel();
			appendLine('}');
			appendEOL();

			appendLine("public Map<"+TYPE+"Variable, "+TYPE+"> "+sort+"Map(){");
			incrementIndentLevel();
				appendLine("return "+sort+"Map;");
			decrementIndentLevel();
			appendLine('}');
			appendEOL();

/*
			appendLine("public "+TYPE+" get"+MettelJavaNames.firstCharToUpperCase(sort)+'('+TYPE+"Variable e){");
			incrementIndentLevel();
			//appendLine("if(e0 instanceof "+TYPE+"Variable){");
			//incrementIndentLevel();
				appendLine("return super.get"+MettelJavaNames.firstCharToUpperCase(sort)+"(("+TYPE+")e);");
			//decrementIndentLevel();
			//appendLine("}else{ return false; }");
			decrementIndentLevel();
			appendLine('}');

			appendEOL();
*/

			appendLine("public boolean append("+TYPE+"Variable v, "+TYPE+" e1){");
			incrementIndentLevel();
				appendLine("if(v == null || e1 == null){ return false; }");
				appendLine("final "+TYPE+" e = "+sort+"Map.get(v);");
				appendLine("if(e == null){");
				incrementIndentLevel();
//					appendLine("if(!e0.equals(e1)){ "+sort+"Map.put(e0,e1); };");
					appendLine(sort+"Map.put(v,e1);");
//appendLine("System.out.println(\"Good replacement\");");
					appendLine("return true;");
				decrementIndentLevel();
				appendLine("}else{");
				incrementIndentLevel();
					appendLine("return (e.equals(e1));");
				decrementIndentLevel();
				appendLine('}');
			decrementIndentLevel();
			appendLine('}');
			appendEOL();

/*			appendLine("public boolean append("+TYPE+"Variable e0, "+TYPE+" e1){");
			incrementIndentLevel();
				//appendLine("if(e0 instanceof "+TYPE+"Variable){");
				//incrementIndentLevel();
					appendLine("return super.append(("+TYPE+")e0,e1);");
				//decrementIndentLevel();
				//appendLine("}else{ return false; }");
			decrementIndentLevel();
			appendLine('}');
			appendEOL();
*/
		}

		appendLine("public boolean append("+prefix+"Substitution s){");
		incrementIndentLevel();
			for(String sort:sorts){
				final String uSort = MettelJavaNames.firstCharToUpperCase(sort);
				final String TYPE = prefix+uSort;
				appendLine("final Map<"+TYPE+"Variable, "+TYPE+"> m"+uSort+" = s."+sort+"Map();");
				appendLine("for("+TYPE+"Variable key:m"+uSort+".keySet()){");
				incrementIndentLevel();
					appendLine("if(!append(key,s.get"+uSort+"(key))){ return false; }");
				decrementIndentLevel();
				appendLine('}');
			}
			appendLine("return true;");
		decrementIndentLevel();
		appendLine('}');
		appendEOL();

		appendLine("public MettelSubstitution merge(MettelSubstitution s){");
		incrementIndentLevel();
			appendLine(prefix+"TreeSubstitution result = new "+prefix+"TreeSubstitution();");
			appendLine("result.append(this);");
			appendLine("if(result.append(("+prefix+"TreeSubstitution)s)){");
			incrementIndentLevel();
				appendLine("return result;");
			decrementIndentLevel();
			appendLine("}else{");
			incrementIndentLevel();
				appendLine("return null;");
			decrementIndentLevel();
			appendLine('}');
		decrementIndentLevel();
		appendLine('}');
		appendEOL();

/*		appendLine("public int compareTo("+prefix+"Replacement r){");
		incrementIndentLevel();
			appendLine("if(!(r instanceof "+prefix+"Substitution)){ return 1; };");//throw an exception?
			appendLine("return super.compareTo(r);");
		decrementIndentLevel();
		appendLine('}');
		appendEOL();

		appendLine("public boolean equals(Object o){");
		incrementIndentLevel();
			appendLine("if(o == this){ return true; }");
			appendLine("if(!(o instanceof "+prefix+"Substitution)){ return false; }");
			appendLine("final "+prefix+"Substitution r = ("+prefix+"Substitution)o;");
			boolean notFirst = false;
			indent();append("return ");
			for(String sort:sorts){
				if(notFirst){
					append(" && ");
				}else{
					notFirst = true;
				}
				append(sort+"Map.equals(r."+sort+"Map())");
			}
			append(';');
			appendEOL();
		decrementIndentLevel();
		appendLine('}');
		appendEOL();
*/
		appendLine("private static "+prefix+"TreeSubstitution appendArray("+prefix+"TreeSubstitution s, MettelSubstitution[] array){");
	    incrementIndentLevel();
			appendLine("if(array == null) return null;");
			appendLine("final int SIZE = array.length;");
	    	appendLine("if(SIZE == 0) return null;");
	    	appendLine("for(int i = 0; i < SIZE; i++){");
	    	incrementIndentLevel();
	    	for(String sort:sorts){
	    		final String TYPE = prefix+MettelJavaNames.firstCharToUpperCase(sort);
	    		final String ENTRY_SET = "entry"+MettelJavaNames.firstCharToUpperCase(sort)+"Set";
	    		appendLine("Set<Entry<"+TYPE+"Variable, "+TYPE+">> "+ENTRY_SET+" = (("+prefix+"Substitution)array[i])."+sort+"Map().entrySet();");
	    		appendLine("for(Entry<"+TYPE+"Variable, "+TYPE+"> entry:"+ENTRY_SET+"){");
	    		incrementIndentLevel();
	    			appendLine("if(!s.append(entry.getKey(),entry.getValue())) return null;");
	    		decrementIndentLevel();
	    		appendLine('}');
	    	}
	    	decrementIndentLevel();
	    	appendLine('}');
	    	appendLine("return s;");
	    decrementIndentLevel();
	    appendLine('}');

	    appendEOL();

	    appendLine("public MettelSubstitution mergeArray(MettelSubstitution[] array){");
	    incrementIndentLevel();
	    	appendLine("return appendArray(new "+prefix+"TreeSubstitution(),array);");
	    decrementIndentLevel();
		appendLine('}');

		appendEOL();

	    appendLine("public MettelSubstitution merge(MettelSubstitution[] array){");
	    incrementIndentLevel();
	    	appendLine(prefix+"TreeSubstitution s = new "+prefix+"TreeSubstitution();");
	    	for(String sort:sorts){
	    		appendLine("s."+sort+"Map().putAll("+sort+"Map);");
	    	}
	    	appendLine("return appendArray(s,array);");
	    decrementIndentLevel();
		appendLine('}');

		appendEOL();

		appendLine("public int compareTo(MettelSubstitution s){");
		incrementIndentLevel();
			appendLine("if(s == this){ return 0; }");
			for(String sort:sorts){
				final String uSort = MettelJavaNames.firstCharToUpperCase(sort);
				final String TYPE = prefix+uSort;
				appendLine("final Set<"+TYPE+"Variable> keys"+uSort+"0 = "+sort+"Map.keySet();");
				appendLine("final Set<"+TYPE+"Variable> keys"+uSort+"1 = (("+prefix+"Substitution)s)."+sort+"Map().keySet();");
				appendLine("final TreeSet<"+TYPE+"Variable> keys"+uSort+" = new TreeSet<"+TYPE+"Variable>(keys"+uSort+"0);");//Note: this is a sorted set!
				appendLine("keys"+uSort+".addAll(keys"+uSort+"1);");
				appendLine("for("+TYPE +"Variable key:keys"+uSort+"){");
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
								appendLine("final "+TYPE + " v0 = "+sort+"Map.get(key);");
								appendLine("final "+TYPE + " v1 = (("+prefix+"Substitution)s).get"+uSort+"(key);");
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

		appendLine("public boolean equals(Object o){");
		incrementIndentLevel();
			appendLine("if(o == this){ return true; }");
			appendLine("if(!(o instanceof "+prefix+"Substitution)){ return false; }");
			appendLine("final "+prefix+"Substitution s = ("+prefix+"Substitution)o;");
			boolean notFirst = false;
			indent();append("return ");
			for(String sort:sorts){
				if(notFirst){
					append(" && ");
				}else{
					notFirst = true;
				}
				append(sort+"Map.equals(s."+sort+"Map())");
			}
			append(';');
			appendEOL();
		decrementIndentLevel();
		appendLine('}');
		appendEOL();

	    appendLine("public String toString(){");
	    incrementIndentLevel();
	    	appendLine("StringBuilder b = new StringBuilder();");
	    	appendLine("b.append(\"$[\");");

	    	appendLine("boolean notFirst = false;");
	    	for(String sort:sorts){
	    		final String NAME = prefix+MettelJavaNames.firstCharToUpperCase(sort);

	    		appendLine("Set<Entry<"+NAME+"Variable, "+NAME+">> "+sort+"EntrySet = "+sort+"Map.entrySet();");
		    	appendLine("for(Entry<"+NAME+"Variable, "+NAME+"> entry:"+sort+"EntrySet){");
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
		    		appendLine("b.append(entry.getValue());");
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
