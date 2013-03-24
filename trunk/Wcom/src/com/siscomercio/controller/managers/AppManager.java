package com.siscomercio.controller.managers;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.siscomercio.init.Config;
import com.siscomercio.model.security.Auth;

import com.siscomercio.utilities.SystemUtil;
import java.awt.EventQueue;
import java.util.Properties;
import java.util.logging.Level;
import javax.swing.UIManager;

/**
 * $Revision$ $Author$ $Date: 2013-03-22 16:52:13 -0300 (Fri,
 * 22 Mar 2013) $
 *
 * @author Rayan
 */
@SuppressWarnings ("serial")
public class AppManager extends JFrame
{
    private static final Logger _log = Logger.getLogger(AppManager.class.getName());

    /**
     *
     * @return AppManager _instance
     */
    public static AppManager getInstance()
    {
        return SingletonHolder._instance;
    }
    @SuppressWarnings ("synthetic-access")
    private static class SingletonHolder
    {
        protected static final AppManager _instance = new AppManager();
    }

    /**
     *
     */
    public static void optimizeRam()
    {
        if (Config.isDeveloper())
        {
            _log.info("Chamando Garbage Collector.. \n ");
        }
        System.gc();
    }

    /**
     *
     * @param requesterClass
     */
    public static void setTema(String requesterClass)
    {
        if (Config.isDeveloper())
        {
            _log.log(Level.INFO, "Setando Tema Visual para a Classe:  {0}.java \n", requesterClass);
        }
        try
        {
            //Define O Tema Visual e o Texto do Pop UP
            Properties props = new Properties();
            props.put("logoString", "Siscom");
            props.put("licenseKey", "INSERT YOUR LICENSE KEY HERE");
            AcrylLookAndFeel.setCurrentTheme(props);
            UIManager.setLookAndFeel(new AcrylLookAndFeel());
        }
        catch (Exception e)
        {
            SystemUtil.showErrorMsg("Nao Foi Possivel Carregar a Skin" + e.getMessage(), true);
        }
    }

    /**
     *
     */
    /**
     *
     */
    public void restartApp()
    {

        if (Config.isEnableSound())
        {
            SoundManager.getInstance().playSound(Config.getPreRestartSound());
        }

        if (Config.isDeveloper())
        {
            _log.info("solicitacao de restart.");
        }

        int selectedOption = JOptionPane.showConfirmDialog(null, "Iniciar Sistema ?", "Pergunta", JOptionPane.OK_CANCEL_OPTION);

        if (selectedOption == JOptionPane.OK_OPTION)
        {
            if (Config.isEnableSound())
            {
                SoundManager.getInstance().playSound(Config.getRestartSound());
            }

            if (Config.isDeveloper())
            {
                _log.info("usuario reiniciou o sistema.");
            }

            //retorna o Status de Autenticidade.
            Auth.getInstance().setAutenticado(false);;

            //Fecha Janela
            dispose();

            EventQueue.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    new Auth().setVisible(true);
                }
            });
        }
    }

    /**
     *
     * @param janelaPai
     */
    public void requestAppShutdown(JFrame janelaPai)
    {
        if (Config.isEnableSound())
        {
            SoundManager.getInstance().playSound(Config.getPreExitSound());
        }


        int selectedOption = JOptionPane.showConfirmDialog(janelaPai, "<html><font color =black size=4 face = Times new Roman ><b>Encerrar Sistema ?</b></font></html>", "Confirmar", JOptionPane.OK_CANCEL_OPTION);
        if (Config.isDeveloper())
        {
            _log.info("solicitacao de shutdown...\n");
        }


        if (selectedOption == JOptionPane.OK_OPTION)
        {
            if (Config.isEnableSound())
            {
                SoundManager.getInstance().playSound(Config.getExitSound());
            }

            if (Config.isDeveloper())
            {
                _log.info("usuario finalizou o sistema.");
            }

            //finaliza a aplicacao
            System.exit(0);
        }
        else if (Config.isDeveloper())
        {
            _log.info("usuario desiste de fechar o sistema.\n");
        }
    }

    /**
     * Metodo para funcoes em desenvolvimento ainda.
     */
    public static void implementar()
    {
        if (Config.isEnableSound())
        {
            SoundManager.getInstance().playSound(Config.getUnimplementedSound());
        }
        SystemUtil.showMsg("Funcao Ainda nao Disponivel", true);
    }
}
