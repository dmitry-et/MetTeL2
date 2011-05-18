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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import mettel.util.MettelJavaNames;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelJavaPackage {

	private String path = "";

	public String path(){
		return path;
	}

	@SuppressWarnings("unused")
	private MettelJavaPackage(){}
	/**
	 *
	 */
	public MettelJavaPackage(String path) {
		super();
		this.path = path;
	}

	private ArrayList<MettelFile> files = new ArrayList<MettelFile>();

	public MettelFile createFile(String fileName, String extension){
		MettelFile file = new MettelFile(fileName, extension, this);
		files.add(file);
		return file;
	}

	public void add(MettelJavaFile file){
		files.add(file);
	}

	//outputPath includes final separator and conforms path format on the system
	public void flush(String outputPath) throws IOException {
		final String PATH = MettelJavaNames.addSeparator(outputPath) + MettelJavaNames.systemPath(path);
		File dir = new File(PATH);
		if(!dir.exists() && !dir.mkdirs()) throw new IOException("Cannot create directory " + PATH);
		for(MettelFile f: files){
			f.flush(outputPath);
		}
	}


}
