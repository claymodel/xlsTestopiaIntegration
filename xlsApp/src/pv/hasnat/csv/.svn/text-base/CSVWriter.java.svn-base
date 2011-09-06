
package pv.hasnat.csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.AbstractList;

import pv.hasnat.form.Main;

/**
 *
 * @author awijasa
 */
public class CSVWriter {
    
    /** Creates a new instance of CSVWriter */
    public CSVWriter( Main main, boolean writeTitles, AbstractList data, AbstractList colTitles ) throws IOException {
        this.main = main;
        this.writeTitles = writeTitles;
        csvFile = main.fileChooser.getFile();
        rowList = data;
        titleList = colTitles;
        write();
    }

    /* Write the content of CSV Data Snapshot into a CSV file */
    private void write() throws IOException {
        PrintWriter pWriter = new PrintWriter( new FileWriter( csvFile ) );

        /* Write tiles to the first line of file if the user wants to */
        if( writeTitles ) {
            line = "";

            for( int i = 0; i < titleList.size() - 1; i++ )
                line += titleList.get( i ).trim() + ",";

            String lastTitle = titleList.get( titleList.size() - 1 );
            
            if( lastTitle != null )
                line += lastTitle.trim();
            else
                line += lastTitle;

            pWriter.println( line );
        }

        /* Write the content of the CSV Data Snapshot into a CSV file, column per column */
        for( int i = 0; i < rowList.size(); i++ ) {
            AbstractList<String> colList = rowList.get( i );

            String column;
            line = "";

            /* Iterate through each column */
            for( int j = 0; j < colList.size(); j++ ) {
                column = colList.get( j );
                
                if( column != null ) {
                    column = column.trim();

                    /* If the iterated item contains comma, enclose it with double quotes */
                    if( column.indexOf( "," ) != -1 )
                        column = "\"" + column + "\"";
                }
                else
                    column = "";

                line += column;

                /* If this is not the last column yet, append comma to indicate the start of the next column */
                if( j < colList.size() - 1 )
                    line += ",";

                /* Print the line to file if this is the last column of the row iterated */
                if( j == colList.size() - 1 )
                    pWriter.println( line );
            }
        }

        pWriter.close();
    }
    
    private boolean writeTitles;                            /* Whether titles should be written into file */
    private File csvFile;                                   /* The output CSV file */
    private Main main;                                      /* The Main class of CSV Loader */
    private String line;                                    /* Line String buffer */
    private AbstractList<AbstractList<String>> rowList;     /* CSV Data Snapshot or Data Comparison */
    private AbstractList<String> titleList;                 /* The titles of CSV Data Snapshot or Data Comparison */
}
