package jp.elias.xls.sql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import jp.elias.xls.form.Main;

public class TablePlan {
    
    /** Creates a new instance of TablePlan */
    public TablePlan( Main main, String schema, String table ) throws ClassNotFoundException, SQLException {
        this.main = main;
        this.schema = schema;
        this.table = table;
        usedCols = new ArrayList<String>();
        initMetaData();
    }
    
    public void addColumn( String column ) {
        usedCols.add( column );
    }
    
    private void initMetaData() throws ClassNotFoundException, SQLException {
        try {
            metaDataQ = new MetaDataQuery( main );

            pKeys = new LinkedList<String>( metaDataQ.getPrimaryKeys( schema, table ) );
            notNulls = new LinkedList<String>( metaDataQ.getNotNullColumns( schema, table ) );
            notNulls.removeAll( pKeys );
            nulls = new LinkedList<String>( metaDataQ.getColumns( schema, table ) );
            nulls.removeAll( pKeys );
            nulls.removeAll( notNulls );

            metaDataQ.closeConnection();

            pKeyIndexes = new int[pKeys.size()];
        }
        catch( SQLException e ) {
            try {
                metaDataQ.closeConnection();
            }
            catch( SQLException se ) {}
            
            throw new SQLException( e.getMessage() );
        }
    }
    
    String getPlan() {
        char cr = 13;
        char lf = 10;
        char ht = 9;
        
        String col;
        String plan;

        usedColIndexes = new int[usedCols.size()];
        
        if( schema == null )
            plan = table + cr + lf;
        else
            plan = schema + "." + table + cr + lf;

        plan += "Primary Keys:" + cr + lf;
        
        for( int i = 0; i < pKeys.size(); i++ ) {
            col = pKeys.get( i );
            
            if( usedCols.contains( col ) )
                plan += ht + col.toUpperCase() + cr + lf;
            else
                plan += ht + col.toLowerCase() + cr + lf;
        }
        
        plan += "Not Null:" + cr + lf;
        
        for( int i = 0; i < notNulls.size(); i++ ) {
            col = notNulls.get( i );
            
            if( usedCols.contains( col ) )
                plan += ht + col.toUpperCase() + cr + lf;
            else
                plan += ht + col.toLowerCase() + cr + lf;
        }
        
        plan += "Nullable:" + cr + lf;
        
        for( int i = 0; i < nulls.size(); i++ ) {
            col = nulls.get( i );
            
            if( usedCols.contains( col ) )
                plan += ht + col.toUpperCase() + cr + lf;
            else
                plan += ht + col.toLowerCase() + cr + lf;
        }
        
        return plan;
    }
    
    ArrayList<String> usedCols;
    int[] pKeyIndexes;                      /* Contains the column locations of Primary Keys in dataComparisonRow of Plan class */
    int[] usedColIndexes;                   /* Contains the column locations of Queried Columns in dataComparisonRow of Plan class */
    private LinkedList<String> notNulls;
    private LinkedList<String> nulls;
    LinkedList<String> pKeys;
    private Main main;
    private MetaDataQuery metaDataQ;
    String schema;
    String table;
}
