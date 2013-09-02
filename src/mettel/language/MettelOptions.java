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

import java.io.IOException;
import java.io.StringReader;

import mettel.MettelRuntimeException;
import mettel.generator.MettelANTLRGrammarGeneratorProperties;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelOptions implements MettelBlock {

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
	private MettelOptions(){};

	MettelOptions(String content) {
		super();
		this.content = content;
	}
	
	public MettelANTLRGrammarGeneratorProperties process(MettelANTLRGrammarGeneratorProperties properties){
		
		MettelANTLRGrammarGeneratorProperties result = new MettelANTLRGrammarGeneratorProperties(properties);
		try{
			result.init(new StringReader(content));
		}catch(IOException e){
			throw new MettelRuntimeException(e, "Options cannot be read.");
		}
		return result;

	}

	public void toBuffer(StringBuilder buf){
		buf.append("options {");
		buf.append(content);
		buf.append("}");
	}
}