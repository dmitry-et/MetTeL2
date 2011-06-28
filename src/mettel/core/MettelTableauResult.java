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
package mettel.core;

import java.util.Set;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision: $ $Date: $
 *
 */
public class MettelTableauResult {
	
		private boolean satisfiable = false;
		private Set<MettelAnnotatedExpression> clashSet = null;
		private Set<MettelAnnotatedExpression> model = null;
		
		@SuppressWarnings("unused")
		private	MettelTableauResult(){}
		
		MettelTableauResult(boolean sat, Set<MettelAnnotatedExpression> cSet, Set<MettelAnnotatedExpression> model){
			super();
			satisfiable = sat;
			clashSet = cSet;
			this.model = model;
		}

		public MettelTableauResult combineClashes(MettelTableauResult[] results){
			for(MettelTableauResult r:results){
				
			}
			return null;
		}
		
}
