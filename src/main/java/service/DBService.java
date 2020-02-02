package service;

import dao.UserDao;
import dao.exceptions.DaoErrorCode;
import model.User;
import org.h2.jdbcx.JdbcDataSource;
import service.exceptions.DbException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBService {
    private final Connection connection;
    private static Properties properties;
    private static FileInputStream fis;

    public DBService() {
        properties = new Properties();
        this.connection = getH2Connection();
    }

    @SuppressWarnings("UnusedDeclaration")
    public static Connection getMysqlConnection(){
        try {
            fis = new FileInputStream("src/main/java/configuration/mysql.properties");
            properties.load(fis);
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance());
            StringBuilder url = new StringBuilder();
            url.
                    append(properties.getProperty("DB_TYPE")).        //db type
                    append(properties.getProperty("HOST_NAME")).           //host name
                    append(properties.getProperty("PORT")).                //port
                    append(properties.getProperty("NAME_DB")).              //db name
                    append(properties.getProperty("TIMEZONE"));
            return DriverManager.getConnection(url.toString(), properties.getProperty("NAME"), properties.getProperty("PASSWORD"));
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("wrong property path ".concat(e.getMessage()));
        }
        return null;
    }

    public String addUser(User user){
        try {
            connection.setAutoCommit(false);
            UserDao userDao = new UserDao(connection);
            userDao.createTable();
            if(userDao.inDb(user.getName())) {
                userDao.insertUser(user);
                connection.commit();
                connection.setAutoCommit(true);
                return userDao.get(user.getName());
            }
            return DaoErrorCode.USER_IN_DB.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean searchUser(User user){
        try {
            UserDao userDao = new UserDao(connection);
            return userDao.getUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void cleanup() throws DbException {
        UserDao userDao = new UserDao(connection);
        try{
            userDao.dropTable();
        } catch (SQLException exc) {
            throw new DbException(exc);
        }
    }

    public static Connection getH2Connection() {
        try {
            fis = new FileInputStream("src/main/java/configuration/h2.properties");
            properties.load(fis);
            String url = properties.getProperty("URL");
            String name = properties.getProperty("NAME");
            String pass = properties.getProperty("PASSWORD");
//            String url = "jdbc:h2:./h2db";
//            String name = "admin";
//            String pass = "admin";
            JdbcDataSource ds = new JdbcDataSource();
            ds.setURL(url);
            ds.setUser(name);
            ds.setPassword(pass);
            return DriverManager.getConnection(url, name, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("wrong property path ".concat(e.getMessage()));
        }
        return null;
    }

    public void printConnectionInfo(){
        try {
            System.out.println("DB Name " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
