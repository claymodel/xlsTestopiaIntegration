
package pv.hasnat.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import pv.hasnat.form.Main;

/**
 *
 * @author awijasa
 */
public class CSVPanelImageWriter {
    
    /** Creates a new instance of CSVPanelImageWriter */
    public CSVPanelImageWriter( Main main ) throws IOException {
        this.main = main;
        write();
    }

    /* Write configurations into a file */
    private void write() throws IOException {
        PrintWriter pWriter = new PrintWriter( new FileWriter( main.fileChooser.getFile() ) );
        ArrayList<String> sqlListArrayList;
        int sqlListSize;

        /* Iterate through all CSV columns */
        for( int i = 0; i < main.csvPanel.currentImage.columnArrayList.size(); i++ ) {

            /* Get the CSV Column - SQL Column Assignments of the column */
            sqlListArrayList = main.csvPanel.currentImage.sqlArrayList.get( i );
            sqlListSize = sqlListArrayList.size();

            /*
                Get the value of the Indicator that determine if the CSV column is going to be loaded into the
                database and write it into the configuration file
             */
            if( main.csvPanel.currentImage.includeArrayList.get( i ) )
                pWriter.print( "Don't Load" );
            else
                pWriter.print( "Load" );

            /* Write the CSV Column - SQL Column Assignments of the column into the configuration file */
            if( sqlListSize > 0 ) {
                for( int j = 0; j < sqlListArrayList.size(); j++ )
                    pWriter.print( "," + sqlListArrayList.get( j ) );
            }
            
            /* Only print the next line character the program is not iterating the last column */
            if( i < main.csvPanel.currentImage.columnArrayList.size() - 1 )
                pWriter.println( "" );
        }

        pWriter.close();
    }
    
    private Main main;
}
