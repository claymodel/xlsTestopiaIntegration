package jp.elias.xls.panel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import jp.elias.xls.form.Main;
import jp.elias.xls.form.TNSExceptionFrame;
import jp.elias.xls.tns.TNSException;
import jp.elias.xls.tns.TNSNamesReader;
import jp.elias.xls.tns.TNSNamesWriter;

public class TNSPanel extends javax.swing.JPanel {

    /** Creates new form TNSPanel */
    public TNSPanel( Main main ) {
        this.main = main;
        initComponents();
        initColumnNames();
        initTNSTable();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tnsScrollPane = new javax.swing.JScrollPane();
        tnsTable = new javax.swing.JTable();
        closeLabel = new javax.swing.JLabel();
        saveLabel = new javax.swing.JLabel();
        resetLabel = new javax.swing.JLabel();
        addLabel = new javax.swing.JLabel();
        removeLabel = new javax.swing.JLabel();

        tnsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TNS Name", "Host", "Port", "SID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tnsScrollPane.setViewportView(tnsTable);

        closeLabel.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        closeLabel.setForeground(new java.awt.Color(0, 102, 255));
        closeLabel.setText("Close");
        closeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeLabelMouseExited(evt);
            }
        });

        saveLabel.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        saveLabel.setForeground(new java.awt.Color(0, 102, 255));
        saveLabel.setText("Save");
        saveLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                saveLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                saveLabelMouseExited(evt);
            }
        });

        resetLabel.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        resetLabel.setForeground(new java.awt.Color(0, 102, 255));
        resetLabel.setText("Reset");
        resetLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resetLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                resetLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                resetLabelMouseExited(evt);
            }
        });

        addLabel.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        addLabel.setForeground(new java.awt.Color(0, 102, 255));
        addLabel.setText("Add");
        addLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addLabelMouseExited(evt);
            }
        });

        removeLabel.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        removeLabel.setForeground(new java.awt.Color(0, 102, 255));
        removeLabel.setText("Remove");
        removeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                removeLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                removeLabelMouseExited(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(284, 284, 284)
                        .add(saveLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(154, 154, 154)
                        .add(resetLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(269, 269, 269))
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(tnsScrollPane))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, closeLabel)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .add(addLabel)
                                .add(36, 36, 36)
                                .add(removeLabel)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(closeLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(tnsScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(addLabel)
                    .add(removeLabel))
                .add(12, 12, 12)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(saveLabel)
                    .add(resetLabel))
                .add(59, 59, 59))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void closeLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeLabelMouseClicked
        closeLabel.setForeground( main.red );

        main.tnsMenu.setEnabled( false );
        main.tnsMenuItem.setEnabled( true );
        main.welcomePanel.tnsLabel.setEnabled( true );
        main.tabbedPane.remove( this );
}//GEN-LAST:event_closeLabelMouseClicked

    private void closeLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeLabelMouseExited
        closeLabel.setForeground( main.blue );
}//GEN-LAST:event_closeLabelMouseExited

    private void closeLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeLabelMouseEntered
        closeLabel.setForeground( main.black );
}//GEN-LAST:event_closeLabelMouseEntered

    private void saveLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveLabelMouseClicked
        saveLabel.setForeground( main.red );

        try {
            new TNSNamesWriter( ( (DefaultTableModel)tnsTable.getModel() ).getDataVector() );
            JOptionPane.showMessageDialog( null, "Successfully saved the TNS Configuration in CSV Loader's TNSNAMES.ORA", "Save Result", JOptionPane.INFORMATION_MESSAGE );
        } catch( Exception e ) {
            JOptionPane.showMessageDialog( null, e.getMessage(), "Input/Output Error", JOptionPane.ERROR_MESSAGE );
        }
}//GEN-LAST:event_saveLabelMouseClicked

    private void saveLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveLabelMouseExited
        saveLabel.setForeground( main.blue );
}//GEN-LAST:event_saveLabelMouseExited

    private void saveLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveLabelMouseEntered
        saveLabel.setForeground( main.black );
}//GEN-LAST:event_saveLabelMouseEntered

    private void resetLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetLabelMouseClicked
        resetLabel.setForeground( main.red );
        initTNSTable();
}//GEN-LAST:event_resetLabelMouseClicked

    private void resetLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetLabelMouseExited
        resetLabel.setForeground( main.blue );
}//GEN-LAST:event_resetLabelMouseExited

    private void resetLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetLabelMouseEntered
        resetLabel.setForeground( main.black );
}//GEN-LAST:event_resetLabelMouseEntered

    private void addLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addLabelMouseClicked
        addLabel.setForeground( main.red );

        ( (DefaultTableModel)tnsTable.getModel() ).addRow( new Object[] { null, null, null, null } );
}//GEN-LAST:event_addLabelMouseClicked

    private void addLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addLabelMouseExited
        addLabel.setForeground( main.blue );
}//GEN-LAST:event_addLabelMouseExited

    private void addLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addLabelMouseEntered
        addLabel.setForeground( main.black );
}//GEN-LAST:event_addLabelMouseEntered

    private void removeLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeLabelMouseClicked
        removeLabel.setForeground( main.red );

        selectedRows = tnsTable.getSelectedRows();
        tableModel = (DefaultTableModel)tnsTable.getModel();

        for( int i = selectedRows.length - 1; i > -1; i-- )
            tableModel.removeRow( selectedRows[i] );
}//GEN-LAST:event_removeLabelMouseClicked

    private void removeLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeLabelMouseExited
        removeLabel.setForeground( main.blue );
}//GEN-LAST:event_removeLabelMouseExited

    private void removeLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeLabelMouseEntered
        removeLabel.setForeground( main.black );
}//GEN-LAST:event_removeLabelMouseEntered

    public void appendTNSTableContent( Vector<Vector> tnsVector ) {
        Vector<Vector> initialVector = ( (DefaultTableModel)tnsTable.getModel() ).getDataVector();
        initialVector.addAll( tnsVector );
        tableModel = new DefaultTableModel( initialVector, new Vector( colNameVector ) ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            @Override
			public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        };
        
        remodelTNSTable();
    }
    
    private void initColumnNames() {
        colNameVector = new Vector<String>( 4 );
        colNameVector.add( "TNS Name" );
        colNameVector.add( "Host" );
        colNameVector.add( "Port" );
        colNameVector.add( "SID" );
    }
    
    public void initTNSTable() {
        try {
            tnsNamesReader = new TNSNamesReader( main, new File( "tnsnames.ora" ) ); // Digest selected ORA file
            setTNSTableContent( tnsNamesReader.getTNSVector() );
        }
        catch( FileNotFoundException fe ) {}
        catch( IOException ie ) {
            JOptionPane.showMessageDialog( null, ie.getMessage(), "Input/Output Error", JOptionPane.ERROR_MESSAGE );
        }
        catch( TNSException te ) {
            main.setEnabled( false );
            new TNSExceptionFrame( main ).setVisible( true );
        }
    }
    
    private void remodelTNSTable() {
        tnsTable.setModel( tableModel );
        
        tnsTableHeader = tnsTable.getTableHeader();
        tnsTableHeader.setReorderingAllowed( false );
        columnModel = (DefaultTableColumnModel)tnsTableHeader.getColumnModel();
        tnsNameCol = columnModel.getColumn( 0 );
        hostCol = columnModel.getColumn( 1 );
        portCol = columnModel.getColumn( 2 );
        sidCol = columnModel.getColumn( 3 );
        tnsNameCol.setPreferredWidth( 125 );
        hostCol.setPreferredWidth( 125 );
        portCol.setPreferredWidth( 25 );
        sidCol.setPreferredWidth( 25 );
    }
    
    public void setTNSTableContent( Vector tnsVector ) {
        tableModel = new DefaultTableModel( new Vector( tnsVector ), new Vector( colNameVector ) ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            @Override
			public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        };
        
        remodelTNSTable();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addLabel;
    private javax.swing.JLabel closeLabel;
    private javax.swing.JLabel removeLabel;
    private javax.swing.JLabel resetLabel;
    private javax.swing.JLabel saveLabel;
    public javax.swing.JScrollPane tnsScrollPane;
    public javax.swing.JTable tnsTable;
    // End of variables declaration//GEN-END:variables

    private DefaultTableColumnModel columnModel;
    private DefaultTableModel tableModel;
    private int[] selectedRows;
    private JTableHeader tnsTableHeader;
    private Main main;
    private TableColumn tnsNameCol;
    private TableColumn hostCol;
    private TableColumn portCol;
    private TableColumn sidCol;
    private TNSNamesReader tnsNamesReader;
    private Vector<String> colNameVector;
}
