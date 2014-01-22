package jp.elias.xls.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import jp.elias.xls.form.Main;

public class PlanWriter {
    
    /** Creates a new instance of PlanWriter */
    public PlanWriter( Main main ) throws IOException, SQLException {
        this.main = main;
        write();
    }
    
    private void write() throws IOException, SQLException {
        PrintWriter pWriter = new PrintWriter( new FileWriter( main.fileChooser.getFile() ) );
        pWriter.println( main.csvPanel.currentImage.plan.getPlan() );
        pWriter.close();
    }
    
    private Main main;
}
