package br.com.container.dao;

import br.com.container.modelo.Professor;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author cel05
 */
public interface ProfessorDao extends BaseDao<Professor, Long> {

    List<Professor> pesqPorNomeOuDisciplina(String nome, String disciplina, Session session) throws HibernateException;

    List<Professor> pesqPorCidadeOuBairro(String cidade, String bairro, Session session) throws HibernateException;
}
