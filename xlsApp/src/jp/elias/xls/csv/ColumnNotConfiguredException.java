package jp.elias.xls.csv;

import jp.elias.xls.form.Main;

public class ColumnNotConfiguredException extends Exception {

    public ColumnNotConfiguredException( Main main, String column ) {
        super( column + " has not been configured to go to any " + main.dbType + " column yet." );
    }
}
