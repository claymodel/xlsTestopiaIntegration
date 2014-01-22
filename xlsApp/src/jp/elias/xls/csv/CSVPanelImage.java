package jp.elias.xls.csv;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jp.elias.xls.form.Main;
import jp.elias.xls.sql.InsertStatements;
import jp.elias.xls.sql.MergeStatements;
import jp.elias.xls.sql.Plan;
import jp.elias.xls.sql.SQLScript;
import jp.elias.xls.sql.TableNotFoundException;

public class CSVPanelImage {
    
    /** Creates a new instance of CSVPanelImage */
    public CSVPanelImage( Main main, ArrayList<ArrayList<String>> csvDataArrayList, ArrayList<String> csvTitleArrayList ) {
        this.main = main;
        
        /* Initialize the ArrayLists with default values */
        this.csvDataArrayList = new ArrayList<ArrayList<String>>( csvDataArrayList );
        columnArrayList = new ArrayList<String>( csvTitleArrayList );
        dataTypeArrayList = new ArrayList<String>();
        nullArrayList = new ArrayList<Boolean>();
        includeArrayList = new ArrayList<Boolean>();
        sqlArrayList = new ArrayList<ArrayList<String>>();
        
        for( int i = 0; i < columnArrayList.size(); i++ ) {
            dataTypeArrayList.add( null );
            nullArrayList.add( false );
            includeArrayList.add( false );
            sqlArrayList.add( new ArrayList<String>() );
        }

        initDBDataTypesAndFormats();
        initArrayLists(); // Initialize Null/Not Null and Data Types based on the data inputted
    }
    
    /* Adds a new column */
    public void add( ArrayList<String> toBeAdded ) {

        /* Assign the position of the new column that is appended to the right of CSV Data Snapshot */
        int newPos = columnArrayList.size();

        /* Add the rows pertaining to the new column that is being added */
        for( int i = 0; i < csvDataArrayList.size(); i++ )
            csvDataArrayList.get( i ).add( toBeAdded.get( i ) );

        /* Populate the Column Config of the new column with default values */
        columnArrayList.add( "New Column" );
        dataTypeArrayList.add( null );
        nullArrayList.add( false );
        includeArrayList.add( false );
        sqlArrayList.add( new ArrayList<String>() );
        
        int maxLength = 0;              // The maximum VARCHAR2 length found in a column
        int maxPrecision = 0;           // The maximum NUMBER precision found in a column
        int maxScale = 0;               // The maximum NUMBER scale found in a column
        boolean isNotNumber = false;    // Indicate if the program should still check if the column is of NUMBER data type
        boolean isNotDate = false;      // Indicate if the program should still check if the column is of DATE data type
        String dateType;                // Indicates the Database date format: DD-MON-YY or MM/DD/YY

        /* Try to determine the value of the new column's NULL/NOT NULL and Data Type */
        for( int i = 0; i < toBeAdded.size(); i++ ) {
            String column = toBeAdded.get( i );
            
            /* If one of the row contains empty string, the column's NULL/NOT NULL property is NULL */
            if( column == null )
                nullArrayList.set( newPos, true );
            else if( column.equals( "" ) )
                nullArrayList.set( newPos, true );
            else {

                /* If one of the row can not be parsed to Double, the column is not NUMBER */
                try {
                    Double.parseDouble( column );
                }
                catch( NumberFormatException e ) {
                    isNotNumber = true;
                }

                /* Get the precision and scale of a NUMBER */
                if( !isNotNumber ) {
                    int length;
                    int periodPos = column.indexOf( "." );
                    int precision;
                    int scale = 0;

                    if( periodPos == -1 ) {
                        precision = column.length();
                    }
                    else {
                        precision = column.length() - 1;
                        scale = column.substring( periodPos + 1 ).length();
                    }

                    length = column.length();

                    if( length > maxLength )
                        maxLength = length;

                    if( precision > maxPrecision )
                        maxPrecision = precision;

                    if( scale > maxScale )
                        maxScale = scale;

                    if( maxPrecision < 39 && maxScale < 128 )
                        dataTypeArrayList.set( newPos, numberDataType + "( " + maxPrecision + ", " + maxScale + " )" );
                    else
                        isNotNumber = true;

                    isNotDate = true;
                }

                /* Get the Date Format of a DATE, set the data type to VARCHAR2 if it's not a DATE */
                else if( !isNotDate ) {
                    isNotDate = true;
                    dateFormats = new String[] { "dd-MMM-yy", "MM/dd/yy" };

                    for( int k = 0; k < dateFormats.length; k++ ) {
                        dateFormat = new SimpleDateFormat( dateFormats[k] );

                        try {
                            date = dateFormat.parse( column );

                            if( dateFormats[k].equals( "dd-MMM-yy" ) ) {
                                if( main.dbType.equals( "PostgreSQL" ) ) {
                                    if( column.lastIndexOf( "-" ) == ( column.length() - 5 ) )
                                        dateType = "DD-Mon-YYYY";
                                    else
                                        dateType = "DD-Mon-YY";
                                }
                                else
                                    dateType = dmyFormat;
                            }
                            else {
                                if( main.dbType.equals( "PostgreSQL" ) ) {
                                    if( column.lastIndexOf( "/" ) == ( column.length() - 5 ) )
                                        dateType = "MM/DD/YYYY";
                                    else
                                        dateType = "MM/DD/YY";
                                }
                                else
                                    dateType = mdyFormat;
                            }

                            dataTypeArrayList.set( newPos, "DATE " + "(" + dateType + ")" );
                            isNotDate = false;
                            break;
                        }
                        catch( ParseException pe ) {}
                    }
                }

                /* Get the maximum length of a VARCHAR2 */
                if( isNotNumber && isNotDate ) {
                    int length = column.length();

                    if( length > maxLength )
                        maxLength = length;

                    if( maxLength < 32768 ) {
                        dataTypeArrayList.set( newPos, charDataType + "( " + maxLength + " )" );
                    }
                    else {
                        dataTypeArrayList.set( newPos, "INVALID: " + charDataType + " Length > 32767" );
                        includeArrayList.set( newPos, true );
                    }
                }
            }
        }
    }

