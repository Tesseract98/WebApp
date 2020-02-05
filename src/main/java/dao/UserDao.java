package dao;

import dao.exceptions.DaoErrorCode;
import dao.exceptions.DaoException;
import model.UsersDataSet;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDao {
    private Session session;

    public UserDao(Session session){
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
        }
        catch (ConstraintViolationException exc){
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
