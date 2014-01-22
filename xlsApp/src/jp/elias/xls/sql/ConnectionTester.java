package jp.elias.xls.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
