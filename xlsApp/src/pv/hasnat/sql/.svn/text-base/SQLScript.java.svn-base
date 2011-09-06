/*
 * SQLScript.java
 *
 * --- Last Update: 6/4/2010 12:43 AM ---
 *
 * Update Notes 6/4/2010 12:43 AM by Adrian Wijasa:
 * Now will work with more than three level of tables.
 *
 * Update Notes 1/30/2010 5:12 PM by Adrian Wijasa:
 * Renamed InsertStatement to SQLStatements.
 * Renamed insStmt to stmts.
 * Renamed from InsertScript to SQLScript
 *
 * Created on April 2, 2007, 12:49 PM
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

import java.util.LinkedList;

/**
 * Compile an SQLScript containing one or more InsertStatements
 *
 * @author awijasa
 */
public class SQLScript {
    
    /** Creates a new instance of SQLScript */
    public SQLScript() {
        script = new LinkedList<SQLStatements>();
    }
    
    public void add( SQLStatements stmts ) {
        if( script.size() == 0 )
            script.addFirst( stmts );
        else if( stmts.level <= script.getFirst().level )
            script.addFirst( stmts );
        else if( stmts.level >= script.getLast().level )
            script.addLast( stmts );
        else {
            for( int i = 0; i < script.size(); i++ ) {
                if( stmts.level <= script.get( i ).level ) {
                    script.add( i, stmts );
                    break;
                }
            }
        }
    }
    
    public LinkedList<SQLStatements> script;
}
