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
package ITL.tableau.ITL;

import java.util.Collection;
import java.util.TreeSet;

import ITL.language.ITL.ITLExpression;
import ITL.language.ITL.ITLLessFormula;
import ITL.language.ITL.ITLPoint;
import ITL.language.ITL.ITLProblemAnalyzer;
import mettel.core.tableau.MettelAnnotatedExpression;
import mettel.core.tableau.MettelExpression;
import mettel.core.tableau.MettelTableauState;
import mettel.core.tableau.MettelTableauStateAcceptor;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class ITLSmallModelAcceptor implements
		MettelTableauStateAcceptor {

	private long size = Long.MAX_VALUE;

	public ITLSmallModelAcceptor(int size){
		super();
		this.size = size;
	}
	
	public ITLSmallModelAcceptor(Collection<ITLExpression> col){
		super();
		
		ITLProblemAnalyzer a = new ITLProblemAnalyzer(col);
		int m = a.boxFormulaOccurences() + a.diamondFormulaOccurences();
		
System.out.println("m: " + m);

		this.size = (long)Math.pow(2., (double)m)*m + 1;
		
System.out.println("Model size: " + this.size);

	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauStateAcceptor#accept(mettel.core.tableau.MettelTableauState)
	 */
	@Override
	public boolean accept(MettelTableauState state) {
		if(!state.isSatisfiable()){
//System.out.println("Rejecting unsatisfiable state " + state);		
			return false;
		}
		
		TreeSet<ITLPoint> points = new TreeSet<ITLPoint>();
		for(MettelAnnotatedExpression e:state.expressions()){
			MettelExpression exp = e.expression();
			if(exp instanceof ITLLessFormula){
				ITLLessFormula f = (ITLLessFormula)exp;
				points.add(f.e0()); 
				points.add(f.e1());
				if(points.size() > size){
//System.out.println("Rejecting unsatisfiable state " + state + " with size greater than " + size);		
						return false;
				}
			}
		}
//System.out.println("Accepting state " + state);		
		return true;
	}

}
