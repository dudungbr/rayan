package com.siscomercio.managers;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.siscomercio.Config;
import com.siscomercio.utilities.SystemUtil;

/**
 * $Revision$
 * $Author$
 * $Date$
 * @author Rayan
 */
public class SoundManager
{
    private static Logger _log = Logger.getLogger(SoundManager.class.getName());

    /**
     * 
     * @param soundName
     */
    public static void playSound(String soundName)
    {

        File file = new File("src/wcom/sounds/" + soundName);
        URI caminho = file.toURI();
        AudioClip sound;
        try
        {
            sound = Applet.newAudioClip(caminho.toURL());
            sound.play();
            Thread.sleep(SystemUtil.convertSecondsToMiliseconds(1));
            if(Config.DEBUG)
                _log.info("playSound() Tocando Arquivo: " + file + "\n");
        }
        catch(InterruptedException ex)
        {
            Logger.getLogger(SoundManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(MalformedURLException ex)
        {
            Logger.getLogger(SoundManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
