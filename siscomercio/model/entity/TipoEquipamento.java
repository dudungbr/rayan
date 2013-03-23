/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio.model.entity;

/**
 *
 * @author William
 */
public class TipoEquipamento
{
    private int _id;
    private String _tipo;

    /**
     *
     * @param id
     * @param tipo
     */
    public TipoEquipamento(int id, String tipo)
    {
        _id = id;
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
    public String getTipo()
    {
        return _tipo;
    }

    /**
     *
     * @param _tipo
     */
    public void setTipo(String _tipo)
    {
        this._tipo = _tipo;
    }
}
