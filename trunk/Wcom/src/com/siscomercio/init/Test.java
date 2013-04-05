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
        //Banco.getInstance().conectaBanco();
        //   Banco.getInstance().c
        boolean cond = Banco.getInstance().conectaBanco();
        //  Banco.getInstance().exe
        Banco.getInstance().executaTabelasMySQL(true);


        if (cond)
        {
            System.out.println("ok");
            //  System.out.println(Banco.getInstance().getConnectionStatus());
        }
        else
        {
            System.out.println("errado");
            //    System.out.println(Banco.getInstance().getConnectionStatus());
        }
    }
}
