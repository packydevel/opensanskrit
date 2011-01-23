package org.opensanskrit.test;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.opensanskrit.widget.JXTrayIcon;

public class jxtrayicon {
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaBlackMoonLookAndFeel");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JXTrayIcon.createGui();
            }
        });
    }
}