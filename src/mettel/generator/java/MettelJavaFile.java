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

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelJavaFile implements Appendable{

	private String fileName = null;

	private MettelJavaPackage pack = null;

	@SuppressWarnings("unused")
	private MettelJavaFile(){}

	/**
	 *
	 */
	MettelJavaFile(String fileName, MettelJavaPackage pack) {
		super();
		this.fileName = fileName;
		this.pack = pack;
	}

	private StringBuilder content = new StringBuilder();

	/* (non-Javadoc)
	 * @see java.lang.Appendable#append(java.lang.CharSequence)
	 */
	@Override
	public Appendable append(CharSequence csq){
		return content.append(csq);
	}

	/* (non-Javadoc)
	 * @see java.lang.Appendable#append(java.lang.CharSequence, int, int)
	 */
	@Override
	public Appendable append(CharSequence csq, int start, int end){
		return content.append(csq, start, end);
	}

	/* (non-Javadoc)
	 * @see java.lang.Appendable#append(char)
	 */
	@Override
	public Appendable append(char c){
		return content.append(c);
	}

	public void flush(String outputPath) throws IOException {
		PrintWriter w = new PrintWriter(
				new FileWriter(outputPath +
						MettelJavaNames.systemPath(pack != null ? pack.path() + fileName : fileName)));
		w.print(content.toString());
		w.close();
	}

}
