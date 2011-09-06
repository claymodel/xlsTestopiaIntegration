/*
 * --- Last Update: 1/30/2010 4:51 PM ---
 *
 * Created on 1/30/2010 4:51 PM by Adrian Wijasa.
 * 
 * To change this template, choose Tools | Templates
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
import java.util.ArrayList;

/**
 * The abstract/template of Java classes that create SQL DML Statements.
 *
 * @author AWIJASA
 */
public abstract class SQLStatements {

    /* Adds new column-values to the SQL Statement */
    public abstract void addNew( String column, String dataType, ArrayList<String> value );

    /* Write the SQL Statements into a File */
    public abstract void writeStatements( PrintWriter pWriter );

    int level;
}
