package jp.elias.xls.tns;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

public class TNSNamesWriter {
    
    /** Creates a new instance of TNSNamesWriter */
    public TNSNamesWriter(
        /*
            ArrayList of TNS Configuration ArrayLists.  Each TNS Configuration
            contains four data elements:
            1. TNS Name (String)
            2. Host (String)
            3. Port (Integer)
            4. SID (String)
         */
        Vector<Vector> tnsVector
    ) throws IOException {
        rowVector = tnsVector;
        write();
    }
    
    public void write() throws IOException {
        PrintWriter pw = new PrintWriter( new FileWriter( "tnsnames.ora" ) );
        
        for( int i = 0; i < rowVector.size(); i++ ) {
            /* Read each TNS Configuration */
            colVector = rowVector.get( i );
            
            try {
                String tnsName = (String)colVector.get( 0 );    // Extract TNS Name
                String host = (String)colVector.get( 1 );       // Extract Host
                int port = (Integer)colVector.get( 2 );         // Extract Port
                String sid = (String)colVector.get( 3 );        // Extract SID
                
                /* Write a TNS Configuration into file */
                if( tnsName != null && host != null && sid != null ) {
                    pw.println( tnsName + " =" );
                    pw.println( "\t(DESCRIPTION =" );
                    pw.println( "\t\t(ADDRESS =" );
                    pw.println( "\t\t\t(PROTOCOL = TCP)" );
                    pw.println( "\t\t\t(Host = " + host + ")" );
                    pw.println( "\t\t\t(Port = " + port + ")" );
                    pw.println( "\t\t)" );
                    pw.println( "\t\t(CONNECT_DATA =" );
                    pw.println( "\t\t\t(SID = " + sid + ")" );
                    pw.println( "\t\t)" );
                    pw.println( "\t)" );
                }
            }
            catch( NullPointerException e ) {}
        }
        
        pw.close();
    }
    
    private Vector colVector;
    private Vector<Vector> rowVector;
}
