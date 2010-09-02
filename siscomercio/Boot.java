/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import java.awt.EventQueue;
import com.siscomercio.frames.FramePrincipal;
import com.siscomercio.frames.LicenseFrame;
import com.siscomercio.managers.DatabaseManager;
import com.siscomercio.security.Auth;
import com.siscomercio.security.Serializer;
import com.siscomercio.utilities.SystemUtil;
import com.siscomercio.utilities.WindowsUtil;
import java.util.logging.Logger;
import javax.swing.UIManager;

/**
 * $Revision$
 * $Date$
 * $Author$
 * $HeadURL$
 * 
 * @author Rayan
 */
public class Boot
{
    private static final Logger _log = Logger.getLogger(Boot.class.getName());
    /**
     * 
     */
    public static boolean isRegistrado = false;

    /**
     * @param args the command line arguments
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception
    {

        if(!isRegistrado)
        {
            Serializer.generateActivationCode();
            EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                // PlasticLookAndFeel.setPlasticTheme(new Silver());
                try
                {
                    /**
                     * #  com.jgoodies.looks.windows.WindowsLookAndFeel
                    # com.jgoodies.looks.plastic.PlasticLookAndFeel
                    # com.jgoodies.looks.plastic.Plastic3DLookAndFeel
                    # com.jgoodies.looks.plastic.PlasticXPLookAndFeel
                     */
                    //BlackBusiness subistantce
                    //Luna jtoo
                    //acryl - jato
                    // UIManager.setLookAndFeel(new PlasticLookAndFeel());
                    UIManager.setLookAndFeel(new AcrylLookAndFeel());
                }
                catch(Exception e)
                {
                    SystemUtil.showErrorMsg("Nao Foi Possivel Carregar a Skin");
                }
                new LicenseFrame().setVisible(true);

            }

        });
        }
        else
        {
            //Carrega as Configs
            Config.load();

            // Checka  O Processo MySQL esta em Execução.
            if(!Config.DEVELOPER)
                WindowsUtil.checkProcess("mysql");

            // Lê a Tabela de Instalacao da DB
            if(!DatabaseManager._installed)
                DatabaseManager.readInstallTable();

            // Chama a Tela de Login
            if(Config.DEBUG)
                EventQueue.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Auth.getInstance().setVisible(true);
                    }

                });
            else
                EventQueue.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        new FramePrincipal().setVisible(true);
                    }
                });
        }
    }

}
