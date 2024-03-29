/*
 * To change this template, choose SystemUtil | Templates
 * and open the template in the editor.
 */

/*
 * FramePrincipal.java
 *
 * Created on 02/04/2010, 05:55:51
 */
package com.siscomercio.model.view.frames;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import com.siscomercio.init.Config;

import com.siscomercio.controller.managers.AppManager;
import com.siscomercio.controller.managers.SoundManager;
import com.siscomercio.model.persistence.dao.Banco;
import com.siscomercio.tables.UserTable;
import com.siscomercio.model.security.Auth;
import com.siscomercio.standards.StringTable;
import com.siscomercio.utilities.FrameLog;
import com.siscomercio.utilities.SystemUtil;
import com.siscomercio.utilities.WindowsUtil;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

/**
 * $Revision$
 * $Author$
 * $Date$
 * <p/>
 * @author Rayan
 */
@SuppressWarnings ("serial")
public class FramePrincipal extends JFrame
{
    /**
     * Looger Desse Arquivo
     */
    private static final Logger _log = Logger.getLogger(FramePrincipal.class.getName());
    Date date = new Date();
    StringTable tabela = StringTable.getInstance();
    Config config = Config.getInstance();
    /**
     * if this frame was created of not.
     */
    public static boolean created;

    /**
     * seta a Desktop do Label
     * <p/>
     * @param label
     */
    private void setDesktop(JLabel label)
    {
        _log.log(Level.INFO, "Carregando Imagem: {0}{1}\n", new Object[]
        {
            tabela.getIMAGE_PATH(), config.getLogo()
        });
        File file = new File(tabela.getIMAGE_PATH() + config.getLogo());

        if (file.exists())
        {
            ImageIcon img = new ImageIcon(file.toString());
            if (img.getImage() == null)
            {
                SystemUtil.getInstance().showErrorMsg("nao foi posivel localizar a imagem: " + config.getLogo() + "\n", true);
            }
            else
            {
                label.setIcon(img);
            }
        }
        else
        {
            SystemUtil.getInstance().showErrorMsg("nao foi posivel localizar a imagem: " + config.getLogo() + "\n", true);
        }
    }

    /**
     * Creates new form FramePrincipal
     */
    public FramePrincipal()
    {

        if (config.isDebug())
        {
            _log.info("Montando janela principal do aplicativo. \n");
        }
        initComponents();
        disparaRelogio();
        setSoundProperties();
        preenchaFrame();
        setDesktop(desktop1);
        if (config.isDebug())
        {
            _log.info("Frame Principal Criado, Aguardando Comandos... \n");
        }
        created = true;
        AppManager.getInstance().optimizeRam();
    }

    /**
     * Atualiza as Opcoes do Som no Frame
     */
    private void setSoundProperties()
    {
        if (config.isEnableSound())
        {
            SoundManager.getInstance().playSound(config.getWelcomeSound());
            // Habilita ou Desabilita o Som de Acordo com a Config.
            itemHabilitarSom.setSelected(true);
        }
        else
        {
            itemHabilitarSom.setSelected(false);
        }
    }

    /**
     * Preenche o Frame com as Informacoes nos Labels
     */
    private void preenchaFrame()
    {
        if (config.isEnableSound())
        {
            _log.info("preenchendo frame...\n");
        }
        pcLabel.setText(WindowsUtil.getPcName());
        statusInfo.setText(Banco.getInstance().getConnectionStatus());
        dadosEmpresa.setText(config.getEmpresa());
        labelNomeEmpresa.setText(config.getEmpresa());
        dadosOperador.setText(UserTable.getInstance().getLastUser());
        siteInfo.setText(config.getSite());
        versionInfo.setText(SystemUtil.getVersion());
    }

    /**
     * Dispara o Relogio
     */
    private void disparaRelogio()
    {
        if (config.isEnableSound())
        {
            _log.info("inicializando relogio ...\n");
        }
        // Dispara o Relogio
        Timer timer = new Timer(1000, new ClockListener());
        timer.start();
    }
    /**
     * Classe de Implementacao do Relogio
     */
    class ClockListener implements ActionListener
    {
        // generates a simple date format
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        // generates String that will get the formater info with values
        String dayInfo = df.format(date);

