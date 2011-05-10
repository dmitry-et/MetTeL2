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
public class MettelReplacementJavaClassFile extends MettelJavaClassFile {

	private String prefix = "Mettel";

	public MettelReplacementJavaClassFile(String prefix,
			MettelJavaPackage pack, String[] sorts) {
		super(prefix+"TreeReplacement", pack, "public", null,
				new String[]{"Comparable<"+prefix+"Replacement>"});
		body(sorts);
	}

	void imports(){
		append("import java.util.Iterator;");appendEOL();
	}

	private void body(String[] sorts){
		for(String sort:sorts){
			final String TYPE = prefix+sort;
			appendLine("private Map<"+TYPE+", "+TYPE+"> r"+sort+" = new TreeMap<"+TYPE+", "+TYPE+">();");

			appendLine("public " + TYPE+" get"+sort+'('+TYPE+"e){");
			incrementIndentLevel();
				appendLine("return r"+sort+".get(e);");
			decrementIndentLevel();
			appendLine('}');
			appendEOL();

			appendLine("public Iterator<"+TYPE+"> "+sort+"Iterator(){");
			incrementIndentLevel();
				appendLine("return r"+sort+".keySet().iterator();");
			decrementIndentLevel();
			appendLine('}');
			appendEOL();


			appendLine("public boolean append("+TYPE+"e0, "+TYPE+"e1){");
			incrementIndentLevel();
				appendLine("if(e0==null || e1==null){return false;}");
				appendLine(TYPE+" e = r"+sort+".get(e0);");
				appendLine("if(e == null){");
				incrementIndentLevel();
					appendLine("if(!e0.equals(e1)){ r"+sort+".put(e0,e1); };");
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
				final String TYPE = prefix+sort;
				appendLine("Iterator<"+TYPE+"> i = r."+sort+"Iterator();");
				appendLine("while(i.hasNext()){");
				incrementIndentLevel();
					appendLine(TYPE+" e = i.next();");
					appendLine("append(e,r.get"+sort+"(e));");
				decrementIndentLevel();
				appendLine('}');
			}
		decrementIndentLevel();
		appendLine('}');
		appendEOL();
		    //MettelReplacement compose(MettelReplacement r);

		appendLine("private int hashCode = 0;");
		appendLine("public int hashCode(){");
		incrementIndentLevel();
			appendLine("hashCode = 1;");
			for(String sort:sorts){
				appendLine("hashCode = 31*hashCode + r" +sort +".hashCode();");
			}
			appendLine("return hashCode;");
		decrementIndentLevel();
		appendLine('}');
		appendEOL();

		appendLine("public int compareTo("+prefix+"Replacement r){");
		incrementIndentLevel();
			appendLine("if(o==this){return 0;}");
			for(String sort:sorts){
				final String TYPE = prefix + sort;
				appendLine("final Set<"+TYPE+"> keys0 = r"+sort+".keySet();");
				appendLine("final Set<"+TYPE+"> keys1 = r."+sort+"keySet();");
				appendLine("final TreeSet<"+TYPE+"> keys = new TreeSet<"+TYPE+">(keys0);");//Note: this is a sorted set!
				appendLine("keys.addAll(keys1);");
				appendLine("for("+TYPE +" key:keys){");
				incrementIndentLevel();
					appendLine("if(!key0.contains(key){");
						incrementIndentLevel();
							appendLine("return 1;");
						decrementIndentLevel();
					appendLine("}else{");
						incrementIndentLevel();
						appendLine("if(!key1.contains(key){");
							incrementIndentLevel();
								appendLine("return -1;");
							decrementIndentLevel();
						appendLine("}else{");
							incrementIndentLevel();
								appendLine("final "+TYPE + "v0 = r"+sort+".get(key);");
								appendLine("final "+TYPE + "v1 = r.get"+sort+"(key);");
								appendLine("final int result = v0.compareTo(v1);");
								appendLine("if(result != 0){return result;}");
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
			appendLine("if(o==this){return true;}");
			appendLine("if(!(o instanceof "+prefix+"Replacement){return false;}");
			appendLine("final "+prefix+"Replacement r = ("+prefix+"Replacement)o;");
			for(String sort:sorts){
				appendLine("boolean result = r"+sort+".equals(r.r"+sort+");");
				appendLine("if(!result){return false;}");
			}
			appendLine("return true;");
		decrementIndentLevel();
		appendLine('}');
		appendEOL();

	}

}
