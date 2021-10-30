package org.chat.dao;

import org.chat.dao.exceptions.DaoErrorCode;
import org.chat.dao.exceptions.DaoException;
import org.chat.model.UsersDataSet;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import java.util.HashMap;
import java.util.Map;

public class UserDao {
    private final Session session;

    public UserDao(Session session) {
        this.session = session;
    }

    public UsersDataSet get(long id) {
        return session.get(UsersDataSet.class, id);
    }

    public UsersDataSet getUser(String name) {
        Criteria criteria = session.createCriteria(UsersDataSet.class);
        return (UsersDataSet) criteria.add(Restrictions.eq("name", name)).uniqueResult();
    }

    public void insertUser(UsersDataSet usersDataSet) throws DaoException {
        try {
            session.save(usersDataSet);
        } catch (ConstraintViolationException exc) {
            throw new DaoException(DaoErrorCode.USER_IN_DB);
        }
    }

    public boolean inDb(String name, String password) {
        Criteria criteria = session.createCriteria(UsersDataSet.class);
        Map<String, String> restrictions = new HashMap<>();
        restrictions.put("name", name);
        restrictions.put("password", password);
        criteria.add(Restrictions.allEq(restrictions));
        return !criteria.list().isEmpty();
    }

}
