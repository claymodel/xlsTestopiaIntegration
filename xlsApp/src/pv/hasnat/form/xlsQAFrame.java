
package pv.hasnat.form;

import pv.hasnat.csv.CSVReader;
import javax.swing.ButtonGroup;

public class xlsQAFrame extends javax.swing.JFrame {

    /** Creates new form ColTitleFrame5 */
    public xlsQAFrame( Main main ) {
        this.main = main;
        initComponents();
        setLocation( main.getX() + 268, main.getY() + 251 );
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        colTitleLabel = new javax.swing.JLabel();
        
        okLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Column TItle Naming");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        //HASNAT
        //main.fileChooser.getFile();
        
        colTitleLabel.setText("hostname: " + main.port + main.host + main.user + main.sid);

        okLabel.setFont(new java.awt.Font("Dialog", 1, 11));
        okLabel.setForeground(new java.awt.Color(0, 102, 255));
        okLabel.setText("OK");
        okLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                okLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                okLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                okLabelMouseExited(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(colTitleLabel)
                    .add(layout.createSequentialGroup()
                        .add(159, 159, 159)
                        .add(okLabel)))
                .addContainerGap(190, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(colTitleLabel)
                .add(54, 54, 54)
                .add(okLabel)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_okLabelMouseClicked
        okLabel.setForeground( main.red );
        main.fileChooser.disposeFileChooser();
        dispose();
}//GEN-LAST:event_okLabelMouseClicked

    private void okLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_okLabelMouseExited
        okLabel.setForeground( main.blue );
}//GEN-LAST:event_okLabelMouseExited

    private void okLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_okLabelMouseEntered
        okLabel.setForeground( main.black );
}//GEN-LAST:event_okLabelMouseEntered

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        main.fileChooser.setEnabled( true );
        main.fileChooser.requestFocus();
    }//GEN-LAST:event_formWindowClosed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel colTitleLabel;
    private javax.swing.JLabel okLabel;
    // End of variables declaration//GEN-END:variables

    private ButtonGroup buttonGroup;
    private Main main;
}
