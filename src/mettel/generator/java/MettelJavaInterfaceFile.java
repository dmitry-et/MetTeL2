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
public class MettelJavaInterfaceFile extends MettelJavaFile {

	/**
	 * @param fileName
	 * @param pack
	 */
	public MettelJavaInterfaceFile(String fileName, MettelJavaPackage pack, String[] interfaces) {
		super(fileName, pack);
		declaration(interfaces);
	}

	void declaration(String[] interfaces){
		append("public interface ");
		append(fileName);
		if(interfaces != null){
			final int SIZE = interfaces.length;
			if(SIZE > 0){
				append(" extends ");
				append(interfaces[0]);
				for(int i = 1; i < SIZE; i++){
					append(',');append(' ');
					append(interfaces[i]);
				}
			}
		}
		opening();
	}

}
