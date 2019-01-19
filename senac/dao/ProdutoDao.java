/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.dao;

import br.com.senac.pojo.Produto;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Aluno
 */
public interface ProdutoDao extends BaseDao<Produto, Long>{
    
    Long pesquisarUltimoID(Session session) throws HibernateException;
    
}




