package executor;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;

public class Executor {
    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public void execUpdate(String update) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(update);
        statement.close();
    }

    public <T> T execQuery(String query, ResultHandler<T> resultHandler) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(query);
        ResultSet resultSet = statement.getResultSet();
        T value = resultHandler.handle(resultSet);
        resultSet.close();
        statement.close();
        return value;
    }

}
