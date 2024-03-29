/*
 * UserSearch.java
 *
 * Created on 11/10/2010, 19:53:03
 */
package com.siscomercio.model.view.frames;

import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 *
 * @author Rayan
 */
public class UserSearch extends JFrame
{
    private static final long serialVersionUID = 1L;

    /**
     * Creates new form UserSearch
     */
    public UserSearch()
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

        jLabel1 = new javax.swing.JLabel();
        campoProcura = new javax.swing.JTextField();
        botaoProcura = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Procurar Usuario");

        jLabel1.setText("Nome ou Login do Usuario a Excluir");

        botaoProcura.setText("Procurar");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(61, 61, 61)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(campoProcura, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 165, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel1)))
                    .add(layout.createSequentialGroup()
                        .add(107, 107, 107)
                        .add(botaoProcura)))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(49, 49, 49)
                .add(jLabel1)
                .add(18, 18, 18)
                .add(campoProcura, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(botaoProcura)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-308)/2, (screenSize.height-222)/2, 308, 222);
    }// </editor-fold>//GEN-END:initComponents

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
                new UserSearch().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoProcura;
    private javax.swing.JTextField campoProcura;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
