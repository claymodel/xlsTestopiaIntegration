/*
 * MergeStatements.java
 *
 * --- Last Update 6/21/2010 1:49 AM ---
 *
 * Update Notes 6/21/2010 1:49 AM by Adrian Wijasa:
 * This class now also works with PostgreSQL.
 *
 * Update Notes 6/18/2010 5:37 PM by Adrian Wijasa:
 * This class now also works with MySQL.
 *
 * Update Notes 6/4/2010 12:15 AM by Adrian Wijasa:
 * Now pases schema as a parameter into DependencyLevel.
 *
 * Update Notes 4/23/2010 6:08 PM by Adrian Wijasa:
 * Now uses main.getValidatedValue( String value, String dataType ) to make sure that the value variable can be
 * used in a DML SQL statement.
 *
 * Created on 1/31/2010 10:17 PM by Adrian Wijasa
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
 * Generate SQL statements to merge values into the table specified
 *
 * @author awijasa
 */
public class MergeStatements extends SQLStatements {
    
    /** Creates a new instance of MergeStatements */
    public MergeStatements( Main main, String schema, String table ) throws ClassNotFoundException, SQLException, TableNotFoundException {
        this.main = main;
        this.schema = schema;
        
        /*
            Get the Dependency Level of the table where the values are going to
            be merged
         */
        DependencyLevel depLevel = new DependencyLevel( main, schema, table );
        level = depLevel.level;
        
        this.table = table;
        
        insertColumns = new ArrayList<String>();
        updateColumns = new ArrayList<String>();
        dataTypes = new ArrayList<String>();
        values = new ArrayList<ArrayList<String>>();

        /* Get the Primary Key of table to identify the existing DB records that need to be updated */
        metaDataQuery = new MetaDataQuery( main );
        primaryKey = metaDataQuery.getPrimaryKeys( schema, table );
        metaDataQuery.closeConnection();
    }
    
    /* Add new column-values to be merged */
    public void addNew( String column, String dataType, ArrayList<String> value ) {
        insertColumns.add( column );
        updateColumns.add( column );
        dataTypes.add( dataType );
        values.add( value );
    }
    
