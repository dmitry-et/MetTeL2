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
package mettel.core.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;

import mettel.core.MettelRuleSelectionStrategy;
import mettel.core.MettelTableauRuleState;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelSimpleRandomRuleSelectionStrategy extends
		MettelSimpleRuleSelectionStrategy implements
		MettelRuleSelectionStrategy {

	/**
	 *
	 */
	public MettelSimpleRandomRuleSelectionStrategy() {
		super();
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(MettelTableauRuleState o1, MettelTableauRuleState o2) {
		final boolean APP1 = o1.isApplicable(), APP2 = o2.isApplicable();
		if(APP1 && !APP2) return -1;
		if(APP2 && !APP1) return 1;

		if(APP1 && APP2){
			final boolean T1 = o1.isTerminal(), T2 = o2.isTerminal();
			if(T1 && !T2) return -1;
			if(T2 && !T1) return 1;

			if(!T1 && !T2){
				final int BF1 = o1.branchingFactor(), BF2 = o2.branchingFactor();
				if(BF1 < BF2) return -1;
				if(BF2 < BF1) return 1;
			}

			final int A1 = o1.arity(), A2 = o2.arity();
			if(A1 < A2) return -1;
			if(A2 < A1) return 1;
		}
		return 0;
	}

	private Random random = new Random();

	/* (non-Javadoc)
	 * @see mettel.core.MettelRuleChoiceStrategy#select(mettel.core.MettelGeneralTableauRuleState[])
	 */
	@Override
	public final MettelTableauRuleState select(
			MettelTableauRuleState[] ruleStates) {

		final TreeSet<MettelTableauRuleState> rstates = new TreeSet<MettelTableauRuleState>(this);
		final int LENGTH0 = ruleStates.length;
		for(int i = 0; i < LENGTH0; i++){
			final MettelTableauRuleState rs = ruleStates[i];
			if(rs.isApplicable()) rstates.add(rs);
		}

		if(rstates.isEmpty()) return null;

		final int LENGTH = rstates.size();
		final int SUM = LENGTH*(LENGTH-1) / 2;

		final int r = random.nextInt(SUM) ;

		Iterator<MettelTableauRuleState> i = rstates.iterator();
		int n = 0;
		while(i.hasNext()){
			final MettelTableauRuleState rs = i.next();
			if(r < n*(n+1)/2){
System.out.println("Rule state: "+rs);
				return rs;
			}
			n++;
		}

    	return super.select(ruleStates);
	}
}
