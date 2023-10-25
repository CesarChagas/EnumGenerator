package config;

import java.sql.Connection;

public interface IConnectionDataBase {

    Connection connect() throws Exception;
}
