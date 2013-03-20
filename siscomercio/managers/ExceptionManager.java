/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio.managers;

import com.siscomercio.utilities.Utilitarios;



/**
 *
 * @author Rayan
 */
public class ExceptionManager
{

    /**
     * 
     * @param msg
     * @param command
     * @param e
     */
    public static void ThrowException(String msg, String command, Exception e)
    {

        String output = msg + "\n" + command + "\n";
        Utilitarios.showErrorMessage(output + e);
    }
    /**
     * 
     * @param msg
     * @param e
     */
    public static void ThrowException(String msg, Exception e)
    {

        String output = msg + "\n" ;
        Utilitarios.showErrorMessage(output + e);
    }
    /**
     * 
     * @param msg
     * @param e
     */
    public static void ThrowException(String msg, Throwable e)
    {

        String output = msg + "\n" ;
        Utilitarios.showErrorMessage(output + e);
    }
}
