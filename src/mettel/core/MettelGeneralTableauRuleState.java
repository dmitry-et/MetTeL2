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

import java.util.ArrayList;
import java.util.Queue;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelGeneralTableauRuleState {

    	Queue<MettelAnnotatedExpression> expressionsQueue = null;

    	MettelAnnotatedExpression e = null;

    	ArrayList<MettelSubstitution>[] subs = null;

    	MettelGeneralTableauRule rule = null;

    	MettelGeneralTableauRuleState(MettelGeneralTableauRule rule){
    		super();
    		this.rule = rule;
    		final int SIZE = rule.arity();
    		this.subs = new ArrayList[SIZE];
    		for(int i = 0; i < SIZE; i++){
    			subs[i] = new ArrayList<MettelSubstitution>();
    		}
    	}

}
