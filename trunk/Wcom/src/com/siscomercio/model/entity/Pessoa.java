/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio.model.entity;

/**
 *
 * @author William
 */
public abstract class Pessoa
{

    private int id;
    private String nome;
    private String dataNascimento;
    private String sexo;
    private String tipo;
    private String cnpj;
    private String cpf;
    private String rg;
    private Endereco endereco;

    /**
     * 
     * @return
     */
    public Endereco getEndereco()
    {
        return endereco;
    }

    /**
     * 
     * @param endereco
     */
    public void setEndereco(Endereco endereco)
    {
        this.endereco = endereco;
    }

    /**
     * 
     * @return
     */
    public String getCpf()
    {
        return cpf;
    }

    /**
     * 
     * @param cpf
     */
    public void setCpf(String cpf)
    {
        this.cpf = cpf;
    }

    /**
     * 
     * @return
     */
    public String getRg()
    {
        return rg;
    }

    /**
     * 
     * @param rg
     */
    public void setRg(String rg)
    {
        this.rg = rg;
    }

    /**
     * 
     * @return
     */
    public String getCnpj()
    {
        return cnpj;
    }

    /**
     * 
     * @param cnpj
     */
    public void setCnpj(String cnpj)
    {
        this.cnpj = cnpj;
    }

    /**
     * 
     * @return
     */
    public String getTipo()
    {
        return tipo;
    }

    /**
     * 
     * @param tipo
     */
    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }

    /**
     * 
     * @return
     */
    public String getDataNascimento()
    {
        return dataNascimento;
    }

    /**
     * 
     * @param dataNascimento
     */
    public void setDataNascimento(String dataNascimento)
    {
        this.dataNascimento = dataNascimento;
    }

    /**
     * 
     * @return
     */
    public String getNome()
    {
        return nome;
    }

    /**
     * 
     * @param nome
     */
    public void setNome(String nome)
    {
        this.nome = nome;
    }

    /**
     * 
     * @return
     */
    public String getSexo()
    {
        return sexo;
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
     * @param sexo
     */
    public void setSexo(String sexo)
    {
        this.sexo = sexo;
    }
}
