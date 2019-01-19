/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.dao;

import br.com.senac.pojo.Fornecedor;
import br.com.senac.pojo.Perfil;
import br.com.senac.pojo.Produto;
import br.com.senac.pojo.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Aluno
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    
    static {
        try {
            Configuration cfg = new Configuration();
            cfg.addAnnotatedClass(Fornecedor.class);
            cfg.addAnnotatedClass(Produto.class);
            cfg.addAnnotatedClass(Usuario.class);
            cfg.addAnnotatedClass(Perfil.class);

            cfg.configure("/br/com/senac/dao/hibernate.cfg.xml");
            StandardServiceRegistryBuilder build = 
                    new StandardServiceRegistryBuilder().applySettings(cfg.
                                              getProperties());
            sessionFactory = cfg.buildSessionFactory(build.build());
        } catch (Throwable ex) {
            System.err.println("Erro ao criar fábrica de conexão." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static Session abreSessao() {
        return sessionFactory.openSession();
    }
}
