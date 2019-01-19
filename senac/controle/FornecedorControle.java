/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.controle;

import br.com.senac.dao.FornecedorDao;
import br.com.senac.dao.FornecedorDaoImpl;
import br.com.senac.dao.HibernateUtil;
import br.com.senac.pojo.Fornecedor;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;

@ManagedBean(name = "fornecedorC")
@ViewScoped
public class FornecedorControle {

    private Fornecedor fornecedor;
    private Session session;
    private FornecedorDao fornecedorDao;
    private DataModel<Fornecedor> modalFornecedor;
    private boolean abreAba;

    private void abreSessao() {
        session = HibernateUtil.abreSessao();
    }

    public void pesquisarPorNome() {
        abreSessao();
        fornecedorDao = new FornecedorDaoImpl();
        List<Fornecedor> fornecedores = fornecedorDao.
                pesquisarPorNome(fornecedor.getNome(), session);
        modalFornecedor = new ListDataModel<>(fornecedores);
        session.close();
    }

    public void novo() {
        abreAba = true;
        fornecedor.setCriacao(new Date());
    }

    public void voltar() {
        abreAba = false;
    }

    public void salvar() {
        abreSessao();
        fornecedorDao = new FornecedorDaoImpl();
        FacesContext contexto = FacesContext.getCurrentInstance();
        try {
            fornecedorDao.salvarOuAlterar(fornecedor, session);
            fornecedor = new Fornecedor();
            fornecedor.setCriacao(new Date());
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Salvo com sucesso", ""));
        } catch (HibernateException e) {
            if (e.getCause().getMessage().contains("cnpj_unico")) {
                contexto.addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "CNPJ já existe", ""));
            }
        }
    }

//    gettes e setts
    public Fornecedor getFornecedor() {
        if (fornecedor == null) {
            fornecedor = new Fornecedor();
        }
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public DataModel<Fornecedor> getModalFornecedor() {
        return modalFornecedor;
    }

    public boolean isAbreAba() {
        return abreAba;
    }

}
