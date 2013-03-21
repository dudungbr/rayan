/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio.model.entity;

/**
 *
 * @author user
 */
public class Tecnico {

    private int _id;
    private String _nome;

    /**
     *
     * @param id
     * @param nome  
     */
    public Tecnico(int id, String nome) {
        _id = id;
        _nome = nome;
    }

    /**
     * 
     * @return
     */
    public int getId() {
        return _id;
    }

    /**
     * 
     * @param _id
     */
    public void setId(int _id) {
        this._id = _id;
    }

    /**
     * 
     * @return
     */
    public String getNome() {
        return _nome;
    }

    /**
     * 
     * @param _nome
     */
    public void setNome(String _nome) {
        this._nome = _nome;
    }
    
    
}
