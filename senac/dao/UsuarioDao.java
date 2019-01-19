/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.dao;

import br.com.senac.pojo.Perfil;
import br.com.senac.pojo.Usuario;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Aluno
 */
public interface UsuarioDao extends BaseDao<Usuario, Long> {

    List<Perfil> todosPerfil(Session session) throws HibernateException;
}
