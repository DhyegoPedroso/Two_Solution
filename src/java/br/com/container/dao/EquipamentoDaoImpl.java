/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.Equipamento;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author felipedania
 */
public class EquipamentoDaoImpl extends BaseDaoImpl<Equipamento, Long> implements EquipamentoDao, Serializable{

    @Override
    public Equipamento pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        return (Equipamento) session.get(Equipamento.class, id);
    }

    @Override
    public List<Equipamento> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Equipamento").list();
    }

    @Override
    public List<Equipamento> pesquisaPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Equipamento e WHERE e.nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }
    
}
