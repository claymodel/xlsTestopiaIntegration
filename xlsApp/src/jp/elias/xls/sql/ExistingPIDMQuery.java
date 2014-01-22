package jp.elias.xls.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.elias.xls.form.Main;

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
