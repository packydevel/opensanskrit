package org.opensanskrit.widget;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jfacility.java.awt.AWT;
import org.jfacility.java.lang.SystemProperty;
import org.jfacility.javax.swing.Swing;

/**
 *
 * @author luca
 */
public class SystemInfoDialog extends JDialog{
    
    private JTable jtable;
    private DefaultTableModel dtm;
    private JButton jbClose, jbCopy;
    
    public SystemInfoDialog(Frame owner, ArrayList<String[]> rows){
        super(owner, "Informazioni di sistema", ModalityType.DOCUMENT_MODAL);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setUndecorated(true);
        setPreferredSize(new Dimension(500, 250));
        getContentPane().setLayout(new BorderLayout());
        initDTM(rows);
        initTable();
        initButtons();
        pack();
    }
    
    private void initTable(){
        jtable = new JTable(dtm);
        jtable.setRowSelectionAllowed(false);
        jtable.getTableHeader().setReorderingAllowed(false);
        Swing.setTableDimensionLockColumn(jtable, 0, 120);
        getContentPane().add(new JScrollPane(jtable), BorderLayout.CENTER);
    }
    
    private void initDTM(ArrayList<String[]> rows){
        dtm = new DefaultTableModel(null, new String[]{"Informazione", "Valore"})  {
            Class[] types = new Class[]{String.class, String.class};
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
            @Override
            public boolean isCellEditable(int rowIndex, int vColIndex) {
                return false;
            }
        };
        dtm.addRow(new String[]{"Java version", SystemProperty.getVersion()});
        dtm.addRow(new String[]{"Java vendor", SystemProperty.getVendor()});
        dtm.addRow(new String[]{"Java Home", SystemProperty.getHome()});
        dtm.addRow(new String[]{"Sistema Operativo", SystemProperty.getOsName()});
        dtm.addRow(new String[]{"Versione SO", SystemProperty.getOsVersion()});
        dtm.addRow(new String[]{"Architettura SO", SystemProperty.getOsArchitecture()});
        dtm.addRow(new String[]{"Directory attuale", SystemProperty.getUserDir()});
        if (rows!=null){
            for (int r=0; r<rows.size(); r++)
                dtm.addRow(rows.get(r));
        }
    }
    
    private void initButtons(){
        JPanel pane = new JPanel();
        jbClose = new JButton("Chiudi");
        jbClose.setToolTipText("Chiude il popup");
        jbClose.addMouseListener(new MouseAdapter() {
        @Override
            public void mouseClicked(MouseEvent evt) {
                SystemInfoDialog.this.dispose();
            }
        });
        
        jbCopy = new JButton("Copia");
        jbCopy.setToolTipText("Copia le informazioni nella clipboard e chiude il popup");
        jbCopy.addMouseListener(new MouseAdapter() {
        @Override
            public void mouseClicked(MouseEvent evt) {
                copyIntoClipboard();
                SystemInfoDialog.this.dispose();
            }
        });
        pane.add(jbClose);
        pane.add(jbCopy);
        getContentPane().add(pane, BorderLayout.SOUTH);
    }
    
    private void copyIntoClipboard(){
        String text = "";
        for (int i=0; i<dtm.getRowCount(); i++)
            text += dtm.getValueAt(i, 0).toString() + ": " + 
                    dtm.getValueAt(i, 1).toString() + "\n";
        AWT.setClipboard(text);
    }
}