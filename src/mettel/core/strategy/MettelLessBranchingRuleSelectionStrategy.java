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

import java.util.Random;

import mettel.core.MettelRuleSelectionStrategy;
import mettel.core.MettelTableauRuleState;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelLessBranchingRuleSelectionStrategy implements
		MettelRuleSelectionStrategy {

	private int counter = 0;

	private int randomJump = 0;

	private final Random random = new Random();

	/**
	 *
	 */
	public MettelLessBranchingRuleSelectionStrategy(int randomJump) {
		super();
		this.randomJump = randomJump;
	}

	/* (non-Javadoc)
	 * @see mettel.core.MettelRuleSelectionStrategy#select(mettel.core.MettelTableauRuleState[])
	 */
	@Override
	public MettelTableauRuleState select(MettelTableauRuleState[] ruleStates) {
		if(counter > randomJump){
			counter = 0;
			return ruleStates[random.nextInt(ruleStates.length)];
		}

		MettelTableauRuleState result = null;
		int branchingFactor = -1;
		for(MettelTableauRuleState state:ruleStates){
			if(state.isApplicable()){
				if(branchingFactor == -1){
					result = state;
					branchingFactor = state.branchingFactor();
				}else{
					final int bf = state.branchingFactor();
					if(bf < branchingFactor){
						result = state;
						branchingFactor = bf;
					}
				}
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(MettelTableauRuleState o1, MettelTableauRuleState o2) {
		// TODO Auto-generated method stub
		return 0;
	}

}
