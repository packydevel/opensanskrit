package org.opensanskrit.widget;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import org.opensanskrit.widget.interfaces.ProgressListener;
/**
 * 
 * @author luca
 */
public class ProgressDialog extends JDialog {
    private JProgressBar bar;
    private JButton jbAbort;
    private static List<ProgressListener> progressListenerList = new ArrayList<ProgressListener>();

    public ProgressDialog(Frame owner, String title, int max) {
        super(owner, title, ModalityType.DOCUMENT_MODAL);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setUndecorated(true);
        setPreferredSize(new Dimension(300, 100));
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        bar = new JProgressBar(0, max);
        bar.setValue(0);
        bar.setStringPainted(true);

        JPanel pane = new JPanel();
        jbAbort = new JButton(" Abort ");
        jbAbort.setPreferredSize(new Dimension(60, 20));
        jbAbort.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jbAbort.addMouseListener(new MouseAdapter()  {
            @Override
            public void mouseClicked(MouseEvent evt) {
                fireKillProgress();
                ProgressDialog.this.dispose();
            }
        });
        pane.add(jbAbort);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(bar, BorderLayout.CENTER);
        getContentPane().add(pane, BorderLayout.SOUTH);
        pack();
        /*
         * Non è chiaro il motivo ma se la jdialog modale è resa visibile in un
         * thread a parte allora l'attività di aggiornamento sullo stato di
         * avanzamento della progress bar sarà visibile. Se non si utilizza un
         * thread a parte per rendere visibile la jdialog allora il fatto che la
         * jdialog è modale blocca tutte le attività.
         */
        Thread t = new Thread(new Runnable()  {
            @Override
            public void run() {
                ProgressDialog.this.setVisible(true);
            }
        });
        t.start();
    }

    public void setProgress(int progress) {
        bar.setValue(progress);
        if (progress == bar.getMaximum()) {
            dispose();
        }
    }

    public synchronized void addProgressListener(ProgressListener pl) {
        progressListenerList.add(pl);
    }

    private synchronized void fireKillProgress() {
        ProgressEvent event = new ProgressEvent(this);
        Iterator<ProgressListener> listeners = progressListenerList.iterator();
        while (listeners.hasNext()) {
            ProgressListener myel = listeners.next();
            myel.objReceived(event);
        }
    }
}