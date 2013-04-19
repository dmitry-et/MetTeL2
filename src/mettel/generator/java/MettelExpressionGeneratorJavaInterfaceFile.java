/**
 * MetTeL is a tableau prover generator.
 * Copyright (C) 2009-2011 Dmitry Tishkovsky
 *
 * This file is part of MetTeL.
 *
 * MetTeL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version with the additional term
 * that all the references to the MetTeL original author,
 * Dmitry Tishkovsky, must be retained.
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

import mettel.util.MettelJavaNames;

public class MettelExpressionGeneratorJavaInterfaceFile extends MettelJavaInterfaceFile {

	private String prefix = "Mettel";
	private MettelJavaPackage langPack = null;

	public MettelExpressionGeneratorJavaInterfaceFile(String prefix, MettelJavaPackage pack, MettelJavaPackage langPack) {
		super(prefix+"ExpressionGenerator", pack, null);
		this.prefix = prefix;
		this.langPack = langPack;
	}

	protected void imports(){
		headings.appendEOL();
	}

	public void addMethod(String sort){
		final String TYPE = prefix + MettelJavaNames.firstCharToUpperCase(sort);
		headings.appendLine("import " + langPack.path() + '.' + TYPE + ';');

		appendLine(TYPE + ' ' + sort + "();");
		appendEOL();
	}
}
