/**
 * This file is generated by MetTeL v2
 * which is designed and implemented
 * by Dmitry Tishkovsky.
 */
package mettel.fo;

import mettel.core.tableau.MettelExpression;

public class MettelFOIndividualVariable extends MettelFOAbstractVariable implements MettelFOTerm{

    public MettelFOIndividualVariable(String name, MettelFOObjectFactory factory) {
        super(name, factory);
    }

    int sortId(){ return SORTID; }

    public MettelFOSubstitution match(MettelFOExpression e){
        MettelFOSubstitution s = new MettelFOTreeSubstitution();
        if(match(e,s)){
            return factory.getSubstitution(s);
        }else{ return null; }
    }

    public boolean match(MettelFOExpression e, MettelFOSubstitution s){
        if(!(e instanceof MettelFOFormula)){ return false; }
        return s.append(this,(MettelFOTerm)e);
    }

    public MettelFOExpression rewrite(MettelFOReplacement r) {
        MettelFOTerm e = r.getTerm(this);
        return (e == null) ? this : e;
    }

    public MettelFOExpression substitute(MettelFOSubstitution s) {
        MettelFOTerm e = s.getTerm(this);
        return (e == null) ? this : e;
    }

    private int hashCode = 0;
    public int hashCode(){
        if(hashCode == 0){
            hashCode = 23*SORTID + name.hashCode();
        }
        return hashCode;
    }

    public int compareTo(MettelExpression e){
        if(e instanceof MettelFOIndividualVariable) return name.compareTo(((MettelFOIndividualVariable)e).name());
        if(e instanceof MettelFOFormula) return -1;
        return SORTID - ((MettelFOAbstractExpression)e).sortId();
    }

    public boolean equals(Object o){
        if(o == this){ return true; }
        if(!(o instanceof MettelFOIndividualVariable)){ return false; }
        return name.equals(((MettelFOIndividualVariable)o).name());
    }
    public boolean isEquality(){
        return false;
    }
    public int length(){
        return 1;
    }
}
