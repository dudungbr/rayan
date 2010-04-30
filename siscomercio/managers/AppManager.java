package com.siscomercio.managers;

import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.siscomercio.Config;

import com.siscomercio.utilities.SystemUtil;

/**
 * $Revision$
 * $Author$
 * $Date$
 * @author Rayan
 */
@SuppressWarnings("serial")
public class AppManager extends JFrame
{
    private Logger _log = Logger.getLogger(AppManager.class.getName());

    /**
     *
     * @return AppManager _instance
     */
    public static AppManager getInstance()
    {
        return SingletonHolder._instance;
    }

    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder
    {
        protected static final AppManager _instance = new AppManager();
    }

    /**
     *
     */
    public void requestAppShutdown()
    {
        if(Config.SOUND)
            SoundManager.playSound(Config.EXIT_SOUND);

        if(Config.DEBUG)
            _log.info("solicitacao de shutdown...\n");
        int selectedOption = JOptionPane.showConfirmDialog(null, "Encerrar Sistema ?", "Pergunta", JOptionPane.OK_CANCEL_OPTION);

        if(selectedOption == JOptionPane.OK_OPTION)
        {
            if(Config.SOUND)
            SoundManager.playSound("exitok.wav");

            if(Config.DEBUG)
                _log.info("usuario finalizou o sistema.");

            //finaliza a aplicacao
            System.exit(0);
        }
        else
        {
            if(Config.DEBUG)
                _log.info("usuario desiste de fechar o sistema.\n");
        }
    }

    /**
     * Metodo para funcoes em desenvolvimento ainda.
     */
    public static void implementar()
    {
        if(Config.SOUND)
        SoundManager.playSound(Config.UNIPLEMENTED_SOUND);
        SystemUtil.showMsg("Funcao Ainda nao Disponivel");
    }

}
