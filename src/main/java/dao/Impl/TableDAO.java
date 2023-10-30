package dao.Impl;

import config.ConnectionFactory;
import config.DataBaseName;
import config.IConnectionDataBase;
import dao.ITable;
import model.MetaDataTableEnum;
import model.Row;
import model.ValueEnumFromColumnName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class TableDAO implements ITable {

    public static String SCHEMA = "SCHEMA_EXAMPLE";

    private static String queryExistTable = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES ";
    private static String queryExistColumns = "SELECT * FROM " + SCHEMA + ".";
    private static String queryFindAll = "SELECT * FROM " + SCHEMA + ".";

    private static final String queryExistTableOracle = "SELECT * FROM ALL_TABLES ";
    private static final String queryExistColumnsOracle = "SELECT * FROM ";
    private static final String queryFindAllOracle = "SELECT * FROM ";

    private IConnectionDataBase connection;

    private TableDAO(){}

    public TableDAO(DataBaseName dataBaseName) throws Exception {

        if (dataBaseName.equals(DataBaseName.Oracle)) {
            queryExistTable = queryExistTableOracle;
            queryExistColumns = queryExistColumnsOracle;
            queryFindAll = queryFindAllOracle;
        }

        connection = ConnectionFactory.getConnection(dataBaseName);
    }

    @Override
    public List<Row> findAll(final String tableName, final String key, final String value) throws Exception {

        List<Row> rows = new ArrayList<>();
        Row row;
        var query = this.queryFindAll + tableName + " ORDER BY " + value;
        try (Connection connection = this.connection.connect();
             PreparedStatement preparedStatement  = connection.prepareStatement(query)  ) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                row = new Row();
                row.setKey(resultSet.getString(key.toUpperCase()));
                row.setValue(resultSet.getString(value.toUpperCase()));
                rows.add(row);
            }
        }finally {
            ConnectionFactory.disconnection();
        }

        return rows;
    }

    @Override
    public List<Row> findAll(final MetaDataTableEnum metaDataTableEnum) throws Exception {

        List<Row> rows = new ArrayList<>();
        Row row;
        var query = this.queryFindAll + metaDataTableEnum.getNameTable() + " ORDER BY " + metaDataTableEnum.getValuesEnumFromColumnName().get(0).getValueFromColumnName();
        try (Connection connection = this.connection.connect();
             PreparedStatement preparedStatement  = connection.prepareStatement(query)  ) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                row = new Row();

                row.setKey(resultSet.getString(metaDataTableEnum.getKeyNameEnum().toUpperCase()));

                row.setValues(new ArrayList<ValueEnumFromColumnName>());
                for (var valueFromColumnName: metaDataTableEnum.getValuesEnumFromColumnName()){

                    var value =
                            ValueEnumFromColumnName.builder()
                                    .valueFromColumnName(resultSet.getString(valueFromColumnName.getValueFromColumnName().toUpperCase()))
                                    .type(valueFromColumnName.getType())
                                    .build();

                    row.getValues().add(value);
                }
                rows.add(row);
            }
        }finally {
            ConnectionFactory.disconnection();
        }

        return rows;
    }

    @Override
    public boolean existTable(final String tableName) throws Exception {

        var query = this.queryExistTable + "  WHERE TABLE_NAME = '" + tableName + "'";
        try (Connection connection = this.connection.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            var resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }finally {
            ConnectionFactory.disconnection();
        }
    }

    @Override
    public boolean existColumns(final String tableName, final String key, final String value) throws Exception{

        boolean existKey = false;
        boolean existValue = false;
        var query = this.queryExistColumns + tableName;

        try (Connection connection = this.connection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columns = metaData.getColumnCount();
            for (int i = 1; i <= columns; i++ ){
                if (metaData.getColumnName(i).equals(key)){
                    existKey = true;
                }else if (metaData.getColumnName(i).equals(value)){
                    existValue = true;
                }
            }
        }finally {
            ConnectionFactory.disconnection();
        }

        return existKey && existValue;
    }

    @Override
    public boolean existColumns(final String tableName, final String key) throws Exception{

        var query = this.queryExistColumns + tableName;

        try (Connection connection = this.connection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            ResultSetMetaData metaData = resultSet.getMetaData();

            for (int i = 1; i <= metaData.getColumnCount(); i++ ){
                if (metaData.getColumnName(i).equals(key)){
                    return true;
                }
            }
        }finally {
            ConnectionFactory.disconnection();
        }

        return false;
    }
}
