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
//       // Banco.getInstance().criaNovaBase();
//        Banco.getInstance().conectaDatabaseSelecionada();
//        if (Banco.getInstance().executaTabelasMySQL())
//        {
//            System.out.println("Base Criada Com Sucesso!");
//        }
//        else
//        {
//            System.out.println("errado");
//        }
        // Thread.sleep(1000);
        // Banco.getInstance().conectaDatabaseSelecionada();
        boolean cond = Banco.getInstance().verificaExistencia();

        if (cond)
        {
            System.out.println("ok");
            System.out.println(Banco.getInstance().getConnectionStatus());
        }
        else
        {
            System.out.println("errado");
            System.out.println(Banco.getInstance().getConnectionStatus());
        }
    }
}