    /* Retrieve the rows within the columnIndex provided */
    public ArrayList<String> getColDataArrayList( int columnIndex ) {
        ArrayList<String> colDataArrayList = new ArrayList<String>();
        
        for( int i = 0; i < csvDataArrayList.size(); i++ )
            colDataArrayList.add( csvDataArrayList.get( i ).get( columnIndex ) );
        
        return colDataArrayList;
    }
    
    /* Convert the data in CSVPanelImage into a Database Insert Script */
    public SQLScript getInsertScript() throws ClassNotFoundException, ColumnNotConfiguredException, SQLException, TableNotFoundException {
        ArrayList<InsertStatements> insertArrayList = new ArrayList<InsertStatements>();
        ArrayList<String> existingTables = new ArrayList<String>();
        InsertStatements insertStatements;

        /* Iterate through each column in the CSV Data Snapshot */
        for( int i = 0; i < columnArrayList.size(); i++ ) {

            /* If the 'Do not Load this Column' checkbox is unchecked */
            if( !includeArrayList.get( i ) ) {

                /* This is to indicate if the Column List in Column Config is populated. */
                sqlColAssignQuantity = sqlArrayList.get( i ).size();

                ArrayList<String> csvColArrayList;
                String schemaName;
                String tableName;
                String colName;

                /* If Column List is not populated, throw an Exception. */
                if( sqlColAssignQuantity == 0 )
                    throw new ColumnNotConfiguredException( main, columnArrayList.get( i ) );

                /*
                    If Column List is populated, use it to override the information entered in the Schema,
                    Table, and Column Text Fields.
                 */
                else {
                    sqlColArrayList = sqlArrayList.get( i );
                    
                    for( int j = 0; j < sqlColAssignQuantity; j++ ) {
                        listReader = new SQLListReader( sqlColArrayList.get( j ) );
                        schemaName = listReader.getSchema();
                        tableName = listReader.getTable();
                        colName = listReader.getColumn();
                        
                        csvColArrayList = new ArrayList<String>();

                        /*
                            Generate new InsertStatements for the currently iterated table if it hasn't been used to
                            create InsertStatements yet.
                        */
                        if( !existingTables.contains( tableName ) ) {
                            insertStatements = new InsertStatements( main, schemaName, tableName );

                            for( int k = 0; k < csvDataArrayList.size(); k++ )
                                csvColArrayList.add( csvDataArrayList.get( k ).get( i ) );

                            insertStatements.addNew( colName, dataTypeArrayList.get( i ), csvColArrayList );
                            insertArrayList.add( insertStatements );

                            /* Added the iterated table to the list of tables used to create InsertStatements */
                            existingTables.add( tableName );
                        }

                        /* Add a new column to the existing InsertStatements for the iterated table */
                        else {
                            int tableIndex = existingTables.indexOf( tableName );

                            for( int k = 0; k < csvDataArrayList.size(); k++ )
                                csvColArrayList.add( csvDataArrayList.get( k ).get( i ) );

                            insertStatements = insertArrayList.get( tableIndex );
                            insertStatements.addNew( colName, dataTypeArrayList.get( i ), csvColArrayList );
                        }
                    }
                }
            }
        }
        
        SQLScript insertScript = new SQLScript();
        
        for( int i = 0; i < insertArrayList.size(); i++ )
            insertScript.add( insertArrayList.get( i ) );
        
        return insertScript;
    }

