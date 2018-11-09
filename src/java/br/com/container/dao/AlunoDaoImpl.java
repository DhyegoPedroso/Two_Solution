package br.com.container.dao;

import br.com.container.modelo.Aluno;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
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
    public List<Aluno> pesqPorNomeOuCpf(String nome, String cpf, Session session) throws HibernateException {
        String sql = "from Aluno a where ";
        Query consulta = null;

        if (!nome.isEmpty()) {
            sql += " a.nome like :nome";
            consulta = session.createQuery(sql);
            consulta.setParameter("nome", "%" + nome + "%");
        } else {
            sql += " a.cpf like :cpf";
            consulta = session.createQuery(sql);
            consulta.setParameter("cpf", cpf);
        }

        return consulta.list();
    }

    @Override
    public Long ultimoIdAluno(Session session) throws HibernateException {
        Query consulta = session.createQuery("SELECT MAX(a.id) FROM Aluno a");
        return (Long) consulta.uniqueResult();
    }

}
