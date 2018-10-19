package br.com.container.dao;

import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class BaseDaoImpl<T, ID>
        implements BaseDao<T, ID>, Serializable {

    private Transaction transaction;

    @Override
    public void salvarOuAlterar(T entidade, Session session) throws HibernateException {
        transaction = session.beginTransaction();
        try {
            session.saveOrUpdate(entidade);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    @Override
    public void remover(T entidade, Session session) throws HibernateException {
        transaction = session.beginTransaction();
        try {
            session.delete(entidade);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }
}