    /* Convert the data in CSVPanelImage into a Database Merge Script */
    public SQLScript getMergeScript() throws ClassNotFoundException, ColumnNotConfiguredException, SQLException, TableNotFoundException {
        ArrayList<MergeStatements> mergeArrayList = new ArrayList<MergeStatements>();
        ArrayList<String> existingTables = new ArrayList<String>();
        MergeStatements mergeStatements;

        /* Iterate through each column in the CSV Data Snapshot */
        for( int i = 0; i < columnArrayList.size(); i++ ) {

            /* If the 'Do not Load this Column' checkbox is unchecked */
            if( !includeArrayList.get( i ) ) {

                /* This is to indicate if the Column List in Column Config is populated. */
                sqlColAssignQuantity = sqlArrayList.get( i ).size();

                ArrayList<String> csvColArrayList;
                String schemaName;
                String tableName;
                String colName;

                /* If Column List is not populated, throw an Exception. */
                if( sqlColAssignQuantity == 0 )
                    throw new ColumnNotConfiguredException( main, columnArrayList.get( i ) );

                /*
                    If Column List is populated, use it to override the information entered in the Schema,
                    Table, and Column Text Fields.
                 */
                else {
                    sqlColArrayList = sqlArrayList.get( i );

                    for( int j = 0; j < sqlColAssignQuantity; j++ ) {
                        listReader = new SQLListReader( sqlColArrayList.get( j ) );
                        schemaName = listReader.getSchema();
                        tableName = listReader.getTable();
                        colName = listReader.getColumn();

                        csvColArrayList = new ArrayList<String>();

                        /*
                            Generate new MergeStatements for the currently iterated table if it hasn't been used to
                            create MergeStatements yet.
                        */
                        if( !existingTables.contains( tableName ) ) {
                            mergeStatements = new MergeStatements( main, schemaName, tableName );

                            for( int k = 0; k < csvDataArrayList.size(); k++ )
                                csvColArrayList.add( csvDataArrayList.get( k ).get( i ) );

                            mergeStatements.addNew( colName, dataTypeArrayList.get( i ), csvColArrayList );
                            mergeArrayList.add( mergeStatements );

                            /* Added the iterated table to the list of tables used to create MergeStatements */
                            existingTables.add( tableName );
                        }

                        /* Add a new column to the existing MergeStatements for the iterated table */
                        else {
                            int tableIndex = existingTables.indexOf( tableName );

                            for( int k = 0; k < csvDataArrayList.size(); k++ )
                                csvColArrayList.add( csvDataArrayList.get( k ).get( i ) );

                            mergeStatements = mergeArrayList.get( tableIndex );
                            mergeStatements.addNew( colName, dataTypeArrayList.get( i ), csvColArrayList );
                        }
                    }
                }
            }
        }

        SQLScript mergeScript = new SQLScript();

        for( int i = 0; i < mergeArrayList.size(); i++ )
            mergeScript.add( mergeArrayList.get( i ) );

        return mergeScript;
    }

