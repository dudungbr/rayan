/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio.model.entity;

/**
 *
 * @author William
 */
public class Funcionario extends Usuario
{
    private String cargo;

    /**
     * 
     * @return
     */
    public String getCargo()
    {
        return cargo;
    }

    /**
     * 
     * @param cargo
     */
    public void setCargo(String cargo)
    {
        this.cargo = cargo;
    }
    
}
