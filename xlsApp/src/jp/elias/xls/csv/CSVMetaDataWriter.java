package jp.elias.xls.csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import jp.elias.xls.form.Main;

public class CSVMetaDataWriter {
    
    /** Creates a new instance of CSVMetaDataWriter */
    public CSVMetaDataWriter( Main main ) throws IOException {
        this.main = main;
        metaDataFile = main.fileChooser.getFile();
        titleArrayList = main.csvPanel.currentImage.columnArrayList;
        dataTypeArrayList = main.csvPanel.currentImage.dataTypeArrayList;
        write();
    }

    /* Write the Titles and Data Types & Length into a file */
    private void write() throws IOException {
        PrintWriter pWriter = new PrintWriter( new FileWriter( metaDataFile ) );

        for( int i = 0; i < titleArrayList.size(); i++ ) {
            pWriter.println( "\"" + titleArrayList.get( i ) + "\",\"" + dataTypeArrayList.get( i ) + "\"" );
        }

        pWriter.close();
    }
    
    private File metaDataFile;
    private Main main;
    private ArrayList<String> titleArrayList;
    private ArrayList<String> dataTypeArrayList;
}
