package mettel.core.tableau;

import java.lang.management.ThreadMXBean;

import java.util.Set;
import java.util.Collection;

public class MettelProverThread extends Thread {
    private MettelSimpleTableauManager m = null;
    private Boolean result = null;
    private Collection<? extends MettelExpression> problem = null;
    private double time = -1;
    private Set<MettelExpression> model = null;
    private Set<MettelExpression> contradiction = null;
    private boolean finished = false;
    
    private ThreadMXBean mxBean = null;
    
    private static double NANO_TO_MILLI_SECONDS = 1000000.0;
    
    public MettelProverThread(Collection<MettelGeneralTableauRule> calculus, Collection<? extends MettelExpression> problem, MettelTableauObjectFactory tfactory, ThreadMXBean mxBean){
        this.m = new MettelSimpleTableauManager(tfactory, calculus);
        this.problem = problem;
        this.mxBean = mxBean;
    }

	@Override
    public void run(){
        if (result = m.isSatisfiable(problem)){
            model = m.model();
        }
        else
        {
            contradiction = m.contradiction();
        }
        time = mxBean.getThreadUserTime(getId());
        finished = true;
    }

    public Boolean result(){
        return result;
    }

    // maybe change to double
    public double time(){
        return time / NANO_TO_MILLI_SECONDS;
    }

    public Set<MettelExpression> model(){
        return model;
    }

    public Set<MettelExpression> contradiction(){
        return contradiction;
    }

    public boolean finished(){
        return finished;
    }
}
