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
public class MettelLPOComparatorJavaClassFile extends MettelJavaClassFile {

	private String prefix = "Mettel";

	public MettelLPOComparatorJavaClassFile(String prefix,
			MettelJavaPackage pack) {
		super(prefix+"LPOComparator", pack, "public", null,
				new String[]{"Comparator<"+prefix+"AbstractExpression>"});
		this.prefix = prefix;
		body();
	}

	protected void imports(){
		headings.appendLine("import java.util.Comparator;");
		headings.appendEOL();
	}

	private void body(){
		
		String TYPE = prefix+"AbstractExpression";
		
		appendLine("public int compare("+TYPE+" e0, "+TYPE+" e1){");
		incrementIndentLevel();
			appendLine("if(e0 == e1){ return 0; }");
			
			appendLine("final int CMP_SORTS = e0.sortId() - e1.sortId();");
			appendLine("if(CMP_SORTS != 0) {return CMP_SORTS;}");
			
			appendLine("if(e0 instanceof "+prefix+"AbstractVariable){");
			incrementIndentLevel();
				appendLine("if(e1 instanceof "+prefix+"AbstractVariable){ return e0.id() - e1.id(); }");
				appendLine("return -1;");
			decrementIndentLevel();
			appendLine("}else{");
			incrementIndentLevel();
				appendLine("if(e1 instanceof "+prefix+"AbstractVariable){ return 1; }");
			decrementIndentLevel();
			appendLine('}');
			
			appendLine("final int PRE0 = e0.compareArgumentsTo(e1,this);");
			appendLine("if(PRE0 != 0){return PRE0;}");
			
			appendLine("if(e0.priority() < e1.priority()){return -1;}");
			
			appendLine("final int PRE1 = e1.compareArgumentsTo(e0,this);");
			appendLine("if(PRE1 != 0){return -PRE1;}");
			
			appendLine("if(e1.priority() < e0.priority()){return 1;}");
			
			appendLine("return e0.compareArguments(e1,this);");
		decrementIndentLevel();
		appendLine('}');
		appendEOL();
	}	

}
