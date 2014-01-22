package jp.elias.xls.table;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;

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
