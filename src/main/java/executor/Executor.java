package executor;

import dao.UserDao;
import dao.exceptions.DaoException;
import model.UsersDataSet;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Executor {

    private final Session session;
    private final UserDao userDao;

    public Executor(Session session) {
        this.session = session;
        userDao = new UserDao(session);
    }

    public UsersDataSet execUpdate(UsersDataSet usersDataSet) throws DaoException {
        Transaction transaction = session.beginTransaction();
        userDao.insertUser(usersDataSet);
        transaction.commit();
        return execGet(usersDataSet.getName());
    }

    public UsersDataSet execGet(String name) {
        UsersDataSet usersDataSet = userDao.getUser(name);
        session.close();
        return usersDataSet;
    }

    public boolean execCheck(String name, String password) {
        boolean flag = userDao.inDb(name, password);
        session.close();
        return flag;
    }

}
