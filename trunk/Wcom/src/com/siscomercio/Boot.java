/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio;

import com.siscomercio.frames.DatabaseFrame;
import java.awt.EventQueue;
import com.siscomercio.frames.FramePrincipal;
import com.siscomercio.frames.LicenseFrame;
import com.siscomercio.managers.AppManager;
import com.siscomercio.managers.DatabaseManager;
import com.siscomercio.security.Auth;
import com.siscomercio.utilities.WindowsUtil;
import java.util.logging.Logger;

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
                    AppManager.setTema(getClass().getName());
                    DatabaseFrame.getInstance().setVisible(true);
                }

            });
        }

        // Caso a DB Esteja Instalada Prosegue Para a Licenca
        // ------------------------------------------------
        else
        {
            //Le os Dados da Licenca
            // --------------------------
            DatabaseManager.readLicenseData();

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
                            AppManager.setTema(getClass().getName());
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
                        AppManager.setTema(getClass().getName());
                        new LicenseFrame().setVisible(true);
                    }

                });
            }
        }
    }

}
