package jp.elias.xls.tns;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import jp.elias.xls.form.Main;

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
