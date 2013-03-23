/*
 * DelUser.java
 *
 * Created on 06/10/2010, 11:02:33
 */
package com.siscomercio.model.view.frames;

import com.siscomercio.controller.managers.DatabaseManager;
import com.siscomercio.utilities.SystemUtil;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
@SuppressWarnings ("serial")
public class DelUser extends JFrame
{
    /**
     * Creates new form DelUser
     */
    public DelUser()
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

        campoNome = new javax.swing.JTextField();
        botaoExcluir = new javax.swing.JButton();
        rotulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Exclusao de Usuario");

        botaoExcluir.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        botaoExcluir.setText("Excluir");
        botaoExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoExcluirActionPerformed(evt);
            }
        });

        rotulo.setFont(new java.awt.Font("Times New Roman", 1, 14));
        rotulo.setText(" Login do Usuário a Excluir");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(58, Short.MAX_VALUE)
                .add(rotulo)
                .add(63, 63, 63))
            .add(layout.createSequentialGroup()
                .add(77, 77, 77)
                .add(campoNome, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 134, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(76, Short.MAX_VALUE))
            .add(layout.createSequentialGroup()
                .add(106, 106, 106)
                .add(botaoExcluir)
                .addContainerGap(118, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(39, 39, 39)
                .add(rotulo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(campoNome, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(botaoExcluir)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-295)/2, (screenSize.height-198)/2, 295, 198);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoExcluirActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_botaoExcluirActionPerformed
    {//GEN-HEADEREND:event_botaoExcluirActionPerformed
        String login = campoNome.getText();
        boolean loginExiste = DatabaseManager.valorExistente(login, false);

        if (login.isEmpty())
        {
            SystemUtil.showErrorMsg("o login deve ser informado", true);
        }
        else
        {
            if (!loginExiste)
            {
                SystemUtil.showErrorMsg("o login informado nao esta cadastrado", true);
                campoNome.setText("");
                return;
            }
            JLabel optionLabel = new JLabel("<html>confirma exclusao do usuario <font color = blue>" + login + "</font> </html>");
//            SoundManager.getInstance().playSound(Config.QUESTION_SOUND);
            int confirm = JOptionPane.showConfirmDialog(null, optionLabel);

            switch (confirm)
            {
                // Switch > Case
                case JOptionPane.YES_OPTION: // Attempt to Login user
                {
                    DatabaseManager.delUser(login);
                    dispose();
                    break;
                }
                case JOptionPane.NO_OPTION: // No Case.(Go back. Set text to 0)
                {
                    //  dispose();
                    break;
                }
                case JOptionPane.CANCEL_OPTION: // Cancel Case.(Go back. Set text to/0)
                {
                    // dispose();
                    break;
                }
            } // End Switch > Case
        }
    }//GEN-LAST:event_botaoExcluirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new DelUser().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoExcluir;
    private javax.swing.JTextField campoNome;
    private javax.swing.JLabel rotulo;
    // End of variables declaration//GEN-END:variables
}
