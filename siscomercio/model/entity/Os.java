/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio.model.entity;

/**
 *
 * @author William
 */
public class Os
{
    private String servico;
    private String dataEntrada;
    private String dataEntrega;
    private String valor;
    private int codigo;

    /**
     *
     * @return
     */
    public int getCodigo()
    {
        return codigo;
    }

    /**
     *
     * @param codigo
     */
    public void setCodigo(int codigo)
    {
        this.codigo = codigo;
    }

    /**
     *
     * @return
     */
    public String getDataEntrada()
    {
        return dataEntrada;
    }

    /**
     *
     * @param dataEntrada
     */
    public void setDataEntrada(String dataEntrada)
    {
        this.dataEntrada = dataEntrada;
    }

    /**
     *
     * @return
     */
    public String getDataEntrega()
    {
        return dataEntrega;
    }

    /**
     *
     * @param dataEntrega
     */
    public void setDataEntrega(String dataEntrega)
    {
        this.dataEntrega = dataEntrega;
    }

    /**
     *
     * @return
     */
    public String getServico()
    {
        return servico;
    }

    /**
     *
     * @param servico
     */
    public void setServico(String servico)
    {
        this.servico = servico;
    }

    /**
     *
     * @return
     */
    public String getValor()
    {
        return valor;
    }

    /**
     *
     * @param valor
     */
    public void setValor(String valor)
    {
        this.valor = valor;
    }
}
