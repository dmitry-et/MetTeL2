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
package mettel.language;

import static mettel.util.MettelStrings.LINE_SEPARATOR;

import java.util.List;

import mettel.generator.MettelANTLRGrammarGeneratorProperties;
import mettel.generator.java.MettelJavaPackageStructure;
import mettel.util.MettelJavaNames;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelTableau implements MettelBlock {

	private String name = null;

	private MettelSyntax syntax = null;

	private List<MettelTableau> parents = null;

	public String name(){
		return name;
	}

	public MettelSyntax syntax(){
		return syntax;
	}

	public List<MettelTableau> parents(){
		return parents;
	}

	/*TODO implement hierarchical extension mechanism
     */

	private String content = null;

	public String content(){
		return content;
	}

	void setContent(String content){
		this.content = content;
	}

	/**
	 *
	 */
    @SuppressWarnings("unused")
	private MettelTableau(){};

	MettelTableau(String name, MettelSyntax syntax) {
		this(name, syntax, null);
	}

	MettelTableau(String name, MettelSyntax syntax, List<MettelTableau> parents) {
		super();
		this.name = name;
		this.syntax = syntax;
		this.parents = parents;
	}

	/*public MettelTableau unravel(){
		if(parents == null) return this;
		MettelTableau tab = new MettelTableau(name);
		for(MettelTableau s:parents){
			MettelTableau s0 = s.unravel();
			for(MettelSort sort:s0.sorts){
				if(! syn.sortExists(sort.name())){
					syn.sorts.add(sort);
				}
			}
			for(MettelSort sort:s0.bnfs.keySet()){
				List<MettelBNFStatement> statements = s0.getBNFs(sort);
				syn.bnfs.put(sort,new ArrayList<MettelBNFStatement>(statements));
			}
		}
		return syn;
	}*/


	//private MettelANTLRGrammarGeneratorProperties properties = null;
	//private MettelJavaPackageStructure pStructure = null;

	void process(MettelJavaPackageStructure pStructure, MettelANTLRGrammarGeneratorProperties properties){
		//this.pStructure = pStructure;
		//this.properties = properties;

		//unravel();

		final String prefix = MettelJavaNames.firstCharToUpperCase(name);
		pStructure.appendStandardTableauClasses(name, syntax.name(), prefix, properties.nameSeparator, properties.searchStrategy);
		pStructure.appendStandardTableauClasses(name, prefix, syntax.sortStrings, properties.branchBound, properties.searchStrategy);
		pStructure.appendTableauFile(name,content);


	}

/*	void init(MettelJavaPackageStructure pStructure, MettelANTLRGrammarGeneratorProperties properties){
		final String prefix = MettelJavaNames.firstCharToUpperCase(name);
		pStructure.appendStandardTableauClasses(name, syntax.name(), prefix, properties.nameSeparator);
	}*/
	
	/**
	 * @param buf
	 */
	public void toBuffer(StringBuilder buf) {
		buf.append("tableau ");
		buf.append(name);
		buf.append(" in syntax ");
		buf.append(syntax.name());
		if(parents != null){
			final int SIZE = parents.size();
			if(SIZE >0){
				buf.append(" extends ");
				buf.append(parents.get(0).name);
				for(int i = 1; i < SIZE; i++){
					buf.append(", ");
					buf.append(parents.get(i).name);
				}
			}
		}
		if(options != null){
			buf.append(LINE_SEPARATOR);
			options.toBuffer(buf);
			buf.append(LINE_SEPARATOR);
		}
		buf.append('{');
		buf.append(content);
		buf.append("}");
	}
	
	MettelOptions options = null;
	
	void setOptions(MettelOptions options){
		this.options = options;
	}
	
	MettelOptions options(){
		return options;
	}

}