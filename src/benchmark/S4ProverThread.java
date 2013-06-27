package S4.tableau;

import mettel.core.tableau.MettelSimpleTableauManager;
import mettel.core.tableau.MettelTableauObjectFactory;
import mettel.core.tableau.MettelGeneralTableauRule;
import mettel.core.tableau.MettelExpression;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;

import S4.language.*;


public class S4ProverThread extends Thread{

    private MettelTableauObjectFactory tfactory;
    private MettelSimpleTableauManager m;
    private boolean result;
    private ArrayList<S4Formula> problem;
    private long time = -1;
    private Set<MettelExpression> model = null;
    private Set<MettelExpression> contradiction = null;
    private boolean finished = false;

    public S4ProverThread(Collection<MettelGeneralTableauRule> calculus, ArrayList<S4Formula> problem){

        this.tfactory = new S4TableauObjectFactory();

        this.m = new MettelSimpleTableauManager(tfactory, calculus);

        this.problem = problem;

    }

    // calculate model and contradiction here? or just retun mettelexpression object?
    public void run(){
        if (result = m.isSatisfiable(problem)){
            model = m.model();
        }
        else
        {
            contradiction = m.contradiction();
        }
        time = ManagementFactory.getThreadMXBean().getThreadCpuTime(Thread.currentThread().getId());
        finished = true;
    }

    public boolean result(){
        return result;
    }

    public long time(){
        return time;
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