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

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import mettel.util.MettelJavaNames;
import mettel.util.MettelIndentedStringBuilder;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelFile extends MettelIndentedStringBuilder{

	protected String fileName = null;
	protected String extension = null;

	private MettelJavaPackage pack = null;

	//@SuppressWarnings("unused")
	//private MettelJavaFile(){}

	/**
	 *
	 */
	MettelFile(String fileName, String extension, MettelJavaPackage pack) {
		super(new StringBuilder(), "    ");
		this.fileName = fileName;
		this.extension = extension;
		this.pack = pack;
	}

	//private StringBuilder content = null; //new StringBuilder();

	/* (non-Javadoc)
	 * @see java.lang.Appendable#append(java.lang.CharSequence)
	 */
/*	@Override
	public Appendable append(CharSequence csq){
		return append(csq);
	}
*/

	/* (non-Javadoc)
	 * @see java.lang.Appendable#append(java.lang.CharSequence, int, int)
	 */
/*	@Override
	public Appendable append(CharSequence csq, int start, int end){
		return append(csq, start, end);
	}
*/
	/* (non-Javadoc)
	 * @see java.lang.Appendable#append(char)
	 */
/*	@Override
	public Appendable append(char c){
		return content.append(c);
	}
*/

	public void flush(String outputPath) throws IOException {
		PrintWriter w = new PrintWriter(
				new FileWriter(
						MettelJavaNames.addSeparator(outputPath) +
						MettelJavaNames.addSeparator(MettelJavaNames.systemPath(pack != null ? pack.path() : "" )) + fileName +
						((extension == null || extension.equals(""))? "": "." + extension)));
		w.print(toString());
		w.close();
	}

}
