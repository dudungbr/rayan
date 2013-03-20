package com.siscomercio.managers;


import com.siscomercio.Config;
import com.siscomercio.standards.StringTable;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * $Revision: 210 $ $Author: rayan_rpg@hotmail.com $ $Date: 2010-09-22 14:43:39
 * -0300 (qua, 22 set 2010) $
 *
 * @author Rayan
 */
public class SoundManager
{

    private static final Logger _log = Logger.getLogger(SoundManager.class.getName());
    private static SoundManager instance;
    private static ArrayList<File> fileList;

    /**
     * @param aInstance the instance to set
     */
    public static void setInstance(SoundManager aInstance)
    {
        instance = aInstance;
    }

    private SoundManager()
    {
        loadSounds();
    }

    /**
     * Toca um Som em Formato WAV
     *
     * @param soundName
     * @return
     */
    public boolean playSound(String soundName)
    {
        if (Config.isEnableSound())
        {
            _log.log(Level.INFO, "tocando arquivo: {0}", soundName);

            File arquivo;
            List sounds = getSoundList();
            for (Iterator iterator = sounds.iterator(); iterator.hasNext();)
            {
                try
                {
                    arquivo = (File) iterator.next();
                    if (arquivo.getName().equalsIgnoreCase(soundName))
                    {
                        URI caminho = arquivo.toURI();
                        AudioInputStream sample = AudioSystem.getAudioInputStream(caminho.toURL());
                        Clip som = AudioSystem.getClip();
                        som.open(sample);
                        som.start();
                        return true;
                    }
                }
                catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex)
                {
                    Logger.getLogger(SoundManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else
        {
            return false;
        }
        return false;
    }

    private void loadSounds()
    {
        try
        {
            fileList = new ArrayList();

            File pasta = new File(StringTable.getSOUND_PATH());// + soundName);

            if (pasta.exists())
            {
                File[] arquivosPasta = pasta.listFiles();

                for (File arquivo : arquivosPasta)
                {
                    if (arquivo.getName().endsWith(".wav"))
                    {
                        //  _log.log(Level.INFO, "Adicionando objeto: {0} A lista.", arquivo.getName());
                        fileList.add(arquivo);
                    }
                }
                _log.log(Level.INFO, "Carregados: {0} Arquivos de Som", fileList.size());

            }

        }
        catch (Exception ex)
        {
            Logger.getLogger(SoundManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @return
     */
    public static SoundManager getInstance()
    {
        if (instance == null)
        {
            instance = new SoundManager();
        }
        return instance;
    }

    private ArrayList<File> getSoundList()
    {
        return fileList;
    }
}
