/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * UserLogon.java
 *
 * Created on 12/04/2010, 09:53:28
 */
package com.siscomercio.frames;

import com.siscomercio.security.Auth;

/**
 * $Revision$
 * $Author$
 * $Date$
 * @author Rayan
 */
@SuppressWarnings("serial")
public class UserLogon extends javax.swing.JFrame
{
    /**
     *
     */
    public static boolean adm;
    /**
     *
     */
    public static boolean _reAuth;
    /**
     *
     */
    public static boolean _authed;

    /** Creates new form UserLogon */
    public UserLogon()
    {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        campoUsuario = new javax.swing.JTextField();
        campoSenha = new javax.swing.JPasswordField();
        rotuloUsuario = new javax.swing.JLabel();
        rotuloSenha = new javax.swing.JLabel();
        botaoLogin = new javax.swing.JButton();
        botaoCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Autenticação", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 14))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(campoUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(84, 33, 63, -1));

        campoSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoSenhaActionPerformed(evt);
            }
        });
        jPanel1.add(campoSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(84, 63, 63, -1));

        rotuloUsuario.setText("Usuário");
        jPanel1.add(rotuloUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 38, -1, 10));

        rotuloSenha.setText("Senha");
        jPanel1.add(rotuloSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 66, -1, -1));

        botaoLogin.setText("Login");
        botaoLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoLoginActionPerformed(evt);
            }
        });
        getRootPane().setDefaultButton(botaoLogin);
        jPanel1.add(botaoLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        botaoCancelar.setText("Cancelar");
        botaoCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(botaoCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
        );

        getRootPane().setDefaultButton(botaoLogin);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-201)/2, (screenSize.height-166)/2, 201, 166);
    }// </editor-fold>//GEN-END:initComponents

    private void campoSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoSenhaActionPerformed
    }//GEN-LAST:event_campoSenhaActionPerformed

    private void botaoCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_botaoCancelarActionPerformed

    private void botaoLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoLoginActionPerformed

        String pass = new String(campoSenha.getPassword());

        if(Auth.checkMasterKey(campoUsuario.getText(), pass))
        {
            _authed = true;
            new DatabaseFrame().setVisible(true);
            dispose();
        }
        /* _reAuth = true;
        String senha = new String(campoSenha.getPassword());
        String usuario = new String(campoUsuario.getText());
        Auth.getInstance().autentique(usuario, senha);
        boolean check = DatabaseManager.checkAccessLevel(500);
        if(check)
        {
        new DatabaseFrame().setVisible(true);
        dispose();
        }
        else
        {
        Util.showErrorMsg(Auth.LAST_USER + ", voce nao tem privilegios para esta operação.");
        }*/

    }//GEN-LAST:event_botaoLoginActionPerformed

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
                new UserLogon().setVisible(true);
            }

        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoCancelar;
    private javax.swing.JButton botaoLogin;
    private javax.swing.JPasswordField campoSenha;
    private javax.swing.JTextField campoUsuario;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel rotuloSenha;
    private javax.swing.JLabel rotuloUsuario;
    // End of variables declaration//GEN-END:variables
}
