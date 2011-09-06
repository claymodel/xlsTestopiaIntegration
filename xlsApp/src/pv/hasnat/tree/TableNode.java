/*
 * TableNode.java
 *
 * Created on March 13, 2007, 9:22 AM
 *
 * --- Last Update: 6/20/2010 4:05 PM ---
 *
 * Update Notes 6/20/2010 4:05 PM by Adrian Wijasa:
 * Changed the wording: Oracle to Database. CSV Loader now works with both MySQL and PostgreSQL.
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

package pv.hasnat.tree;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * A JTree Node for a Database Table
 *
 * @author awijasa
 */
public class TableNode extends DefaultMutableTreeNode {
    
    /** Creates a new instance of TableNode */
    public TableNode( Object userObject ) {
        super( userObject );
    }
    
}
