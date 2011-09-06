/*
 * TNSNamesReader.java
 *
 * --- Last Update: 5/11/2010 12:23 AM ---
 *
 * Update Notes 5/11/2010 12:23 AM by Adrian Wijasa:
 * Now this class doesn't automatically turn the values written in TNSNAMES.ORA into uppercase.
 *
 * Created on March 6, 2007, 4:35 PM
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import pv.hasnat.form.Main;

/**
 * Reads tnsnames.ora and save it into a configuration file local to CSV Loader
 *
 * @author awijasa
 */
public class TNSNamesReader {
    
    /** Creates a new instance of TNSNamesReader */
    public TNSNamesReader( Main main, File tnsNamesFile ) throws FileNotFoundException, IOException, TNSException {
        this.main = main;
        this.tnsNamesFile = tnsNamesFile;
        digest();
    }
    
    /*
        Read and generate a local configuration file.
        Throws FileNotFoundException if the tnsnames.ora file selected is gone,
        IOException for Input/Output Error, TNSException if unsupported syntaxes
        are found in the ORA file read.
     */
    private void digest() throws FileNotFoundException, IOException, TNSException {
        BufferedReader bReader = new BufferedReader( new FileReader( tnsNamesFile ) );
        
        rowVector = new Vector<Vector>();
        
        while( true ) {
            Vector colArrayList = new Vector( 4 );
            
            line = bReader.readLine();
            
            /* Found EOF (End of File). Go out from the loop */
            if( line == null )
                break;
            
            /* Remove all line breaks and spacing */
            tns = line.trim().replaceAll( " ", "" );
            
            for( int i = 0; i < 10; i++ ) {
                line = bReader.readLine();
                
                if( line == null )
                    throw new TNSException();
                
                tns += line.trim().replaceAll( " ", "" );
            }
            
            /* Extract TNS Name */
            index = tns.indexOf( '=' );
            
            if( index == 0 || index == -1 )
                throw new TNSException();
            
            colArrayList.add( tns.substring( 0, index ) );
            
            /* Extract Host */
            index = tns.toUpperCase().indexOf( "(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=", index );
            
            if( index == -1 )
                throw new TNSException();
            
            index += 42;
            
            colArrayList.add( tns.substring( index, tns.indexOf( ')', index ) ) );
            
            /* Extract Port */
            index = tns.toUpperCase().indexOf( ")(PORT=", index );
            
            if( index == -1 )
                throw new TNSException();
            
            index += 7;
            
            colArrayList.add( Integer.parseInt( tns.substring( index, tns.indexOf( ')', index ) ) ) );
            
            /* Extract SID */
            index = tns.toUpperCase().indexOf( "))(CONNECT_DATA=(SID=", index );
            
            if( index == -1 )
                throw new TNSException();
            
            index += 21;
            
            colArrayList.add( tns.substring( index, tns.indexOf( ')', index) ) );
            
            index = tns.indexOf( ")))", index );
            
            if( index == -1 )
                throw new TNSException();
            
            rowVector.add( colArrayList );
        }
        
        bReader.close();
    }
    
    public Vector<Vector> getTNSVector() {
        return rowVector;
    }
    
    private File tnsNamesFile;
    private int index;
    private Main main;
    private String line;
    private String tns;
    private Vector<Vector> rowVector;
}
