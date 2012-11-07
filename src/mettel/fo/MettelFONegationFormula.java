/**
 * This file is generated by MetTeL v2
 * which is designed and implemented
 * by Dmitry Tishkovsky.
 */
package mettel.fo;

import mettel.core.tableau.MettelExpression;

public class MettelFONegationFormula extends MettelFOAbstractExpression implements MettelFOFormula{

    static final int PRIORITY = 3;
    private final int LENGTH;
    protected MettelFOFormula e0 = null;

    public MettelFONegationFormula(MettelFOFormula e0, MettelFOObjectFactory f){
        super(f);
        int l = 1;
        this.e0 = e0;
        l += e0.length();
        LENGTH = l;
    }

    int sortId(){ return SORTID; }

    int priority(){ return PRIORITY; }

    public int length(){ return LENGTH; }

    public MettelFOSubstitution match(MettelFOExpression e){
        MettelFOSubstitution s = new MettelFOTreeSubstitution();
        if(match(e,s)){
            return factory.getSubstitution(s);
        }else{ return null; }
    }

    public boolean match(MettelFOExpression e, MettelFOSubstitution s){
        if(!(e instanceof MettelFONegationFormula)){ return false;}
        final MettelFONegationFormula ee = (MettelFONegationFormula)e;
        return e0.match(ee.e0, s);
    }

    public MettelFOExpression substitute(MettelFOSubstitution s){
        return factory.createNegationFormula((MettelFOFormula)e0.substitute(s));
    }

    public MettelFOExpression rewrite(MettelFOReplacement s){
        MettelFOExpression e = s.getFormula(this);
        if(e != null){ return e; }
        final MettelFONegationFormula ee = factory.createNegationFormula((MettelFOFormula)e0.rewrite(s));
        e = s.getFormula(ee);
        if(e == null){ return ee; }else{ return e; }
    }

    private int hashCode = 0;
    public int hashCode(){
        if(hashCode == 0){
            hashCode = 31*SORTID + PRIORITY;
            hashCode = 31*hashCode + e0.hashCode();
        }
        return hashCode;
    }

    public boolean equals(Object o){
        if(o == this){ return true; }
        if(!(o instanceof MettelFONegationFormula)){ return false; }
        MettelFONegationFormula e = (MettelFONegationFormula) o;
        return (e0.equals(e.e0));
    }

    public int compareTo(MettelExpression e){
        if(e == this){ return 0; }
        if(!(e instanceof MettelFOFormula)){ return SORTID - ((MettelFOAbstractExpression)e).sortId(); }
        if(e instanceof MettelFOFormulaVariable){ return 1; }
        if(!(e instanceof MettelFONegationFormula)){ return PRIORITY - ((MettelFOAbstractExpression)e).priority(); }
        MettelFONegationFormula ee = (MettelFONegationFormula) e;
        int result = 0;
        result = e0.compareTo(ee.e0);
        if(result != 0){ return result; }
        return 0;
    }

    private String str = null;
    public String toString(){
        if(str == null){
            StringBuilder b = new StringBuilder();
            b.append('(');
            b.append(' ');
            b.append('~');
            b.append(' ');
            b.append(e0);
            b.append(' ');
            b.append(')');
            str = b.toString();
        }
        return str;
    }
}
