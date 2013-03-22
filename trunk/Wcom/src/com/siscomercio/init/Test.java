
package com.siscomercio.init;

import com.siscomercio.controller.managers.SoundManager;

/**
 *
 * @author William
 */


public class Test {

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Config.load();
        SoundManager.getInstance().playSound(Config.getLoginSound());
    }
}
