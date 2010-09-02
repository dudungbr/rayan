/*
 * To change this template, choose SystemUtil | Templates
 * and open the template in the editor.
 */

/*
 * FramePrincipal.java
 *
 * Created on 02/04/2010, 05:55:51
 */
package com.siscomercio.frames;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
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
import com.siscomercio.Config;

import com.siscomercio.managers.AppManager;
import com.siscomercio.managers.DatabaseManager;
import com.siscomercio.managers.SoundManager;
import com.siscomercio.tables.UserTable;
import com.siscomercio.security.Auth;
import com.siscomercio.tables.StringTable;
import com.siscomercio.utilities.SystemUtil;
import com.siscomercio.utilities.WindowsUtil;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * $Revision$
 * $Author$
 * $Date$
 * @author Rayan
 */
@SuppressWarnings("serial")
public class FramePrincipal extends JFrame
{

    /**
     * Looger Desse Arquivo
     */
    private static final Logger _log = Logger.getLogger(FramePrincipal.class.getName());
    Date date = new Date();
    /**
     * if this frame was created of not.
     */
    public static boolean created;



    /**
     * seta  a Desktop do Label
     * @param label
     */
    private void setDesktop(JLabel label)
    {
        _log.info("Carregando Imagem: " + StringTable.IMAGE_PATH + Config.LOGO + "\n");
        File file = new File(StringTable.IMAGE_PATH + Config.LOGO);

        if(file.exists())
        {
            ImageIcon img = new ImageIcon(file.toString());
            if(img != null)
                label.setIcon(img);
            else
                SystemUtil.showErrorMsg("nao foi posivel localizar a imagem: " + Config.LOGO + "\n");
        }
        else
        {
            SystemUtil.showErrorMsg("nao foi posivel localizar a imagem: " + Config.LOGO + "\n");
        }
    }

    /** Creates new form FramePrincipal */
    public FramePrincipal()
    {
        if(!Config._loaded)
        Config.load();
        if(Config.DEBUG)
            _log.info("montando janela principal do aplicativo. \n");
        initComponents();
        disparaRelogio();
        setSoundProperties();
        preenchaFrame();
        setDesktop(jLabel5);
        created = true;

    }

    /**
     * Atualiza as Opcoes do Som no Frame
     */
    private void setSoundProperties()
    {
        if(Config.ENABLE_SOUND)
        {
            SoundManager.playSound(Config.WELCOME_SOUND);
            // Habilita ou Desabilita o Som de Acordo com a Config.
            jCheckBoxMenuItem1.setSelected(true);
        }
        else
            jCheckBoxMenuItem1.setSelected(false);
    }

    /**
     * Preenche o Frame com as Informacoes nos Labels
     */
    private void preenchaFrame()
    {
        if(Config.ENABLE_SOUND)
            _log.info("preenchendo frame...\n");
        pcLabel.setText(WindowsUtil.getPcName());
        statusInfo.setText(DatabaseManager.getConnectionStatus());
        dadosEmpresa.setText(Config.EMPRESA);
        jLabel11.setText(Config.EMPRESA);
        dadosOperador.setText(UserTable.getInstance().getLastUser());
        siteInfo.setText(Config.SITE);
        versionInfo.setText(SystemUtil.getVersion());
    }

