package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionH2 implements IConnectionDataBase {
    public Connection connect() throws Exception {

        Class.forName("org.h2.Driver");

        String url = "jdbc:h2:file:./database;USER=sa;PASSWORD=;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE;INIT=runscript from './schema.sql'\\;runscript from './import.sql'";
        String user = "sa";
        String password = "";

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            throw new Exception("Error to generate enum: Is not possible to connect with the database");
        }
    }
}
