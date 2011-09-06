/*
 * ExistingPIDMQuery.java
 *
 * Created on April 30, 2007, 10:21 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *
 * --- Last Update: 6/15/2010 10:38 PM ---
 *
 * Update Notes 6/15/2010 10:38 PM by Adrian Wijasa:
 * This class now works with MySQL and PostgreSQL.
 *
 * Update Notes 4/23/2010 5:48 PM by Adrian Wijasa:
 * Changed the final variable: ALUM_ID to ID.
 *
 * Update Notes 2/17/2009 4:23 PM:
 * When no PIDM is found from SSN or ID, add "" instead of null to the pidmArrayList. This is to prevent NullPointerException in sql.InsertStatement
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
import java.util.ArrayList;

import pv.hasnat.form.Main;

/**
 * Query Existing PIDMs using a filter:
 * 1. SSN
 * 2. ID
 * 3. 900 ID (CSBSJU Only)
 *
 * @author awijasa
 */
public class ExistingPIDMQuery {
    
    /** Creates a new instance of ExistingPIDMQuery */
    public ExistingPIDMQuery(
        Main main
        , int sourceColIndex    // The column index where to get the filter data source
        , int sourceType        // The data type of filter: SSN, ID, or PROD ID
    ) throws ClassNotFoundException, SQLException {
        this.main = main;
        this.sourceType = sourceType;
        sourceArrayList = main.csvPanel.currentImage.getColDataArrayList( sourceColIndex ); // Get Filter Data
        pidmArrayList = new ArrayList<String>();
        queryPIDMs();
    }
    
    private void closeConnection() {
        try {
            if( stmt != null )
                stmt.close();

            if( conn != null )
                conn.close();
        }
        catch( SQLException e ) {}
    }
    
    public ArrayList<String> getPidms() {
        return pidmArrayList;
    }
    
    private void queryPIDMs() throws ClassNotFoundException, SQLException {
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

            String selectStmt;
            
            if( sourceType == SSN )
                selectStmt = "select spbpers_pidm from spbpers where spbpers_ssn = ?";                                  // Get PIDMs from SSN
            else if( sourceType == ID )
                selectStmt = "select spriden_pidm from spriden where spriden_id = ? and spriden_change_ind is null";    // Get PIDMs from ALUM ID
            else
                selectStmt = "select spriden_pidm from spriden where spriden_id = ? and spriden_change_ind = 'I'";      // Get PIDMs from PROD ID
            
            stmt = conn.prepareStatement( selectStmt );
            
            /* Run the query */
            for( int i = 0; i < sourceArrayList.size(); i++ ) {
                stmt.setString( 1, sourceArrayList.get( i ) );
                ResultSet rs = stmt.executeQuery();
                
                if( rs.next() )
                    pidmArrayList.add( "" + rs.getInt( 1 ) );
                else
                    pidmArrayList.add( "" );
                
                rs.close();
            }

            closeConnection();
        }
        catch( SQLException e ) {
            closeConnection();
            throw new SQLException( e.getMessage() );
        }
    }
    
    private Connection conn;
    private int sourceType;
    private Main main;
    private PreparedStatement stmt;
    private ArrayList<String> pidmArrayList;
    private ArrayList<String> sourceArrayList;
    public static final int SSN = 0;
    public static final int ID = 1;
    public static final int PROD_ID = 2;
}
