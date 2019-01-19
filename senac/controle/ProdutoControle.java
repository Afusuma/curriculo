/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.controle;

import br.com.senac.dao.FornecedorDao;
import br.com.senac.dao.FornecedorDaoImpl;
import br.com.senac.dao.HibernateUtil;
import br.com.senac.dao.ProdutoDao;
import br.com.senac.dao.ProdutoDaoImpl;
import br.com.senac.pojo.Fornecedor;
import br.com.senac.pojo.Perfil;
import br.com.senac.pojo.Produto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Aluno
 */
@ManagedBean(name = "produtoC")
@ViewScoped
public class ProdutoControle implements Serializable {

    private Produto produto;
    private Fornecedor fornecedor;
    private ProdutoDao produtoDao;
    private Session session;
    private DataModel<Produto> produtosModel;
    private boolean abreAba;
    private List<Produto> produtos;
    private List<SelectItem> fornecedorItem;

    public ProdutoControle() {
        produtoDao = new ProdutoDaoImpl();
    }

    public void novo() {
        abreAba = true;
        carregarComboBox();
    }

    public void voltar() {
        abreAba = false;
    }

    public void pesquisarPorNome() {
        session = HibernateUtil.abreSessao();
        try {
            produtos = produtoDao.
                    pesquisarPorNome(produto.getNome(), session);
            produtosModel = new ListDataModel<>(produtos);
        } catch (HibernateException he) {
            System.out.println("Erro ao pesquisar " + he.getMessage());
        } finally {
            session.close();
        }
    }

    public void salvar() {
        session = HibernateUtil.abreSessao();
        FacesContext contexto = FacesContext.getCurrentInstance();
        try {
            gerarCodigo();
            produto.setFornecedor(fornecedor);
            produtoDao.salvarOuAlterar(produto, session);
            produto = new Produto();
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Salvo com sucesso", ""));
        } catch (HibernateException e) {
            System.out.println("erro ao salvar " + e.getMessage());
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao salvar", ""));
        } finally {
            session.close();
        }
    }

    private void gerarCodigo() {
        Long id = produtoDao.pesquisarUltimoID(session);
        Calendar cal = Calendar.getInstance();
        Integer ano = cal.get(Calendar.YEAR);
        if (id == null) {
            id = 0L;
        }
        produto.setCodigo(ano.toString() + id);
    }

    public void excluir() {
        FacesContext contexto = FacesContext.getCurrentInstance();
        produto = produtosModel.getRowData();
        try {
            session = HibernateUtil.abreSessao();
            produtoDao.excluir(produto, session);
            produtosModel = null;
            produto = null;
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Excluído com sucesso", ""));
        } catch (HibernateException e) {
            System.out.println("Erro ao excluir " + e.getMessage());
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao excluir", ""));
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }
    
    public void carregarProduto(){
        produto = produtosModel.getRowData();
        fornecedor = produto.getFornecedor();
        carregarComboBox();
        abreAba = true;
    }
    
    public void carregarComboBox() {
        FornecedorDao fornecedorDao = new FornecedorDaoImpl();
        if (fornecedorItem == null) {
            fornecedorItem = new ArrayList<>();
            session = HibernateUtil.abreSessao();
            List<Fornecedor> fornecedores = fornecedorDao.todosFornecedor(session);
            for ( Fornecedor fornec : fornecedores) {
                fornecedorItem.add(new SelectItem(fornec.getId(), fornec.getNome()));
            }
        }

    }

//    getters e setters
    public Produto getProduto() {
        if (produto == null) {
            produto = new Produto();
        }
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public DataModel<Produto> getProdutosModel() {
        return produtosModel;
    }

    public boolean isAbreAba() {
        return abreAba;
    }

    public Fornecedor getFornecedor() {
        if (fornecedor == null) {
            fornecedor = new Fornecedor();
            
        }
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public List<SelectItem> getFornecedorItem() {
        return fornecedorItem;
    }
    
    
    
}
