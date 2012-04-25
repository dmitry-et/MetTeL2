/**
 * This file is generated by MetTeL v2
 * which is designed and implemented
 * by Dmitry Tishkovsky.
 */
package mettel.fo;

import java.util.Map;
import java.util.TreeMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import mettel.core.MettelReplacement;
import mettel.core.MettelSubstitution;
import mettel.core.MettelExpression;

public class MettelFOTreeReplacement implements MettelFOReplacement{

    private class Pointer<T extends MettelFOExpression>{
        T expression = null;
        Pointer(T expression){
            super();
            this.expression = expression;
        }
        public int hashCode(){
            return expression.hashCode();
        }
    }

    protected final Map<MettelFOFormula, Pointer<MettelFOFormula>> formulaMap = new TreeMap<MettelFOFormula, Pointer<MettelFOFormula>>();
    public MettelFOFormula getFormula(MettelFOFormula e){
        final Pointer<MettelFOFormula> p = formulaMap.get(e);
        return p == null? null: p.expression;
    }

    public Set<MettelFOFormula> formulaKeys(){
        return formulaMap.keySet();
    }

    public boolean append(MettelFOFormula e0, MettelFOFormula e1){
        if(e0 == null || e1 == null){ return false; }
        final int ID0 = e0.id();
        int ID1 = e1.id();
        if(ID0 == ID1) return false;
            MettelFOFormula left = null;
            MettelFOFormula right = null;
            if(ID0 > ID1){
                left = e0;
                right = e1;
            }else{
                left = e1;
                right = e0;
                ID1 = ID0;
            }
            final Pointer<MettelFOFormula> entry = formulaMap.get(left);
            if(entry == null){
                formulaMap.put(left, new Pointer<MettelFOFormula>(right));
                return true;
            }else{
                final MettelFOFormula third = entry.expression;
                final int ID2 = third.id();
                if(ID1 == ID2) return false;
                if(ID1 > ID2){
                    formulaMap.put(right, entry);
                    return true;
                }else{
                    entry.expression = right;
                    formulaMap.put(third, entry);
                    return true;
                }
            }
        }

        public boolean isEmpty(){
            return formulaMap.isEmpty();
        }

        public boolean append(MettelExpression e0, MettelExpression e1){
            if(e0 instanceof MettelFOFormula){ return append((MettelFOFormula)e0, (MettelFOFormula)e1); }
            return false;
        }

        public boolean append(MettelReplacement r){
            if(r == null){ return false; }
            return append((MettelFOReplacement)r);
        }

        public MettelExpression rewrite(MettelExpression e) {
            if(e instanceof MettelFOFormula){
                return ((MettelFOFormula)e).rewrite(this);
            }
            return null;
        }

        public MettelSubstitution rewrite(MettelSubstitution s) {
            final MettelFOSubstitution s0 = (MettelFOSubstitution)s;
            MettelFOSubstitution res = new MettelFOTreeSubstitution();
            final Set<Entry<MettelFOFormulaVariable, MettelFOFormula>> formulaEntrySet = s0.formulaMap().entrySet();
            for(Entry<MettelFOFormulaVariable, MettelFOFormula> entry:formulaEntrySet){
                res.append(entry.getKey(),(MettelFOFormula)rewrite(entry.getValue()));
            }
            return res;
        }

        public boolean append(MettelFOReplacement r){
            boolean result = false;
            final Set<MettelFOFormula> formulaKeys = r.formulaKeys();
            for(MettelFOFormula key:formulaKeys){
                result |= append(key, r.getFormula(key));
            }
            return result;
        }

        private int hashCode = 0;
        public int hashCode(){
            hashCode = 1;
            hashCode = 31*hashCode + formulaMap.hashCode();
            return hashCode;
        }

        public int compareTo(MettelReplacement r){
            if(r == this){ return 0; }
            final Set<MettelFOFormula> keysFormula0 = formulaMap.keySet();
            final Set<MettelFOFormula> keysFormula1 = ((MettelFOReplacement)r).formulaKeys();
            final TreeSet<MettelFOFormula> keysFormula = new TreeSet<MettelFOFormula>(keysFormula0);
            keysFormula.addAll(keysFormula1);
            for(MettelFOFormula key:keysFormula){
                if(!keysFormula0.contains(key)){
                    return 1;
                }else{
                    if(!keysFormula1.contains(key)){
                        return -1;
                    }else{
                        final MettelFOFormula v0 = formulaMap.get(key).expression;
                        final MettelFOFormula v1 = ((MettelFOReplacement)r).getFormula(key);
                        final int result = v0.compareTo(v1);
                        if(result != 0){ return result; }
                    }
                }
            }
            return 0;
        }

        public boolean equals(Object o){
            if(o == this){ return true; }
            if(!(o instanceof MettelFOReplacement)){ return false; }
            final MettelFOReplacement r = (MettelFOReplacement)o;
            final Set<MettelFOFormula> formulaKeys = formulaMap.keySet();
            if(!formulaKeys.equals(r.formulaKeys())) return false;
            for(MettelFOFormula key:formulaKeys){
                final MettelFOFormula v = formulaMap.get(key).expression;
                if(!v.equals(r.getFormula(key))) return false;
            }
            return true;
        }

        public String toString(){
            StringBuilder b = new StringBuilder();
            b.append("$[");
            boolean notFirst = false;
            final Set<Entry<MettelFOFormula, Pointer<MettelFOFormula>>> formulaEntrySet = formulaMap.entrySet();
            for(Entry<MettelFOFormula, Pointer<MettelFOFormula>> entry:formulaEntrySet){
                if(notFirst){
                    b.append(", ");
                }else{
                    notFirst = true;
                }
                b.append(entry.getKey());
                b.append('/');
                b.append(entry.getValue().expression);
            }
            b.append(']');
            return b.toString();
        }

}