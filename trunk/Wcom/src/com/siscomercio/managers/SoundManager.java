package com.siscomercio.managers;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.logging.Logger;
import com.siscomercio.Config;
import com.siscomercio.tables.StringTable;
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
     * Toca um Som em Formato WAV
     * @param soundName
     */
    public static void playSound(String soundName)
    {

        File file = new File(StringTable.SOUND_PATH + soundName);
        if(file.exists())
        {
            try
            {
                URI caminho = file.toURI();
                AudioClip sound;
                sound = Applet.newAudioClip(caminho.toURL());
                sound.play();
                Thread.sleep(SystemUtil.convertSecondsToMiliseconds(1/2));
                if(Config.DEBUG)_log.info("playSound() Tocando Arquivo: " + file + "\n");
            }
            catch(InterruptedException ex)
            {
                SystemUtil.showErrorMsg(ex.getMessage());
            }
            catch(MalformedURLException ex)
            {
                SystemUtil.showErrorMsg(ex.getMessage());
            }
        }
        else
            SystemUtil.showErrorMsg("Arquivo de Som: " + soundName + " nao foi encontrado !");
    }

}
