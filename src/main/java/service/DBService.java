package service;

import dao.UserDao;
import dao.exceptions.DaoException;
import model.UsersDataSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.FileInputStream;
import java.util.Properties;

public class DBService {

    private static final String hibernate_show_sql = "false";
    private static final String hibernate_hbm2ddl_auto = "validate";

    private final SessionFactory sessionFactory;
    private static Properties properties;
    private static FileInputStream fis;

    public DBService() {
        properties = new Properties();
        Configuration configuration = getH2Connection();
        sessionFactory = createSessionFactory(configuration);
    }

    @SuppressWarnings("UnusedDeclaration")
    public static Configuration getMysqlConnection(){
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UsersDataSet.class);
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/db_example");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "admin");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

    public UsersDataSet addUser(UsersDataSet usersDataSet) throws DaoException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        UserDao userDao = new UserDao(session);
        userDao.insertUser(usersDataSet);
        UsersDataSet dataSet = userDao.getUser(usersDataSet.getName());
        transaction.commit();
        session.close();
        return dataSet;
    }

    public UsersDataSet searchUser(String name){
        Session session = sessionFactory.openSession();
        UserDao userDao = new UserDao(session);
        UsersDataSet dataSet = userDao.getUser(name);
        session.close();
        return dataSet;
    }

    public boolean userInDB(String name, String password){
        Session session = sessionFactory.openSession();
        UserDao userDao = new UserDao(session);
        boolean flag = userDao.inDb(name, password);
        session.close();
        return flag;
    }


    public static Configuration getH2Connection() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UsersDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./h2db");
        configuration.setProperty("hibernate.connection.username", "admin");
        configuration.setProperty("hibernate.connection.password", "admin");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

}
