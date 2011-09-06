
package pv.hasnat.csv;

import pv.hasnat.form.Main;

/**
 * This exception is thrown if a CSV Column is not configured to go into any DB Column
 *
 * @author awijasa
 */
public class ColumnNotConfiguredException extends Exception {

    public ColumnNotConfiguredException( Main main, String column ) {
        super( column + " has not been configured to go to any " + main.dbType + " column yet." );
    }
}
