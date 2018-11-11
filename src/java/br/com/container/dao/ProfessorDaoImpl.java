/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.Professor;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author cel05
 */
public class ProfessorDaoImpl extends BaseDaoImpl<Professor, Long> implements ProfessorDao {

    @Override
    public Professor pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        return (Professor) session.get(Professor.class, id);
    }

    @Override
    public List<Professor> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Professor").list();
    }

    @Override
    public List<Professor> pesquisaPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Professor p where p.nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }

    @Override
    public List<Professor> pesqPorNomeOuDisciplina(String nome, String disciplina, Session session) throws HibernateException {
        String sql = "from Professor p where ";
        Query consulta = null;
        if (!nome.isEmpty()) {
            sql += " p.nome like :nome";
            consulta = session.createQuery(sql);
            consulta.setParameter("nome", "%" + nome + "%");
        } else {
            sql += " p.disciplinas like :disciplinas";
            consulta = session.createQuery(sql);
            consulta.setParameter("disciplinas", "%" + disciplina + "%");
        }
        return consulta.list();
    }

    @Override
    public List<Professor> pesqPorCidadeOuBairro(String cidade, String bairro, Session session) throws HibernateException {
        String sql = "from Professor p where ";
        Query consulta = null;
        if (!cidade.isEmpty()) {
            sql += " p.endereco.cidade like :cidade";
            consulta = session.createQuery(sql);
            consulta.setParameter("cidade", "%" + cidade + "%");
        } else {
            sql += " p.endereco.bairro like :bairro";
            consulta = session.createQuery(sql);
            consulta.setParameter("bairro", "%" + bairro + "%");
        }
        return consulta.list();
    }
}