    /**
     * Dispara o Relogio
     */
    private void disparaRelogio()
    {
        if(Config.ENABLE_SOUND)
            _log.info("inicializando relogio ...\n");
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
     * @return AppManager _instance
     */
    public static FramePrincipal getInstance()
    {
        return SingletonHolder._instance;
    }

    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder
    {
        protected static final FramePrincipal _instance = new FramePrincipal();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings(
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
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        dadosData = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        dadosRelogio = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        dadosEmpresa = new javax.swing.JLabel();
        dadosOperador = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        pcLabel = new javax.swing.JLabel();
        statusInfo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        versionInfo = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        siteInfo = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        barraDeMenu = new javax.swing.JMenuBar();
        menuSistema = new javax.swing.JMenu();
        itemRegistro = new javax.swing.JMenuItem();
        itemReiniciar = new javax.swing.JMenuItem();
        itemSair = new javax.swing.JMenuItem();
        subMenuInformacoes = new javax.swing.JMenu();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuHardware = new javax.swing.JMenu();
        itemMemoria = new javax.swing.JMenuItem();
        itemProcessador = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        itemDb = new javax.swing.JMenuItem();
        itemJava = new javax.swing.JMenuItem();
        itemSistemaOperacional = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        itemVersao = new javax.swing.JMenuItem();
        menuAdministracao = new javax.swing.JMenu();
        jMenu9 = new javax.swing.JMenu();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        itemExcluir = new javax.swing.JMenuItem();
        jMenu10 = new javax.swing.JMenu();
        menuDb = new javax.swing.JMenu();
        subMenuDB = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        menuSuporte = new javax.swing.JMenu();
        itemAssistenciaTecnica = new javax.swing.JMenuItem();

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
        setTitle("AutSiS- Sistema de Automacao Comercial");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Informações", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel3.setText("Data: ");

        dadosData.setText("dInfo");

        jLabel2.setText("Horario:");

        dadosRelogio.setText("rInfo");

        jLabel4.setText("Empresa:");

        dadosEmpresa.setText("eInfo");

        dadosOperador.setText("opInfo");

        jLabel6.setText("Operador: ");

        pcLabel.setText("pInforma");

        statusInfo.setText("sInfo");

        jLabel1.setText("Status:");

        jLabel7.setText("Versao: ");

        versionInfo.setText("vInfo");

        jLabel10.setText("Site:");

        siteInfo.setText("siteInfo");

        jLabel8.setText("Terminal :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(6, 6, 6)
                        .addComponent(dadosRelogio)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel6)
                        .addGap(6, 6, 6)
                        .addComponent(dadosOperador))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(6, 6, 6)
                        .addComponent(dadosData)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dadosEmpresa)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel1))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pcLabel)
                    .addComponent(statusInfo))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(versionInfo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(22, 22, 22)
                        .addComponent(siteInfo)))
                .addContainerGap(276, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(dadosData))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dadosEmpresa)
                            .addComponent(jLabel4)
                            .addComponent(jLabel8)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(versionInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pcLabel)))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(dadosRelogio)
                    .addComponent(jLabel6)
                    .addComponent(dadosOperador)
                    .addComponent(jLabel1)
                    .addComponent(statusInfo)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(siteInfo)
                        .addComponent(jLabel10)))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setAlignmentX(SwingConstants.CENTER);
        jLabel5.setAlignmentY(SwingConstants.CENTER);
        jLabel5.setAutoscrolls(true);
        jLabel5.setDoubleBuffered(true);
        jPanel2.add(jLabel5, java.awt.BorderLayout.CENTER);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("nomeEmpresa");
        jPanel2.add(jLabel11, java.awt.BorderLayout.PAGE_START);

        jLabel12.setText("jLabel12");

        jLabel13.setText("jLabel13");

        menuSistema.setText("Sistema");

        itemRegistro.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.ALT_MASK));
        itemRegistro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/icones/8271_16x16.png"))); // NOI18N
        itemRegistro.setText("Registro");
        itemRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemRegistroActionPerformed(evt);
            }
        });
        menuSistema.add(itemRegistro);

        itemReiniciar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_MASK));
        itemReiniciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/icones/8437_16x16.png"))); // NOI18N
        itemReiniciar.setText("Reiniciar");
        itemReiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemReiniciarActionPerformed(evt);
            }
        });
        menuSistema.add(itemReiniciar);

        itemSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK));
        itemSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/icones/6980_16x16.png"))); // NOI18N
        itemSair.setText("Sair");
        itemSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSairActionPerformed(evt);
            }
        });
        menuSistema.add(itemSair);

        barraDeMenu.add(menuSistema);

        subMenuInformacoes.setText("Informações");
        subMenuInformacoes.add(jSeparator1);

        menuHardware.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/icones/10524_16x16.png"))); // NOI18N
        menuHardware.setText("Hardware");

        itemMemoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/icones/10517_16x16.png"))); // NOI18N
        itemMemoria.setText("Memória");
        itemMemoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMemoriaActionPerformed(evt);
            }
        });
        menuHardware.add(itemMemoria);

        itemProcessador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/icones/985_16x16.png"))); // NOI18N
        itemProcessador.setText("Processador");
        itemProcessador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemProcessadorActionPerformed(evt);
            }
        });
        menuHardware.add(itemProcessador);

        subMenuInformacoes.add(menuHardware);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/icones/9719_16x16.png"))); // NOI18N
        jMenu2.setText("Software");

        itemDb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/icones/1023_16x16.png"))); // NOI18N
        itemDb.setText("Banco de Dados");
        itemDb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemDbActionPerformed(evt);
            }
        });
        jMenu2.add(itemDb);

        itemJava.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/icones/9400_16x16.png"))); // NOI18N
        itemJava.setText("Java");
        itemJava.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemJavaActionPerformed(evt);
            }
        });
        jMenu2.add(itemJava);

        itemSistemaOperacional.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/icones/1080_16x16.png"))); // NOI18N
        itemSistemaOperacional.setText("Sistema Operacional");
        itemSistemaOperacional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSistemaOperacionalActionPerformed(evt);
            }
        });
        jMenu2.add(itemSistemaOperacional);

        subMenuInformacoes.add(jMenu2);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/icones/6957_16x16.png"))); // NOI18N
        jMenu1.setText("Sistema");

        itemVersao.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.ALT_MASK));
        itemVersao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/icones/8246_16x16.png"))); // NOI18N
        itemVersao.setText("Versão");
        itemVersao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemVersaoActionPerformed(evt);
            }
        });
        jMenu1.add(itemVersao);

        subMenuInformacoes.add(jMenu1);

        barraDeMenu.add(subMenuInformacoes);

        menuAdministracao.setText("Administração");
        menuAdministracao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAdministracaoActionPerformed(evt);
            }
        });

        jMenu9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/icones/6986_16x16.png"))); // NOI18N
        jMenu9.setText("Financeiro");
        menuAdministracao.add(jMenu9);

        jMenu8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/icones/6475_16x16.png"))); // NOI18N
        jMenu8.setText("Cadastro de Usuarios");

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/icones/6420_16x16.png"))); // NOI18N
        jMenuItem5.setText("Adicionar");
        jMenu8.add(jMenuItem5);

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/icones/6439_16x16.png"))); // NOI18N
        jMenuItem1.setText("Editar");
        jMenu8.add(jMenuItem1);

        itemExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/icones/6464_16x16.png"))); // NOI18N
        itemExcluir.setText("Excluir");
        jMenu8.add(itemExcluir);

        menuAdministracao.add(jMenu8);

        jMenu10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/icones/4317_16x16.png"))); // NOI18N
        jMenu10.setText("Auditoria");
        menuAdministracao.add(jMenu10);

        barraDeMenu.add(menuAdministracao);

        menuDb.setText("Configurações");

        subMenuDB.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.ALT_MASK));
        subMenuDB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/icones/1023_16x16.png"))); // NOI18N
        subMenuDB.setText("Banco de Dados");
        subMenuDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subMenuDBActionPerformed(evt);
            }
        });
        menuDb.add(subMenuDB);

        barraDeMenu.add(menuDb);

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("       Habilitar Som");
        jCheckBoxMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/icones/10521_16x16.png"))); // NOI18N
        jCheckBoxMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jCheckBoxMenuItem1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/icones/7724_16x16.png"))); // NOI18N
        jMenuItem2.setText("      Trocar Senha");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        barraDeMenu.add(jMenu3);

        menuSuporte.setText("Suporte");

        itemAssistenciaTecnica.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.ALT_MASK));
        itemAssistenciaTecnica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/icones/8405_16x16.png"))); // NOI18N
        itemAssistenciaTecnica.setText("Assistência Técnica");
        itemAssistenciaTecnica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAssistenciaTecnicaActionPerformed(evt);
            }
        });
        menuSuporte.add(itemAssistenciaTecnica);

        barraDeMenu.add(menuSuporte);

        setJMenuBar(barraDeMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-761)/2, (screenSize.height-534)/2, 761, 534);
    }// </editor-fold>//GEN-END:initComponents

    private void itemSairActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemSairActionPerformed
    {//GEN-HEADEREND:event_itemSairActionPerformed

        AppManager.getInstance().requestAppShutdown(FramePrincipal.this);

    }//GEN-LAST:event_itemSairActionPerformed

    private void itemReiniciarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemReiniciarActionPerformed
    {//GEN-HEADEREND:event_itemReiniciarActionPerformed
        if(Config.ENABLE_SOUND)
            SoundManager.playSound(Config.PRE_RESTART_SOUND);

        if(Config.DEBUG)
            _log.info("solicitacao de restart.");

        int selectedOption = JOptionPane.showConfirmDialog(null, "Reiniciar Sistema ?", "Pergunta", JOptionPane.OK_CANCEL_OPTION);

        if(selectedOption == JOptionPane.OK_OPTION)
        {
            if(Config.ENABLE_SOUND)
                SoundManager.playSound(Config.RESTART_SOUND);

            if(Config.DEBUG)
                _log.info("usuario reiniciou o sistema.");

            //retorna o Status de Autenticidade.
            Auth._autenticado = false;

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
            if(Config.DEBUG)
                _log.info("usuario desiste de reiniciar o sistema.");
            return;
        }
    }//GEN-LAST:event_itemReiniciarActionPerformed

    private void itemProcessadorActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemProcessadorActionPerformed
    {//GEN-HEADEREND:event_itemProcessadorActionPerformed
        SystemUtil.printCpuInfo();
    }//GEN-LAST:event_itemProcessadorActionPerformed

    private void itemMemoriaActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemMemoriaActionPerformed
    {//GEN-HEADEREND:event_itemMemoriaActionPerformed
        WindowsUtil.printMemUsage();
    }//GEN-LAST:event_itemMemoriaActionPerformed

    private void itemSistemaOperacionalActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemSistemaOperacionalActionPerformed
    {//GEN-HEADEREND:event_itemSistemaOperacionalActionPerformed
        SystemUtil.printOSInfo();
    }//GEN-LAST:event_itemSistemaOperacionalActionPerformed

    private void itemJavaActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemJavaActionPerformed
    {//GEN-HEADEREND:event_itemJavaActionPerformed
        SystemUtil.printJvmInfo();
    }//GEN-LAST:event_itemJavaActionPerformed

    private void itemVersaoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemVersaoActionPerformed
    {//GEN-HEADEREND:event_itemVersaoActionPerformed
        SystemUtil.printVersion();

    }//GEN-LAST:event_itemVersaoActionPerformed

    private void subMenuDBActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_subMenuDBActionPerformed
    {//GEN-HEADEREND:event_subMenuDBActionPerformed
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new LogonFrame().setVisible(true);
            }

        });

    }//GEN-LAST:event_subMenuDBActionPerformed

    private void itemRegistroActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemRegistroActionPerformed
    {//GEN-HEADEREND:event_itemRegistroActionPerformed
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new LicenseFrame().setVisible(true);
            }

        });
    }//GEN-LAST:event_itemRegistroActionPerformed

    private void itemAssistenciaTecnicaActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemAssistenciaTecnicaActionPerformed
    {//GEN-HEADEREND:event_itemAssistenciaTecnicaActionPerformed
        SystemUtil.showMsg("Suporte Técnico: 8695-1664");
    }//GEN-LAST:event_itemAssistenciaTecnicaActionPerformed

    private void itemDbActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemDbActionPerformed
    {//GEN-HEADEREND:event_itemDbActionPerformed
        try
        {
            SystemUtil.printDbInfo();
        }
        catch(SQLException ex)
        {
            Logger.getLogger(FramePrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_itemDbActionPerformed

    private void menuAdministracaoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_menuAdministracaoActionPerformed
    {//GEN-HEADEREND:event_menuAdministracaoActionPerformed
    }//GEN-LAST:event_menuAdministracaoActionPerformed

    private void jCheckBoxMenuItem1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBoxMenuItem1ActionPerformed
    {//GEN-HEADEREND:event_jCheckBoxMenuItem1ActionPerformed

        if(jCheckBoxMenuItem1.isSelected())
            Config.ENABLE_SOUND = true;
        else
            Config.ENABLE_SOUND = false;
    }//GEN-LAST:event_jCheckBoxMenuItem1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
        AppManager.getInstance().requestAppShutdown(FramePrincipal.this);
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem2ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem2ActionPerformed
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new PassChangeFrame().setVisible(true);
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
                 try
                {
                    //OfficeBlue
                    UIManager.setLookAndFeel(new AcrylLookAndFeel());
                   
                }
                catch(Exception e)
                {
                    SystemUtil.showErrorMsg("Nao Foi Possivel Carregar a Skin");
                }
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
    private javax.swing.JMenuItem itemAssistenciaTecnica;
    private javax.swing.JMenuItem itemDb;
    private javax.swing.JMenuItem itemExcluir;
    private javax.swing.JMenuItem itemJava;
    private javax.swing.JMenuItem itemMemoria;
    private javax.swing.JMenuItem itemProcessador;
    private javax.swing.JMenuItem itemRegistro;
    private javax.swing.JMenuItem itemReiniciar;
    private javax.swing.JMenuItem itemSair;
    private javax.swing.JMenuItem itemSistemaOperacional;
    private javax.swing.JMenuItem itemVersao;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JDesktopPane jDesktopPane3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenu menuAdministracao;
    private javax.swing.JMenu menuDb;
    private javax.swing.JMenu menuHardware;
    private javax.swing.JMenu menuSistema;
    private javax.swing.JMenu menuSuporte;
    private javax.swing.JLabel pcLabel;
    private javax.swing.JLabel siteInfo;
    private javax.swing.JLabel statusInfo;
    private javax.swing.JMenuItem subMenuDB;
    private javax.swing.JMenu subMenuInformacoes;
    private javax.swing.JLabel versionInfo;
    // End of variables declaration//GEN-END:variables
}