    /* Write the Merge Statements into a File */
    public void writeStatements( PrintWriter pWriter ) {
        char cr = 13;
        char lf = 10;
        char ht = 9;
        
        String header;
        String footer = "";

        /* Remove the Primary Key columns from the list of columns that need to be updated if a record matches during a MERGE */
        updateColumns.removeAll( primaryKey );

        /* Create the MERGE Statement header */
        if( main.dbType.equals( "Oracle" ) ) {
            if( schema == null )
                 header = "merge into " + table + cr + lf +
                    "using( " + cr + lf +
                    ht + "select" + cr + lf;
            else
                 header = "merge into " + schema + "." + table + cr + lf +
                    "using( " + cr + lf +
                    ht + "select" + cr + lf;
        }
        else if( main.dbType.equals( "MySQL" ) ) {
            header = "insert into " + table + "(" + cr + lf;

            for( int i = 0; i < insertColumns.size(); i++ ) {
                if( i == 0 )
                    header += ht + insertColumns.get( i );
                else if( i == insertColumns.size() - 1 ) {
                    header += "" + cr + lf + ht + ", " + insertColumns.get( i ) + " )" + cr + lf +
                        "values( " + cr + lf;
                }
                else
                    header += "" + cr + lf + ht + ", " + insertColumns.get( i );
            }
        }
        else {
            pWriter.println( "create or replace function csvldr.f_merge_" + schema + "_" + table + "() returns boolean as $$" );
            pWriter.println( "declare" );
            pWriter.println( ht + "merge_cur cursor( " );

            for( int i = 0; i < primaryKey.size(); i++ ) {
                if( i == 0 ) {
                    pWriter.print( "" + ht + ht + primaryKey.get( i ) + "_in \"" + schema + "\".\"" + table + "\".\"" +
                        primaryKey.get( i ) + "\"%type" );
                }
                else {
                    pWriter.print( "" + cr + lf + ", " + ht + ht + primaryKey.get( i ) + "_in \"" + schema + "\".\"" +
                        table + "\".\"" + primaryKey.get( i ) + "\"%type" );
                }
            }

            pWriter.println( "" + cr + lf + ht + ") is" );
            pWriter.println( "" + ht + ht + "select" );
            pWriter.println( "" + ht + ht + ht + "1" );
            pWriter.println( "" + ht + ht + "from" );
            pWriter.println( "" + ht + ht + ht + "\"" + schema + "\".\"" + table + "\"" );
            pWriter.println( "" + ht + ht + "where" );

            for( int i = 0; i < primaryKey.size(); i++ ) {
                if( i == 0 )
                    pWriter.print( "" + ht + ht + ht + "\"" + primaryKey.get( i ) + "\" = " + primaryKey.get( i ) + "_in" );
                else {
                    pWriter.print( " and" + cr + lf + ht + ht + ht + "\"" + primaryKey.get( i ) + "\" = " +
                        primaryKey.get( i ) + "_in" );
                }
            }

            pWriter.println( "" + cr + lf + ht + ht + "for update;" + cr + lf );
            pWriter.println( ht + "v_dummy smallint;" );
            pWriter.println( ht + "v_is_cursor_open boolean;" );

            for( String pkColumn: primaryKey ) {
                pWriter.println( ht + "v_" + pkColumn + " \"" + schema + "\".\"" + table + "\".\"" + pkColumn + "\"%type;" );
            }

            pWriter.println( "begin" );
            pWriter.println( ht + "v_is_cursor_open := false;" + cr + lf );

            header = "" + ht + ht + "insert into \"" + schema + "\".\"" + table + "\"(" + cr + lf;

            for( int i = 0; i < insertColumns.size(); i++ ) {
                if( i == 0 )
                    header += "" + ht + ht + ht + "\"" + insertColumns.get( i ) + "\"";
                else if( i == insertColumns.size() - 1 ) {
                    header += "" + cr + lf + ht + ht + ht + ", \"" + insertColumns.get( i ) + "\" )" + cr + lf +
                        ht + ht + "values( " + cr + lf;
                }
                else
                    header += "" + cr + lf + ht + ht + ht + ", \"" + insertColumns.get( i ) + "\"";
            }
        }

        /* Create the MERGE Statement footer */
        if( main.dbType.equals( "Oracle" ) ) {
            footer = ht + "from" + cr + lf +
                ht + ht + "dual" + cr + lf +
                ") dual" + cr + lf +
                "on(" + cr + lf +
                ht + "dual." + primaryKey.get( 0 ) + " = " + table + "." + primaryKey.get( 0 );

            for( int i = 1; i < primaryKey.size(); i++ )
                footer += " and" + cr + lf +
                    ht + "dual." + primaryKey.get( i ) + " = " + table + "." + primaryKey.get( i );

            footer += "" + cr + lf +
                ")" + cr + lf +
                "when matched then update set" + cr + lf;

            footer += ht + table + "." + updateColumns.get( 0 ) + " = " + "dual." + updateColumns.get( 0 ) + cr + lf;

            for( int i = 1; i < updateColumns.size(); i++ )
                footer += ht + ", " + table + "." + updateColumns.get( i ) + " = " + "dual." + updateColumns.get( i ) + cr + lf;

            footer += "when not matched then insert(" + cr + lf;

            footer += ht + table + "." + insertColumns.get( 0 ) + cr + lf;

            for( int i = 1; i < insertColumns.size(); i++ )
                footer += ht + ", " + table + "." + insertColumns.get( i ) + cr + lf;

            footer += ")" + cr + lf +
                "values(" + cr + lf +
                ht + "dual." + insertColumns.get( 0 ) + cr + lf;

            for( int i = 1; i < insertColumns.size(); i++ )
                footer += ht + ", dual." + insertColumns.get( i ) + cr + lf;

            footer += ");";
        }
        else if( main.dbType.equals( "PostgreSQL" ) ) {
            footer = ht + "return true;" + cr + lf;
            footer += "exception" + cr + lf;
            footer += ht + "when others then" + cr + lf;
            footer += "" + ht + ht + "raise notice '% %";

            String errIdentifier = "', sqlstate, sqlerrm";

            for( String pkColumn: primaryKey ) {
                errIdentifier += ", v_" + pkColumn;
                footer += " %";
            }

            footer += errIdentifier + ";" + cr + lf + cr + lf;

            footer += "" + ht + ht + "if v_is_cursor_open then" + cr + lf;
            footer += "" + ht + ht + ht + "close merge_cur;" + cr + lf;
            footer += "" + ht + ht + "end if;" + cr + lf + cr + lf;
            footer += "" + ht + ht + "return false;" + cr + lf;
            footer += "end;" + cr + lf;
            footer += "$$ language plpgsql;" + cr + lf + cr + lf;
            footer += "select csvldr.f_merge_" + schema + "_" + table + "();" + cr + lf + cr + lf;
        }
        
        /* Iterate through each row of data to be merged */
        for( int i = 0; i < values.get( 0 ).size(); i++ ) {
            String column;
            String dataType;
            String stmt = "";
            String value;

            if( !main.dbType.equals( "PostgreSQL" ) )
                stmt += header;

            if( main.dbType.equals( "Oracle" ) ) {
                /*
                    Write the first column value into the merge statement.  trim()
                    method is always employed to every value in order to remove
                    unnecessary empty space (" ") at the beginning/end of the value
                 */
                value = values.get( 0 ).get( i );
                dataType = dataTypes.get( 0 );
                column = insertColumns.get( 0 );

                value = main.getValidatedValue( value, dataType );

                stmt += "" + ht + ht + value + " as " + column + cr + lf;

                /*
                    Write the values of other columns into the merge statement.
                    trim() method is always employed to every value in order to
                    remove unnecessary empty space (" ") at the beginning/end of the
                    value
                 */
                for( int j = 1; j < values.size(); j++ ) {
                    value = values.get( j ).get( i );
                    dataType = dataTypes.get( j );
                    column = insertColumns.get( j );

                    value = main.getValidatedValue( value, dataType);

                    stmt += "" + ht + ht + ", " + value + " as " + column + cr + lf;
                }

                stmt += footer + cr + lf + cr + lf; // End a merge statement
            }
            else if( main.dbType.equals( "MySQL" ) ) {
                /*
                    Write the first column value into the merge statement.  trim()
                    method is always employed to every value in order to remove
                    unnecessary empty space (" ") at the beginning/end of the value
                 */
                value = values.get( 0 ).get( i );
                dataType = dataTypes.get( 0 );

                value = main.getValidatedValue( value, dataType );

                stmt += "" + ht + value + cr + lf;

                /*
                    Write the values of other columns into the merge statement.
                    trim() method is always employed to every value in order to
                    remove unnecessary empty space (" ") at the beginning/end of the
                    value
                 */
                for( int j = 1; j < values.size(); j++ ) {
                    value = values.get( j ).get( i );
                    dataType = dataTypes.get( j );

                    value = main.getValidatedValue( value, dataType);

                    if( j == values.size() - 1 )
                        stmt += "" + ht + ", " + value + " )" + cr + lf;
                    else
                        stmt += "" + ht + ", " + value + cr + lf;
                }

                stmt += "on duplicate key" + cr + lf +
                    "update" + cr + lf;

                for( int j = 0; j < updateColumns.size(); j++ ) {
                    for( int k = 0; k < insertColumns.size(); k++ ) {
                        if( updateColumns.get( j ).equals( insertColumns.get( k ) ) ) {
                            value = values.get( k ).get( i );
                            dataType = dataTypes.get( k );

                            if( j == 0 )
                                stmt += ht + updateColumns.get( j ) + " = " + main.getValidatedValue( value, dataType );
                            else
                                stmt += "" + cr + lf + ht + ", " + updateColumns.get( j ) + " = " + main.getValidatedValue( value, dataType );

                            break;
                        }
                    }
                }

                stmt += ";" + cr + lf + cr + lf; // End a merge statement
            }
            else {
                for( int j = 0; j < primaryKey.size(); j++ ) {
                    for( int k = 0; k < insertColumns.size(); k++ ) {
                        if( primaryKey.get( j ).equals( insertColumns.get( k ) ) ) {
                            stmt += ht + "v_" + primaryKey.get( j ) + " := " +
                                main.getValidatedValue( values.get( k ).get( i ), dataTypes.get( k ) ) +
                                ";" + cr + lf;
                        }
                    }
                }

                stmt += "" + cr + lf + ht + "open merge_cur( ";

                for( int j = 0; j < primaryKey.size(); j++ ) {
                    if( j == 0 )
                        stmt += "v_" + primaryKey.get( j );
                    else
                        stmt += ", v_" + primaryKey.get( j );
                }

                stmt += " );" + cr + lf + cr + lf;

                stmt += ht + "v_is_cursor_open := true;" + cr + lf + cr + lf;

                stmt += ht + "fetch merge_cur into v_dummy;" + cr + lf + cr + lf;

                stmt += ht + "if found then" + cr + lf;
                stmt += "" + ht + ht + "update \"" + schema + "\".\"" + table + "\" set" + cr + lf;

                for( int j = 0; j < updateColumns.size(); j++ ) {
                    for( int k = 0; k < insertColumns.size(); k++ ) {
                        if( updateColumns.get( j ).equals( insertColumns.get( k ) ) ) {
                            dataType = dataTypes.get( k );
                            value = main.getValidatedValue( values.get( k ).get( i ), dataType );

                            if( j == 0 ) {
                                stmt += "" + ht + ht + ht + "\"" + updateColumns.get( j ) + "\" = " + value +
                                    cr + lf;
                            }
                            else {
                                stmt += "" + ht + ht + ht + ", \"" + updateColumns.get( j ) + "\" = " + value +
                                    cr + lf;
                            }

                            break;
                        }
                    }
                }

                stmt += "" + ht + ht + "where current of merge_cur;" + cr + lf;
                stmt += ht + "else" + cr + lf;

                stmt += header;

                for( int j = 0; j < values.size(); j++ ) {
                    dataType = dataTypes.get( j );
                    value = main.getValidatedValue( values.get( j ).get( i ), dataType );

                    if( j == 0 )
                        stmt += "" + ht + ht + ht + value + cr + lf;
                    else if(j == values.size() - 1)
                        stmt += "" + ht + ht + ht + ", " + value + " );" + cr + lf;
                    else
                        stmt += "" + ht + ht + ht + ", " + value + cr + lf;
                }

                stmt += ht + "end if;" + cr + lf + cr + lf;

                stmt += ht + "close merge_cur;" + cr + lf + cr + lf;

                stmt += ht + "v_is_cursor_open := false;" + cr + lf;
            }

            pWriter.println( stmt );
        }

        if( main.dbType.equals( "PostgreSQL" ) )
            pWriter.println( footer );
    }

    private ArrayList<String> primaryKey;           // The Primary Key of the table, where the Merge Statements are going to be run against.
    private Main main;
    private MetaDataQuery metaDataQuery;            // Provides Table Meta Data information.
    private String schema;
    private String table;
    private ArrayList<String> dataTypes;
    private ArrayList<String> insertColumns;        // The list of all columns that will be affected by the MERGE, especially the columns where data should be inserted if it does not match any existing record.
    private ArrayList<String> updateColumns;        // The list of columns that need to be updated during a MERGE if the new data matches an existing record.
    private ArrayList<ArrayList<String>> values;
}
