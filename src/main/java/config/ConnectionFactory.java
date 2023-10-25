package config;

import java.sql.Connection;

public class ConnectionFactory {

    private static IConnectionDataBase connection;

    private ConnectionFactory(){}

    public static void create(IConnectionDataBase connection){
        if (ConnectionFactory.connection != null){
            return;
        }

        ConnectionFactory.connection = connection;
    }

    public static Connection getConnection() throws Exception{
        return connection.connect();
    }
}
