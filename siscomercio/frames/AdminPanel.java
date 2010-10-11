
/*
 * AdminPanel.java
 *
 * Created on 22/09/2010, 12:07:46
 */

package com.siscomercio.frames;

import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 *
 * @author Usuario
 */
public class AdminPanel extends JFrame
{
    private static final long serialVersionUID = 1L;
    /** Creates new form AdminPanel */
    public AdminPanel()
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

        jMenuBar1 = new javax.swing.JMenuBar();
        menuCadastro = new javax.swing.JMenu();
        subMenuCadUsuarios = new javax.swing.JMenu();
        itemAddUsuario = new javax.swing.JMenuItem();
        itemEditarUsuario = new javax.swing.JMenuItem();
        itemExcluirUsuario = new javax.swing.JMenuItem();

        setTitle("Painel de Administração");

        menuCadastro.setText("Cadastros");
        menuCadastro.setFont(new java.awt.Font("Times New Roman", 1, 14));

        subMenuCadUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/icones/6475_16x16.png"))); // NOI18N
        subMenuCadUsuarios.setText("Usuarios");
        subMenuCadUsuarios.setFont(new java.awt.Font("Times New Roman", 1, 14));

        itemAddUsuario.setFont(new java.awt.Font("Times New Roman", 1, 14));
        itemAddUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/icones/6420_16x16.png"))); // NOI18N
        itemAddUsuario.setText("Incluir");
        itemAddUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAddUsuarioActionPerformed(evt);
            }
        });
        subMenuCadUsuarios.add(itemAddUsuario);

        itemEditarUsuario.setFont(new java.awt.Font("Times New Roman", 1, 14));
        itemEditarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/icones/6439_16x16.png"))); // NOI18N
        itemEditarUsuario.setText("Editar");
        itemEditarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemEditarUsuarioActionPerformed(evt);
            }
        });
        subMenuCadUsuarios.add(itemEditarUsuario);

        itemExcluirUsuario.setFont(new java.awt.Font("Times New Roman", 1, 14));
        itemExcluirUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/siscomercio/icones/6464_16x16.png"))); // NOI18N
        itemExcluirUsuario.setText("Excluir");
        itemExcluirUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemExcluirUsuarioActionPerformed(evt);
            }
        });
        subMenuCadUsuarios.add(itemExcluirUsuario);

        menuCadastro.add(subMenuCadUsuarios);

        jMenuBar1.add(menuCadastro);

        setJMenuBar(jMenuBar1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 277, Short.MAX_VALUE)
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-408)/2, (screenSize.height-334)/2, 408, 334);
    }// </editor-fold>//GEN-END:initComponents

    private void itemAddUsuarioActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemAddUsuarioActionPerformed
    {//GEN-HEADEREND:event_itemAddUsuarioActionPerformed
     EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new AddUser().setVisible(true);
            }
        });
    }//GEN-LAST:event_itemAddUsuarioActionPerformed

    private void itemEditarUsuarioActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemEditarUsuarioActionPerformed
    {//GEN-HEADEREND:event_itemEditarUsuarioActionPerformed
       EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new AddUser().setVisible(true);
            }
        });
    }//GEN-LAST:event_itemEditarUsuarioActionPerformed

    private void itemExcluirUsuarioActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemExcluirUsuarioActionPerformed
    {//GEN-HEADEREND:event_itemExcluirUsuarioActionPerformed
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new DelUser().setVisible(true);
            }
        });
    }//GEN-LAST:event_itemExcluirUsuarioActionPerformed

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
                new EditUser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem itemAddUsuario;
    private javax.swing.JMenuItem itemEditarUsuario;
    private javax.swing.JMenuItem itemExcluirUsuario;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu menuCadastro;
    private javax.swing.JMenu subMenuCadUsuarios;
    // End of variables declaration//GEN-END:variables

}
