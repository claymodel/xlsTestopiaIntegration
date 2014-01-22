package jp.elias.xls.file;

import java.io.File;
import javax.swing.filechooser.FileFilter;

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
