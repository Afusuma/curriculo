/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.dao;

import br.com.senac.pojo.Produto;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Aluno
 */
public class ProdutoDaoImpl extends BaseDaoImpl<Produto, Long>
        implements ProdutoDao, Serializable {

    @Override
    public Produto pesquisarPorId(Long id, Session session) throws HibernateException {
        return (Produto) session.get(Produto.class, id);
    }

    @Override
    public List<Produto> pesquisarPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.
                createQuery("from Produto p where p.nome like :nome");
//        consulta.setParameter("nome", "%" + nome + "%");
//        return consulta.list();
        return consulta.setParameter("nome", "%" + nome + "%").list();
    }

    @Override
    public Long pesquisarUltimoID(Session session) throws HibernateException {
        Query consulta = session.createQuery("Select max(p.id) from Produto p");
        Long id = (Long)consulta.uniqueResult();
        return id;
    }

}
