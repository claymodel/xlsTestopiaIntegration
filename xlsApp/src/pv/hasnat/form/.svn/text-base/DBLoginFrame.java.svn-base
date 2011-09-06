/*
 * DBLoginFrame.java
 *
 * --- Last Update: 8/10/2010 3:37 PM ---
 *
 * Update Notes 8/10/2010 3:37 PM by Adrian Wijasa:
 * Displays an Installation Error if the installation steps in http://csvloader.sourceforge.net haven't been performed.
 *
 * Update Notes 6/20/2010 3:52 PM by Adrian Wijasa:
 * Changed the uses of word Oracle into Database since CSV Loader now works with MySQL and PostgreSQL.
 *
 * Update Notes 6/19/2010 6:07 PM by Adrian Wijasa:
 * Now only two things can be done after DBLoginFrame login is successful: DO_NOTHING or
 * UPLOAD_CSV_DATA_SNAPSHOT.
 *
 * Update Notes 6/18/2010 9:21 PM by Adrian Wijasa:
 * Changed the name of this class from OraLoginFrame to DBLoginFrame.
 * Loading the CSV Data Snapshot will now require Login.  This is needed to determine the DB Data Types that
 * are going to be used.
 *
 * Update Notes 5/13/2010 6:25 PM by Adrian Wijasa:
 * Now also processes DB Type info.
 *
 * Update Notes 5/11/2010 12:07 AM by Adrian Wijasa:
 * Now also catch ClassNotFoundException thrown by ConnectionTester.
 *
 * Update Notes 4/18/2010 12:07 PM by Adrian Wijasa:
 * This class is made to work with Java 5.
 *
 * Update Notes 4/15/2010 11:02 PM by Adrian Wijasa:
 * Pop Up a Plan Window instead of Saving a Plan into a file.
 *
 * Update Notes 3/22/2010 6:50 PM by Adrian Wijasa:
 * Modified the text of userLabel from "Username" to "User" so that it will not be cut off in Linux.
 *
 * Update Notes 1/31/2010 12:05 AM by Adrian Wijasa:
 * Added a code to invoke FileChooserFrame task: SAVE_SQL_MERGE when toDoAfterLogin value is CREATE_SQL_MERGE.
 *
 * Update Notes 1/30/2010 11:34 PM by Adrian Wijasa:
 * Added CREATE_SQL_MERGE, as an identifier to direct DBLoginFrame to save an SQL script that contains MERGE
 * statements.
 *
 * Created on February 28, 2007, 3:28 PM
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

package pv.hasnat.form;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;

import pv.hasnat.sql.ConnectionTester;
import pv.hasnat.sql.MetaDataQuery;
import pv.hasnat.sql.TableNotFoundException;
import pv.hasnat.tns.TNSException;
import pv.hasnat.tns.TNSNamesReader;

/**
 * Database Authentication Window
 * 
 * @author matianyuan
 */
public class DBLoginFrame extends javax.swing.JFrame {

    /** Creates new form DBLoginFrame */
    public DBLoginFrame( Main main, int toDoAfterLogin ) {
        this.toDoAfterLogin = toDoAfterLogin;
        this.main = main;
        enableMain = true;
        initComponents();

        try {
            initComboBox();

            /* Make this window appears in the middle of the Main Window */
            setLocation( main.getX() + 291, main.getY() + 233 );
            setVisible( true );
        }
        catch( FileNotFoundException fe ) {
            JOptionPane.showMessageDialog( null, fe.getMessage(), "File Not Found", JOptionPane.ERROR_MESSAGE );
            dispose();
        }
        catch( IOException ie ) {
            JOptionPane.showMessageDialog( null, ie.getMessage(), "Input/Output Error", JOptionPane.ERROR_MESSAGE );
            dispose();
        }
        catch( TNSException te ) {
            main.setEnabled( false );
            new TNSExceptionFrame( main ).setVisible( true );
            dispose();
        }
    }

