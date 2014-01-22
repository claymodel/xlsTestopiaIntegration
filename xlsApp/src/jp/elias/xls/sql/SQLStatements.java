package jp.elias.xls.sql;

import java.io.PrintWriter;
import java.util.ArrayList;

public abstract class SQLStatements {

    /* Adds new column-values to the SQL Statement */
    public abstract void addNew( String column, String dataType, ArrayList<String> value );

    /* Write the SQL Statements into a File */
    public abstract void writeStatements( PrintWriter pWriter );

    int level;
}
