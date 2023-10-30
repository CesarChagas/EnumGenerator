package config;

public class ConnectionFactory {

    private static IConnectionDataBase connection;

    private ConnectionFactory(){}

    public static IConnectionDataBase getConnection(DataBaseName dataBaseName) throws Exception {

        applyConnection(DataBaseName.Oracle, dataBaseName, new ConnectionOracle());

        applyConnection(DataBaseName.H2, dataBaseName, new ConnectionH2());

        if(ConnectionFactory.connection == null){
            throw new Exception("Error to generate enum: Implementation from IConnectionDataBase not found!");
        }

        return ConnectionFactory.connection;
    }

    public static void disconnection(){
        ConnectionFactory.connection = null;
    }

    private static void applyConnection(DataBaseName dataBaseNameSource, DataBaseName dataBaseNameTarget,
                                        IConnectionDataBase iConnectionDataBaseTarget){

        if (!dataBaseNameSource.equals(dataBaseNameTarget)){
            return;
        }

        if (ConnectionFactory.connection != null){
            return;
        }

        ConnectionFactory.connection = iConnectionDataBaseTarget;
    }
}
