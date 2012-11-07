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
package mettel.core.tableau;

import java.util.Collection;
import java.util.Set;
/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelGeneralTableauStateWithRewriting extends MettelAbstractTableauState {

	private MettelReplacement replacement = null;

	private MettelTableauStatePool equalities = null;

	public MettelGeneralTableauStateWithRewriting(MettelTableauObjectFactory factory, Collection<? extends MettelTableauRule> calculus) {
		super(factory, calculus);
		replacement = factory.createReplacement();
		equalities = new MettelTableauStatePool();
		equalities.init(this);
	}

	/**
	 * @param set
	 */
	public MettelGeneralTableauStateWithRewriting(MettelTableauObjectFactory factory, Collection<? extends MettelTableauRule> calculus,
			Set<MettelAnnotatedExpression> set) {
		super(factory, calculus, set);
		replacement = factory.createReplacement();
		equalities = new MettelTableauStatePool();
		equalities.init(this);
	}

	/**
	 * @param state
	 */
	public MettelGeneralTableauStateWithRewriting(MettelGeneralTableauStateWithRewriting state){
		super(state);
		replacement = factory.createReplacement();
		replacement.append(state.replacement);
		//if(!replacement.isEmpty()){
		//	final MettelTableauRewriteAction action = new MettelTableauRewriteAction(this);
		//	action.add(this);
		//	actions.add(action);
		//}
//System.out.println("*****Replacement="+replacement);		
		equalities = new MettelTableauStatePool();
		equalities.embed(state.equalities);
		equalities.init(this);
//		this.rewrite();
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauState#add(mettel.core.tableau.MettelAnnotatedExpression)
	 */
	@Override
	public boolean add(MettelAnnotatedExpression e) {
//System.out.println("Adding "+e);
//System.out.println("Replacement is "+replacement);
		final MettelExpression exp = e.expression();
		final MettelExpression exp0 = replacement.rewrite(exp);//XXX: Does not work as expected, needs fix!
		MettelAnnotatedExpression e0 = null;
		//boolean result = false;
		if(exp0 != exp){
			e0 = annotator.annotate(exp0, this);//TODO: Needs specific annotations to be effective
			if(!expressions.contains(e0)){
				final Set<MettelAnnotatedExpression> deps = e0.annotation().dependencies();
				deps.addAll(e.annotation().dependencies());
				deps.addAll(equalities);
				//result = expressions.add(e0);
			}
		}else{
			//result = expressions.add(e); 
			e0 = e;
		}
		
		final boolean result = expressions.add(e0);
		if(result){
			for(MettelTableauRuleState rs:ruleStates) rs.add(e0);
			if(exp0 instanceof MettelEqualityExpression){
//System.out.println("Replacement is "+replacement);				
				equalities.add(e0);
				final MettelEqualityExpression eq = (MettelEqualityExpression)exp0;
				if(replacement.append(eq.left(), eq.right())){
					this.rewrite();//Immediate rewrite is faster on the specified examples
//System.out.println("Rewriting action added");
				}
			}
		}
		return result;
	}

	public Set<MettelTableauState> rewrite(){
//System.out.println("Rewrite action execution");
		if(replacement.isEmpty()) return null;
//System.out.println("Rewriting: "+replacement);
		final MettelTableauStatePool pool = new MettelTableauStatePool();
		pool.init(this);
		expanded = false;
		for(MettelAnnotatedExpression ae:expressions){
			final MettelExpression e0 = ae.expression();
			final MettelExpression e1 = replacement.rewrite(e0);
			if(e0 == e1){//Only single instance of expression exists!
				pool.add(ae);
			}else{
				expanded = true;
				final MettelAnnotatedExpression ae1 = annotator.annotate(e1,this/*max*/);
				if(!expressions.contains(ae1)){
					final Set<MettelAnnotatedExpression> deps = ae1.annotation().dependencies();
					deps.addAll(ae.annotation().dependencies());
					deps.addAll(equalities);
					pool.add(ae1);
				}
			}
		}
		if(expanded){
			expressions = pool;
			for(MettelGeneralTableauRuleState rs:ruleStates){
				rs.rewrite(this, replacement);
//System.out.println("Rule state: "+rs);
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelAbstractTableauState#makeChildOf(mettel.core.tableau.MettelTableauState)
	 */
	@Override
	protected MettelTableauState makeChildOf(MettelTableauState state) {
		return new MettelGeneralTableauStateWithRewriting((MettelGeneralTableauStateWithRewriting)state);
	}

	/* (non-Javadoc)
	 * @see mettel.core.tableau.MettelTableauState#equalities()
	 */
	@Override
	public Set<MettelAnnotatedExpression> equalities() {
		return equalities;
	}
}
