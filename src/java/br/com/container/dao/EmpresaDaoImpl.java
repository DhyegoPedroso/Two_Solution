/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.Empresa;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author felipedania
 */
public class EmpresaDaoImpl extends BaseDaoImpl<Empresa, Long> implements EmpresaDao, Serializable {

    @Override
    public Empresa pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        return (Empresa) session.get(Empresa.class, id);
    }

    @Override
    public List<Empresa> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Empresa ").list();
    }

    @Override
    public List<Empresa> pesquisaPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Empresa e WHERE e.nome like :nome ORDER BY e.nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }

}