    /* Generate an Insert/Merge Plan and return it as a Plan object */
    public Plan getPlan() throws ClassNotFoundException, ColumnNotConfiguredException, SQLException {
        plan = new Plan( main );

        /* Iterate through each column in the CSV Data Snapshot */
        for( int i = 0; i < columnArrayList.size(); i++ ) {

            /* If the 'Do not Load this Column' checkbox is unchecked */
            if( !includeArrayList.get( i ) ) {

                /* This is to indicate if the Column List in Column Config is populated. */
                sqlColAssignQuantity = sqlArrayList.get( i ).size();

                /* If Column List is not populated, throw an Exception. */
                if( sqlColAssignQuantity == 0 )
                    throw new ColumnNotConfiguredException( main, columnArrayList.get( i ) );

                /*
                    If Column List is populated, use it to override the information entered in the Schema,
                    Table, and Column Text Fields.
                 */
                else {
                    sqlColArrayList = sqlArrayList.get( i );

                    for( int j = 0; j < sqlColAssignQuantity; j++ ) {
                        listReader = new SQLListReader( sqlColArrayList.get( j ) );
                        plan.addPlan( listReader.getSchema(), listReader.getTable(), listReader.getColumn() );
                    }
                }
            }
        }

        return plan;
    }
    
    /* Initialize Null/Not Null and Data Types based on the data inputted */
    private void initArrayLists() {
        int[] maxLength = new int[columnArrayList.size()];             // The maximum VARCHAR2 length found in a column
        int[] maxPrecision = new int[columnArrayList.size()];          // The maximum NUMBER precision found in a column
        int[] maxScale = new int[columnArrayList.size()];              // The maximum NUMBER scale found in a column
        boolean[] isNotNumber = new boolean[columnArrayList.size()];   // Indicate if the program should still check if the column is of NUMBER data type
        boolean[] isNotDate = new boolean[columnArrayList.size()];     // Indicate if the program should still check if the column is of DATE data type
        String dateType;                                            // Indicates the Database date format: DD-MON-YY or MM/DD/YY
        
        /* Iterate through each table row */
        for( int i = 0; i < csvDataArrayList.size(); i++ ) {
            ArrayList<String> colArrayList = csvDataArrayList.get( i );
            
            /* Iterate through each table column */
            for( int j = 0; j < colArrayList.size(); j++ ) {
                String column = colArrayList.get( j );
                
                /* If one of the row contains empty string, the column's NULL/NOT NULL property is NULL */
                if( column == null )
                    nullArrayList.set( j, true );
                else if( column.equals( "" ) )
                    nullArrayList.set( j, true );
                else {
                    
                    /* If one of the row can not be parsed to Double, the column is not NUMBER */
                    try {
                        Double.parseDouble( column );
                    }
                    catch( NumberFormatException e ) {
                        isNotNumber[j] = true;
                    }
                    
                    /* Get the precision and scale of a NUMBER */
                    if( !isNotNumber[j] ) {
                        int length;
                        int periodPos = column.indexOf( "." );
                        int precision;
                        int scale = 0;
                        
                        if( periodPos == -1 ) {
                            precision = column.length();
                        }
                        else {
                            precision = column.length() - 1;
                            scale = column.substring( periodPos + 1 ).length();
                        }
                        
                        length = column.length();

                        if( length > maxLength[j] )
                            maxLength[j] = length;
                        
                        if( precision > maxPrecision[j] )
                            maxPrecision[j] = precision;

                        if( scale > maxScale[j] )
                            maxScale[j] = scale;

                        if( maxPrecision[j] < 39 && maxScale[j] < 128 )
                            dataTypeArrayList.set( j, numberDataType + "( " + maxPrecision[j] + ", " + maxScale[j] + " )" );
                        else
                            isNotNumber[j] = true;
                        
                        isNotDate[j] = true;
                    }
                    
                    /* Get the Date Format of a DATE, set the data type to VARCHAR2 if it's not a DATE */
                    else if( !isNotDate[j] ) {
                        isNotDate[j] = true;
                        dateFormats = new String[] { "dd-MMM-yy", "MM/dd/yy" };
                        
                        for( int k = 0; k < dateFormats.length; k++ ) {
                            dateFormat = new SimpleDateFormat( dateFormats[k] );
                            
                            try {
                                date = dateFormat.parse( column );

                                if( dateFormats[k].equals( "dd-MMM-yy" ) ) {
                                    if( main.dbType.equals( "PostgreSQL" ) ) {
                                        if( column.lastIndexOf( "-" ) == ( column.length() - 5 ) )
                                            dateType = "DD-Mon-YYYY";
                                        else
                                            dateType = "DD-Mon-YY";
                                    }
                                    else
                                        dateType = dmyFormat;
                                }
                                else {
                                    if( main.dbType.equals( "PostgreSQL" ) ) {
                                        if( column.lastIndexOf( "/" ) == ( column.length() - 5 ) )
                                            dateType = "MM/DD/YYYY";
                                        else
                                            dateType = "MM/DD/YY";
                                    }
                                    else
                                        dateType = mdyFormat;
                                }

                                dataTypeArrayList.set( j, "DATE " + "(" + dateType + ")" );
                                isNotDate[j] = false;
                                break;
                            }
                            catch( ParseException pe ) {}
                        }
                    }
                        
                    /* Get the maximum length of a VARCHAR2 */
                    if( isNotNumber[j] && isNotDate[j] ) {
                        int length = column.length();

                        if( length > maxLength[j] )
                            maxLength[j] = length;

                        if( maxLength[j] < 32768 )
                            dataTypeArrayList.set( j, charDataType + "( " + maxLength[j] + " )" );
                        else {
                            dataTypeArrayList.set( j, "INVALID: " + charDataType + " Length > 32767" );
                            includeArrayList.set( j, true );
                        }
                    }
                }
            }
        }
        
        /* Handle an empty column */
        for( int i = 0; i < dataTypeArrayList.size(); i++ )
            if( dataTypeArrayList.get( i ) == null ) {
                dataTypeArrayList.set( i, "INVALID: No Data Found" );
                includeArrayList.set( i, true );
            }
    }

