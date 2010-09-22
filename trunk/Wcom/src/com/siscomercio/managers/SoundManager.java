package com.siscomercio.managers;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.logging.Level;
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
    private static final Logger _log = Logger.getLogger(SoundManager.class.getName());

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
                if(Config.DEBUG)_log.log(Level.INFO, "playSound() Tocando Arquivo: {0}\n", file);
            }
            catch(MalformedURLException ex)
            {
                SystemUtil.showErrorMsg(ex.getMessage(),false);
            }
        }
        else
            SystemUtil.showErrorMsg("Arquivo de Som: " + soundName + " nao foi encontrado !",false);
    }

}
