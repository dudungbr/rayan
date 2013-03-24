/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AddUser.java
 *
 * Created on 23/04/2010, 02:22:50
 */
package com.siscomercio.model.view.frames;

import com.siscomercio.init.Config;
import javax.swing.JFrame;
import com.siscomercio.controller.managers.DatabaseManager;
import com.siscomercio.model.persistence.dao.Banco;
import com.siscomercio.model.security.Criptografia;
import com.siscomercio.utilities.SystemUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * $Revision$
 * $Author$
 * $Date$
 * <p/>
 * @author Rayan
 */
public class AddUser extends JFrame
{
    private static final Logger _log = Logger.getLogger(AddUser.class.getName());
    private static final long serialVersionUID = 1L;

    /**
     * Creates new form AddUser
     */
    public AddUser()
    {
        initComponents();
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings ("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        labelNome = new javax.swing.JLabel();
        labelSobrenome = new javax.swing.JLabel();
        labelLogin = new javax.swing.JLabel();
        labelSenha = new javax.swing.JLabel();
        botaoCancelar = new javax.swing.JButton();
        botaoLimpar = new javax.swing.JButton();
        botaoCadastrar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        confirmarSenha = new javax.swing.JLabel();
        campoNome = new javax.swing.JTextField();
        campoSobrenome = new javax.swing.JTextField();
        campoLogin = new javax.swing.JTextField();
        campoConfirma = new javax.swing.JPasswordField();
        campoSenha = new javax.swing.JPasswordField();
        caixaCombinacao = new javax.swing.JComboBox();

        setTitle("Adcionar Usuário");
        getContentPane().setLayout(null);

        jPanel1.setLayout(new java.awt.CardLayout());
        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 0, 0);

        jPanel3.setLayout(null);

        labelNome.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelNome.setText("Nome");
        jPanel3.add(labelNome);
        labelNome.setBounds(80, 30, 35, 20);

        labelSobrenome.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelSobrenome.setText("Sobrenome");
        jPanel3.add(labelSobrenome);
        labelSobrenome.setBounds(46, 60, 69, 20);

        labelLogin.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelLogin.setText("Login");
        jPanel3.add(labelLogin);
        labelLogin.setBounds(80, 90, 35, 20);

        labelSenha.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelSenha.setText("Senha");
        jPanel3.add(labelSenha);
        labelSenha.setBounds(80, 130, 38, 20);

        botaoCancelar.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        botaoCancelar.setText("Cancelar");
        botaoCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCancelarActionPerformed(evt);
            }
        });
        jPanel3.add(botaoCancelar);
        botaoCancelar.setBounds(120, 300, 87, 25);

        botaoLimpar.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        botaoLimpar.setText("Limpar");
        botaoLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoLimparActionPerformed(evt);
            }
        });
        jPanel3.add(botaoLimpar);
        botaoLimpar.setBounds(220, 300, 77, 25);

        botaoCadastrar.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        botaoCadastrar.setText("Cadastrar");
        botaoCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCadastrarActionPerformed(evt);
            }
        });
        getRootPane().setDefaultButton(botaoCadastrar);
        jPanel3.add(botaoCadastrar);
        botaoCadastrar.setBounds(20, 300, 93, 25);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("Nível de Acesso");
        jPanel3.add(jLabel2);
        jLabel2.setBounds(120, 210, 95, 17);

        confirmarSenha.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        confirmarSenha.setText("Confirma Senha");
        jPanel3.add(confirmarSenha);
        confirmarSenha.setBounds(20, 170, 101, 17);
        jPanel3.add(campoNome);
        campoNome.setBounds(130, 30, 90, 20);
        jPanel3.add(campoSobrenome);
        campoSobrenome.setBounds(130, 60, 90, 20);
        jPanel3.add(campoLogin);
        campoLogin.setBounds(130, 90, 90, 20);
        jPanel3.add(campoConfirma);
        campoConfirma.setBounds(130, 170, 90, 20);
        jPanel3.add(campoSenha);
        campoSenha.setBounds(130, 130, 90, 20);

        caixaCombinacao.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        caixaCombinacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Funcionario", "Gerente", "Administrador" }));
        jPanel3.add(caixaCombinacao);
        caixaCombinacao.setBounds(100, 240, 130, 20);

        getContentPane().add(jPanel3);
        jPanel3.setBounds(0, 0, 340, 350);

        setSize(new java.awt.Dimension(345, 381));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoCadastrarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_botaoCadastrarActionPerformed
    {//GEN-HEADEREND:event_botaoCadastrarActionPerformed
        String login = campoLogin.getText();
        String senha = String.valueOf(campoSenha.getPassword());
        String confirmaSenha = String.valueOf(campoConfirma.getPassword());

        boolean loginExiste = Banco.getInstance().valorExistente(login, true);

        if (login.equals(""))
        {
            SystemUtil.showErrorMsg("o login nao deve ser em branco", true);
            return;
        }
        if (senha.equalsIgnoreCase(confirmaSenha))
        {
            //caso o login ja exista encerra a funcao.
            if (loginExiste)
            {
                return;
            }

            String tipoAcesso = caixaCombinacao.getSelectedItem().toString();
            int nivelAcesso = 0;

            if (Config.isDebug())
            {
                _log.log(Level.INFO, "tipo acesso = {0}\n", tipoAcesso);
            }
            if (tipoAcesso.startsWith("Fun"))
            {
                if (Config.isDebug())
                {
                    _log.info("0");
                }
                // nivelAcesso = Config.OPERADOR_LVL;
            }
            else if (tipoAcesso.startsWith("Ger"))
            {
                if (Config.isDebug())
                {
                    _log.info("1");
                }
                //   nivelAcesso = Config.GERENTE_LVL;
            }
            else
            {
                if (Config.isDebug())
                {
                    _log.info("2");
                }
                //   nivelAcesso = Config.ADMIN_LVL;
            }

            JLabel optionLabel = new JLabel("<html>confirma adição do usuario <font color = blue>" + login + "</font> como <font color = blue>" + tipoAcesso + "</font> ? </font> </html>");
//           SoundManager.getInstance().playSound(Config.QUESTION_SOUND);
            int confirm = JOptionPane.showConfirmDialog(null, optionLabel);

            switch (confirm)
            {
                // Switch > Case
                case JOptionPane.YES_OPTION: // Attempt to Login user
                {
                    String senhaCripto = Criptografia.criptografe(senha);
                    Banco.getInstance().addUser(login, senhaCripto, nivelAcesso);
                    dispose();
                    break;
                }
                case JOptionPane.NO_OPTION: // No Case.(Go back. Set text to 0)
                {
                    resetCampos();
                    break;
                }
                case JOptionPane.CANCEL_OPTION: // Cancel Case.(Go back. Set text to/0)
                {
                    resetCampos();
                    break;
                }
            } // End Switch > Case

        }
        else
        {
            SystemUtil.showErrorMsg("os campos de senha devem coincidir", true);
            campoSenha.setText("");
            campoConfirma.setText("");
        }

}//GEN-LAST:event_botaoCadastrarActionPerformed

    private void botaoLimparActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_botaoLimparActionPerformed
    {//GEN-HEADEREND:event_botaoLimparActionPerformed
        campoNome.setText("");
        campoLogin.setText("");
        campoSenha.setText("");
        campoConfirma.setText("");
        campoSobrenome.setText("");
}//GEN-LAST:event_botaoLimparActionPerformed

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
                new AddUser().setVisible(true);
            }
        });
    }

    /**
     * Reseta Todos os Campos.
     */
    private void resetCampos()
    {
        campoNome.setText("");
        campoLogin.setText("");
        campoSenha.setText("");
        campoConfirma.setText("");
        campoSobrenome.setText("");
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoCadastrar;
    private javax.swing.JButton botaoCancelar;
    private javax.swing.JButton botaoLimpar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox caixaCombinacao;
    private javax.swing.JPasswordField campoConfirma;
    private javax.swing.JTextField campoLogin;
    private javax.swing.JTextField campoNome;
    private javax.swing.JPasswordField campoSenha;
    private javax.swing.JTextField campoSobrenome;
    private javax.swing.JLabel confirmarSenha;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel labelLogin;
    private javax.swing.JLabel labelNome;
    private javax.swing.JLabel labelSenha;
    private javax.swing.JLabel labelSobrenome;
    // End of variables declaration//GEN-END:variables
}
