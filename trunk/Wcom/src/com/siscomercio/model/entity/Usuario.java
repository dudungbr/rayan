/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio.model.entity;

/**
 *
 * @author Rayan
 */
public class Usuario extends Pessoa
{
    private int nivelAcesso;
    private int tempoOcioso;
    private String login = "N/A";
    private String senhaAtual;
    private String novaSenha;

    /**
     *
     * @return
     */
    public String getSenhaAtual()
    {
        return senhaAtual;
    }

    /**
     *
     * @param senha
     */
    public void setSenhaAtual(String senha)
    {
        this.senhaAtual = senha;
    }

    /**
     *
     * @return
     */
    public String getLogin()
    {
        return login;
    }

    /**
     *
     * @param login
     */
    public void setLogin(String login)
    {
        this.login = login;
    }

    /**
     *
     * @return
     */
    public String getSenhaNova()
    {
        return getNovaSenha();
    }

    /**
     *
     * @param senhaNova
     */
    public void setSenhaNova(String senhaNova)
    {
        this.setNovaSenha(senhaNova);
    }

    /**
     *
     * @return
     */
    public int getNivelAcesso()
    {
        return nivelAcesso;
    }

    /**
     *
     * @param nivelAcesso
     */
    public void setNivelAcesso(int nivelAcesso)
    {
        this.nivelAcesso = nivelAcesso;
    }

    /**
     *
     * @return
     */
    public int getTempoOcioso()
    {
        return tempoOcioso;
    }

    /**
     *
     * @param tempoOcioso
     */
    public void setTempoOcioso(int tempoOcioso)
    {
        this.tempoOcioso = tempoOcioso;
    }

    /**
     * @return the novaSenha
     */
    public String getNovaSenha()
    {
        return novaSenha;
    }

    /**
     * @param novaSenha the novaSenha to set
     */
    public void setNovaSenha(String novaSenha)
    {
        this.novaSenha = novaSenha;
    }
}
