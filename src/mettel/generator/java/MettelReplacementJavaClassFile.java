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

	public MettelReplacementJavaClassFile(String prefix,
			MettelJavaPackage pack, String[] sorts) {
		super(prefix+"TreeReplacement", pack, "public", null,
				new String[]{"Comparable<"+prefix+"Replacement>"});
		this.prefix = prefix;
		body(sorts);
	}

	void imports(){
		appendLine("import java.util.Map;");
		appendLine("import java.util.TreeMap;");
		appendLine("import java.util.Set;");
		appendLine("import java.util.TreeSet;");
		appendEOL();
	}

	private void body(String[] sorts){
		for(String sort:sorts){
			final String TYPE = prefix+MettelJavaNames.firstCharToUpperCase(sort);
			appendLine("protected final Map<"+TYPE+", "+TYPE+"> "+sort+"Map = new TreeMap<"+TYPE+", "+TYPE+">();");

			appendLine("public " + TYPE+" get"+MettelJavaNames.firstCharToUpperCase(sort)+'('+TYPE+" e){");
			incrementIndentLevel();
				appendLine("return "+sort+"Map.get(e);");
			decrementIndentLevel();
			appendLine('}');
			appendEOL();

			appendLine("public Map<"+TYPE+", "+TYPE+"> "+sort+"Map(){");
			incrementIndentLevel();
				appendLine("return "+sort+"Map;");
			decrementIndentLevel();
			appendLine('}');
			appendEOL();


			appendLine("public boolean append("+TYPE+" e0, "+TYPE+" e1){");
			incrementIndentLevel();
				appendLine("if(e0 == null || e1 == null){ return false; }");
				appendLine("final "+TYPE+" e = "+sort+"Map.get(e0);");
				appendLine("if(e == null){");
				incrementIndentLevel();
					appendLine("if(!e0.equals(e1)){ "+sort+"Map.put(e0,e1); };");
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
		}

		appendLine("public boolean append("+prefix+"Replacement r){");
		incrementIndentLevel();
			for(String sort:sorts){
				final String uSort = MettelJavaNames.firstCharToUpperCase(sort);
				final String TYPE = prefix+uSort;
				appendLine("final Map<"+TYPE+", "+TYPE+"> m"+uSort+" = r."+sort+"Map();");
				appendLine("for("+TYPE+" key:m"+uSort+".keySet()){");
				incrementIndentLevel();
					appendLine("if(!append(key,r.get"+uSort+"(key))){ return false; }");
				decrementIndentLevel();
				appendLine('}');
			}
			appendLine("return true;");
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

		appendLine("public int compareTo("+prefix+"Replacement r){");
		incrementIndentLevel();
			appendLine("if(r == this){ return 0; }");
			for(String sort:sorts){
				final String uSort = MettelJavaNames.firstCharToUpperCase(sort);
				final String TYPE = prefix+uSort;
				appendLine("final Set<"+TYPE+"> keys"+uSort+"0 = "+sort+"Map.keySet();");
				appendLine("final Set<"+TYPE+"> keys"+uSort+"1 = r."+sort+"Map().keySet();");
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
								appendLine("final "+TYPE + " v0 = "+sort+"Map.get(key);");
								appendLine("final "+TYPE + " v1 = r.get"+uSort+"(key);");
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

	}

}
