/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio.tables;

/**
 *$Revision$
 * $Author$
 * $Date$
 * @author Rayan
 */
public class ErrorTable
{
    
    /**
     *
     */
    public static String MYSQL_PROCESS_ERROR = "0x001";


    /**
     *
     * @param errorCode
     * @return
     * 1 - MySQL Process Not Found.
     */
    public static String throwError(int errorCode)
    {
        String msg;
        switch(errorCode)
        {
            case 1: // mysql process error
            {
                msg = "Erro de Código: " + ErrorTable.MYSQL_PROCESS_ERROR + "(MySQL Process Not Found)";
                break;
            }
            default:
                msg = "null";
        }
        return msg;
    }
}
