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
package mettel.core.tableau.strategy;

import mettel.core.tableau.MettelRuleSelectionStrategy;
import mettel.core.tableau.MettelTableauRuleState;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 */
public class MettelSimpleRuleSelectionStrategy implements MettelRuleSelectionStrategy{

		private int index = -1;

		private final MettelTableauRuleState[] ruleStates;

		private final int LENGTH;

		public MettelSimpleRuleSelectionStrategy(MettelTableauRuleState[] ruleStates){
			super();
			this.ruleStates = ruleStates;
			LENGTH = ruleStates.length;
		}

		//private Random random = new Random();

		/* (non-Javadoc)
		 * @see mettel.core.tableau.MettelRuleChoiceStrategy#select(mettel.core.tableau.MettelGeneralTableauRuleState[])
		 */
		@Override
		public MettelTableauRuleState select() {
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

		/* (non-Javadoc)
		 * @see mettel.core.tableau.MettelRuleSelectionStrategy#create(mettel.core.tableau.MettelTableauRuleState[])
		 */
		@Override
		public MettelRuleSelectionStrategy create(
				MettelTableauRuleState[] ruleStates) {
			return new MettelSimpleRuleSelectionStrategy(ruleStates);
		}

	}