/*
 * StaticTableModel.java
 *
 * Created on March 15, 2007, 12:44 PM
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

package pv.hasnat.table;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 * Non-Editable JTable
 *
 * @author awijasa
 */
public class StaticTableModel extends DefaultTableModel {
    
    /** Creates a new instance of StaticTableModel */
    public StaticTableModel( Vector data, Vector columnNames ) {
        super( data, columnNames );
    }
    
    @Override
	public boolean isCellEditable( int row, int column ) {
        return false;
    }
}
