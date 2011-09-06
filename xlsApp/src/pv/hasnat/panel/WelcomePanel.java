/*
 * WelcomePanel.java
 *
 * --- Last Update 6/19/2010 9:13 PM ---
 *
 * Update Notes 6/19/2010 9:13 PM by Adrian Wijasa:
 * When "Import a CSV File into Database" is clicked, request a DB Connection credentials and a CSV File to
 * import right away.
 *
 * Update Notes 6/16/2010 10:30 PM by Adrian Wijasa:
 * Changed variable names and texts so that it doesn't specifically mentions Oracle.
 *
 * Update Notes 5/14/2010 6:27 PM by Adrian Wijasa:
 * Displays DB Type in the Connection Parameters section.
 *
 * Created on February 28, 2007, 2:25 PM
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

package pv.hasnat.panel;

import pv.hasnat.form.DBLoginFrame;
import pv.hasnat.form.Main;

/**
 * The content of Welcome tab.
 *
 * @author matianyuan
 */
public class WelcomePanel extends javax.swing.JPanel {

    /** Creates new form WelcomePanel */
    public WelcomePanel( Main main ) {
        this.main = main;
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

    	xlsLabel = new javax.swing.JLabel();
        csvLabel = new javax.swing.JLabel();
        connLabel = new javax.swing.JLabel();
        tnsLabel = new javax.swing.JLabel();
        connPanel = new javax.swing.JPanel();
        userLabel = new javax.swing.JLabel();
        hostLabel = new javax.swing.JLabel();
        portLabel = new javax.swing.JLabel();
        sidLabel = new javax.swing.JLabel();
        colonLabel1 = new javax.swing.JLabel();
        colonLabel2 = new javax.swing.JLabel();
        colonLabel3 = new javax.swing.JLabel();
        colonLabel4 = new javax.swing.JLabel();
        colonLabel5 = new javax.swing.JLabel();
        userValueLabel = new javax.swing.JLabel();
        hostValueLabel = new javax.swing.JLabel();
        portValueLabel = new javax.swing.JLabel();
        sidValueLabel = new javax.swing.JLabel();
        dbLabel = new javax.swing.JLabel();
        dbValueLabel = new javax.swing.JLabel();

        xlsLabel.setFont(new java.awt.Font("Dialog", 1, 11));
        xlsLabel.setForeground(new java.awt.Color(0, 102, 255));
        xlsLabel.setText("Import XLS File into Database");
        xlsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
                csvLabelMouseClicked(evt);
            }
            @Override
			public void mouseEntered(java.awt.event.MouseEvent evt) {
                csvLabelMouseEntered(evt);
            }
            @Override
			public void mouseExited(java.awt.event.MouseEvent evt) {
                csvLabelMouseExited(evt);
            }
        });
        
        
        
        csvLabel.setFont(new java.awt.Font("Dialog", 1, 11));
        csvLabel.setForeground(new java.awt.Color(0, 102, 255));
        csvLabel.setText("Import XLS File into Database");
        csvLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
                csvLabelMouseClicked(evt);
            }
            @Override
			public void mouseEntered(java.awt.event.MouseEvent evt) {
                csvLabelMouseEntered(evt);
            }
            @Override
			public void mouseExited(java.awt.event.MouseEvent evt) {
                csvLabelMouseExited(evt);
            }
        });

        connLabel.setFont(new java.awt.Font("Dialog", 1, 11));
        connLabel.setForeground(new java.awt.Color(0, 102, 255));
        connLabel.setText("DB Login");
        connLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
                connLabelMouseClicked(evt);
            }
            @Override
			public void mouseEntered(java.awt.event.MouseEvent evt) {
                connLabelMouseEntered(evt);
            }
            @Override
			public void mouseExited(java.awt.event.MouseEvent evt) {
                connLabelMouseExited(evt);
            }
        });

        tnsLabel.setFont(new java.awt.Font("Dialog", 1, 11));
        tnsLabel.setForeground(new java.awt.Color(0, 102, 255));
        tnsLabel.setText("DB Settings");
        tnsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
                tnsLabelMouseClicked(evt);
            }
            @Override
			public void mouseEntered(java.awt.event.MouseEvent evt) {
                tnsLabelMouseEntered(evt);
            }
            @Override
			public void mouseExited(java.awt.event.MouseEvent evt) {
                tnsLabelMouseExited(evt);
            }
        });

        connPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Database Connection Parameters")))));

        userLabel.setText("User");
        userLabel.setPreferredSize(new java.awt.Dimension(50, 14));

        hostLabel.setText("Host");
        hostLabel.setPreferredSize(new java.awt.Dimension(50, 14));

        portLabel.setText("Port");
        portLabel.setPreferredSize(new java.awt.Dimension(50, 14));

        sidLabel.setText("SID");
        sidLabel.setPreferredSize(new java.awt.Dimension(50, 14));

        colonLabel1.setText(": ");

        colonLabel2.setText(": ");

        colonLabel3.setText(": ");

        colonLabel4.setText(": ");

        colonLabel5.setText(": ");

        userValueLabel.setText(" ");

        hostValueLabel.setText(" ");

        portValueLabel.setText(" ");

        sidValueLabel.setText(" ");

        dbLabel.setText("DB");

        org.jdesktop.layout.GroupLayout connPanelLayout = new org.jdesktop.layout.GroupLayout(connPanel);
        connPanel.setLayout(connPanelLayout);
        connPanelLayout.setHorizontalGroup(
            connPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(connPanelLayout.createSequentialGroup()
                .add(connPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(connPanelLayout.createSequentialGroup()
                        .add(userLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(colonLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(userValueLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
                    .add(connPanelLayout.createSequentialGroup()
                        .add(hostLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(colonLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(hostValueLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
                    .add(connPanelLayout.createSequentialGroup()
                        .add(portLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(colonLabel3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(portValueLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
                    .add(connPanelLayout.createSequentialGroup()
                        .add(connPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, dbLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, sidLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(connPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(connPanelLayout.createSequentialGroup()
                                .add(colonLabel4)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(sidValueLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
                            .add(connPanelLayout.createSequentialGroup()
                                .add(colonLabel5)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(dbValueLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        connPanelLayout.setVerticalGroup(
            connPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(connPanelLayout.createSequentialGroup()
                .add(connPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(userLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(colonLabel1)
                    .add(userValueLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(connPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(hostLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(colonLabel2)
                    .add(hostValueLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(connPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(portLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(colonLabel3)
                    .add(portValueLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(connPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(sidLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(colonLabel4)
                    .add(sidValueLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(connPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(dbLabel)
                    .add(colonLabel5)
                    .add(dbValueLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(csvLabel)
                .addContainerGap(627, Short.MAX_VALUE))
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(connLabel)
                .addContainerGap(566, Short.MAX_VALUE))
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(tnsLabel)
                .addContainerGap(674, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(517, Short.MAX_VALUE)
                .add(connPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(csvLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(connLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(tnsLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 324, Short.MAX_VALUE)
                .add(connPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void csvLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_csvLabelMouseClicked
        csvLabel.setForeground( main.red );

        if( csvLabel.isEnabled() ) {
            main.addCSVTab();
            main.checkConnection( DBLoginFrame.UPLOAD_CSV_DATA_SNAPSHOT );
        }
}//GEN-LAST:event_csvLabelMouseClicked

    private void csvLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_csvLabelMouseExited
        csvLabel.setForeground( main.blue );
}//GEN-LAST:event_csvLabelMouseExited

    private void csvLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_csvLabelMouseEntered
        csvLabel.setForeground( main.black );
}//GEN-LAST:event_csvLabelMouseEntered

    private void connLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_connLabelMouseClicked
        connLabel.setForeground( main.red );

        main.popUpDBLogin( DBLoginFrame.DO_NOTHING );
}//GEN-LAST:event_connLabelMouseClicked

    private void connLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_connLabelMouseExited
        connLabel.setForeground( main.blue );
}//GEN-LAST:event_connLabelMouseExited

    private void connLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_connLabelMouseEntered
        connLabel.setForeground( main.black );
}//GEN-LAST:event_connLabelMouseEntered

    private void tnsLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tnsLabelMouseClicked
        tnsLabel.setForeground( main.red );

        if( tnsLabel.isEnabled() )
            main.addTNSTab();
}//GEN-LAST:event_tnsLabelMouseClicked

    private void tnsLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tnsLabelMouseExited
        tnsLabel.setForeground( main.blue );
}//GEN-LAST:event_tnsLabelMouseExited

    private void tnsLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tnsLabelMouseEntered
        tnsLabel.setForeground( main.black );
}//GEN-LAST:event_tnsLabelMouseEntered

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel colonLabel1;
    private javax.swing.JLabel colonLabel2;
    private javax.swing.JLabel colonLabel3;
    private javax.swing.JLabel colonLabel4;
    private javax.swing.JLabel colonLabel5;
    public javax.swing.JLabel connLabel;
    private javax.swing.JPanel connPanel;
    public javax.swing.JLabel xlsLabel;
    public javax.swing.JLabel csvLabel;
    private javax.swing.JLabel dbLabel;
    public javax.swing.JLabel dbValueLabel;
    private javax.swing.JLabel hostLabel;
    public javax.swing.JLabel hostValueLabel;
    private javax.swing.JLabel portLabel;
    public javax.swing.JLabel portValueLabel;
    private javax.swing.JLabel sidLabel;
    public javax.swing.JLabel sidValueLabel;
    public javax.swing.JLabel tnsLabel;
    private javax.swing.JLabel userLabel;
    public javax.swing.JLabel userValueLabel;
    // End of variables declaration//GEN-END:variables

    private Main main;
}