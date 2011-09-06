/*
 * CSVPanelImageReader.java
 *
 * --- Last Update: 3/30/2010 11:00 PM ---
 *
 * Update Notes 3/30/2010 10:48 PM by Adrian Wijasa:
 * Now also reads the "Do not load" configurations.
 *
 * Created on April 12, 2007, 2:38 PM
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

package pv.hasnat.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import pv.hasnat.form.Main;

/**
 *
 * @author awijasa
 */
public class CSVPanelImageReader {
    
    /** Creates a new instance of CSVPanelImageReader */
    public CSVPanelImageReader( Main main ) throws FileNotFoundException, IOException {
        this.main = main;
        includeArrayList = new ArrayList<Boolean>();
        sqlArrayList = new ArrayList<ArrayList<String>>();
        read();
    }

    /* Read configurations from a file */
    private void read() throws FileNotFoundException, IOException {
        BufferedReader bReader = new BufferedReader( new FileReader( main.fileChooser.fileChooser.getSelectedFile() ) );

        /* Iterate until the end of file (EOF) */
        while( true ) {
            String line = bReader.readLine();

            /* Stop reading or break from loop when reaching EOF */
            if( line == null )
                break;
            
            ArrayList<String> sqlColArrayList = new ArrayList<String>();

            /* Enter the configurations of a column into a config array */
            String config[] = line.split( "," );
                
            /* Pick up the "Do not load column" indicator */
            if( config[0].equals( "Load" ) )
                includeArrayList.add( false );
            else
                includeArrayList.add( true );

            /* Pick up the CSV Column - SQL Column Assignments */
            for( int i = 1; i < config.length; i++ )
                sqlColArrayList.add( config[i] );
            
            sqlArrayList.add( sqlColArrayList );
        }
        
        bReader.close();
    }
    
    private Main main;                          /* The main class of CSV Loader */
    public ArrayList<Boolean> includeArrayList;       /* Contains the states of 'Do not Load into Column' Checkboxes */
    public ArrayList<ArrayList<String>> sqlArrayList;    /* Contains the Column List Configurations */
}
