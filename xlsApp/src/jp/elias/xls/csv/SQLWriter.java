package jp.elias.xls.csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.LinkedList;

import jp.elias.xls.form.Main;
import jp.elias.xls.sql.SQLScript;
import jp.elias.xls.sql.SQLStatements;
import jp.elias.xls.sql.TableNotFoundException;

public class SQLWriter {
    
    /** Creates a new instance of SQLWriter */
    public SQLWriter( Main main, int task ) throws ClassNotFoundException, ColumnNotConfiguredException, IOException, SQLException, TableNotFoundException {
        this.main = main;
        this.task = task;
        sqlFile = main.fileChooser.getFile();
        write();
    }
    
    private void write() throws ClassNotFoundException, ColumnNotConfiguredException, IOException, SQLException, TableNotFoundException {
        pWriter = new PrintWriter( new FileWriter( sqlFile ) );
        char cr = 13;
        char lf = 10;

        SQLScript sqlScript;

        if( task == INSERT )
            sqlScript = main.csvPanel.currentImage.getInsertScript();
        else
            sqlScript = main.csvPanel.currentImage.getMergeScript();

        LinkedList<SQLStatements> script = sqlScript.script;

        if( main.dbType.equals( "MySQL" ) )
            pWriter.println( "set sql_mode = 'STRICT_ALL_TABLES';" + cr + lf );
        
        if( main.dbType.equals( "Oracle" ) )
            pWriter.println( "whenever sqlerror exit sql.sqlcode rollback" + cr + lf );
        else
            pWriter.println( "start transaction;" + cr + lf );

        System.out.println( "There are SQL statements to be written for " + script.size() + " tables." );
        
        for( int i = 0; i < script.size(); i++ ) {
            script.get( i ).writeStatements( pWriter );
            System.out.println( i + 1 + " out of " + script.size() + " tables have had their SQL statements written." );
        }

        pWriter.close();
    }
    
    private File sqlFile;
    public static final int INSERT = 1; // The task value that is used to make SQLWriter produce an Insert Script.
    public static final int MERGE = 2;  // The task value that is used to make SQLWriter produce a Merge Script.
    private int task;
    private Main main;
    private PrintWriter pWriter;
}
