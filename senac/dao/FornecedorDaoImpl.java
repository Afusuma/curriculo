/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.dao;

import br.com.senac.pojo.Fornecedor;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Aluno
 */
public class FornecedorDaoImpl extends BaseDaoImpl<Fornecedor, Long>
        implements FornecedorDao, Serializable {

    @Override
    public Fornecedor pesquisarPorId(Long id, Session session) throws HibernateException {
//        Fornecedor fornecedor = (Fornecedor) session.get(Fornecedor.class, id);
//        return fornecedor;
        return (Fornecedor) session.get(Fornecedor.class, id);
    }

    @Override
    public List<Fornecedor> pesquisarPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Fornecedor f where "
                                                    + " f.nome LIKE :nomeHQL");
        consulta.setParameter("nomeHQL", "%" + nome + "%");
        return consulta.list();
    }

    @Override
    public List<Fornecedor> todosFornecedor(Session session) throws HibernateException {
        Query consulta = session.createQuery("from Fornecedor");
        return consulta.list();
    }

}
