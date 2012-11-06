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
package mettel.util;

import static mettel.util.MettelStrings.FILE_SEPARATOR;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelJavaNames {

	public static String firstCharToUpperCase(String s){
		return s.substring(0,1).toUpperCase()+s.substring(1);
	}

	public static String firstCharToLowerCase(String s){
		return s.substring(0,1).toLowerCase()+s.substring(1);
	}


	public static String methodName(String prefix, String postfix){
		return firstCharToLowerCase(prefix)+firstCharToUpperCase(postfix);
	}

	public static String className(String prefix, String postfix){
		return firstCharToUpperCase(prefix)+firstCharToUpperCase(postfix);
	}

	public static String packageName(String s){
		return s.toLowerCase();
	}

	public static String systemPath(String javaPath){
		//return javaPath.replaceAll("\\.", FILE_SEPARATOR.replace("\\" ,"\\\\"));
		return javaPath.replace(".", FILE_SEPARATOR);
	}

	public static String javaPath(String javaPath){
		//return javaPath.replaceAll("\\.", FILE_SEPARATOR.replace("\\" ,"\\\\"));
		return javaPath.replace(".", "/");
	}

	public static String quote(String s){
		//return javaPath.replaceAll("\\.", FILE_SEPARATOR.replace("\\" ,"\\\\"));
		return s.replace("\\", "\\\\");
	}

	public static String addSeparator(String path){
		if(path == null || path.equals("")) return "";
		if(path.endsWith(FILE_SEPARATOR)) return path;
		return path + FILE_SEPARATOR;
	}

	public static String getClassName(String path){
		return path.substring(path.lastIndexOf('.')+1);
	}

}
