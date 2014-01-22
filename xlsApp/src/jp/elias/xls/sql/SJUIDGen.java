package jp.elias.xls.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleTypes;
import jp.elias.xls.form.Main;

public class SJUIDGen {
    
    /** Creates a new instance of SJUIDGen */
    public SJUIDGen( Main main ) throws SQLException {
        this.main = main;
        quantity = main.csvPanel.csvTable.getRowCount(); // How many IDs need to be generated?
        idArrayList = new ArrayList<String>();
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

            /* Call the PL/SQL Package Procedure for generating IDs */
            stmt = conn.prepareCall( "{call wijasa.yukshrd.p_generate_id( ?, ?, ? )}" );
            id = 935000001; // Set the floor ID Number

            for( int i = 0; i < quantity; i++ ) {
                stmt.registerOutParameter( 1, OracleTypes.VARCHAR );
                stmt.setString( 2, "sju" );
                stmt.setString( 3, "" + id );
                stmt.executeUpdate();
                
                id = Integer.parseInt( stmt.getString( 1 ) ); // Get the new SJU ID
                idArrayList.add( "" + id );
                id++; // Set the new floor ID Number
            }

            closeConnection();
        }
        catch( SQLException e ) {
            closeConnection();
            throw new SQLException( e.getMessage() );
        }
    }
    
    public ArrayList<String> getIDs() {
        return idArrayList;
    }
    
    private CallableStatement stmt;
    private Connection conn;
    private int id;
    private int quantity;
    private Main main;
    private ArrayList<String> idArrayList;
}
