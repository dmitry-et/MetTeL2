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
public class MettelJavaClassFile extends MettelJavaFile {

	/**
	 * @param fileName
	 * @param pack
	 */
	public MettelJavaClassFile(String fileName, MettelJavaPackage pack, String superClass, String[] interfaces) {
		super(fileName, pack);
		declaration(superClass, interfaces);
	}

	void declaration(String superClass, String[] interfaces){
		append("public class ");
		append(fileName);
		if(superClass != null && !"".equals(superClass)){
			append(" extends ");
			append(superClass);
		}
		final int SIZE =  interfaces.length;
		if(SIZE > 0){
			append(" implements ");
			append(interfaces[0]);
			for(int i = 1; i < SIZE; i++){
				append(',');append(' ');
				append(interfaces[i]);
			}
		}
		opening();
	}
}
