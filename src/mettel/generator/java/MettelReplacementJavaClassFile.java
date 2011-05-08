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

import java.util.Map;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelReplacementJavaClassFile extends MettelJavaInterfaceFile {

	private String prefix = "Mettel";

	public MettelReplacementJavaClassFile(String prefix,
			MettelJavaPackage pack, String[] sorts) {
		super(prefix+"Replacement", pack,
				new String[]{"Comparable<"+prefix+"Replacement>"});
		body(sorts);
	}

	void imports(){
		append("import java.util.Iterator;");appendEOL();
	}

	private void body(String[] sorts){
		for(String sort:sorts){
			final String TYPE = prefix+sort;
			appendLine("private Map<"+TYPE+", "+TYPE+"> r"+sort+" = new HashMap<"+TYPE+", "+TYPE+">();");

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
				appendLine(TYPE+" e = r"+sort+".get(e0);");
				appendLine("if(e == null){");
				incrementIndentLevel();
					appendLine("r"+sort+".put(e0,e1);");
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

		appendLine("boolean append("+prefix+"Replacement r){");
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

		Comparable?
	}

}
