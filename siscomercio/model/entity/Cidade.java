/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio.model.entity;

/**
 *
 * @author William
 */
public class Cidade
{

    private int _id;
    private int _estado;
    private String _uf;
    private String _nome;

    /**
     *
     * @param id
     * @param estado
     * @param uf
     * @param nome
     */
    public Cidade(int id, int estado, String uf, String nome)
    {
        _id = id;
        _estado = estado;
        _uf = uf;
        _nome = nome;

    }

    /**
     *
     * @return
     */
    public int getEstado()
    {
        return _estado;
    }

    /**
     *
     * @param _estado
     */
    public void setEstado(int _estado)
    {
        this._estado = _estado;
    }

    /**
     *
     * @return
     */
    public int getId()
    {
        return _id;
    }

    /**
     *
     * @param _id
     */
    public void setId(int _id)
    {
        this._id = _id;
    }

    /**
     *
     * @return
     */
    public String getNome()
    {
        return _nome;
    }

    /**
     *
     * @param _nome
     */
    public void setNome(String _nome)
    {
        this._nome = _nome;
    }

    /**
     *
     * @return
     */
    public String getUf()
    {
        return _uf;
    }

    /**
     *
     * @param _uf
     */
    public void setUf(String _uf)
    {
        this._uf = _uf;
    }
}
