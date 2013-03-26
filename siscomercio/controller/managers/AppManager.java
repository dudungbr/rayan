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
    Config config = Config.getInstance();

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
    public void optimizeRam()
    {
        if (config.isDeveloper())
        {
            _log.info("Chamando Garbage Collector.. \n ");
        }
        System.gc();
    }

    /**
     *
     * @param requesterClass
     */
    public void setTema(String requesterClass)
    {
        if (config.isDeveloper())
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
            SystemUtil.getInstance().getInstance().showErrorMsg("Nao Foi Possivel Carregar a Skin" + e.getMessage(), true);
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

        if (config.isEnableSound())
        {
            SoundManager.getInstance().playSound(config.getPreRestartSound());
        }

        if (config.isDeveloper())
        {
            _log.info("solicitacao de restart.");
        }

        int selectedOption = JOptionPane.showConfirmDialog(null, "Iniciar Sistema ?", "Pergunta", JOptionPane.OK_CANCEL_OPTION);

        if (selectedOption == JOptionPane.OK_OPTION)
        {
            if (config.isEnableSound())
            {
                SoundManager.getInstance().playSound(config.getRestartSound());
            }

            if (config.isDeveloper())
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
        if (config.isEnableSound())
        {
            SoundManager.getInstance().playSound(config.getPreExitSound());
        }


        int selectedOption = JOptionPane.showConfirmDialog(janelaPai, "<html><font color =black size=4 face = Times new Roman ><b>Encerrar Sistema ?</b></font></html>", "Confirmar", JOptionPane.OK_CANCEL_OPTION);
        if (config.isDeveloper())
        {
            _log.info("solicitacao de shutdown...\n");
        }


        if (selectedOption == JOptionPane.OK_OPTION)
        {
            if (config.isEnableSound())
            {
                SoundManager.getInstance().playSound(config.getExitSound());
            }

            if (config.isDeveloper())
            {
                _log.info("usuario finalizou o sistema.");
            }

            //finaliza a aplicacao
            System.exit(0);
        }
        else if (config.isDeveloper())
        {
            _log.info("usuario desiste de fechar o sistema.\n");
        }
    }

    /**
     * Metodo para funcoes em desenvolvimento ainda.
     */
    public void implementar()
    {
        if (config.isEnableSound())
        {
            SoundManager.getInstance().playSound(config.getUnimplementedSound());
        }
        SystemUtil.getInstance().getInstance().showMsg("Funcao Ainda nao Disponivel", true);
    }
}
