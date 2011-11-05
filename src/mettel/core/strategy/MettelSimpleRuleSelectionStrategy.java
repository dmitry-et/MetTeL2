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

import mettel.core.MettelRuleSelectionStrategy;
import mettel.core.MettelTableauRuleState;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 */
public class MettelSimpleRuleSelectionStrategy implements MettelRuleSelectionStrategy{

		private int index = -1;
		//private Random random = new Random();

		/* (non-Javadoc)
		 * @see mettel.core.MettelRuleChoiceStrategy#select(mettel.core.MettelGeneralTableauRuleState[])
		 */
		@Override
		public MettelTableauRuleState select(
				MettelTableauRuleState[] ruleStates) {

//			Arrays.sort(ruleStates, this);
//			if(!ruleStates[0].isApplicable()){
//System.out.println("None is applicable.");
//				return null;
//			}

			final int LENGTH = ruleStates.length;
//			final int SUM = LENGTH*(LENGTH-1) / 2;

//			final int r = random.nextInt( SUM ) ;

/*			for(int i = 0; i < LENGTH; i++){
				if(r < i*(i+1)/2){
					final MettelTableauRuleState rs = ruleStates[i];
					if(rs.isApplicable()){
						return ruleStates[i];
					}else{
						for(int j = 0; j < LENGTH; j++){
							final MettelTableauRuleState rs0 = ruleStates[j];
							if(rs0.isApplicable()){
								return ruleStates[j];
							}
						}
						return null;
					}
				}
			}

			throw new RuntimeException("Never reach here.");
*/
			index++;
			if(index >= LENGTH) index = 0;
			int i = index;
			do{
				final MettelTableauRuleState rs = ruleStates[i];
				if(rs.isApplicable()){
					index = i;
					return rs;
				}
				i++;
				if(i >= LENGTH) i = 0;
			}while(i != index);
			return null;

		}

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 * Note: this comparator imposes orderings that are inconsistent with equals.
		 */
		@Override
		public int compare(MettelTableauRuleState o1, MettelTableauRuleState o2) {
			return 0;
		}

	}