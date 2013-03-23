/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio.model.entity;

/**
 *
 * @author William
 */
public final class Marca
{
    private int _id;
    private String _marca;
    private String _tipo;

    /**
     *
     * @return
     */
    public String getMarca()
    {
        return _marca;
    }

    /**
     *
     * @param _tipo
     */
    public void setTipo(String _tipo)
    {
        this._tipo = _tipo;
    }

    /**
     *
     * @param id
     * @param marca
     * @param tipo
     */
    public Marca(int id, String marca, String tipo)
    {
        _id = id;
        _marca = marca;

        _tipo = tipo;
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
     * @param id
     */
    public void setId(int id)
    {
        this._id = id;
    }

    /**
     *
     * @return
     */
    public String getTipo()
    {
        return _tipo;
    }

    /**
     *
     * @param marca
     */
    public void setMarca(String marca)
    {
        this._marca = marca;
    }
}
