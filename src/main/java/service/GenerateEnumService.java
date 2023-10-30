package service;

import config.DataBaseName;
import model.MetaDataTableEnum;
import model.Row;
import model.ValueEnumFromColumnName;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GenerateEnumService {

    private final GenerateEnumFileService generateEnumFileService = new GenerateEnumFileService();

    private TableService tableService;

    private GenerateEnumService(){}

    public GenerateEnumService(DataBaseName dataBaseName) throws Exception {
        this.tableService = new TableService(dataBaseName);
    }

    public String generate(final String tableName, final String key, final String value, final Integer typeProperty) throws Exception {

        this.validate(tableName,key,value);
        String fileName = this.generateEnumFileService.constructorFileName(tableName);
        String absoluteFileName = generateEnumFileService.buildAbsoluteFileName(fileName);
        File file = this.generateEnumFileService.createFile(absoluteFileName);
        String enumName = this.generateEnumFileService.constructorNameEnum(tableName);
        List<Row> rows = this.tableService.findAll(tableName,key,value);
        this.generateEnumFileService.writeFile(file,enumName,rows, typeProperty);
        return enumName;
    }

    public void generate(final List<MetaDataTableEnum> list) throws Exception {

        for (var metaDataTableEnum : list) {
            this.validate(metaDataTableEnum);
            String fileName = this.generateEnumFileService.constructorFileName(metaDataTableEnum.getNameTable());
            String absoluteFileName = generateEnumFileService.buildAbsoluteFileName(fileName);
            File file = this.generateEnumFileService.createFile(absoluteFileName);
            String enumName = this.generateEnumFileService.constructorNameEnum(metaDataTableEnum.getNameTable());
            List<Row> rows = this.tableService.findAll(metaDataTableEnum);
            this.generateEnumFileService.writeFile(file,enumName,rows);
        }
    }

    private void validate(final String tableName,final String key, final String value) throws Exception {
        if (!this.tableService.existTable(tableName)){
            throw new Exception("Error to generate enum: The table \"" + tableName + "\" is not found in the database");
        }
        if (!this.tableService.existColumns(tableName, key, value)){
            throw new Exception("Error to generate enum: The Columns \"" + key + "\" and/or \"" + value + "\" is not found in the database in the table \"" + tableName + "\"");
        }
    }

    private void validate(final MetaDataTableEnum metaDataTableEnum) throws Exception {
        if (!this.tableService.existTable(metaDataTableEnum.getNameTable())){
            throw new Exception("Error to generate enum: The table \""
                    + metaDataTableEnum.getNameTable() + "\" is not found in the database");
        }

        var valuesEnumFromColumnName = new ArrayList<String>();

        valuesEnumFromColumnName.add(metaDataTableEnum.getKeyNameEnum());
        valuesEnumFromColumnName.addAll(metaDataTableEnum.getValuesEnumFromColumnName().stream().map(ValueEnumFromColumnName::getValueFromColumnName).collect(Collectors.toList()));

        for (var valueEnumFromColumnName: valuesEnumFromColumnName){
            if (!this.tableService.existColumns(metaDataTableEnum.getNameTable(), valueEnumFromColumnName)){

                throw new Exception("Error to generate enum: The Columns \"" + metaDataTableEnum.getKeyNameEnum()
                        + "\" and/or \"" + valueEnumFromColumnName
                        + "\" is not found in the database in the table \"" + metaDataTableEnum.getNameTable() + "\"");
            }
        }
    }
}
