package jp.elias.xls.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JOptionPane;

import jp.elias.xls.form.Main;

public class Plan {
    
    /** Creates a new instance of Plan */
    public Plan( Main main ) {
        this.main = main;
        existingTables = new ArrayList<String>();
        planArrayList = new ArrayList<TablePlan>();
    }
    
    /* Add a TablePlan into the Plan */
    public void addPlan( String schema, String table, String column ) throws ClassNotFoundException, SQLException {
        existingTablesIndex = existingTables.indexOf( table ); // Has the table been added into Plan?
        
        if( existingTablesIndex > -1 )                                  // Yes
            planArrayList.get( existingTablesIndex ).addColumn( column );  // Just add column into the added TablePlan
        
        else {                                                  // No
            existingTables.add( table );                        // Add the table into the list of existing tables

            TablePlan tablePlan = new TablePlan( main, schema, table );   // Create a new TablePlan
            tablePlan.addColumn( column );
            planArrayList.add( tablePlan );
        }
    }

    public void buildDataComparison() throws ClassNotFoundException, DataComparisonException, SQLException {
        columnTitleList = new LinkedList<String>( main.csvPanel.currentImage.columnArrayList );
        dataComparisonList = main.get2DLinkedList( main.csvPanel.currentImage.csvDataArrayList );
        sqlList = main.get2DLinkedList( main.csvPanel.currentImage.sqlArrayList );

        try {
            ArrayList<String> sqlColArrayList;
            String query;
            LinkedList<String> dataComparisonRow;
            LinkedList<String> sqlColList;

            for( TablePlan tablePlan: planArrayList ) {
                dataComparisonRow = dataComparisonList.get( 0 );
                query = "select ";

                for( int i = 0; i < tablePlan.usedCols.size(); i++ ) {
                    if( i > 0 )
                        query += ", ";

                    if( !main.dbType.equals( "MySQL" ) )
                        query += "\"";
                    
                    query += tablePlan.usedCols.get( i );

                    if( !main.dbType.equals( "MySQL" ) )
                        query += "\"";
                }

                if( main.dbType.equals( "MySQL" ) ) {
                    query += " from " +
                            tablePlan.table +
                        " where ";
                }
                else {
                    query += " from " +
                            "\"" + tablePlan.schema + "\".\"" + tablePlan.table + "\"" +
                        " where ";
                }

                if( tablePlan.pKeys.size() == 0 ) {
                    JOptionPane.showMessageDialog( null
                        , "CREATE SQL MERGE will not work because " + tablePlan.table + " does not have any " +
                            "Primary Key."
                        , "WARNING: CREATE SQL MERGE option will not work."
                        , JOptionPane.INFORMATION_MESSAGE );
                    break;
                }

                for( int i = 0; i < tablePlan.pKeys.size(); i++ ) {
                    String pkColumn = tablePlan.pKeys.get( i );
                    String value;

                    if( !main.dbType.equals( "MySQL" ) )
                        query += "\"";

                    query += pkColumn;

                    if( !main.dbType.equals( "MySQL" ) )
                        query += "\"";

                    query += " = ";

                    for( int j = 0; j < main.csvPanel.currentImage.columnArrayList.size(); j++ ) {
                        sqlColArrayList = main.csvPanel.currentImage.sqlArrayList.get( j );

                        boolean isPKColumnFound = false;

                        for( int k = 0; k < sqlColArrayList.size(); k++ ) {

                            if( main.dbType.equals( "MySQL" ) ) {
                                if( sqlColArrayList.get( k ).equals( tablePlan.table + " > " + pkColumn ) ) {
                                    value = main.getValidatedValue( main.csvPanel.currentImage.csvDataArrayList.get( 0 ).get( j ), main.csvPanel.currentImage.dataTypeArrayList.get( j ) );
                                    query += value;
                                    tablePlan.pKeyIndexes[i] = j;
                                    isPKColumnFound = true;
                                    break;
                                }
                            }
                            else {
                                if( sqlColArrayList.get( k ).equals( tablePlan.schema + " > " + tablePlan.table + " > " + pkColumn ) ) {
                                    value = main.getValidatedValue( main.csvPanel.currentImage.csvDataArrayList.get( 0 ).get( j ), main.csvPanel.currentImage.dataTypeArrayList.get( j ) );
                                    query += value;
                                    tablePlan.pKeyIndexes[i] = j;
                                    isPKColumnFound = true;
                                    break;
                                }
                            }
                        }

                        if( isPKColumnFound )
                            break;
                       
                        if( j == main.csvPanel.currentImage.columnArrayList.size() - 1 )
                            throw new DataComparisonException( tablePlan.table, pkColumn );
                    }

                    if( i < tablePlan.pKeys.size() - 1 )
                        query += " and ";
                }

                /* Connect to Database: Oracle, MySQL, or PostgreSQL */
                if( main.dbType.equals( "Oracle" ) ) {
                    DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
                    conn = DriverManager.getConnection( "jdbc:oracle:thin:@" + main.host + ":" + main.port + ":" + main.sid, main.user, main.password );
                }
                else if( main.dbType.equals( "MySQL" ) ) {
                    Class.forName( "com.mysql.jdbc.Driver" );
                    conn = DriverManager.getConnection( "jdbc:mysql://" + main.host + ":" + main.port + "/" + main.sid, main.user, main.password );
                }
                else {
                    Class.forName( "org.postgresql.Driver" );
                    conn = DriverManager.getConnection( "jdbc:postgresql://" + main.host + ":" + main.port + "/" + main.sid, main.user, main.password );
                }

                stmt = conn.createStatement();

                rs = stmt.executeQuery( query );

                if( rs.next() ) {
                    for( int i = 0; i < tablePlan.usedCols.size(); i++ ) {
                        for( int j = 0; j < dataComparisonRow.size(); j++ ) {
                            if( tablePlan.pKeys.contains( tablePlan.usedCols.get( i ) ) )
                                break;

                            sqlColList = sqlList.get( j );

                            boolean isUsedColFound = false;

                            if( main.dbType.equals( "MySQL" ) ) {
                                for( int k = 0; k < sqlColList.size(); k++ ) {
                                    if( sqlColList.get( k ).equals( tablePlan.table + " > " + tablePlan.usedCols.get( i ) ) ) {
                                        tablePlan.usedColIndexes[i] = j;
                                        columnTitleList.add( j, "Old " + tablePlan.usedCols.get( i ) );
                                        dataComparisonRow.add( j, rs.getString( i + 1 ) );
                                        sqlList.add( j, new LinkedList<String>() );
                                        j+= 1;
                                        isUsedColFound = true;
                                        break;
                                    }
                                }
                            }
                            else {
                                for( int k = 0; k < sqlColList.size(); k++ ) {
                                    if( sqlColList.get( k ).equals( tablePlan.schema + " > " + tablePlan.table + " > " + tablePlan.usedCols.get( i ) ) ) {
                                        tablePlan.usedColIndexes[i] = j;
                                        columnTitleList.add( j, "Old " + tablePlan.usedCols.get( i ) );
                                        dataComparisonRow.add( j, rs.getString( i + 1 ) );
                                        sqlList.add( j, new LinkedList<String>() );
                                        j+= 1;
                                        isUsedColFound = true;
                                        break;
                                    }
                                }
                            }

                            if( isUsedColFound )
                                break;
                        }
                    }
                }
                else {
                    for( int i = 0; i < tablePlan.usedCols.size(); i++ ) {
                        for( int j = 0; j < dataComparisonRow.size(); j++ ) {
                            if( tablePlan.pKeys.contains( tablePlan.usedCols.get( i ) ) )
                                break;

                            sqlColList = sqlList.get( j );

                            boolean isUsedColFound = false;

                            if( main.dbType.equals( "MySQL" ) ) {
                                for( int k = 0; k < sqlColList.size(); k++ ) {
                                    if( sqlColList.get( k ).equals( tablePlan.table + " > " + tablePlan.usedCols.get( i ) ) ) {
                                        tablePlan.usedColIndexes[i] = j;
                                        columnTitleList.add( j, "Old " + tablePlan.usedCols.get( i ) );
                                        dataComparisonRow.add( j, "" );
                                        sqlList.add( j, new LinkedList<String>() );
                                        j+= 1;
                                        isUsedColFound = true;
                                        break;
                                    }
                                }

                                if( isUsedColFound )
                                    break;
                            }
                            else {
                                for( int k = 0; k < sqlColList.size(); k++ ) {
                                    if( sqlColList.get( k ).equals( tablePlan.schema + " > " + tablePlan.table + " > " + tablePlan.usedCols.get( i ) ) ) {
                                        tablePlan.usedColIndexes[i] = j;
                                        columnTitleList.add( j, "Old " + tablePlan.usedCols.get( i ) );
                                        dataComparisonRow.add( j, "" );
                                        sqlList.add( j, new LinkedList<String>() );
                                        j+= 1;
                                        isUsedColFound = true;
                                        break;
                                    }
                                }

                                if( isUsedColFound )
                                    break;
                            }
                        }
                    }
                }
            }

            System.out.println( "The Data Comparison from Line 1 is written." );

            for( int i = 1; i < dataComparisonList.size(); i++ ) {
                for( TablePlan tablePlan: planArrayList ) {
                    dataComparisonRow = dataComparisonList.get( i );
                    query = "select ";

                    for( int j = 0; j < tablePlan.usedCols.size(); j++ ) {
                        if( j > 0 )
                            query += ", ";

                        if( !main.dbType.equals( "MySQL" ) )
                            query += "\"";

                        query += tablePlan.usedCols.get( j );

                        if( !main.dbType.equals( "MySQL" ) )
                            query += "\"";
                    }

                    if( main.dbType.equals( "MySQL" ) ) {
                        query += " from " +
                                tablePlan.table +
                            " where ";
                    }
                    else {
                        query += " from " +
                                "\"" + tablePlan.schema + "\".\"" + tablePlan.table + "\"" +
                            " where ";
                    }

                    if( tablePlan.pKeys.size() == 0 ) {
                        break;
                    }

                    for( int j = 0; j < tablePlan.pKeys.size(); j++ ) {
                        String pkColumn = tablePlan.pKeys.get( j );
                        String value;

                        if( !main.dbType.equals( "MySQL" ) )
                            query += "\"";

                        query += pkColumn;

                        if( !main.dbType.equals( "MySQL" ) )
                            query += "\"";

                        query += " = ";

                        value = main.getValidatedValue( main.csvPanel.currentImage.csvDataArrayList.get( i ).get( tablePlan.pKeyIndexes[j] ), main.csvPanel.currentImage.dataTypeArrayList.get( tablePlan.pKeyIndexes[j] ) );
                        query += value;

                        if( j < tablePlan.pKeys.size() - 1 )
                            query += " and ";
                    }

                    rs = stmt.executeQuery( query );

                    if( rs.next() ) {
                        for( int j = 0; j < tablePlan.usedCols.size(); j++ ) {
                            if( !tablePlan.pKeys.contains( tablePlan.usedCols.get( j ) ) )
                                dataComparisonRow.add( tablePlan.usedColIndexes[j], rs.getString( j + 1 ) );
                        }
                    }
                    else {
                        for( int j = 0; j < tablePlan.usedCols.size(); j++ ) {
                            if( !tablePlan.pKeys.contains( tablePlan.usedCols.get( j ) ) )
                                dataComparisonRow.add( tablePlan.usedColIndexes[j], "" );
                        }
                    }
                }

                System.out.println( "The Data Comparison from Line " + ( i + 1 ) + " is written." );
            }

            closeConnection();
        }
        catch( DataComparisonException de ) {
            closeConnection();
            throw new DataComparisonException( de.getMessage() );
        }
        catch( SQLException se ) {
            closeConnection();
            throw new SQLException( se.getMessage() );
        }
    }

    private void closeConnection() {
        try {
            if( rs != null )
                rs.close();

            if( stmt != null )
                stmt.close();

            if( conn != null )
                conn.close();
        }
        catch( SQLException e ) {}
    }

    /* Get the Plan in String format */
    public String getPlan() {
        char cr = 13;
        char lf = 10;
        String plan = "CSV Loader will insert/merge data into the columns written in upper case" + cr + lf + cr + lf;
        
        for( int i = 0; i < planArrayList.size(); i++ )
            plan += planArrayList.get( i ).getPlan() + cr + lf;
        
        return plan;
    }

    private ArrayList<String> existingTables;
    private ArrayList<TablePlan> planArrayList;
    private Connection conn;
    private int existingTablesIndex;
    public LinkedList<String> columnTitleList;
    public LinkedList<LinkedList<String>> dataComparisonList;
    private LinkedList<LinkedList<String>> sqlList;
    private Main main;
    private ResultSet rs;
    private Statement stmt;
}
