package br.com.container.dao;

import br.com.container.modelo.Carterinha;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Dhyego Pedroso
 */
public class CarterinhaDaoImpl extends BaseDaoImpl<Carterinha, Long> implements CarterinhaDao {

    @Override
    public Carterinha pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        return (Carterinha) session.get(Carterinha.class, id);
    }

    @Override
    public List<Carterinha> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Carterinha").list();
    }

    @Override
    public List<Carterinha> pesquisaPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Carterinha c where c.aluno.nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }

    @Override
    public List<Carterinha> pesqPorAlunoOuMatricula(String aluno, String matricula, Session session) throws HibernateException {
        String sql = "FROM Carterinha c where ";
        Query consulta = null;

        if (!aluno.isEmpty()) {
            sql += " c.aluno.nome like :aluno";
            consulta = session.createQuery(sql);
            consulta.setParameter("aluno", "%" + aluno + "%");
        } else {
            sql += " c.matricula like :matricula";
            consulta = session.createQuery(sql);
            consulta.setParameter("matricula", matricula);
        }
        return consulta.list();
    }
}
