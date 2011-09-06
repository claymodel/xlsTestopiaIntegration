/*
 * TablePlan.java
 *
 * Created on April 13, 2007, 10:35 AM
 *
 * --- Last Update: 6/18/2010 10:25 PM ---
 *
 * Update Notes 6/18/2010 10:25 PM by Adrian Wijasa:
 * schema variable can now be accessed by Plan class.
 *
 * Update Notes 4/23/2010 7:09 PM by Adrian Wijasa:
 * Added usedColIndexes array to determine the location of Queried columns in dataComparisonRow of Plan class.
 * Added pKeyIndexes array to determine the location of Primary Key columns in dataComparisonRow of Plan class.
 *
 * Update Notes 4/22/2010 11:14 PM by Adrian Wijasa:
 * Changed the security of usedCols, pKeys, and table from private to default, so that it's accessible to Plan
 * class.
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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import pv.hasnat.form.Main;

/**
 *
 * @author awijasa
 */
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
