/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import com.siscomercio.frames.DatabaseFrame;
import java.awt.EventQueue;
import com.siscomercio.frames.FramePrincipal;
import com.siscomercio.frames.LicenseFrame;
import com.siscomercio.managers.DatabaseManager;
import com.siscomercio.security.Auth;
import com.siscomercio.utilities.SystemUtil;
import com.siscomercio.utilities.WindowsUtil;
import java.util.logging.Level;
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

    private static void setTema()
    {
        try
        {
            UIManager.setLookAndFeel(new AcrylLookAndFeel());
        }
        catch(Exception e)
        {
            SystemUtil.showErrorMsg("Nao Foi Possivel Carregar a Skin" + e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception
    {
        //Carrega as Configs
        Config.load();

        // Checka  O Processo MySQL esta em Execução.
        // ------------------------
        // if(!Config.DEVELOPER)
        WindowsUtil.checkProcess("mysql");

        // Lê a Tabela de Instalacao da DB
        //--------------------------------
        DatabaseManager.tryReadInstallData();

        // Abre o Frame de instalacao da DB caso nao a db nao esteja instalada.
        // ------------------------
        if(DatabaseManager._installed==0)
        {
            EventQueue.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    setTema();
                    DatabaseFrame.getInstance().setVisible(true);
                }

            });
        }

        // Caso a DB Esteja Instalada Prosegue Para a Licenca
        // ------------------------------------------------
        if(DatabaseManager._installed==1)
        {
            //Le os Dados da Licenca
            // --------------------------
            DatabaseManager.readLicenseData();

            _log.log(Level.INFO, "LICENCA: {0}", DatabaseManager._licensed);

            // OK! Podemos Abrir o Sistema.
            // ------------------------
            if(DatabaseManager._licensed==1)
            {
                // Chama a Tela de Login
                // ------------------------
                if(Config.DEBUG)
                {
                    EventQueue.invokeLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            setTema();
                            Auth.getInstance().setVisible(true);
                        }

                    });
                }
                else
                {
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
            //Caso a Aplicacao nao Tenha Sido Licenciada.. Abre o Frame de Licenca.
            // -----------------------------------------------------------
            else
            {
                EventQueue.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        setTema();
                        new LicenseFrame().setVisible(true);
                    }

                });
            }
        }
    }

}