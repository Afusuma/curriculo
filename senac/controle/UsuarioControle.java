/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.controle;

import br.com.senac.dao.HibernateUtil;
import br.com.senac.dao.UsuarioDao;
import br.com.senac.dao.UsuarioDaoImpl;
import br.com.senac.pojo.Perfil;
import br.com.senac.pojo.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Aluno
 */
@ManagedBean(name = "usuarioC")
@ViewScoped
public class UsuarioControle {

    private Usuario usuario;
    private Perfil perfil;
    private UsuarioDao usuarioDao;
    private Session session;
    private DataModel<Usuario> modelUsuarios;
    private List<SelectItem> perfilItens;
    private boolean abreAba;

    public UsuarioControle() {
        usuarioDao = new UsuarioDaoImpl();
    }

    public void novo() {
        carregarTodosPerfil();
        abreAba = true;
    }

    public void voltar() {
        abreAba = false;
    }

    public void pesquisarPorNome() {
        session = HibernateUtil.abreSessao();
        try {
            List<Usuario> usuarios = usuarioDao.
                    pesquisarPorNome(usuario.getNome(), session);
            modelUsuarios = new ListDataModel<>(usuarios);
        } catch (HibernateException he) {
            Mensagem.erro("Erro ao pesquisar");
        } finally {
            session.close();
        }
    }

    public void salvar() {
        session = HibernateUtil.abreSessao();
        try {
            usuario.setPerfil(perfil);
            if(usuario.getId() == null){
                usuario.setSenha("12345");
            }
            usuarioDao.salvarOuAlterar(usuario, session);
            usuario = new Usuario();
            Mensagem.sucesso("Salvo com sucesso");
        } catch (HibernateException e) {
            Mensagem.erro("Erro ao salvar");
        } finally {
            session.close();
        }
    }

    public void excluir() {
        usuario = modelUsuarios.getRowData();
        try {
            session = HibernateUtil.abreSessao();
            usuarioDao.excluir(usuario, session);
            modelUsuarios = null;
            usuario = null;
            Mensagem.sucesso("Excluído com sucesso");
        } catch (HibernateException e) {
            Mensagem.sucesso("Erro ao excluir");
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    public void carregarUsuario() {
        usuario = modelUsuarios.getRowData();
        perfil = usuario.getPerfil();
        carregarTodosPerfil();
        abreAba = true;
    }

    public void carregarTodosPerfil() {
        if (perfilItens == null) {
            perfilItens = new ArrayList<>();
            session = HibernateUtil.abreSessao();
            List<Perfil> perfis = usuarioDao.todosPerfil(session);
            for (Perfil perfi : perfis) {
                perfilItens.add(new SelectItem(perfi.getId(), perfi.getNome()));
            }
        }

    }

//    leitura escrita
    public Usuario getUsuario() {
        if (usuario == null) {
            usuario = new Usuario();
        }
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Perfil getPerfil() {
        if (perfil == null) {
            perfil = new Perfil();
        }
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public DataModel<Usuario> getModelUsuarios() {
        return modelUsuarios;
    }

    public List<SelectItem> getPerfilItens() {
        return perfilItens;
    }

    public boolean isAbreAba() {
        return abreAba;
    }

}
