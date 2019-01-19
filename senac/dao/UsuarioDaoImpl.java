/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.dao;

import br.com.senac.pojo.Perfil;
import br.com.senac.pojo.Usuario;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Aluno
 */
public class UsuarioDaoImpl extends BaseDaoImpl<Usuario, Long> 
                                        implements UsuarioDao, Serializable{

    @Override
    public Usuario pesquisarPorId(Long id, Session session) throws HibernateException {
        return (Usuario) session.get(Usuario.class, id);
    }

    @Override
    public List<Usuario> pesquisarPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Usuario where nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }

    @Override
    public List<Perfil> todosPerfil(Session session) throws HibernateException {
        Query consulta = session.createQuery("from Perfil");
        return consulta.list();
    }
    
}
