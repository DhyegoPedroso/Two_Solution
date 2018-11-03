package br.com.container.dao;

import br.com.container.modelo.Aluno;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Dhyego Pedroso
 */
public interface AlunoDao extends BaseDao<Aluno, Long> {

    List<Aluno> pesqPorNomeOuCpf(String nome, String cpf, Session session) throws HibernateException;

}
