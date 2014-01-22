package jp.elias.xls.sql;

public class DataComparisonException extends Exception {

    public DataComparisonException( String message ) {
        super( message );
    }

    public DataComparisonException( String table, String pkColumn ) {
        super( "Data Comparison could not be performed.  The Primary Key column: " + pkColumn + " from Table: " +
            table + " is missing from CSV Data Snapshot" );
    }
}
