/*
 * InsertStatements.java
 *
 * --- Last Update 6/16/2010 11:34 PM ---
 *
 * Update Notes 6/16/2010 11:34 PM by Adrian Wijasa:
 * Differenciated Insert Statements created for MySQL from Oracle/PostgreSQL.
 *
 * Update Notes 6/15/2010 10:45 PM by Adrian Wijasa:
 * Now throws ClassNotFoundException.
 *
 * Update Notes 6/4/2010 12:14 AM by Adrian Wijasa:
 * Now passes schema as a parameter to DependencyLevel.
 *
 * Update Notes 5/18/2010 10:10 AM by Adrian Wijasa:
 * Added " around any usage of schema or table, so that they work with MySQL and PostgreSQL.
 *
 * Update Notes 4/23/2010 6:03 PM by Adrian Wijasa:
 * Now uses main.getValidatedValue( String value, String dataType ) to make sure that the value variable can be
 * used in a DML SQL statement.
 *
 * Update Notes 1/30/2010 4:37 PM by Adrian Wijasa:
 * If the column data that is going to be inserted is in DATE format, it needs to be wrapped by to_date() function in the Insert Statement produced.
 * Renamed from InsertStatement to InsertStatements.
 * Renamed getStatement() to getStatements().
 * Now extends SQLStatements.
 *
 * Created on April 2, 2007, 11:10 AM
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

package pv.hasnat.sql;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import pv.hasnat.form.Main;

/**
 * Generate SQL statements to insert values into the table specified
 *
 * @author awijasa
 */
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
