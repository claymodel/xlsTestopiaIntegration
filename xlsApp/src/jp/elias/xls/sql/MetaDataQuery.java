package jp.elias.xls.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.elias.xls.form.Main;

public class MetaDataQuery {
    
    /** Creates a new instance of MetaDataQuery */
    public MetaDataQuery( Main main ) throws ClassNotFoundException, SQLException {
        dbType = main.dbType;
        host = main.host;
        password = main.password;
        port = main.port;
        sid = main.sid;
        user = main.user;
        connect();
    }

    public MetaDataQuery( String user, String password, String host, int port, String sid, String dbType ) throws ClassNotFoundException, SQLException {
        this.dbType = dbType;
        this.host = host;
        this.password = password;
        this.port = port;
        this.sid = sid;
        this.user = user;
        connect();
    }
    
    public void closeConnection() throws SQLException {
        if( rs != null )
            rs.close();
        
        if( conn != null )
            conn.close();
    }
    
    private void connect() throws ClassNotFoundException, SQLException {
        if( dbType.equals( "Oracle" ) ) {
            DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection( "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid, user, password );
        }
        else if( dbType.equals( "MySQL" ) ) {
            Class.forName( "com.mysql.jdbc.Driver" );
            conn = DriverManager.getConnection( "jdbc:mysql://" + host + ":" + port + "/" + sid, user, password );
        }
        else {
            Class.forName( "org.postgresql.Driver" );
            conn = DriverManager.getConnection( "jdbc:postgresql://" + host + ":" + port + "/" + sid, user, password );
        }
    }
    
    /* Get all column of the table specified */
    public ArrayList<String> getColumns( String schema, String table ) throws SQLException {
        try {
            metaData = conn.getMetaData();
            ArrayList<String> columnList = new ArrayList<String>();

            rs = metaData.getColumns( null, schema, table, null );

            while( rs.next() )
                columnList.add( rs.getString( "COLUMN_NAME" ) );

            return columnList;
        }
        catch( SQLException e ) {
            closeConnection();
            throw new SQLException( e.getMessage() );
        }
    }
    
    /* Get all Not Null column of the table specified */
    public ArrayList<String> getNotNullColumns( String schema, String table ) throws SQLException {
        try {
            metaData = conn.getMetaData();
            ArrayList<String> columnList = new ArrayList<String>();

            rs = metaData.getColumns( null, schema, table, null );

            while( rs.next() )
                if( rs.getString( "IS_NULLABLE" ).equals( "NO" ) )
                    columnList.add( rs.getString( "COLUMN_NAME" ) );

            return columnList;
        }
        catch( SQLException e ) {
            closeConnection();
            throw new SQLException( e.getMessage() );
        }
    }
    
    /* Get all Primary Key of the table specified */
    public ArrayList<String> getPrimaryKeys( String schema, String table ) throws SQLException {
        try {
            metaData = conn.getMetaData();
            ArrayList<String> columnList = new ArrayList<String>();

            rs = metaData.getPrimaryKeys( null, schema, table );

            while( rs.next() )
                columnList.add( rs.getString( "COLUMN_NAME" ) );

            return columnList;
        }
        catch( SQLException e ) {
            closeConnection();
            throw new SQLException( e.getMessage() );
        }
    }
    
    /* Get all schemas found from the DB Connection */
    public ArrayList<String> getSchemas() throws SQLException {
        try {
            metaData = conn.getMetaData();
            ArrayList<String> schemaList = new ArrayList<String>();

            rs = metaData.getSchemas();

            while( rs.next() )
                schemaList.add( rs.getString( "TABLE_SCHEM" ) );

            return schemaList;
        }
        catch( SQLException e ) {
            closeConnection();
            throw new SQLException( e.getMessage() );
        }
    }

    /* Get all table of the Schema specified */
    public ArrayList<String> getTables( String schema ) throws SQLException {
        try {
            metaData = conn.getMetaData();
            ArrayList<String> tableList = new ArrayList<String>();

            rs = metaData.getTables( null, schema, null, new String[] { "TABLE" } );

            while( rs.next() )
                tableList.add( rs.getString( "TABLE_NAME" ) );

            return tableList;
        }
        catch( SQLException e ) {
            closeConnection();
            throw new SQLException( e.getMessage() );
        }
    }
    
    private Connection conn;
    private DatabaseMetaData metaData;
    private int port;
    private ResultSet rs;
    private String dbType;
    private String host;
    private String password;
    private String sid;
    private String user;
}
