package dao.Impl;


import config.ConnectionOracle;

public class TableOracleDAO extends TableDAO {

    private static final String queryExistTable = "SELECT * FROM ALL_TABLES ";
    private static final String queryExistColumns = "SELECT * FROM ";
    private static final String queryFindAll = "SELECT * FROM ";

    public TableOracleDAO(){
        super(new ConnectionOracle(), queryExistTable, queryExistColumns, queryFindAll);
    }
}
