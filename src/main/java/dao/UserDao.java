package dao;

import executor.Executor;
import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private Executor executor;

    public UserDao(Connection connection){
        this.executor = new Executor(connection);
    }

    public String get(String name) throws SQLException {
        return executor.execQuery(String.format("select * from users where name='%s'", name), result ->
        {
            result.next();
            User user = new User(result.getString(2), result.getString(3));
            user.setId(result.getLong(1));
            return user.toString();
        });
    }

    public boolean getUser(User user) throws SQLException {
        return executor.execQuery(String.format("select * from users where name='%s' and password='%s'", user.getName(), user.getPassword()),
                ResultSet::next);
    }

    public void insertUser(User user) throws SQLException {
        executor.execUpdate(String.format(
                "insert into users (name, password) values ('%s', '%s')", user.getName(), user.getPassword()));
    }

    public void createTable() throws SQLException {
        executor.execUpdate("create table if not exists users(id bigint auto_increment, name varchar(256), password varchar(256), primary key(id))");
    }

    public void dropTable() throws SQLException {
        executor.execUpdate("drop table users");
    }

    public boolean inDb(String name) throws SQLException {
        return executor.execQuery(String.format("select * from users where name='%s'", name), result ->
        {
            if(result.next()){
                return false;
            }
            return true;
        });
    }

}
