package com.siscomercio.utilities;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

/**
 * $Revision$
 * $Author$
 * $Date$
 *
 * @author Usuario
 */
@SuppressWarnings("serial")
public class MouseController extends JFrame
{
    /*
    Este exemplo mostra como saber se o usuário
    clicou o botão direito ou esquerdo do mouse.
     */
    public MouseController()
    {
        super("Eventos do Mouse e Teclado");
        Container c = getContentPane();
        FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
        c.setLayout(layout);

        this.addMouseListener(
                new MouseAdapter()
                {
                    @Override
                    public void mouseClicked(MouseEvent e)
                    {
                        if((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0)
                            setTitle("Você pressionou o botão esquerdo.");
                        else if((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0)
                                setTitle("Você pressionou o botão direito.");
                            else
                                setTitle("voce pressionou o botao do meio.");
                    }

                });

        setSize(350, 250);
        setVisible(true);
    }

    public static void main(String args[])
    {
        MouseController app = new MouseController();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
