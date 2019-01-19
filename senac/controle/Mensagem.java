/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.controle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Aluno
 */
public class Mensagem {
    
    public static  void sucesso(String msg){
        FacesContext contexto = FacesContext.getCurrentInstance();
        contexto.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, msg, ""));
    }
    
    public static  void erro(String msg){
        FacesContext contexto = FacesContext.getCurrentInstance();
        contexto.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, msg, ""));
    }
}
