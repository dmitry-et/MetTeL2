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

import mettel.util.MettelIndentedStringBuilder;
import mettel.util.MettelJavaNames;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelObjectFactoryClassFile extends MettelJavaClassFile {

	private String name = null;
	/**
	 * @param name
	 * @param pack
	 */
	public MettelObjectFactoryClassFile(String name, MettelJavaPackage pack) {
		//if(name == null) throw MettelGeneratorRuntimeException("Name is null");
		super(MettelJavaNames.firstCharToUpperCase(name) + "DefaultObjectFactory", pack, null,
			  new String[]{MettelJavaNames.firstCharToUpperCase(name) + "ObjectFactory"});
		this.name = MettelJavaNames.firstCharToUpperCase(name);
	}


	public void addCreateMethod(String connective, String sort, String[] children, String[] cSorts){
		final String TYPE = MettelJavaNames.firstCharToUpperCase(connective) +
							MettelJavaNames.firstCharToUpperCase(sort);
		final int SIZE = children.length;
		String[] types = new String[SIZE];
		for(int i = 0; i < SIZE; i++){
			types[i] = MettelJavaNames.firstCharToUpperCase(children[i]) +
					   MettelJavaNames.firstCharToUpperCase(cSorts[i]);
		}
		addCreateMethod(TYPE,types);
	}


	public void addCreateMethod(String type, String[] types){
		final String TYPE = name + type;

		append("Map");append('<');append("String");append(',');append(' ');append(TYPE);append('>');append(' ');append(type);
		append(' ');append('=');append(' ');
		append("new TreeMap");append('<');append("String");append(',');append(' ');append(TYPE);append('>');
		append('(');append(')');append(';');

		appendEOL();

		append("public");
		append(' ');
		append(TYPE);
		//append(type);
		append(' ');
		append("create");
		append(type);
		append('(');
		final int SIZE = types.length;
		if(SIZE > 0){
			append(name);
			append(types[0]);
			append(' ');
			append("param"+0);
			for(int i = 1; i < SIZE; i++){
				append(',');append(' ');
				append(name);
				append(types[i]);
				append(' ');
				append("param"+i);
			}
		}
		append(')');append('{');
		appendEOL();

		MettelIndentedStringBuilder ib = new MettelIndentedStringBuilder(this);

		ib.indent();

		ib.append("String key = ");
		ib.append(TYPE);
		//append(type);
		ib.append('.');
        ib.append("toString");
        ib.append('(');
        if(SIZE > 0){
        	ib.append("param"+0);
        	for(int i = 1; i < SIZE; i++){
        		ib.append(',');
        		ib.append(' ');
        		ib.append("param"+i);
        	}
        }
        ib.append(')');ib.append(';');
        ib.appendEOL();

        ib.indent();

        ib.append(TYPE);
        ib.append(' ');
        ib.append('e');
        ib.append(' ');ib.append('=');ib.append(' ');
        ib.append(type);
        ib.append('.');ib.append("get");ib.append('(');ib.append("key");ib.append(')');ib.append(';');
        ib.appendEOL();

        ib.indent();

        ib.append("if(e == null)");ib.append('{');ib.appendEOL();

        	MettelIndentedStringBuilder ibb = new MettelIndentedStringBuilder(ib);
            ibb.indent();
        	ibb.append("e = new ");
        	ibb.append(TYPE);
        	ibb.append('(');ibb.append("this");
        		for(int i = 0; i < SIZE; i++){
        			ibb.append(',');ibb.append(' ');
        			ibb.append("param"+i);
        		}
        	ibb.append(')');
        	ibb.appendEOL();
        	ibb.indent();
        	ibb.append(type);
            ibb.append('.');ibb.append("put");ibb.append('(');
            	ibb.append("key");ibb.append(',');ibb.append(' ');ibb.append('e');
            ibb.append(')');ibb.append(';');
            ibb.appendEOL();

        ib.indent();
        ib.append('}');
        ib.appendEOL();
        ib.indent();
        ib.append("return e;");
        ib.appendEOL();

        append('}');
		appendEOL();
	}

}
