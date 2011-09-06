/*
 * DataComparisonException.java
 *
 * Created on April 23, 2010, 6:33 PM
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

/**
 * Thrown if one of the Primary Keys of a table can not be found in CSV Data Snapshot when performing a Data
 * Comparison through Plan.
 *
 * @author awijasa
 */
public class DataComparisonException extends Exception {

    public DataComparisonException( String message ) {
        super( message );
    }

    public DataComparisonException( String table, String pkColumn ) {
        super( "Data Comparison could not be performed.  The Primary Key column: " + pkColumn + " from Table: " +
            table + " is missing from CSV Data Snapshot" );
    }
}
