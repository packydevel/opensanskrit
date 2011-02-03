package org.opensanskrit.widget;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class TrayIcon extends java.awt.TrayIcon {
    private final Window owner;
    private JWindow windowMenu;
    private JPopupMenu menu;

    public TrayIcon(Window owner, Image image) {
        super(image);
        this.owner = owner;
        windowMenu = new JWindow(owner);
        windowMenu.setAlwaysOnTop(true);

        addMouseListener(new MouseAdapter()  {
            @Override
            public void mouseClicked(MouseEvent e) {
                SystemTray.getSystemTray().remove(TrayIcon.this);
                TrayIcon.this.owner.setVisible(true);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                showJPopupMenu(e);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                showJPopupMenu(e);
            }
        });
    }

    public JPopupMenu getJPopuMenu() {
        return menu;
    }

    public void setJPopuMenu(JPopupMenu jpm) {
        if (menu != null)
            menu.removePopupMenuListener(popupListener);            
        menu = jpm;
        menu.addPopupMenuListener(popupListener);
    }

    private void showJPopupMenu(MouseEvent e) {
        if (e.isPopupTrigger() && menu != null) {
            Dimension size = menu.getPreferredSize();
            windowMenu.setBounds(e.getX(), e.getY(), size.width, size.height);
            windowMenu.setVisible(true);
            menu.show(windowMenu.getContentPane(), 0, 0);
            windowMenu.toFront();
        }
    }
    
    private PopupMenuListener popupListener = new PopupMenuListener()  {
        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            SwingUtilities.invokeLater(new Runnable()  {
                @Override
                public void run() {
                    windowMenu.setVisible(false);
                }
            });
        }
        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
            SwingUtilities.invokeLater(new Runnable()  {
                @Override
                public void run() {
                    windowMenu.setVisible(false);
                }
            });
        }
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}
    };
}