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
package mettel.generator.antlr;

import mettel.util.MettelIndentedStringBuilder;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
abstract class MettelANTLRExpression {

	private MettelANTLRJavaBlock blockBefore = null;
	private MettelANTLRJavaBlock blockAfter = null;

//	abstract void toStringBuilder(StringBuilder b);

	void appendJavaToPrefix(CharSequence csq){
		blockBefore.append(csq);
	}

	public void appendJavaToPostfix(CharSequence csq){
		if(blockAfter == null) blockAfter = new MettelANTLRJavaBlock();
		blockAfter.append(csq);
	}

/*	void prefixOutput(MettelIndentedStringBuilder ib){
		if(blockBefore != null){
			ib.appendLine('(');
		    blockBefore.toStringBuilder(ib);
		}else{
			ib.indent();
			ib.append('(');
		}
	}

	void postfixOutput(MettelIndentedStringBuilder ib){
		if(blockAfter != null){
			blockAfter.toStringBuilder(ib);
			ib.appendLine(')');
		}else{
			ib.append(')');
			ib.appendEOL();
		}
	}
*/

	void toStringBuilder(MettelIndentedStringBuilder ib){
		if(blockBefore != null){
			ib.appendLine('(');
		    blockBefore.toStringBuilder(ib);
		}else{
			ib.append('(');
		}
		toStringBuilder0(ib);
		if(blockAfter != null){
			ib.appendEOL();
			blockAfter.toStringBuilder(ib);
			ib.appendLine(')');
		}else{
			ib.append(')');
		}
	}

	abstract void toStringBuilder0(MettelIndentedStringBuilder ib);

}
