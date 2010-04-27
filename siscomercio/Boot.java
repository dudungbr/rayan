/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio;

import java.awt.EventQueue;
import com.siscomercio.frames.FramePrincipal;
import com.siscomercio.managers.DatabaseManager;
import com.siscomercio.security.Auth;
import com.siscomercio.utilities.WindowsUtils;

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
    /**
     * @param args the command line arguments
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception
    {
        //Carrega as Configs
        Config.load();
      
        // Checka  O Processo MySQL esta em Execução.
        WindowsUtils.checkProcess("mysql");

        // Ler a Tabela de Instalacao da DB
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
