//This file needs fixing!!!
package mettel.fo;

import mettel.core.tableau.MettelExpression;

public class MettelFOExistentialFormula extends MettelFOAbstractExpression implements MettelFOFormula{

    static final int PRIORITY = 3;
    private final int LENGTH;
    protected MettelFOFormula e0 = null;
    
    protected MettelFOIndividualVariable[] variables = null;

    public MettelFOExistentialFormula(MettelFOIndividualVariable[] vars, MettelFOFormula e0, MettelFOObjectFactory f){
        super(f);
        this.e0 = e0;
        final int varsize = vars.length; 
        variables = new MettelFOIndividualVariable[varsize];
        System.arraycopy(vars, 0, variables, 0, varsize);
        LENGTH = e0.length() + varsize;        
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
        if(!(e instanceof MettelFOExistentialFormula)){ return false;}
        final MettelFOExistentialFormula ee = (MettelFOExistentialFormula)e;
        boolean result = e0.match(ee.e0, s);
        for(MettelFOIndividualVariable v:variables){
        	
        }
        return true;//TODO rewrite method
    }

    public MettelFOExpression substitute(MettelFOSubstitution s){
    	//TODO fix
    	return null;
        //return factory.createExistentialFormula((MettelFOFormula)e0.substitute(s));
    }

    public MettelFOExpression rewrite(MettelFOReplacement s){
        MettelFOExpression e = s.getFormula(this);
        if(e != null){ return e; }
        final MettelFOExistentialFormula ee = null;//TODO fix: factory.createExistentialFormula((MettelFOFormula)e0.rewrite(s));
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
        if(!(o instanceof MettelFOExistentialFormula)){ return false; }
        MettelFOExistentialFormula e = (MettelFOExistentialFormula) o;
        return (e0.equals(e.e0));
    }

    public int compareTo(MettelExpression e){
        if(e == this){ return 0; }
        if(!(e instanceof MettelFOFormula)){ return SORTID - ((MettelFOAbstractExpression)e).sortId(); }
        if(e instanceof MettelFOFormulaVariable){ return 1; }
        if(!(e instanceof MettelFOExistentialFormula)){ return PRIORITY - ((MettelFOAbstractExpression)e).priority(); }
        MettelFOExistentialFormula ee = (MettelFOExistentialFormula) e;
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
