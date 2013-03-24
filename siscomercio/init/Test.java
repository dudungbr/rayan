package com.siscomercio.init;

import com.siscomercio.model.persistence.Banco;

/**
 *
 * @author William
 */
public class Test
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Config.load();
        Banco.getInstance().conectaDatabaseSelecionada();
        Banco.getInstance().readInstallationState();

        if (Banco.getInstance().getInstalled() != 0)
        {
            System.out.println("ok");
        }
        else
        {
            System.out.println("errado");
        }
    }
}
