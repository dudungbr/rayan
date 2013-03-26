/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio.init;

import com.siscomercio.model.view.frames.FrameSplashScreen;
import com.siscomercio.controller.managers.ExceptionManager;
import com.siscomercio.controller.managers.LogManager;
import com.siscomercio.controller.managers.SoundManager;
import com.siscomercio.model.persistence.dao.Banco;
import com.siscomercio.model.security.Auth;
import com.siscomercio.model.view.frames.DBConfig;
import com.siscomercio.model.view.frames.FrameLicenca;
import com.siscomercio.utilities.Utilitarios;
import java.awt.EventQueue;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.logging.Logger;
import javax.swing.JFrame;
import sun.jvmstat.monitor.HostIdentifier;
import sun.jvmstat.monitor.MonitorException;
import sun.jvmstat.monitor.MonitoredHost;
import sun.jvmstat.monitor.MonitoredVm;
import sun.jvmstat.monitor.MonitoredVmUtil;
import sun.jvmstat.monitor.VmIdentifier;

/**
 * $Revision: 679 $ $Date: 2013-03-21 19:34:43 -0300 (Thu, 21 Mar 2013) $
 * $Author: rayanrpg $ $HeadURL:
 * https://rayan.googlecode.com/svn/trunk/Wcom/src/com/siscomercio/init/Boot.java
 * $
 *
 * @author Rayan
 */
public class Boot extends JFrame
{
    private static final Logger _log = Logger.getLogger(Boot.class.getName());
    private static final long serialVersionUID = 1L;
    private FrameSplashScreen fss = new FrameSplashScreen();
    Banco banco;
    Config config;

