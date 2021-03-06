/**
 * This file is generated by MetTeL v2
 * which is designed and implemented
 * by Dmitry Tishkovsky.
 */
package mettel.fo;

import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import mettel.core.tableau.MettelSubstitution;
public class MettelFOTreeSubstitution implements MettelFOSubstitution{

    protected final Map<MettelFOFormulaVariable, MettelFOFormula> formulaMap = new TreeMap<MettelFOFormulaVariable, MettelFOFormula>();
    
    protected final Map<MettelFOIndividualVariable, MettelFOTerm> termMap = new TreeMap<MettelFOIndividualVariable, MettelFOTerm>();
        
    public MettelFOFormula getFormula(MettelFOFormulaVariable v){
        return formulaMap.get(v);
    }

    public Map<MettelFOFormulaVariable, MettelFOFormula> formulaMap(){
        return formulaMap;
    }
    
    public MettelFOTerm getTerm(MettelFOIndividualVariable v){
        return termMap.get(v);
    }

    public Map<MettelFOIndividualVariable, MettelFOTerm> termMap(){
        return termMap;
    }

    public boolean append(MettelFOFormulaVariable v, MettelFOFormula e1){
        if(v == null || e1 == null){ return false; }
        final MettelFOFormula e = formulaMap.get(v);
        if(e == null){
            formulaMap.put(v,e1);
            return true;
        }else{
            return (e.equals(e1));
        }
    }
    
    public boolean append(MettelFOIndividualVariable v, MettelFOTerm e1){
        if(v == null || e1 == null){ return false; }
        final MettelFOTerm e = termMap.get(v);
        if(e == null){
            termMap.put(v,e1);
            return true;
        }else{
            return (e.equals(e1));
        }
    }

    public boolean append(MettelFOSubstitution s){
        final Map<MettelFOFormulaVariable, MettelFOFormula> mFormula = s.formulaMap();
        for(MettelFOFormulaVariable key:mFormula.keySet()){
            if(!append(key, s.getFormula(key))){ return false; }
        }
        final Map<MettelFOIndividualVariable, MettelFOTerm> mTerm = s.termMap();
        for(MettelFOIndividualVariable key:mTerm.keySet()){
            if(!append(key, s.getTerm(key))){ return false; }
        }
        return true;
    }

    public MettelSubstitution merge(MettelSubstitution s){
        MettelFOTreeSubstitution result = new MettelFOTreeSubstitution();
        result.append(this);
        if(result.append((MettelFOTreeSubstitution)s)){
            return result;
        }else{
            return null;
        }
    }

    private static MettelFOTreeSubstitution appendArray(MettelFOTreeSubstitution s, MettelSubstitution[] array){
        if(array == null) return null;
        final int SIZE = array.length;
        if(SIZE == 0) return null;
        for(int i = 0; i < SIZE; i++){
            Set<Entry<MettelFOFormulaVariable, MettelFOFormula>> entryFormulaSet = ((MettelFOSubstitution)array[i]).formulaMap().entrySet();
            for(Entry<MettelFOFormulaVariable, MettelFOFormula> entry:entryFormulaSet){
                if(!s.append(entry.getKey(),entry.getValue())) return null;
            }
            Set<Entry<MettelFOIndividualVariable, MettelFOTerm>> entryTermSet = ((MettelFOSubstitution)array[i]).termMap().entrySet();
            for(Entry<MettelFOIndividualVariable, MettelFOTerm> entry:entryTermSet){
                if(!s.append(entry.getKey(),entry.getValue())) return null;
            }
        }
        return s;
    }

    public MettelSubstitution mergeArray(MettelSubstitution[] array){
        return appendArray(new MettelFOTreeSubstitution(),array);
    }

    public MettelSubstitution merge(MettelSubstitution[] array){
        MettelFOTreeSubstitution s = new MettelFOTreeSubstitution();
        s.formulaMap().putAll(formulaMap);
        s.termMap().putAll(termMap);
        return appendArray(s, array);
    }

    public int compareTo(MettelSubstitution s){
        if(s == this){ return 0; }
        final Set<MettelFOFormulaVariable> keysFormula0 = formulaMap.keySet();
        final Set<MettelFOFormulaVariable> keysFormula1 = ((MettelFOSubstitution)s).formulaMap().keySet();
        final TreeSet<MettelFOFormulaVariable> keysFormula = new TreeSet<MettelFOFormulaVariable>(keysFormula0);
        keysFormula.addAll(keysFormula1);
        for(MettelFOFormulaVariable key:keysFormula){
            if(!keysFormula0.contains(key)){
                return 1;
            }else{
                if(!keysFormula1.contains(key)){
                    return -1;
                }else{
                    final MettelFOFormula v0 = formulaMap.get(key);
                    final MettelFOFormula v1 = ((MettelFOSubstitution)s).getFormula(key);
                    final int result = v0.compareTo(v1);
                    if(result != 0){ return result; }
                }
            }
        }
        final Set<MettelFOIndividualVariable> keysTerm0 = termMap.keySet();
        final Set<MettelFOIndividualVariable> keysTerm1 = ((MettelFOSubstitution)s).termMap().keySet();
        final TreeSet<MettelFOIndividualVariable> keysTerm = new TreeSet<MettelFOIndividualVariable>(keysTerm0);
        keysTerm.addAll(keysTerm1);
        for(MettelFOIndividualVariable key:keysTerm){
            if(!keysTerm0.contains(key)){
                return 1;
            }else{
                if(!keysTerm1.contains(key)){
                    return -1;
                }else{
                    final MettelFOTerm v0 = termMap.get(key);
                    final MettelFOTerm v1 = ((MettelFOSubstitution)s).getTerm(key);
                    final int result = v0.compareTo(v1);
                    if(result != 0){ return result; }
                }
            }
        }
        return 0;
    }

    private int hashCode = 0;
    public int hashCode(){
        hashCode = 1;
        hashCode = 31*hashCode + formulaMap.hashCode();
        hashCode = 31*hashCode + termMap.hashCode();
        return hashCode;
    }

    public boolean equals(Object o){
        if(o == this){ return true; }
        if(!(o instanceof MettelFOSubstitution)){ return false; }
        final MettelFOSubstitution s = (MettelFOSubstitution)o;
        return formulaMap.equals(s.formulaMap()) && termMap.equals(s.termMap());
    }

    public String toString(){
        StringBuilder b = new StringBuilder();
        b.append("$[");
        boolean notFirst = false;
        Set<Entry<MettelFOFormulaVariable, MettelFOFormula>> formulaEntrySet = formulaMap.entrySet();
        for(Entry<MettelFOFormulaVariable, MettelFOFormula> entry:formulaEntrySet){
            if(notFirst){
                b.append(", ");
            }else{
                notFirst = true;
            }
            b.append(entry.getKey());
            b.append('/');
            b.append(entry.getValue());
        }
        b.append(';');
        notFirst = false;
        Set<Entry<MettelFOIndividualVariable, MettelFOTerm>> termEntrySet = termMap.entrySet();
        for(Entry<MettelFOIndividualVariable, MettelFOTerm> entry:termEntrySet){
            if(notFirst){
                b.append(", ");
            }else{
                notFirst = true;
            }
            b.append(entry.getKey());
            b.append('/');
            b.append(entry.getValue());
        }
        b.append(']');
        return b.toString();
    }

}
