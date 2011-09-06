/*
 * TableNotFoundException.java
 *
 * Created on April 2, 2007, 11:04 AM
 *
 * --- Last Update 6/16/2010 11:07 PM ---
 *
 * Update Notes 6/16/2010 11:07 PM by Adrian Wijasa:
 * Does not prepare error message based on the table inputted anymore. This class now just passes along the
 * error message it receives.
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
 * Thrown if a table is not found in CSVLDR_TBL_DEPS.
 *
 * @author awijasa
 */
public class TableNotFoundException extends java.lang.Exception {
    
    /**
     * Constructs an instance of <code>TableNotFoundException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public TableNotFoundException(String message) {
        super( message );
    }
}
