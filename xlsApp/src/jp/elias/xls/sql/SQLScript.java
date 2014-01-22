package jp.elias.xls.sql;

import java.util.LinkedList;

public class SQLScript {
    
    /** Creates a new instance of SQLScript */
    public SQLScript() {
        script = new LinkedList<SQLStatements>();
    }
    
    public void add( SQLStatements stmts ) {
        if( script.size() == 0 )
            script.addFirst( stmts );
        else if( stmts.level <= script.getFirst().level )
            script.addFirst( stmts );
        else if( stmts.level >= script.getLast().level )
            script.addLast( stmts );
        else {
            for( int i = 0; i < script.size(); i++ ) {
                if( stmts.level <= script.get( i ).level ) {
                    script.add( i, stmts );
                    break;
                }
            }
        }
    }
    
    public LinkedList<SQLStatements> script;
}
