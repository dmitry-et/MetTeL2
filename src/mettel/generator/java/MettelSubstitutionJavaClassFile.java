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
public class MettelSubstitutionJavaClassFile extends MettelJavaClassFile {

	private String prefix = "Mettel";

	public MettelSubstitutionJavaClassFile(String prefix,
			MettelJavaPackage pack, String[] sorts) {
		super(prefix+"TreeSubstitution", pack, "public", prefix+"TreeReplacement",
				new String[]{"Comparable<"+prefix+"Substitution>"});
		body(sorts);
	}

	private void body(String[] sorts){
		for(String sort:sorts){
			final String TYPE = prefix+sort;

			appendLine("public boolean append("+TYPE+"e0, "+TYPE+"e1){");
			incrementIndentLevel();
				appendLine("if(e0 instanceof "+TYPE+"Variable){");
				incrementIndentLevel();
					appendLine("return super.append(e0,e1);");
				decrementIndentLevel();
				appendLine("}else{ return false; }");
			decrementIndentLevel();
			appendLine('}');
			appendEOL();
		}

		appendLine("public int compareTo("+prefix+"Replacement r){");
		incrementIndentLevel();
			appendLine("if(!(r instanceof "+prefix+"Substitution)){ return 1; };");//throw an exception?
			appendLine("return super.compareTo(r);");
		decrementIndentLevel();
		appendLine('}');
		appendEOL();

		appendLine("public boolean equals(Object o){");
		incrementIndentLevel();
			appendLine("if(o==this){return true;}");
			appendLine("if(!(o instanceof "+prefix+"Substitution){return false;}");
			appendLine("final "+prefix+"Substitution r = ("+prefix+"Substitution)o;");
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
