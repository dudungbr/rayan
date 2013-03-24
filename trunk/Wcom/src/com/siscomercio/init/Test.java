package com.siscomercio.init;

import com.siscomercio.model.persistence.dao.Banco;

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

        if (Banco.getInstance().getInstalled())
        {
            System.out.println("ok");
        }
        else
        {
            System.out.println("errado");
        }
    }
}
