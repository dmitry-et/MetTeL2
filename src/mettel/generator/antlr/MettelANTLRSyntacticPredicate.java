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
package mettel.generator.antlr;

import mettel.util.MettelIndentedStringBuilder;

/**
 * @author Dmitry Tishkovsky
 *
 */
public class MettelANTLRSyntacticPredicate {
	
	private MettelANTLRExpression expression = null;
	
	public void setExpression(MettelANTLRExpression expression){
		this.expression = expression;
	}
	
	public MettelANTLRSyntacticPredicate(){
		super();
	}
	
	public MettelANTLRSyntacticPredicate(MettelANTLRExpression expression){
		super();
		this.expression = expression;
	}

	void toStringBuilder(MettelIndentedStringBuilder b){
		b.append('(');
		expression.toStringBuilder0(b, true);
		b.append(')');
		b.append('=');
		b.append('>');
		b.append(' ');
	}
}
