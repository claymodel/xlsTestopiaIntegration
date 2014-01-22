package jp.elias.xls.sql;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.elias.xls.form.Main;

public class InsertStatements extends SQLStatements {
    
    /** Creates a new instance of InsertStatements */
    public InsertStatements( Main main, String schema, String table ) throws ClassNotFoundException, SQLException, TableNotFoundException {
        this.main = main;
        this.schema = schema;
        
        /*
            Get the Dependency Level of the table where the values are going to
            be inserted
         */
        DependencyLevel depLevel = new DependencyLevel( main, schema, table );
        level = depLevel.level;
        
        this.table = table;
        
        columns = new ArrayList<String>();
        dataTypes = new ArrayList<String>();
        values = new ArrayList<ArrayList<String>>();
    }
    
    /* Add new column-values to be inserted */
    @Override
	public void addNew( String column, String dataType, ArrayList<String> value ) {
        columns.add( column );
        dataTypes.add( dataType );
        values.add( value );
    }
    
    /* Write Insert Statements into a File */
    @Override
	public void writeStatements( PrintWriter pWriter ) {
        char cr = 13;
        char lf = 10;
        char ht = 9;
        
        String header;
        
        if( main.dbType.equals( "MySQL" ) )
             header = "insert into " + table + "(" + cr + lf;
        else
             header = "insert into \"" + schema + "\".\"" + table + "\"(" + cr + lf;
        
        /* List the columns */
        if( main.dbType.equals( "MySQL" ) )
            header += ht + columns.get( 0 ) + cr + lf;
        else
            header += ht + "\"" + columns.get( 0 ) + "\"" + cr + lf;
        
        for( int i = 1; i < columns.size(); i++ ) {

            if( main.dbType.equals( "MySQL" ) )
                header += ht + ", " + columns.get( i ) + cr + lf;
            else
                header += ht + ", \"" + columns.get( i ) + "\"" + cr + lf;
        }
        
        header += ") values(" + cr + lf;
        
        /* Iterate through each row of data to be inserted */
        for( int i = 0; i < values.get( 0 ).size(); i++ ) {
            String dataType;
            String stmt = "";
            String value;
            
            stmt += header;
            
            /*
                Write the first column value into the insert statement.  trim()
                method is always employed to every value in order to remove
                unnecessary empty space (" ") at the beginning/end of the value
             */
            value = values.get( 0 ).get( i );
            dataType = dataTypes.get( 0 );
            
            value = main.getValidatedValue( value, dataType );
            
            stmt += ht + value + cr + lf;

            /*
                Write the values of other columns into the insert statement.
                trim() method is always employed to every value in order to
                remove unnecessary empty space (" ") at the beginning/end of the
                value
             */
            for( int j = 1; j < values.size(); j++ ) {
                value = values.get( j ).get( i );
                dataType = dataTypes.get( j );
                
                value = main.getValidatedValue( value, dataType );
                
                stmt += ht + ", " + value + cr + lf;
            }
            
            stmt += ");" + cr + lf + cr + lf; // End an insert statement

            pWriter.println( stmt );
        }
    }

    private Main main;
    private String schema;
    private String table;
    private ArrayList<String> columns;
    private ArrayList<String> dataTypes;
    private ArrayList<ArrayList<String>> values;
}
