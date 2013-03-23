/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio.model.entity;

/**
 *
 * @author Rayan
 */
public class Entrada
{
    private int id;
    private int numEnt;
    private String data;
    private String hora;
    private String usuario;
    private String status;
    private String marca;
    private String modelo;
    private String quantidade;
    private String responsavel;
    private String defeitos;
    private String perifericos;
    private String descricao;

    /**
     *
     * @return
     */
    public int getNumEnt()
    {
        return numEnt;
    }

    /**
     *
     * @param numEnt
     */
    public void setNumEnt(int numEnt)
    {
        this.numEnt = numEnt;
    }

    /**
     *
     * @return
     */
    public String getData()
    {
        return data;
    }

    /**
     *
     * @param data
     */
    public void setData(String data)
    {
        this.data = data;
    }

    /**
     *
     * @return
     */
    public String getDefeitos()
    {
        return defeitos;
    }

    /**
     *
     * @param defeitos
     */
    public void setDefeitos(String defeitos)
    {
        this.defeitos = defeitos;
    }

    /**
     *
     * @return
     */
    public String getDescricao()
    {
        return descricao;
    }

    /**
     *
     * @param descricao
     */
    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }

    /**
     *
     * @return
     */
    public String getHora()
    {
        return hora;
    }

    /**
     *
     * @param hora
     */
    public void setHora(String hora)
    {
        this.hora = hora;
    }

    /**
     *
     * @return
     */
    public int getId()
    {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getMarca()
    {
        return marca;
    }

    /**
     *
     * @param marca
     */
    public void setMarca(String marca)
    {
        this.marca = marca;
    }

    /**
     *
     * @return
     */
    public String getModelo()
    {
        return modelo;
    }

    /**
     *
     * @param modelo
     */
    public void setModelo(String modelo)
    {
        this.modelo = modelo;
    }

    /**
     *
     * @return
     */
    public String getPerifericos()
    {
        return perifericos;
    }

    /**
     *
     * @param perifericos
     */
    public void setPerifericos(String perifericos)
    {
        this.perifericos = perifericos;
    }

    /**
     *
     * @return
     */
    public String getQuantidade()
    {
        return quantidade;
    }

    /**
     *
     * @param quantidade
     */
    public void setQuantidade(String quantidade)
    {
        this.quantidade = quantidade;
    }

    /**
     *
     * @return
     */
    public String getResponsavel()
    {
        return responsavel;
    }

    /**
     *
     * @param responsavel
     */
    public void setResponsavel(String responsavel)
    {
        this.responsavel = responsavel;
    }

    /**
     *
     * @return
     */
    public String getStatus()
    {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(String status)
    {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public String getUsuario()
    {
        return usuario;
    }

    /**
     *
     * @param usuario
     */
    public void setUsuario(String usuario)
    {
        this.usuario = usuario;
    }
}
