/*
 * MetaDataQuery.java
 *
 * --- Last Update 8/9/2010 9:54 PM ---
 *
 * Update Notes 8/9/2010 9:54 PM by Adrian Wijasa:
 * Added another constructor to enable connection to DB without Main Java class.
 *
 * Update Notes 6/20/2010 3:59 PM by Adrian Wijasa:
 * Changed the uses of word Oracle to Database. CSV Loader now works with both MySQL and PostgreSQL.
 *
 * Update Notes 5/17/2010 10:12 PM by Adrian Wijasa:
 * Don't force table to be Upper Case in methods: getColumns, getNotNullColumns, and getPrimaryKeys, so that
 * this class can work with MySQL tables that are case sensitive.
 *
 * Update Notes 5/16/2010 6:34 PM by Adrian Wijasa:
 * Added a getSchemas() method.
 *
 * Update Notes 4/6/2010 10:10 PM by Adrian Wijasa:
 * Now is able to handle Schema Text Field Inputs.
 *
 * Created on March 12, 2007, 2:39 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *
 * CSV Loader
 * Copyright 2007, 2009, 2010 Adrian Wijasa
 *
 * This file is part of CSV Loader.
 *
 * CSV Loader is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CSV Loader is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CSV Loader.  If not, see <http://www.gnu.org/licenses/>.
 */

package pv.hasnat.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pv.hasnat.form.Main;

/**
 * Query Meta Data Information from the Database
 *
 * @author awijasa
 */
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
