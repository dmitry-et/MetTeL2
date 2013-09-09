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

import java.lang.management.ThreadMXBean;

import java.util.Set;
import java.util.Collection;

public class MettelProverThread extends Thread {
    private MettelTableauManager m = null;
    private Boolean result = null;
    private Collection<? extends MettelExpression> problem = null;
    private double time = -1;
    private Set<MettelExpression> model = null;
    private Set<MettelExpression> contradiction = null;
    private boolean finished = false;
    private String error = null;
    
    private ThreadMXBean mxBean = null;
    
    private static double NANO_TO_MILLI_SECONDS = 1000000.0;
    
    public MettelProverThread(MettelTableauManager m, Collection<? extends MettelExpression> problem, ThreadMXBean mxBean){
        this.m = m;
        this.problem = problem;
        this.mxBean = mxBean;
    }

	@Override
    public void run(){
		try{
	        if (result = m.isSatisfiable(problem)){
	            model = m.model();
	        }
	        else
	        {
	            contradiction = m.contradiction();
	        }
		}catch(ThreadDeath e0){
			throw e0;
		}catch(Throwable e0){
			error = e0.toString();
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
    
    public String exception(){
    	return error;
    }
}