    /*
        Initializes Data Types and Date Formats that are used by the Database flavor that CSV Loader is
        connected to.
     */
    private void initDBDataTypesAndFormats() {
        if( main.dbType.equals( "Oracle" ) ) {
            charDataType = "VARCHAR2";
            numberDataType = "NUMBER";
            dmyFormat = "DD-MON-RRRR";
            mdyFormat = "MM/DD/RRRR";
        }
        else if( main.dbType.equals( "MySQL" ) ) {
            charDataType = "VARCHAR";
            numberDataType = "NUMERIC";
            dmyFormat = "%d-%b-%Y";
            mdyFormat = "%m/%d/%Y";
        }
        else {
            charDataType = "CHARACTER VARYING";
            numberDataType = "NUMERIC";
        }
    }
    
    private Date date;                                      /* A dummy holder of a value that is parsed into a Date type.  If the value can not be parsed into a Date, then it will be declared as a VARCHAR2 */
    private int sqlColAssignQuantity;                       /* The number of items in the Column List within the Column Config */
    private Main main;                                      /* The Main class of CSV Loader */
    public Plan plan;
    private SimpleDateFormat dateFormat;                    /* Contains possible date formats that should be recognized as a DATE type */
    private SQLListReader listReader;                       /* The Reader of Column List */
    private String charDataType;                            /* How the String data type of the Database is called */
    private String[] dateFormats;                           /* A collection of dateFormats */
    private String dmyFormat;                               /* The 1st possible Database date format (DD-MON-YYYY) */
    private String mdyFormat;                               /* The 2nd possible Database date format (MM/DD/YYYY) */
    private String numberDataType;                          /* How the Number data type of the Database is called */
    public ArrayList<ArrayList<String>> csvDataArrayList;   /* Contains data in the CSV Data Snapshot */
    public ArrayList<String> columnArrayList;               /* Contains Database columns that are entered into Column Text Fields */
    public ArrayList<String> dataTypeArrayList;             /* Contains Data Types of the Database columns from the CSV Data Snapshot */
    public ArrayList<Boolean> nullArrayList;                /* Contains NULL/NOT NULL Ind of the Database columns from the CSV Data Snapshot */
    public ArrayList<Boolean> includeArrayList;             /* Contains the states of 'Do not Load into Column' checkboxes */
    private ArrayList<String> sqlColArrayList;              /* Contains the Column List entries of a Database Column */
    public ArrayList<ArrayList<String>> sqlArrayList;       /* Contains the Column List entries of all Database columns listed in the CSV Data Snapshot */
}