    private Boot()
    {
        this.banco = Banco.getInstance();
        this.config = Config.getInstance();


        // Inicializa o Log Monitor
        // ---------------
        if (config.isEnableLog())
        {
            LogManager.init();
        }
        // Carrega Sons
        SoundManager.getInstance();

        RuntimeMXBean rt = ManagementFactory.getRuntimeMXBean();
        final int runtimePid = Integer.parseInt(rt.getName().substring(0, rt.getName().indexOf("@")));

        if (config.isEnableLog())
        {
            _log.info("Verificando a Existencia de Outra Instancia da Aplicacao...");
        }

        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                // se existe outra instância, mostra a mensagem e finaliza a instância atual.
                // Caso contrário inicia a aplicação.
                if (getMonitoredVMs(runtimePid))
                {
                    inicializaApp();
                }
                else
                {
                    String msg = "Impossivel Iniciar Novamente!";
                    _log.warning(msg);
                    Utilitarios.showWarningMessage(msg);
                    System.exit(0);
                }
            }
        });
    }

    /**
     * Inicializa a App...
     */
    private void inicializaApp()
    {
        if (config.isDebug())
        {
            _log.info("Iniciando aplicativo...");
        }
        new Thread()
        {
            @Override
            public void run()
            {
                //mostra a percentagem de progresso no carregamento.
                //   getBarra().setStringPainted(true);
                int i = 0;
                while (i <= 30)
                {
                    fss.getBarraProgresso().setValue(i);

                    switch (i)
                    {
                        case 10:
                        {
                            //Abre Splash Screen e Seta o Valor da Versão do Sistema.
                            fss.getLabelInformacao().setText("Inicializando Aplicação...");
                            fss.setVisible(true);
                            fss.getLabelVersao().setText(String.valueOf(Config.getSystemVersion()));

                            break;
                        }
                        case 20:
                        {
                            //Verifica o Processo MySQL Rodando na Maquina Local
                            fss.getLabelInformacao().setText("Verificando Servidor de Banco de Dados....");
                            String processo = "mysql";

                            if (!config.isDeveloper())
                            {
                                if (!Utilitarios.checkIfProcessIsRunning(processo))
                                {
                                    Utilitarios.showErrorMessage("<br>O Servidor de Banco de Dados nao está em Execução!<br> O Sistema Foi Finalizado!");
                                    System.exit(0);
                                }
                            }
                            else
                            {
                                Utilitarios.showWarningMessage("Voce Iniciou o Sistema em Modo Developer! \n caso nao seja desenvolvedor desabilite essa configuração.!");
                            }
                            break;
                        }
                        case 30:
                        {

                            fss.getLabelInformacao().setText("Verificando Database...");
                            if (config.isDeveloper())
                            {
                                _log.info("Lendo Tabela de Instalacao do Banco ...");
                            }

                            // Lê a Tabela de Instalacao da DB
                            //--------------------------------
                            banco.readInstallationState();

                            // Abre o Frame de instalacao da DB caso nao a db nao esteja instalada.
                            // --------------------------------------
                            if (!banco.getInstalled())
                            {
                                _log.info("Database nao Instalada, Abrindo Instalador Banco Dados...");
                                EventQueue.invokeLater(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        DBConfig.getInstance().setVisible(true);
                                    }
                                });
                            }
                            else
                            {
                                // Caso a DB Esteja Instalada Verifica Tabelas
                                // ------------------------------------------------
                                fss.getLabelInformacao().setText("Verificando Database...");
                                if (banco.atualizaDatabase())//-> Caso Passe o Banco Estara Instalado.
                                {
                                    //
                                }
                                else
                                {
                                    Utilitarios.showErrorMessage("Nao Foi Possivel Atualizar a Base de Dados.");
                                    System.exit(0);
                                }
                            }
                            break;
                        }
                        case 40:
                        {
                            fss.getLabelInformacao().setText("Verificando Licenca...");
                            if (config.isDeveloper())
                            {
                                _log.info("Verificando Licenca...");
                            }
                            //Le os Dados da Licenca
                            // --------------------------
                            banco.readLicenseData();


                            if (Banco.getInstance().getLicensed())
                            {
                                // OK! Podemos Abrir o Sistema.
                                // ------------------------
                                EventQueue.invokeLater(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        fss.getLabelInformacao().setText("Abrindo Aplicativo Principal....");
                                        if (config.isDeveloper())
                                        {
                                            _log.finest("Fim do Boot.");
                                        }
                                        //fecha a Splash Screen
                                        fss.dispose();
                                        Auth.getInstance().setVisible(true);
                                        // FrameLogin.getInstance().setVisible(true);
                                    }
                                });

                                //fecha a tela de splash
                                dispose();
                            }
                            //App Nao Licenciada, Abre o Frame de Licenca.
                            // -------------------------------------------
                            else
                            {
                                Utilitarios.showPlainMessage("Aplicação Não Licenciada, por Favor Insira seu Numero de Registro");
                                EventQueue.invokeLater(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        new FrameLicenca().setVisible(true);
                                    }
                                });
                            }
                        }
                        ///   TabelaCidade.getInstance();
                        //  TabelaEstado.getInstance();

                        //  IdleManager.getInstance().checaTempoOcioso();
                        //SoundManager.getInstance();
                        //new FileMonitor(2000);
                        break;
                    }
                    i++;
                }
            }
        }.start();
    }

    /**
     *
     * @param processPid
     * <p/>
     * @return
     */
    private static boolean getMonitoredVMs(int processPid)
    {
        MonitoredVm mvm;
        MonitoredHost host = null;
        Set vms = null;
        try
        {
            host = MonitoredHost.getMonitoredHost(new HostIdentifier((String) null));
            vms = host.activeVms();
        }
        catch (URISyntaxException ex)
        {
            _log.severe(ex.getMessage());
            ExceptionManager.ThrowException("Erro ao Monitorar JVM", ex);

        }
        catch (MonitorException ex)
        {
            _log.severe(ex.getMessage());
            ExceptionManager.ThrowException("Erro ao Monitorar JVM", ex);

        }

        String processName = null;
        try
        {
            mvm = host.getMonitoredVm(new VmIdentifier(String.valueOf(processPid)));
            processName = MonitoredVmUtil.commandLine(mvm);
            processName = processName.substring(processName.lastIndexOf("\\") + 1, processName.length());
            mvm.detach();
        }
        catch (URISyntaxException | MonitorException ex)
        {
            _log.severe(ex.getMessage());
            ExceptionManager.ThrowException("Erro ao Monitorar JVM", ex);
        }
        // Essa linha é somente para verificar o nome do processo aberto. Pode ser retirada
        //JOptionPane.showMessageDialog(null, processName);
        for (Object vmid : vms)
        {
            if (vmid instanceof Integer)
            {
                int pid = ((Integer) vmid).intValue();
                String name = vmid.toString(); // default to pid if name not available
                try
                {
                    mvm = host.getMonitoredVm(new VmIdentifier(name));

                    // use the command line as the display name
                    name = MonitoredVmUtil.commandLine(mvm);
                    name = name.substring(name.lastIndexOf("\\") + 1, name.length());
                    mvm.detach();
                    if ((name.equalsIgnoreCase(processName)) && (processPid != pid))
                    {
                        return false;
                    }
                }
                catch (URISyntaxException | MonitorException ex)
                {
                    _log.severe(ex.getMessage());
                    ExceptionManager.ThrowException("Erro ao Monitorar JVM", ex);
                }
            }
        }

        return true;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Boot boot = new Boot();
    }
//
//    /**
//     * @return the fss
//     */
//    public FrameSplashScreen getFss()
//    {
//        return fss;
//    }
//    /**
//     * @param fss the fss to set
//     */
//    public void setFss(FrameSplashScreen fss)
//    {
//        this.fss = fss;
//    }
}
