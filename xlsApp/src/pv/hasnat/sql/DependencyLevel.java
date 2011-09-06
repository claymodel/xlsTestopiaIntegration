/*
 * DependencyLevel.java
 *
 * Created on April 2, 2007, 10:52 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *
 * --- Last Update: 8/11/2010 5:31 PM ---
 *
 * Update Notes 8/11/2010 5:31 PM by Adrian Wijasa:
 * Added a suggestion to reload CSVLDR_TBL_DEPS if a table is not found.
 *
 * Update Notes 6/20/2010 11:58 PM by Adrian Wijasa:
 * Now works with PostgreSQL.
 *
 * Update Notes 6/16/2010 11:12 PM by Adrian Wijasa:
 * Passes SID as Schema if the DB Type is MySQL.
 * Passes the complete error message into TableNotFoundException, instead of just the table name.
 *
 * Update Notes 6/15/2010 10:35 PM by Adrian Wijasa:
 * This class now works with MySQL and PostgreSQL.
 *
 * Update Notes 6/4/2010 12:13 AM by Adrian Wijasa:
 * Now uses csvldr_tbl_deps instead of the YURLEV tables to figure out the table levels.
 * Schema parameter is added to the class constructor.
 *
 * Update Notes 2/1/2010 9:09 PM by Adrian Wijasa:
 * In an SQL query that determines the dependency level of a table, wijasa.yurlev1, 2, and 3 are changed to yurlev1, 2, and 3.  That way, yurlev1, 2, and 3 can be
 * set as public synonyms, and wijasa schema is not the only one that can use them.
 *
 * Update Notes 1/23/2010 3:49 PM by Adrian Wijasa:
 * Added every table that starts with WMT to Level 1 Table list.
 *
 * Update Notes 9/1/2009 11:32 AM:
 * When TableNotFoundException is thrown, there was no script that will close any SQL Connection before the
 * exception is thrown.  Now every SQL Connection will be closed before TableNotFoundException is thrown.
 *
 * Update Notes 8/24/2009 12:23 PM:
 * Added 'table.equalsIgnoreCase( "etvdtyp" )' into the list of level 1 tables.
 * This will consider ETVDTYP as a level 1 table.
 *
 * Update Notes 5/23/2009 2:53 PM:
 * Added 'table.equalsIgnoreCase( "ytvdtyp" )' into the list of level 1 tables.
 * This will consider YTVDTYP as a level 1 table.
 *
 * Update Notes 4/28/2009 4:52 PM:
 * Added 'table.equalsIgnoreCase( "gurhmnu" )' into the list of level 1 tables.
 * This will consider GURHMNU as a level 1 table.
 *
 * Update Notes 2/20/2009 3:17 PM:
 * Added 'table.equalsIgnoreCase( "gobdiro" )' into the list of level 1 tables.
 * This will consider GOBDIRO as a level 1 table.
 *
 * Update Notes 2/20/2009 1:48 PM:
 * Added every table that starts with GTV to Level 1 Table list.
 *
 * Update Notes 2/20/2009 12:34 PM:
 * Added every table that starts with STV to Level 1 Table list.
 *
 * Update Notes 2/19/2009 5:46 PM:
 * Added 'table.equalsIgnoreCase( "guriden" )' into the list of level 1 tables.
 * This will consider GURIDEN as a level 1 table.
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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pv.hasnat.form.Main;

/**
 * Define the dependency level of a table:
 * 1. Level 1: The table is not dependant on any other table
 * 2. Level 2 - ... : A row can only exist in this table, if some of its data exists in
 *      the Level 1 or lower level table it is dependent on.
 *
 * @author awijasa
 */
public class DependencyLevel {
    
    /** Creates a new instance of DependencyLevel */
    public DependencyLevel( Main main, String schema, String table ) throws ClassNotFoundException, SQLException, TableNotFoundException {
        this.main = main;

        if( main.dbType.equals( "MySQL" ) )
            this.schema = main.sid;
        else
            this.schema = schema;

        this.table = table;
        runQuery();
    }
    
    private void closeConnection() {
        try {
            if( rs != null )
                rs.close();

            if( stmt != null )
                stmt.close();

            if( conn != null )
                conn.close();
        }
        catch( SQLException e ) {}
    }
    
    private void runQuery() throws ClassNotFoundException, SQLException, TableNotFoundException {

        try {
            /* Connect to Database: Oracle, MySQL, or PostgreSQL */
            if( main.dbType.equals( "Oracle" ) ) {
                DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
                conn = DriverManager.getConnection( "jdbc:oracle:thin:@" + main.host + ":" + main.port + ":" + main.sid, main.user, main.password );
            }
            else if( main.dbType.equals( "MySQL" ) ) {
                Class.forName( "com.mysql.jdbc.Driver" );
                conn = DriverManager.getConnection( "jdbc:mysql://" + main.host + ":" + main.port + "/" + main.sid, main.user, main.password );
            }
            else {
                Class.forName( "org.postgresql.Driver" );
                conn = DriverManager.getConnection( "jdbc:postgresql://" + main.host + ":" + main.port + "/" + main.sid, main.user, main.password );
            }

            /* Is the table Level 1? */

            if( main.dbType.equals( "PostgreSQL" ) ) {
                stmt = conn.prepareStatement( "select " +
                        "table_level " +
                    "from " +
                        "csvldr.csvldr_tbl_deps " +
                    "where " +
                        "owner = ? and " +
                        "table_name = ?" );
            }
            else {
                stmt = conn.prepareStatement( "select " +
                        "table_level " +
                    "from " +
                        "csvldr_tbl_deps " +
                    "where " +
                        "owner = ? and " +
                        "table_name = ?" );
            }

            stmt.setString( 1, schema );
            stmt.setString( 2, table );
            rs = stmt.executeQuery();

            if( rs.next() )
                level = rs.getInt( "table_level" );
            else {
                String errMsg = "Failed to find " + table + " in the database.\n" +
                    "Try/Ask your programmer to reload CSVLDR_TBL_DEPS by re-running in " + main.dbType + ":\n\n";

                if( main.dbType.equals( "Oracle" ) ) {
                    errMsg += "set echo off\n" +
                        "set serverout on size unlimited\n\n" +
                        "truncate table csvldr_tbl_deps;\n\n" +
                        "begin\n" +
                            "\tcsvldr.p_build_tbl_deps;\n" +
                        "end;\n" +
                        "/\n\n" +
                        "commit;";
                }
                else if( main.dbType.equals( "MySQL" ) ) {
                    errMsg += "truncate table csvldr_tbl_deps;\n\n" +
                        "call p_build_tbl_deps();";
                }
                else {
                    errMsg += "truncate table csvldr.csvldr_tbl_deps;\n\n" +
                        "select csvldr.f_build_tbl_deps();";
                }

                throw new TableNotFoundException( errMsg );
            }

            closeConnection();
        }
        catch( SQLException se ) {
            closeConnection();
            throw new SQLException( se.getMessage() );
        }
        catch( TableNotFoundException te ) {
            closeConnection();
            throw new TableNotFoundException( te.getMessage() );
        }
    }
    
    private Connection conn;
    int level;
    private Main main;
    private PreparedStatement stmt;
    private ResultSet rs;
    private String schema;
    private String table;
}
