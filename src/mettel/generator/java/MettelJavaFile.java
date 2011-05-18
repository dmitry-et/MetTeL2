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

import java.io.IOException;

import mettel.util.MettelIndentedStringBuilder;
import mettel.util.MettelStrings;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelJavaFile extends MettelFile {

	/**
	 * @param fileName
	 * @param pack
	 */
	public MettelJavaFile(String fileName, MettelJavaPackage pack) {
		super(fileName, "java", pack);
		headings.append(MettelStrings.PACKAGE_STRING);
		headings.append(' ');
		headings.append(pack.path());
		headings.append(';');
		headings.appendEOL();
		headings.appendEOL();
	}

	protected MettelIndentedStringBuilder headings = new MettelIndentedStringBuilder(new StringBuilder());

	protected void imports(){

	}

	void opening(){
		append('{');appendEOL();appendEOL();
		incrementIndentLevel();
	}

	private boolean closed = false;

	void  closing(){
		if(! closed){
			decrementIndentLevel();
			append('}');appendEOL();
			closed = true;
		}
	}

/*	private boolean initialised = false;

	private void init(){
		if(!initialised){
			imports();
			initialised = true;
		}
	}

	public StringBuilder append(CharSequence csq, int start, int end) {
		init();
		return b.append(csq,start,end);
	}

	public StringBuilder append(char c) {
		init();
		return b.append(c);
	}
*/
	public void flush(String outputPath) throws IOException {
		imports();
		this.b.insert(0, headings);
		closing();
		super.flush(outputPath);
	}
}
