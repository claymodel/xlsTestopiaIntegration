package jp.elias.xls.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import jp.elias.xls.form.Main;

public class CSVPanelImageReader {
    
    /** Creates a new instance of CSVPanelImageReader */
    public CSVPanelImageReader( Main main ) throws FileNotFoundException, IOException {
        this.main = main;
        includeArrayList = new ArrayList<Boolean>();
        sqlArrayList = new ArrayList<ArrayList<String>>();
        read();
    }

    /* Read configurations from a file */
    private void read() throws FileNotFoundException, IOException {
        BufferedReader bReader = new BufferedReader( new FileReader( main.fileChooser.fileChooser.getSelectedFile() ) );

        /* Iterate until the end of file (EOF) */
        while( true ) {
            String line = bReader.readLine();

            /* Stop reading or break from loop when reaching EOF */
            if( line == null )
                break;
            
            ArrayList<String> sqlColArrayList = new ArrayList<String>();

            /* Enter the configurations of a column into a config array */
            String config[] = line.split( "," );
                
            /* Pick up the "Do not load column" indicator */
            if( config[0].equals( "Load" ) )
                includeArrayList.add( false );
            else
                includeArrayList.add( true );

            /* Pick up the CSV Column - SQL Column Assignments */
            for( int i = 1; i < config.length; i++ )
                sqlColArrayList.add( config[i] );
            
            sqlArrayList.add( sqlColArrayList );
        }
        
        bReader.close();
    }
    
    private Main main;                          /* The main class of CSV Loader */
    public ArrayList<Boolean> includeArrayList;       /* Contains the states of 'Do not Load into Column' Checkboxes */
    public ArrayList<ArrayList<String>> sqlArrayList;    /* Contains the Column List Configurations */
}