        @Override
        public void actionPerformed(ActionEvent e)
        {
            Calendar now = Calendar.getInstance();
            dadosRelogio.setText(String.format("%1$tH:%1$tM:%1$tS", now));
            dadosData.setText(dayInfo);
        }
    }

    /**
     * Retorna Apenas 1 Instancia dessa Classe
     * <p/>
     * @return AppManager _instance
     */
    public static FramePrincipal getInstance()
    {
        return SingletonHolder._instance;
    }
    @SuppressWarnings ("synthetic-access")
    private static class SingletonHolder
    {
        protected static final FramePrincipal _instance = new FramePrincipal();
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings (
            {
        "unused"
    })
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        jDesktopPane3 = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu6 = new javax.swing.JMenu();
        jMenu7 = new javax.swing.JMenu();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jLabel9 = new javax.swing.JLabel();
        rodape = new javax.swing.JPanel();
        labelData = new javax.swing.JLabel();
        dadosData = new javax.swing.JLabel();
        lalbelHorario = new javax.swing.JLabel();
        dadosRelogio = new javax.swing.JLabel();
        labelEmpresa = new javax.swing.JLabel();
        dadosEmpresa = new javax.swing.JLabel();
        dadosOperador = new javax.swing.JLabel();
        labelOperador = new javax.swing.JLabel();
        pcLabel = new javax.swing.JLabel();
        statusInfo = new javax.swing.JLabel();
        labelStatus = new javax.swing.JLabel();
        labelVersao = new javax.swing.JLabel();
        versionInfo = new javax.swing.JLabel();
        labelSite = new javax.swing.JLabel();
        siteInfo = new javax.swing.JLabel();
        labelTerminal = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        desktop1 = new javax.swing.JLabel();
        labelNomeEmpresa = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        barraDeMenu = new javax.swing.JMenuBar();
        menuSistema = new javax.swing.JMenu();
        itemReiniciar = new javax.swing.JMenuItem();
        itemSair = new javax.swing.JMenuItem();
        menuAdministracao = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        menuInformacoes = new javax.swing.JMenu();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        itemHardware = new javax.swing.JMenu();
        itemMemoria = new javax.swing.JMenuItem();
        itemProcessador = new javax.swing.JMenuItem();
        itemSoftware = new javax.swing.JMenu();
        itemDb = new javax.swing.JMenuItem();
        itemJava = new javax.swing.JMenuItem();
        itemSistemaOperacional = new javax.swing.JMenuItem();
        itemSistema = new javax.swing.JMenu();
        itemRegistro = new javax.swing.JMenuItem();
        itemVersao = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        menuDb = new javax.swing.JMenu();
        itemBancoDados = new javax.swing.JMenuItem();
        menuOpcoes = new javax.swing.JMenu();
        itemHabilitarSom = new javax.swing.JCheckBoxMenuItem();
        itemTrocarSenha = new javax.swing.JMenuItem();
        menuSuporte = new javax.swing.JMenu();
        itemSuporteTecnico = new javax.swing.JMenuItem();

        jMenu4.setText("File");
        jMenuBar1.add(jMenu4);

        jMenu5.setText("Edit");
        jMenuBar1.add(jMenu5);

        jMenu6.setText("File");
        jMenuBar2.add(jMenu6);

        jMenu7.setText("Edit");
        jMenuBar2.add(jMenu7);

        jLabel9.setText("jLabel9");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("SiSCOM - Sistema de Automação Comercial");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        rodape.setBackground(new java.awt.Color(255, 255, 255));
        rodape.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Informações", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        labelData.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelData.setText("Data: ");

        dadosData.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        dadosData.setText("dInfo");

        lalbelHorario.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lalbelHorario.setText("Horario:");

        dadosRelogio.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        dadosRelogio.setText("rInfo");

        labelEmpresa.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelEmpresa.setText("Empresa:");

