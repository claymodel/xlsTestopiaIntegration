/*
 * TNSNamesWriter.java
 *
 * Created on March 9, 2007, 10:16 AM
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

package pv.hasnat.tns;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

/**
 * Write TNS Configurations into tnsnames.ora that is local to CSV Loader
 *
 * @author awijasa
 */
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
