package main;

import config.DataBaseName;
import model.MetaDataTableEnum;
import model.ValueEnumFromColumnName;
import service.GenerateEnumService;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Main {


    public static void main(String[] args) throws Exception {

        generateFromH2DataBaseExample();

        List<String> datasEnum = getDatasEnum();
        System.out.println();
        Integer typeProperty = getTypeValueProperty();
        System.out.println("** Generating Enum **");

        String enumName = new GenerateEnumService(DataBaseName.Oracle)
                .generate(datasEnum.get(0), datasEnum.get(1), datasEnum.get(2), typeProperty);

        System.out.println("** The Enum: "+ enumName + " was generated **");
    }

    private static List<String> getDatasEnum(){
        System.out.println("***** Enter with datas for to create an Enum: ***** ");
        List<String> datasEnum = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter table name: ");
        String dataTable = sc.next();
        System.out.println("Table Name: ".concat(dataTable));
        datasEnum.add(dataTable);
        System.out.print("Enter key name of table ".concat(dataTable).concat(": "));
        String data = sc.next();
        System.out.println("Key Name: ".concat(data));
        datasEnum.add(data);
        System.out.print("Enter valeu name of table ".concat(dataTable).concat(": "));
        data = sc.next();
        System.out.println("Value Name: ".concat(data));
        datasEnum.add(data);
        return datasEnum;
    }

    private static Integer getTypeValueProperty(){
        System.out.println("***** The type of the Enum value property: 0 or 1: ***** ");

        Scanner sc = new Scanner(System.in);
        var typeProperty = "";
        do{
            System.out.print("Enter 0 for String or 1 for Long: ");
            typeProperty = sc.next();
            System.out.println("Value type property: "+typeProperty);
            if (!typeProperty.equals("0") && !typeProperty.equals("1")){
                System.out.println("Error: Value type property is different than 0 or 1  ");
            }
        }while (!typeProperty.equals("0") && !typeProperty.equals("1"));
        return Integer.valueOf(typeProperty);
    }

    public static void generateFromH2DataBaseExample() throws Exception {

        System.out.println("** Generating Enum example from H2 database ... **");

        var propertyFileInputStream =
                new FileInputStream("src/main/java/main/datas-enum.properties");
        var properties = new Properties();
        properties.load(propertyFileInputStream);

        var list = new ArrayList<MetaDataTableEnum>();

        properties.forEach( (tableNameKey, property) -> {
            var keysAndColumns = ((String)property).split("=");
            var keyName = keysAndColumns[0];
            var columnNames = keysAndColumns[1];
            var columnsAndTypes = (columnNames).split(",");

            var metaDataTableEnum = MetaDataTableEnum.builder()
                    .nameTable((String) tableNameKey)
                    .keyNameEnum(keyName)
                    .valuesEnumFromColumnName(
                            Arrays.stream(columnsAndTypes).map( item ->{
                                var columnNamesAndTypes = item.split(":");

                                return ValueEnumFromColumnName.builder()
                                        .valueFromColumnName(columnNamesAndTypes[0])
                                        .type(Integer.valueOf(columnNamesAndTypes[1]))
                                        .build();
                            }).collect(Collectors.toList())
                    )
                    .build();

            list.add(metaDataTableEnum);
        });

        new GenerateEnumService(DataBaseName.H2).generate(list);

        System.out.println("** The Enums example from H2 database was generated **");
    }
}
