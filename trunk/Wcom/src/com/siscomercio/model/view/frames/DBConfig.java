/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FrameDatabase.java
 *
 * Created on 04/04/2010, 14:55:29
 */
package com.siscomercio.model.view.frames;

import java.util.logging.Logger;
import javax.swing.JFrame;
import com.siscomercio.Config;
import com.siscomercio.controller.managers.AppManager;
import com.siscomercio.controller.managers.DatabaseManager;
import java.awt.EventQueue;

/**
 * $Revision$
 * $Author$
 * $Date$
 *
 * @author Rayan
 *
 */
@SuppressWarnings("serial")
public class DBConfig extends JFrame
{
    private static Logger _log = Logger.getLogger(DBConfig.class.getName());

    /** Creates new form FrameDatabase */
    public DBConfig()
    {
        initComponents();
        dadosStatus.setText(DatabaseManager.getConnectionStatus());

        //o usuario esta reconfigurando a db.
        if(DatabaseManager._installed==1)
            botaoInstalar.setEnabled(false);
        else
            botaoDeletar.setEnabled(false);
        if(DatabaseManager.isDbDeleted)
            botaoDeletar.setEnabled(false);
        if(Config.isDebug())
            _log.info("montando janela de configuracao de database \n");
    }

    /**
     * retorna apenas uma instancia dessa classe.
     * @return SingletonHolder._instance
     */
    public static DBConfig getInstance()
    {
        return SingletonHolder._instance;
    }

    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder
    {
        protected static final DBConfig _instance = new DBConfig();
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        painel = new javax.swing.JPanel();
        botaoDeletar = new javax.swing.JButton();
        botaoInstalar = new javax.swing.JButton();
        botaoCancelar = new javax.swing.JButton();
        labelStatus = new javax.swing.JLabel();
        dadosStatus = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Banco de Dados");
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(new java.awt.Color(204, 204, 255));
        setResizable(false);

        painel.setBackground(new java.awt.Color(255, 255, 255));
        painel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Banco de Dados", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Franklin Gothic Medium Cond", 1, 18), new java.awt.Color(51, 51, 51))); // NOI18N
        painel.setForeground(new java.awt.Color(204, 204, 204));
        painel.setFont(new java.awt.Font("Times New Roman", 1, 14));
        painel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        botaoDeletar.setFont(new java.awt.Font("Times New Roman", 1, 14));
        botaoDeletar.setText("Deletar Database");
        botaoDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoDeletarActionPerformed(evt);
            }
        });
        painel.add(botaoDeletar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, -1, -1));

        botaoInstalar.setFont(new java.awt.Font("Times New Roman", 1, 14));
        botaoInstalar.setText("Instalar Database");
        botaoInstalar.setToolTipText("Clique para Instalar a Base de Dados.");
        botaoInstalar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoInstalarActionPerformed(evt);
            }
        });
        painel.add(botaoInstalar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, -1, -1));

        botaoCancelar.setFont(new java.awt.Font("Times New Roman", 1, 14));
        botaoCancelar.setText("Cancelar");
        botaoCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCancelarActionPerformed(evt);
            }
        });
        painel.add(botaoCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, -1, -1));

        labelStatus.setFont(new java.awt.Font("Times New Roman", 1, 14));
        labelStatus.setText("Status do Banco: ");
        painel.add(labelStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, -1, -1));

        dadosStatus.setFont(new java.awt.Font("Times New Roman", 1, 14));
        dadosStatus.setText("statusDb");
        painel.add(dadosStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painel, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painel, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-270)/2, (screenSize.height-269)/2, 270, 269);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoInstalarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_botaoInstalarActionPerformed
    {//GEN-HEADEREND:event_botaoInstalarActionPerformed
        if(Config.isDebug())
            _log.info("chamando funcao de instalacao do banco");

        DatabaseManager.instaleBanco();

        dadosStatus.setText(DatabaseManager.getConnectionStatus());
        if(DatabaseManager._installed==1)
        {
            //desabilita o botao instalar apos o banco ja ter sido instalado.
            botaoInstalar.setEnabled(false);
            botaoDeletar.setEnabled(true);
        }

        //fecha esse frame
        // -------------------
        dispose();


        //Le os Dados da Licenca
        // --------------------------
        DatabaseManager.readLicenseData();

        // Abre o Frame de Licenca caso a aplicacao nao esteja licenciada.
        // ------------------------
        if(DatabaseManager._licensed==0)
        {
            EventQueue.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                   AppManager.setTema(DBConfig.class.getSimpleName());
                    new FrameLicenca().setVisible(true);
                }
            });
        }

    }//GEN-LAST:event_botaoInstalarActionPerformed

    private void botaoDeletarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_botaoDeletarActionPerformed
    {//GEN-HEADEREND:event_botaoDeletarActionPerformed
    //    if(!Config.isLoad())
            Config.load();

        DatabaseManager.dropDatabase();
        dadosStatus.setText(DatabaseManager.getConnectionStatus());
        if(DatabaseManager._installed==0)
        {
            //Habilita o botao instalar apos o banco ter sido deletado.
            botaoInstalar.setEnabled(true);
            botaoDeletar.setEnabled(false);
        }
    }//GEN-LAST:event_botaoDeletarActionPerformed

    private void botaoCancelarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_botaoCancelarActionPerformed
    {//GEN-HEADEREND:event_botaoCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_botaoCancelarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new DBConfig().setVisible(true);
            }

        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoCancelar;
    private javax.swing.JButton botaoDeletar;
    private javax.swing.JButton botaoInstalar;
    private javax.swing.JLabel dadosStatus;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelStatus;
    private javax.swing.JPanel painel;
    // End of variables declaration//GEN-END:variables
}
