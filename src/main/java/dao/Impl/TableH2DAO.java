package dao.Impl;


import config.ConnectionH2;

public class TableH2DAO extends TableDAO {

    public static String SCHEMA = "PADRAO";

    private static final String queryExistTable = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES ";
    private static final String queryExistColumns = "SELECT * FROM " + SCHEMA + ".";
    private static final String queryFindAll = "SELECT * FROM " + SCHEMA + ".";

    public TableH2DAO(){
        super(new ConnectionH2(), queryExistTable, queryExistColumns, queryFindAll);
    }
}
