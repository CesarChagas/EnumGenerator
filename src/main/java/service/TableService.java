package service;

import dao.ITable;
import model.MetaDataTableEnum;
import model.Row;

import java.util.List;

public class TableService {

    private final ITable tableDAO;

    public TableService(ITable tableDAO){
        this.tableDAO = tableDAO;
    }

    public List<Row> findAll(final String tableName, final String key, final String value) throws Exception {
        return this.tableDAO.findAll(tableName,key,value);
    }

    public List<Row> findAll(final MetaDataTableEnum metaDataTableEnum) throws Exception {
        return this.tableDAO.findAll(metaDataTableEnum);
    }

    public boolean existTable(final String tableName) throws Exception {
        return this.tableDAO.existTable(tableName);
    }

    public boolean existColumns(final String tableName, final String key, final String value) throws Exception {
        return this.tableDAO.existColumns(tableName,key, value );
    }

    public boolean existColumns(final String tableName, final String column) throws Exception {
        return this.tableDAO.existColumns(tableName,column);
    }

}
