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
		append(MettelStrings.PACKAGE_STRING);
		append(' ');
		append(pack.path());
		append(';');
		appendEOL();

		imports();
	}

	void imports(){

	}

	void opening(){
		append('{');appendEOL();
		this.incrementIndentLevel();
	}

	private boolean closed = false;

	void  closing(){
		if(! closed){
			append('}');appendEOL();
			closed = true;
		}
	}

	public void flush(String outputPath) throws IOException {
		closing();
		super.flush(outputPath);
	}
}
