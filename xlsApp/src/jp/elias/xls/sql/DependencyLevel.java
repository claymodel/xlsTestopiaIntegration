package jp.elias.xls.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.elias.xls.form.Main;

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
