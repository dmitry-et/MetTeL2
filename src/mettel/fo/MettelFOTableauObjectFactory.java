/**
 * This file is generated by MetTeL v2
 * which is designed and implemented
 * by Dmitry Tishkovsky.
 */
package mettel.fo;

import mettel.core.MettelTableauObjectFactory;
import mettel.core.MettelReplacement;

public class MettelFOTableauObjectFactory implements MettelTableauObjectFactory{

    public MettelReplacement createReplacement(){
        return new MettelFOTreeReplacement();
    }

}
