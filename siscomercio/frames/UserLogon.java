/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LogonFrame.java
 *
 * Created on 12/04/2010, 09:53:28
 */
package com.siscomercio.frames;

import java.util.logging.Logger;
import javax.swing.JFrame;
import com.siscomercio.Config;
import com.siscomercio.managers.DatabaseManager;
import com.siscomercio.security.Auth;
import com.siscomercio.utilities.SystemUtil;

/**
 * $Revision: 123 $
 * $Author: rayan_rpg@hotmail.com $
 * $Date: 2010-05-03 14:39:12 -0300 (seg, 03 mai 2010) $
 * @author Rayan
 */
@SuppressWarnings("serial")
public class UserLogon extends JFrame
{
    /**
     *
     */
    public static boolean _securityAuth;
    private Logger _log = Logger.getLogger(UserLogon.class.getName());

    /** Creates new form LogonFrame */
    public UserLogon()
    {
        initComponents();
        if(Config.DEBUG)
            _log.info("montando janela de logon de usuario \n");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painel = new javax.swing.JPanel();
        campoUsuario = new javax.swing.JTextField();
        campoSenha = new javax.swing.JPasswordField();
        labelUsuario = new javax.swing.JLabel();
        labelSenha = new javax.swing.JLabel();
        botaoLogin = new javax.swing.JButton();
        botaoCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        painel.setBackground(new java.awt.Color(255, 255, 255));
        painel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Autenticação", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N
        painel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        painel.add(campoUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(84, 33, 63, -1));

        campoSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoSenhaActionPerformed(evt);
            }
        });
        painel.add(campoSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(84, 63, 63, -1));

        labelUsuario.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelUsuario.setText("Usuário");
        painel.add(labelUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 38, -1, 10));

        labelSenha.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelSenha.setText("Senha");
        painel.add(labelSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 66, -1, -1));

        botaoLogin.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        botaoLogin.setText("Login");
        botaoLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoLoginActionPerformed(evt);
            }
        });
        getRootPane().setDefaultButton(botaoLogin);
        painel.add(botaoLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        botaoCancelar.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        botaoCancelar.setText("Cancelar");
        botaoCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCancelarActionPerformed(evt);
            }
        });
        painel.add(botaoCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painel, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painel, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
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

        //pega o nome do usuario
        String usuario = campoUsuario.getText();

        // Pega a Senha Digitada Pelo Usuario
        String senha = String.valueOf(campoSenha.getPassword());

        boolean autorizado = Auth.getInstance().isAuthed(usuario, senha);

        int accessLevel = DatabaseManager.getAccessLevel(usuario);

        if(autorizado)
        {
            if(accessLevel >= Config.ADMIN_LVL)
            {
                dispose();
                new AdministrationFrame().setVisible(true);
            }
            else
                SystemUtil.showMsg("Voce nao Tem permissao para executar esta ação.");
        }
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
    private javax.swing.JLabel labelSenha;
    private javax.swing.JLabel labelUsuario;
    private javax.swing.JPanel painel;
    // End of variables declaration//GEN-END:variables
}
