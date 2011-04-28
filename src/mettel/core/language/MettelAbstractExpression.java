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
package mettel.core.language;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
abstract class MettelAbstractExpression
        implements MettelExpression {

    private static volatile int expressionCounter = 0;

    private int id = -1;

    protected MettelAbstractExpression(){
    	super();
    	id = expressionCounter++;
    }

    public int id() {
        return id;
    }


//TODO the commented code must go into a generated class
/*
    MettelAbstractExpression() {
        this(MettelObjectFactory.DEFAULT);
    }

    MettelAbstractExpression(MettelObjectFactory factory) {
        super();
        this.factory = factory;
        id = expressionCounter++;
    }

    MettelObjectFactory factory = null;

    public MettelObjectFactory factory() {
        return factory;
    }
*/

    public int hashCode() {
        return id;
    }

    public int compareTo(MettelExpression e) {
        return (id - e.id());
    }

}