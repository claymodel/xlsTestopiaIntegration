package jp.elias.xls.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleTypes;
import jp.elias.xls.form.Main;

public class PIDMGen {
    
    /** Creates a new instance of PIDMGen */
    public PIDMGen( Main main ) throws SQLException {
        this.main = main;
        quantity = main.csvPanel.csvTable.getRowCount(); // How many PIDMs need to be generated?
        pidmArrayList = new ArrayList<String>();
        generate();
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
    
    private void generate() throws SQLException {
        try {
            /* Connect to a TNS in Oracle */
            DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection( "jdbc:oracle:thin:@" + main.host + ":" + main.port + ":" + main.sid, main.user, main.password );

            /* Call the PL/SQL Package Procedure for generating PIDMs */
            stmt = conn.prepareCall( "{call wijasa.yukshrd.p_generate_pidm( ? )}" );
            pidm = 1; // Set the floor PIDM

            for( int i = 0; i < quantity; i++ ) {
                stmt.setInt( 1, pidm );
                stmt.registerOutParameter( 1, OracleTypes.NUMBER );
                stmt.executeUpdate();
                
                pidm = stmt.getInt( 1 ); // Get the new PIDM
                pidmArrayList.add( "" + pidm );
                pidm++; // Set the new floor PIDM
            }

            closeConnection();
        }
        catch( SQLException e ) {
            closeConnection();
            throw new SQLException( e.getMessage() );
        }
    }
    
    public ArrayList<String> getPidms() {
        return pidmArrayList;
    }
    
    private CallableStatement stmt;
    private Connection conn;
    private int pidm;
    private int quantity;
    private Main main;
    private ArrayList<String> pidmArrayList;
}
