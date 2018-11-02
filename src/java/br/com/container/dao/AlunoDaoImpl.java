package br.com.container.dao;

import br.com.container.modelo.Aluno;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Dhyego Pedroso
 */
public class AlunoDaoImpl extends BaseDaoImpl<Aluno, Long> implements AlunoDao {

    @Override
    public Aluno pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        return (Aluno) session.get(Aluno.class, id);
    }

    @Override
    public List<Aluno> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Aluno").list();
    }

    @Override
    public List<Aluno> pesquisaPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Aluno a where a.nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }

    @Override
    public List<Aluno> pesqPorCpf(String cpf, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Aluno a where a.cpf like :cpf");
        consulta.setParameter("cpf", "%" + cpf + "%");
        return consulta.list();
    }

}