    public DBLoginFrame( Main main, int toDoAfterLogin, int sourceColIndex, int sourceType ) {
        this.toDoAfterLogin = toDoAfterLogin;
        this.sourceColIndex = sourceColIndex;
        this.sourceType = sourceType;
        this.main = main;
        enableMain = true;
        initComponents();

        try {
            initComboBox();

            /* Make this window appears in the middle of the Main Window */
            setLocation( main.getX() + 291, main.getY() + 233 );
            setVisible( true );
        }
        catch( FileNotFoundException fe ) {
            JOptionPane.showMessageDialog( null, fe.getMessage(), "File Not Found", JOptionPane.ERROR_MESSAGE );
            dispose();
        }
        catch( IOException ie ) {
            JOptionPane.showMessageDialog( null, ie.getMessage(), "Input/Output Error", JOptionPane.ERROR_MESSAGE );
            dispose();
        }
        catch( TNSException te ) {
            main.setEnabled( false );
            new TNSExceptionFrame( main ).setVisible( true );
            dispose();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        userLabel = new javax.swing.JLabel();
        pwdLabel = new javax.swing.JLabel();
        dbLabel = new javax.swing.JLabel();
        colonLabel1 = new javax.swing.JLabel();
        colonLabel2 = new javax.swing.JLabel();
        colonLabel3 = new javax.swing.JLabel();
        userTextField = new javax.swing.JTextField();
        pwdPasswordField = new javax.swing.JPasswordField();
        dbComboBox = new javax.swing.JComboBox();
        loginLabel = new javax.swing.JLabel();
        errLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Authentication");
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
			public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        userLabel.setText("User");
        userLabel.setPreferredSize(new java.awt.Dimension(60, 14));

        pwdLabel.setText("Password");
        pwdLabel.setPreferredSize(new java.awt.Dimension(60, 14));

        dbLabel.setText("SID");
        dbLabel.setPreferredSize(new java.awt.Dimension(60, 14));

        colonLabel1.setText(": ");

        colonLabel2.setText(": ");

        colonLabel3.setText(": ");

        userTextField.setPreferredSize(new java.awt.Dimension(120, 20));

        pwdPasswordField.setPreferredSize(new java.awt.Dimension(120, 20));

        loginLabel.setFont(new java.awt.Font("Dialog", 1, 11));
        loginLabel.setForeground(new java.awt.Color(0, 102, 255));
        loginLabel.setText("login");
        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginLabelMouseClicked(evt);
            }
            @Override
			public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginLabelMouseEntered(evt);
            }
            @Override
			public void mouseExited(java.awt.event.MouseEvent evt) {
                loginLabelMouseExited(evt);
            }
        });

        errLabel.setForeground(new java.awt.Color(255, 0, 0));

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, errLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(userLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(colonLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(userTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(pwdLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(colonLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(pwdPasswordField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(dbLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(colonLabel3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(10, 10, 10)
                                .add(loginLabel))
                            .add(dbComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(userLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(colonLabel1)
                    .add(userTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(pwdLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(colonLabel2)
                    .add(pwdPasswordField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(dbLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(colonLabel3)
                    .add(dbComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(loginLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(errLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginLabelMouseClicked
        /* Login using the selected TNS and other inputted parameters */
        loginLabel.setForeground( main.red );

        colVector = (Vector)rowVector.get( dbComboBox.getSelectedIndex() );

        String user = userTextField.getText();
        String password = new String( pwdPasswordField.getPassword() );
        String host = (String)colVector.get( 1 );
        int port = (Integer)colVector.get( 2 );
        String sid = (String)colVector.get( 3 );
        String dbType = "";

        try {
            ConnectionTester connTester = new ConnectionTester(
                    user
                    , password
                    , host
                    , port
                    , sid );

            dbType = connTester.getDBType();

            /*
                Check if the installation steps in http://csvloader.sourceforge.net have been performed by checking the existence of table:
                CSVLDR_TBL_DEPS.
             */
            metaDataQuery = new MetaDataQuery( user, password, host, port, sid, dbType );

            String installErr = "CSVLDR_TBL_DEPS is not found.\n" +
                "Please email to hasnat@pv.com";

                /**
            if( dbType.equals( "Oracle" ) ) {
                if( !metaDataQuery.getTables( null ).contains( "CSVLDR_TBL_DEPS" ) )
                    throw new TableNotFoundException( installErr );
            }
            else if( dbType.equals( "MySQL" ) ) {
                if( !metaDataQuery.getTables( null ).contains( "csvldr_tbl_deps" ) )
                    throw new TableNotFoundException( installErr );
            }
            else {
                if( !metaDataQuery.getTables( "csvldr" ).contains( "csvldr_tbl_deps" ) )
                    throw new TableNotFoundException( installErr );
            }
            */
            metaDataQuery.closeConnection();

            /* Finalize Login */
            main.setConnectionParams( user, password, host, port, sid, dbType );

            /* Perform the next step after Login */
            if( toDoAfterLogin == DO_NOTHING ) {
                dispose();
            } else if( toDoAfterLogin == UPLOAD_CSV_DATA_SNAPSHOT ) {
                enableMain = false;
                setVisible( false );
                main.popUpFileChooser( FileChooserFrame.UPLOAD_XLS );
                dispose();
            }
        }
        catch( ClassNotFoundException ce ) {
            try {
                metaDataQuery.closeConnection();
            }
            catch( SQLException e ) {}
            ce.printStackTrace();
            errLabel.setText( "Connection Attempt Failed" );
        }
        catch( SQLException se ) {
            try {
                metaDataQuery.closeConnection();
            }
            catch( SQLException e ) {}
            se.printStackTrace();
            errLabel.setText( "Connection Attempt Failed" );
        }
/**
        catch( TableNotFoundException te ) {
            try {
                metaDataQuery.closeConnection();
            }
            catch( SQLException e ) {}
            JOptionPane.showMessageDialog( null, te.getMessage(), "Installation Error", JOptionPane.ERROR_MESSAGE );
            errLabel.setText( "Connection Attempt Failed" );
        }
        */
}//GEN-LAST:event_loginLabelMouseClicked

    private void loginLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginLabelMouseExited
        loginLabel.setForeground( main.blue );
}//GEN-LAST:event_loginLabelMouseExited

    private void loginLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginLabelMouseEntered
        loginLabel.setForeground( main.black );
}//GEN-LAST:event_loginLabelMouseEntered

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        if( enableMain ) {
            main.setEnabled( true );
            main.requestFocus();
        }
    }//GEN-LAST:event_formWindowClosed

    /* Initialize TNS Combo Box */
    private void initComboBox() throws FileNotFoundException, IOException, TNSException {
        rowVector = new TNSNamesReader( main, new File( "tnsnames.ora" ) ).getTNSVector();

        for( int i = 0; i < rowVector.size(); i++ ) {
            colVector = (Vector)rowVector.get( i );
            dbComboBox.addItem( colVector.get( 0 ) );
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel colonLabel1;
    private javax.swing.JLabel colonLabel2;
    private javax.swing.JLabel colonLabel3;
    private javax.swing.JComboBox dbComboBox;
    private javax.swing.JLabel dbLabel;
    private javax.swing.JLabel errLabel;
    private javax.swing.JLabel loginLabel;
    private javax.swing.JLabel pwdLabel;
    private javax.swing.JPasswordField pwdPasswordField;
    private javax.swing.JLabel userLabel;
    private javax.swing.JTextField userTextField;
    // End of variables declaration//GEN-END:variables

    private boolean enableMain;
    private int toDoAfterLogin;
    private int sourceColIndex;
    private int sourceType;
    private Main main;
    private MetaDataQuery metaDataQuery;
    private Vector colVector;
    private Vector rowVector;
    public static final int DO_NOTHING = 0;
    public static final int UPLOAD_CSV_DATA_SNAPSHOT = 1;
}
