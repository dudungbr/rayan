/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio.model.entity;

/**
 *
 * @author William
 */
public class Endereco
{
    private String rua;
    private String cidade;
    private String bairro;
    private String numero;
    private String cep;
    private String estado;
    private String celular;
    private String fixo;
    private String complemento;

    /**
     *
     * @return
     */
    public String getCelular()
    {
        return celular;
    }

    /**
     *
     * @param celular
     */
    public void setCelular(String celular)
    {
        this.celular = celular;
    }

    /**
     *
     * @return
     */
    public String getEstado()
    {
        return estado;
    }

    /**
     *
     * @param estado
     */
    public void setEstado(String estado)
    {
        this.estado = estado;
    }

    /**
     *
     * @return
     */
    public String getFixo()
    {
        return fixo;
    }

    /**
     *
     * @param fixo
     */
    public void setFixo(String fixo)
    {
        this.fixo = fixo;
    }

    /**
     *
     * @return
     */
    public String getComplemento()
    {
        return complemento;
    }

    /**
     *
     * @param complemento
     */
    public void setComplemento(String complemento)
    {
        this.complemento = complemento;
    }

    /**
     *
     * @return
     */
    public String getEmail()
    {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email)
    {
        this.email = email;
    }
    private String email;

    /**
     *
     * @return
     */
    public String getBairro()
    {
        return bairro;
    }

    /**
     *
     * @param bairro
     */
    public void setBairro(String bairro)
    {
        this.bairro = bairro;
    }

    /**
     *
     * @return
     */
    public String getCep()
    {
        return cep;
    }

    /**
     *
     * @param cep
     */
    public void setCep(String cep)
    {
        this.cep = cep;
    }

    /**
     *
     * @return
     */
    public String getCidade()
    {
        return cidade;
    }

    /**
     *
     * @param cidade
     */
    public void setCidade(String cidade)
    {
        this.cidade = cidade;
    }

    /**
     *
     * @return
     */
    public String getNumero()
    {
        return numero;
    }

    /**
     *
     * @param numero
     */
    public void setNumero(String numero)
    {
        this.numero = numero;
    }

    /**
     *
     * @return
     */
    public String getRua()
    {
        return rua;
    }

    /**
     *
     * @param rua
     */
    public void setRua(String rua)
    {
        this.rua = rua;
    }
}
