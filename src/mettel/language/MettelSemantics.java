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

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import mettel.fo.MettelFOFormula;
import mettel.fo.MettelFOLexer;
import mettel.fo.MettelFOParser;
import mettel.generator.MettelANTLRGrammarGeneratorProperties;
import mettel.generator.java.MettelJavaPackageStructure;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelSemantics implements MettelBlock {

	private String name = null;

	private MettelSyntax syntax = null;

	private List<MettelSemantics> parents = null;

	public String name(){
		return name;
	}

	public MettelSyntax syntax(){
		return syntax;
	}

	public List<MettelSemantics> parents(){
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
	private MettelSemantics(){};

	MettelSemantics(String name, MettelSyntax syntax) {
		this(name, syntax, null);
	}

	MettelSemantics(String name, MettelSyntax syntax, List<MettelSemantics> parents) {
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

	private ArrayList<MettelFOFormula> formulae = new ArrayList<MettelFOFormula>();
	
	void process(MettelJavaPackageStructure pStructure, MettelANTLRGrammarGeneratorProperties properties){
		CommonTokenStream tokens = new CommonTokenStream();
		MettelFOParser parser = new MettelFOParser(tokens, pStructure.parser(syntax.name()));
		tokens.setTokenSource(new MettelFOLexer(new ANTLRStringStream(content)));
		
		try {
			parser.formulae(formulae);
			System.out.println(formulae);
		} catch (RecognitionException e) {
			e.printStackTrace();
		}

	}

	void init(MettelJavaPackageStructure pStructure, MettelANTLRGrammarGeneratorProperties properties){
//TODO: fill in
	}
}