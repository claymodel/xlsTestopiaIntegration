
package pv.hasnat.file;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * FileNameExtensionFilter
 *
 * javax.swing.filechooser.FileNameExtensionFilter only exists in Java 6.  This class is used to make up for that functionality in Java 5.
 *
 * Created on 4/17/2010 12:52 PM by Adrian Wijasa
 *
 * @author matianyuan
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
public class FileNameExtensionFilter extends FileFilter{

    public FileNameExtensionFilter( String desc, String ext ) {
        this.desc = desc;
        this.ext = ext;
    }

    @Override
	public boolean accept( File file ) {
        String fileName = file.getName();
        if( fileName.substring( fileName.lastIndexOf( "." ) + 1 ).toLowerCase().equals( ext.toLowerCase() ) )
            return true;
        else if( file.isDirectory() )
            return true;
        else
            return false;
    }

    @Override
	public String getDescription() {
        return desc;
    }

    private String desc;
    private String ext;
}
