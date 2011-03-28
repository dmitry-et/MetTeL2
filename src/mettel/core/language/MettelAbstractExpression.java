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

    private static int expressionCounter = 0;

    private int id = 0;

    //TODO implement the default constructor
/*    MettelAbstractExpression() {
        this(MettelObjectFactory.DEFAULT);
    }
*/
    @SuppressWarnings("unused")
	private MettelAbstractExpression(){}

    MettelAbstractExpression(MettelObjectFactory factory) {
        super();
        this.factory = factory;
        id = expressionCounter++;
    }

    public int id() {
        return id;
    }

    MettelObjectFactory factory = null;

    public MettelObjectFactory factory() {
        return factory;
    }

    volatile String string = null;

    private volatile int hash = 0;

    //TODO rewrite hashcode
    public int hashCode() {
        if (hash == 0) {
            hash = toString().hashCode();
        }
        return hash;
    }

    //TODO rewrite equals
    public boolean equals(Object obj) {
        return (obj != null)
                && (getClass() == obj.getClass())
                && (toString().equals(obj.toString()));
    }

    public int compareTo(MettelExpression e) {
        return (id - e.id());
    }

}
