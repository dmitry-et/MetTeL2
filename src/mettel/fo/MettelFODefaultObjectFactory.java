/**
 * This file is generated by MetTeL v2
 * which is designed and implemented
 * by Dmitry Tishkovsky.
 */
package mettel.fo;

import java.util.Map;
import java.util.TreeMap;

public class MettelFODefaultObjectFactory implements MettelFOObjectFactory{

    private Map<MettelFOSubstitution, MettelFOSubstitution> subs = new TreeMap<MettelFOSubstitution, MettelFOSubstitution>();

    public MettelFOSubstitution getSubstitution(MettelFOSubstitution s){
        final MettelFOSubstitution old = subs.get(s);
        if(old == null){ 
            subs.put(s,s);
            return s;
        }else{ return old; }
    }

    private Map<MettelFOReplacement, MettelFOReplacement> reps = new TreeMap<MettelFOReplacement, MettelFOReplacement>();

    public MettelFOReplacement getReplacement(MettelFOReplacement r){
        final MettelFOReplacement old = reps.get(r);
        if(old == null){ 
            reps.put(r,r);
            return r;
        }else{ return old; }
    }

    private MettelFOTrueFormula trueFormulaConstant = new MettelFOTrueFormula(this);

    public MettelFOTrueFormula createTrueFormula(){
        return trueFormulaConstant;
    }
    private MettelFOFalseFormula falseFormulaConstant = new MettelFOFalseFormula(this);

    public MettelFOFalseFormula createFalseFormula(){
        return falseFormulaConstant;
    }
    private Map<MettelFONegationFormula, MettelFONegationFormula> negationFormulas = new TreeMap<MettelFONegationFormula, MettelFONegationFormula>();

    public MettelFONegationFormula createNegationFormula(MettelFOFormula e0){
        final MettelFONegationFormula e = new MettelFONegationFormula(e0, this);
        final MettelFONegationFormula old = negationFormulas.get(e);
        if(old == null){ 
            negationFormulas.put(e,e);
            return e;
        }else{ return old; }
    }

    private Map<MettelFOConjunctionFormula, MettelFOConjunctionFormula> conjunctionFormulas = new TreeMap<MettelFOConjunctionFormula, MettelFOConjunctionFormula>();

    public MettelFOConjunctionFormula createConjunctionFormula(MettelFOFormula e0, MettelFOFormula e1){
        final MettelFOConjunctionFormula e = new MettelFOConjunctionFormula(e0, e1, this);
        final MettelFOConjunctionFormula old = conjunctionFormulas.get(e);
        if(old == null){ 
            conjunctionFormulas.put(e,e);
            return e;
        }else{ return old; }
    }

    private Map<MettelFODisjunctionFormula, MettelFODisjunctionFormula> disjunctionFormulas = new TreeMap<MettelFODisjunctionFormula, MettelFODisjunctionFormula>();

    public MettelFODisjunctionFormula createDisjunctionFormula(MettelFOFormula e0, MettelFOFormula e1){
        final MettelFODisjunctionFormula e = new MettelFODisjunctionFormula(e0, e1, this);
        final MettelFODisjunctionFormula old = disjunctionFormulas.get(e);
        if(old == null){ 
            disjunctionFormulas.put(e,e);
            return e;
        }else{ return old; }
    }

    private Map<MettelFOImplicationFormula, MettelFOImplicationFormula> implicationFormulas = new TreeMap<MettelFOImplicationFormula, MettelFOImplicationFormula>();

    public MettelFOImplicationFormula createImplicationFormula(MettelFOFormula e0, MettelFOFormula e1){
        final MettelFOImplicationFormula e = new MettelFOImplicationFormula(e0, e1, this);
        final MettelFOImplicationFormula old = implicationFormulas.get(e);
        if(old == null){ 
            implicationFormulas.put(e,e);
            return e;
        }else{ return old; }
    }

    private Map<MettelFOFormulaEquivalenceFormula, MettelFOFormulaEquivalenceFormula> formulaEquivalenceFormulas = new TreeMap<MettelFOFormulaEquivalenceFormula, MettelFOFormulaEquivalenceFormula>();

    public MettelFOFormulaEquivalenceFormula createFormulaEquivalenceFormula(MettelFOFormula e0, MettelFOFormula e1){
        final MettelFOFormulaEquivalenceFormula e = new MettelFOFormulaEquivalenceFormula(e0, e1, this);
        final MettelFOFormulaEquivalenceFormula old = formulaEquivalenceFormulas.get(e);
        if(old == null){ 
            formulaEquivalenceFormulas.put(e,e);
            return e;
        }else{ return old; }
    }

    private Map<String, MettelFOFormulaVariable> formulaVariables = new TreeMap<String, MettelFOFormulaVariable>();

    public MettelFOFormulaVariable createFormulaVariable(String name){
        MettelFOFormulaVariable v = formulaVariables.get(name);
        if(v == null){
            v = new MettelFOFormulaVariable(name, this);
            formulaVariables.put(name, v);
        }
        return v;
    }

}
