package jp.elias.xls.csv;

public class SQLListReader {
    
    /** Creates a new instance of SQLListReader */
    public SQLListReader( String sqlListElement ) {
        this.sqlListElement = sqlListElement;
        read();
    }
    
    String getColumn() {
        return splittedElement[splittedElement.length - 1];
    }
    
    String getSchema() {
        if( splittedElement.length == 3 )
            return splittedElement[0];
        else
            return null;
    }
    
    String getTable() {
        return splittedElement[splittedElement.length - 2];
    }
    
    private void read() {
        splittedElement = sqlListElement.split( " > " );
    }
    
    String[] splittedElement;
    private String sqlListElement;
}
