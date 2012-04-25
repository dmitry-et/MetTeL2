/**
 * This file is generated by MetTeL v2
 * which is designed and implemented
 * by Dmitry Tishkovsky.
 */
package mettel.fo;

import mettel.core.MettelExpression;
public interface MettelFOExpression extends MettelExpression{

    int id();
    MettelFOObjectFactory factory();
    MettelFOExpression substitute(MettelFOSubstitution s);
    MettelFOExpression rewrite(MettelFOReplacement r);
    MettelFOSubstitution match(MettelFOExpression e);

    boolean match(MettelFOExpression e, MettelFOSubstitution s);
}