package br.com.container.dao;

import br.com.container.modelo.Carterinha;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Dhyego Pedroso
 */
public interface CarterinhaDao extends BaseDao<Carterinha, Long> {

    public List<Carterinha> pesqPorAlunoOuMatricula(String aluno, String matricula, Session session) throws HibernateException;

    public Long ultimoIdCarterinha(Session session) throws HibernateException;

}
