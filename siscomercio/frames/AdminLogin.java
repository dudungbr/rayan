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
public class AdminLogin extends JFrame
{
    /**
     *
     */
    public static boolean _securityAuth;
    private Logger _log = Logger.getLogger(AdminLogin.class.getName());

    /** Creates new form LogonFrame */
    public AdminLogin()
    {
        initComponents();
        if(Config.isDebug())
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Autenticação");
        setResizable(false);

        painel.setBackground(new java.awt.Color(255, 255, 255));
        painel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Autenticação", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(0, 0, 0))); // NOI18N
        painel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        painel.add(campoUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(97, 35, 63, -1));

        campoSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoSenhaActionPerformed(evt);
            }
        });
        painel.add(campoSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 61, 63, -1));

        labelUsuario.setFont(new java.awt.Font("Times New Roman", 1, 14));
        labelUsuario.setText("Usuário");
        painel.add(labelUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 39, -1, 10));

        labelSenha.setFont(new java.awt.Font("Times New Roman", 1, 14));
        labelSenha.setText("Senha");
        painel.add(labelSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 61, 46, -1));

        botaoLogin.setFont(new java.awt.Font("Times New Roman", 1, 14));
        botaoLogin.setText("Login");
        botaoLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoLoginActionPerformed(evt);
            }
        });
        getRootPane().setDefaultButton(botaoLogin);
        painel.add(botaoLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        botaoCancelar.setFont(new java.awt.Font("Times New Roman", 1, 14));
        botaoCancelar.setText("Cancelar");
        botaoCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCancelarActionPerformed(evt);
            }
        });
        painel.add(botaoCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painel, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
        );

        getRootPane().setDefaultButton(botaoLogin);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-195)/2, (screenSize.height-177)/2, 195, 177);
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
/*            if(accessLevel >= Config.ADMIN_LVL)
            {
                dispose();
                new AdminPanel().setVisible(true);
            }
            else
                SystemUtil.showMsg("voce nao tem privilegios para acessar o painel de administração.",true);
        */}
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
                new AdminLogin().setVisible(true);


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
