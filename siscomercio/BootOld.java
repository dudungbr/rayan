/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio;

import com.siscomercio.frames.DBConfig;
import java.awt.EventQueue;
import com.siscomercio.frames.FramePrincipal;
import com.siscomercio.frames.FrameLicenca;
import com.siscomercio.managers.AppManager;
import com.siscomercio.managers.DatabaseManager;
import com.siscomercio.managers.LogMonitor;
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
public class BootOld
{
    private static final Logger _log = Logger.getLogger(BootOld.class.getName());

    /**
     * @param args the command line arguments
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception
    {
        //Carrega as Configs
        // ---------------
        _log.info("Inicializando Config...");
        Config.load();

         // Inicializa o Log Monitor
        // ---------------
        if(Config.isEnableLog())
        {
             _log.info("Inicializando Log Monitor...");
        LogMonitor.init();
        }
        // Checka  O Processo MySQL esta em Execução.
        // ------------------------
          _log.info("Checando Servidor Banco de Dados...");
        WindowsUtil.checkProcess("mysql");

        // Lê a Tabela de Instalacao da DB
        //--------------------------------
          _log.info("Lendo Tabela de Instalacao do Banco ...");
        DatabaseManager.tryReadInstallData();

        // Abre o Frame de instalacao da DB caso nao a db nao esteja instalada.
        // ------------------------
        if(DatabaseManager._installed == 0)
        {
              _log.info("Database nao Instalada, Abrindo Instalador Banco Dados...");
            EventQueue.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    AppManager.setTema(DBConfig.class.getSimpleName());
                      _log.info("Abrindo Instalador da Database...");
                    DBConfig.getInstance().setVisible(true);
                }

            });
        }
        // Caso a DB Esteja Instalada Prosegue Para a Licenca
        // ------------------------------------------------
        else
        {
              _log.info("Inicializando Log Monitor...");
            //Le os Dados da Licenca
            // --------------------------
            DatabaseManager.readLicenseData();

            // OK! Podemos Abrir o Sistema.
            // ------------------------
            if(DatabaseManager._licensed == 1)
            {
                // Chama a Tela de Login
                // ------------------------
                if(Config.isDebug())
                {
                    EventQueue.invokeLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            _log.finest("Fim do Boot.");
                            AppManager.setTema(BootOld.class.getSimpleName());
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
                        AppManager.setTema(BootOld.class.getSimpleName());
                        new FrameLicenca().setVisible(true);
                    }

                });
            }
        }
    }

}