        dadosEmpresa.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        dadosEmpresa.setText("eInfo");

        dadosOperador.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        dadosOperador.setText("opInfo");

        labelOperador.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelOperador.setText("Operador: ");

        pcLabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        pcLabel.setText("pInforma");

        statusInfo.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        statusInfo.setText("statusInfor");

        labelStatus.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelStatus.setText("Status:");

        labelVersao.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelVersao.setText("Versao: ");

        versionInfo.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        versionInfo.setText("vInfo");

        labelSite.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelSite.setText("Site:");

        siteInfo.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        siteInfo.setText("siteInformation");

        labelTerminal.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelTerminal.setText("Terminal :");

        javax.swing.GroupLayout rodapeLayout = new javax.swing.GroupLayout(rodape);
        rodape.setLayout(rodapeLayout);
        rodapeLayout.setHorizontalGroup(
            rodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rodapeLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(rodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rodapeLayout.createSequentialGroup()
                        .addComponent(lalbelHorario)
                        .addGap(6, 6, 6)
                        .addComponent(dadosRelogio))
                    .addGroup(rodapeLayout.createSequentialGroup()
                        .addComponent(labelData)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dadosData)))
                .addGap(23, 23, 23)
                .addGroup(rodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelEmpresa)
                    .addComponent(labelOperador, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(rodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dadosEmpresa)
                    .addComponent(dadosOperador))
                .addGap(14, 14, 14)
                .addGroup(rodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rodapeLayout.createSequentialGroup()
                        .addComponent(labelTerminal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pcLabel))
                    .addGroup(rodapeLayout.createSequentialGroup()
                        .addComponent(labelStatus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(statusInfo)))
                .addGap(10, 10, 10)
                .addGroup(rodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rodapeLayout.createSequentialGroup()
                        .addComponent(labelVersao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(versionInfo))
                    .addGroup(rodapeLayout.createSequentialGroup()
                        .addComponent(labelSite)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(siteInfo)))
                .addContainerGap(247, Short.MAX_VALUE))
        );
        rodapeLayout.setVerticalGroup(
            rodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rodapeLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(rodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dadosData)
                    .addComponent(dadosEmpresa)
                    .addComponent(labelTerminal)
                    .addComponent(pcLabel)
                    .addComponent(versionInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelEmpresa)
                    .addComponent(labelVersao)
                    .addComponent(labelData))
                .addGap(9, 9, 9)
                .addGroup(rodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lalbelHorario)
                    .addGroup(rodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(dadosRelogio)
                        .addComponent(labelStatus)
                        .addComponent(statusInfo)
                        .addComponent(dadosOperador)
                        .addComponent(labelOperador)
                        .addComponent(labelSite)
                        .addComponent(siteInfo)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        desktop1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        desktop1.setAlignmentX(SwingConstants.CENTER);
        desktop1.setAlignmentY(SwingConstants.CENTER);
        desktop1.setAutoscrolls(true);
        desktop1.setDoubleBuffered(true);
        jPanel2.add(desktop1, java.awt.BorderLayout.CENTER);

        labelNomeEmpresa.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelNomeEmpresa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelNomeEmpresa.setText("nomeEmpresa");
        jPanel2.add(labelNomeEmpresa, java.awt.BorderLayout.PAGE_START);

        jLabel12.setText("jLabel12");

        jLabel13.setText("jLabel13");

        menuSistema.setText("Sistema");
        menuSistema.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        itemReiniciar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_MASK));
        itemReiniciar.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        itemReiniciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/model/view/icones/8437_16x16.png"))); // NOI18N
        itemReiniciar.setText("Reiniciar");
        itemReiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemReiniciarActionPerformed(evt);
            }
        });
        menuSistema.add(itemReiniciar);

        itemSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK));
        itemSair.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        itemSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/model/view/icones/6183_16x16.png"))); // NOI18N
        itemSair.setText("Sair");
        itemSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSairActionPerformed(evt);
            }
        });
        menuSistema.add(itemSair);

        barraDeMenu.add(menuSistema);

        menuAdministracao.setText("Administração");
        menuAdministracao.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jMenuItem1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/model/view/icones/1031_16x16.png"))); // NOI18N
        jMenuItem1.setText("Painel de Administração");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menuAdministracao.add(jMenuItem1);

        barraDeMenu.add(menuAdministracao);

        menuInformacoes.setText("Informações");
        menuInformacoes.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        menuInformacoes.add(jSeparator1);

        itemHardware.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/model/view/icones/10524_16x16.png"))); // NOI18N
        itemHardware.setText("Hardware");
        itemHardware.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        itemMemoria.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        itemMemoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/model/view/icones/10517_16x16.png"))); // NOI18N
        itemMemoria.setText("Memória");
        itemMemoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMemoriaActionPerformed(evt);
            }
        });
        itemHardware.add(itemMemoria);

        itemProcessador.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        itemProcessador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/model/view/icones/985_16x16.png"))); // NOI18N
        itemProcessador.setText("Processador");
        itemProcessador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemProcessadorActionPerformed(evt);
            }
        });
        itemHardware.add(itemProcessador);

        menuInformacoes.add(itemHardware);

        itemSoftware.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/model/view/icones/1720_16x16.png"))); // NOI18N
        itemSoftware.setText("Software");
        itemSoftware.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        itemDb.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        itemDb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/model/view/icones/1023_16x16.png"))); // NOI18N
        itemDb.setText("Banco de Dados");
        itemDb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemDbActionPerformed(evt);
            }
        });
        itemSoftware.add(itemDb);

        itemJava.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        itemJava.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/model/view/icones/9400_16x16.png"))); // NOI18N
        itemJava.setText("Java");
        itemJava.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemJavaActionPerformed(evt);
            }
        });
        itemSoftware.add(itemJava);

        itemSistemaOperacional.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        itemSistemaOperacional.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/model/view/icones/169_16x16.png"))); // NOI18N
        itemSistemaOperacional.setText("Sistema Operacional");
        itemSistemaOperacional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSistemaOperacionalActionPerformed(evt);
            }
        });
        itemSoftware.add(itemSistemaOperacional);

        menuInformacoes.add(itemSoftware);

        itemSistema.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/model/view/icones/8270_16x16.png"))); // NOI18N
        itemSistema.setText("Sistema");
        itemSistema.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        itemRegistro.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.ALT_MASK));
        itemRegistro.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        itemRegistro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/model/view/icones/8271_16x16.png"))); // NOI18N
        itemRegistro.setText("Registro");
        itemRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemRegistroActionPerformed(evt);
            }
        });
        itemSistema.add(itemRegistro);

        itemVersao.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.ALT_MASK));
        itemVersao.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        itemVersao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/model/view/icones/8246_16x16.png"))); // NOI18N
        itemVersao.setText("Versão");
        itemVersao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemVersaoActionPerformed(evt);
            }
        });
        itemSistema.add(itemVersao);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/model/view/icones/8163_16x16.png"))); // NOI18N
        jMenuItem2.setText("Logs");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        itemSistema.add(jMenuItem2);

        menuInformacoes.add(itemSistema);

        barraDeMenu.add(menuInformacoes);

        menuDb.setText("Configurações");
        menuDb.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        itemBancoDados.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.ALT_MASK));
        itemBancoDados.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        itemBancoDados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/model/view/icones/10692_16x16.png"))); // NOI18N
        itemBancoDados.setText("Banco de Dados");
        itemBancoDados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemBancoDadosActionPerformed(evt);
            }
        });
        menuDb.add(itemBancoDados);

        barraDeMenu.add(menuDb);

        menuOpcoes.setText("Opções");
        menuOpcoes.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        itemHabilitarSom.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        itemHabilitarSom.setSelected(true);
        itemHabilitarSom.setText("       Habilitar Som");
        itemHabilitarSom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/model/view/icones/10521_16x16.png"))); // NOI18N
        itemHabilitarSom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemHabilitarSomActionPerformed(evt);
            }
        });
        menuOpcoes.add(itemHabilitarSom);

        itemTrocarSenha.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        itemTrocarSenha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/model/view/icones/11254_16x16.png"))); // NOI18N
        itemTrocarSenha.setText("      Trocar Senha");
        itemTrocarSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemTrocarSenhaActionPerformed(evt);
            }
        });
        menuOpcoes.add(itemTrocarSenha);

        barraDeMenu.add(menuOpcoes);

        menuSuporte.setText("Suporte");
        menuSuporte.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        itemSuporteTecnico.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        itemSuporteTecnico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/model/view/icones/8405_16x16.png"))); // NOI18N
        itemSuporteTecnico.setText("Contato");
        itemSuporteTecnico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSuporteTecnicoActionPerformed(evt);
            }
        });
        menuSuporte.add(itemSuporteTecnico);

        barraDeMenu.add(menuSuporte);

        setJMenuBar(barraDeMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rodape, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addComponent(rodape, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(800, 534));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void itemSairActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemSairActionPerformed
    {//GEN-HEADEREND:event_itemSairActionPerformed

        AppManager.getInstance().requestAppShutdown(FramePrincipal.this);

    }//GEN-LAST:event_itemSairActionPerformed

    private void itemReiniciarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemReiniciarActionPerformed
    {//GEN-HEADEREND:event_itemReiniciarActionPerformed
        if (config.isEnableSound())
        {
            SoundManager.getInstance().playSound(config.getPreRestartSound());
        }

        if (config.isDebug())
        {
            _log.info("solicitacao de restart.");
        }

        int selectedOption = JOptionPane.showConfirmDialog(null, "Reiniciar Sistema ?", "Pergunta", JOptionPane.OK_CANCEL_OPTION);

        if (selectedOption == JOptionPane.OK_OPTION)
        {
            if (config.isEnableSound())
            {
                SoundManager.getInstance().playSound(config.getRestartSound());
            }

            if (config.isDebug())
            {
                _log.info("usuario reiniciou o sistema.");
            }

            //retorna o Status de Autenticidade.
            Auth.getInstance().setAutenticado(false);

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
        else
        {
            if (config.isDebug())
            {
                _log.info("usuario desiste de reiniciar o sistema.");
            }
        }
    }//GEN-LAST:event_itemReiniciarActionPerformed

    private void itemProcessadorActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemProcessadorActionPerformed
    {//GEN-HEADEREND:event_itemProcessadorActionPerformed
        SystemUtil.getInstance().printCpuInfo();
    }//GEN-LAST:event_itemProcessadorActionPerformed

    private void itemMemoriaActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemMemoriaActionPerformed
    {//GEN-HEADEREND:event_itemMemoriaActionPerformed
        WindowsUtil.printMemUsage();
    }//GEN-LAST:event_itemMemoriaActionPerformed

    private void itemSistemaOperacionalActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemSistemaOperacionalActionPerformed
    {//GEN-HEADEREND:event_itemSistemaOperacionalActionPerformed
        SystemUtil.getInstance().printOSInfo();
    }//GEN-LAST:event_itemSistemaOperacionalActionPerformed

    private void itemJavaActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemJavaActionPerformed
    {//GEN-HEADEREND:event_itemJavaActionPerformed
        SystemUtil.getInstance().printJvmInfo();
    }//GEN-LAST:event_itemJavaActionPerformed

    private void itemVersaoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemVersaoActionPerformed
    {//GEN-HEADEREND:event_itemVersaoActionPerformed
        SystemUtil.getInstance().printVersion();

    }//GEN-LAST:event_itemVersaoActionPerformed

    private void itemBancoDadosActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemBancoDadosActionPerformed
    {//GEN-HEADEREND:event_itemBancoDadosActionPerformed
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new UserLogon().setVisible(true);
            }
        });

    }//GEN-LAST:event_itemBancoDadosActionPerformed

    private void itemRegistroActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemRegistroActionPerformed
    {//GEN-HEADEREND:event_itemRegistroActionPerformed
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new RegistryInfo().setVisible(true);
            }
        });
    }//GEN-LAST:event_itemRegistroActionPerformed

    private void itemDbActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemDbActionPerformed
    {//GEN-HEADEREND:event_itemDbActionPerformed
        try
        {
            SystemUtil.getInstance().printDbInfo();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(FramePrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_itemDbActionPerformed

    private void itemHabilitarSomActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemHabilitarSomActionPerformed
    {//GEN-HEADEREND:event_itemHabilitarSomActionPerformed

        if (itemHabilitarSom.isSelected())
        {
            config.setEnableSound(true);
        }
        else
        {
            config.setEnableSound(false);
        }
    }//GEN-LAST:event_itemHabilitarSomActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
        AppManager.getInstance().requestAppShutdown(FramePrincipal.this);
    }//GEN-LAST:event_formWindowClosing

    private void itemTrocarSenhaActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemTrocarSenhaActionPerformed
    {//GEN-HEADEREND:event_itemTrocarSenhaActionPerformed
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new TrocaSenha().setVisible(true);
            }
        });
    }//GEN-LAST:event_itemTrocarSenhaActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem1ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem1ActionPerformed
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                //   AppManager.setTema(FramePrincipal.class.getSimpleName());
                new AdminLogin().setVisible(true);
            }
        });
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void itemSuporteTecnicoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemSuporteTecnicoActionPerformed
    {//GEN-HEADEREND:event_itemSuporteTecnicoActionPerformed
        AppManager.getInstance().implementar();
    }//GEN-LAST:event_itemSuporteTecnicoActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                //  AppManager.setTema(FramePrincipal.class.getSimpleName());
                new FrameLog().setVisible(true);
            }
        });
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    /**
     *
     * @param args
     */
    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                //   AppManager.setTema(FramePrincipal.class.getSimpleName());
                new FramePrincipal().setVisible(true);
            }
        });
    }
    JLabel lblClock;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar barraDeMenu;
    private javax.swing.JLabel dadosData;
    private javax.swing.JLabel dadosEmpresa;
    private javax.swing.JLabel dadosOperador;
    private javax.swing.JLabel dadosRelogio;
    private javax.swing.JLabel desktop1;
    private javax.swing.JMenuItem itemBancoDados;
    private javax.swing.JMenuItem itemDb;
    private javax.swing.JCheckBoxMenuItem itemHabilitarSom;
    private javax.swing.JMenu itemHardware;
    private javax.swing.JMenuItem itemJava;
    private javax.swing.JMenuItem itemMemoria;
    private javax.swing.JMenuItem itemProcessador;
    private javax.swing.JMenuItem itemRegistro;
    private javax.swing.JMenuItem itemReiniciar;
    private javax.swing.JMenuItem itemSair;
    private javax.swing.JMenu itemSistema;
    private javax.swing.JMenuItem itemSistemaOperacional;
    private javax.swing.JMenu itemSoftware;
    private javax.swing.JMenuItem itemSuporteTecnico;
    private javax.swing.JMenuItem itemTrocarSenha;
    private javax.swing.JMenuItem itemVersao;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JDesktopPane jDesktopPane3;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel labelData;
    private javax.swing.JLabel labelEmpresa;
    private javax.swing.JLabel labelNomeEmpresa;
    private javax.swing.JLabel labelOperador;
    private javax.swing.JLabel labelSite;
    private javax.swing.JLabel labelStatus;
    private javax.swing.JLabel labelTerminal;
    private javax.swing.JLabel labelVersao;
    private javax.swing.JLabel lalbelHorario;
    private javax.swing.JMenu menuAdministracao;
    private javax.swing.JMenu menuDb;
    private javax.swing.JMenu menuInformacoes;
    private javax.swing.JMenu menuOpcoes;
    private javax.swing.JMenu menuSistema;
    private javax.swing.JMenu menuSuporte;
    private javax.swing.JLabel pcLabel;
    private javax.swing.JPanel rodape;
    private javax.swing.JLabel siteInfo;
    private javax.swing.JLabel statusInfo;
    private javax.swing.JLabel versionInfo;
    // End of variables declaration//GEN-END:variables
}
