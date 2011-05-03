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
public class MettelReplacementJavaInterfaceFile extends MettelJavaInterfaceFile {

	private String prefix = "Mettel";

	public MettelReplacementJavaInterfaceFile(String prefix,
			MettelJavaPackage pack, String[] sorts) {
		super(prefix+"Replacement", pack,
				new String[]{"Comparable<"+prefix+"Replacement>"});
		body(sorts);
	}

	void imports(){
		append("import java.util.Iterator;");appendEOL();
	}

	private void body(String[] sorts){
		for(String sort:sorts){
			final String TYPE = prefix+sort;
			indent();append(TYPE+" get"+sort+'('+TYPE+"e);");appendEOL();
			indent();append("Iterator<"+TYPE+"> "+sort+"Iterator();");appendEOL();
			indent();append("boolean append("+TYPE+"e0, "+TYPE+"e1);");appendEOL();
		}

		indent();append("boolean append("+prefix+"Replacement r);");appendEOL();
		    //MettelReplacement compose(MettelReplacement r);
	}

}
