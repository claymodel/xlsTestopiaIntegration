/*
 * ConnectionTester.java
 *
 * --- Last Update: 5/13/2010 6:21 PM ---
 *
 * Update Notes 5/13/2010 6:21 PM by Adrian Wijasa:
 * Changed test() method to getDBType() that returns the DB Type found from three connection trials.
 * Connect to PostgreSQL if MySQL connection failed.
 *
 * Update Notes 5/11/2010 12:02 AM by Adrian Wijasa:
 * Connect to MySQL if Oracle connection failed.
 *
 * Created on March 12, 2007, 10:29 AM
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
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author awijasa
 */
public class ConnectionTester {
    
    /** Creates a new instance of ConnectionTester */
    public ConnectionTester( String user, String password, String host, int port, String sid ) throws ClassNotFoundException, SQLException {
        this.user = user;
        this.password = password;
        this.host = host;
        this.port = port;
        this.sid = sid;
    }
    
    public String getDBType() throws ClassNotFoundException, SQLException {
        Connection conn;

        try {
            DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection( "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid, user, password );
            conn.close();

            dbType = "Oracle";
        }
        catch( SQLException oracleE ) {
            try {
                Class.forName( "com.mysql.jdbc.Driver" );
                conn = DriverManager.getConnection( "jdbc:mysql://" + host + ":" + port + "/" + sid, user, password );
                conn.close();

                dbType = "MySQL";
            }
            catch( Exception mysqlE ) {
                try {
                    Class.forName( "org.postgresql.Driver" );
                    conn = DriverManager.getConnection( "jdbc:postgresql://" + host + ":" + port + "/" + sid, user, password );
                    conn.close();

                    dbType = "PostgreSQL";
                }
                catch( ClassNotFoundException ce ) {
                    throw new ClassNotFoundException( ce.getMessage() );
                }
                catch( SQLException se ) {
                    throw new SQLException( se.getMessage() );
                }
            }
        }

        return dbType;
    }
    
    private String host;
    private String password;
    private String sid;
    private String user;
    private String dbType;
    private int port;
}
